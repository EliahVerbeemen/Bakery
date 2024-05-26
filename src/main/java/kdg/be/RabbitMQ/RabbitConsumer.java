package kdg.be.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import kdg.be.DTO.OrdersFromClientDTO;
import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;
import kdg.be.Models.Product;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import kdg.be.Services.BatchService;
import kdg.be.Services.ProductService;
import kdg.be.Services.PurchaseOrderManagerService;
import kdg.be.Services.PurchaseOrderProductManagerOrderProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RabbitConsumer {

    private final ProductService productService;
    private final BatchService batchService;
    private final PurchaseOrderManagerService purchaseOrderService;
    private final PurchaseOrderProductManagerOrderProductService purchaseOrderProductService;

    private final Logger logger = LoggerFactory.getLogger(RabbitConsumer.class);

    public RabbitConsumer(ProductService productService, BatchService batchService, PurchaseOrderManagerService purchaseOrderService, PurchaseOrderProductManagerOrderProductService purchaseOrderProductService) {
        this.productService = productService;
        this.batchService = batchService;
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderProductService = purchaseOrderProductService;
    }

    @RabbitListener(queues = "${queue.purchaseOrder}")
    public void ReceiveMessage(String purchaseOrderJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            PurchaseOrder purchaseOrder = objectMapper.readValue(purchaseOrderJson, PurchaseOrder.class);
            PurchaseOrder savedPurchaseOrder = purchaseOrderProductService.saveOrUpdateAllProductsOfOrder(purchaseOrder);
            PurchaseOrder savedPurchase = purchaseOrderService.saveOrUpdate(savedPurchaseOrder);
            Map<PurchaseProduct, Integer> productsQuantity = new HashMap<>();
            savedPurchase.getProducts().forEach((p -> {
                productsQuantity.put(p, p.getQuantity());
            }));
            addToBatch(productsQuantity);

        } catch (Exception ex) {
            logger.warn("Deserialiseren van een purchaseOrder niet gelukt" + ex.getMessage());
        }
    }

    private Optional<OrdersFromClientDTO> deserializeOrder(String order) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return Optional.ofNullable(objectMapper.readValue(order, OrdersFromClientDTO.class));

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            return Optional.of(new OrdersFromClientDTO());
        }

    }


    @RabbitListener(queues = {"receiveOrder"})
    public void receiveOrder(String incomingOrder) {
        Optional<OrdersFromClientDTO> optionalOrder = deserializeOrder(incomingOrder);
        if (optionalOrder.isPresent()) {
            OrdersFromClientDTO order = optionalOrder.get();
            Map<Product, Integer> productQuantityMap = new HashMap<>();
            for (Map.Entry<Long, Integer> keyValuePair : order.getProducts().entrySet()) {
                Optional<Product> optionaProduct = productService.getProductById(keyValuePair.getKey());
                if (optionaProduct.isPresent()) {
                    Product product = optionaProduct.get();
                    productQuantityMap.put(product, keyValuePair.getValue());
                }

            }
            addToBatch(productQuantityMap);
        }
    }

    private <T extends Product> void addToBatch(Map<T, Integer> products) {

        Batch batch = null;
        List<Batch> batchOptional = this.batchService.findBatchByState(BatchState.NOT_YET_PREPARED);
        if (batchOptional.size() == 1) {
            batch = batchOptional.get(0);
        } else {
            batch = batchService.save(new Batch());
        }
        for (Map.Entry<T, Integer> entry : products.entrySet()) {
            if (batch.getProductsinBatch().containsKey(entry.getKey())) {
                batch.getProductsinBatch().replace(entry.getKey(), (batch.getProductsinBatch().get(entry.getKey()) + entry.getValue()));
            } else {
                batch.getProductsinBatch().put(entry.getKey(), entry.getValue());
            }
            try {
                batchService.saveOrUpdate(batch);
            } catch (Exception ex) {
                logger.warn("Something went wrong while updating the batch");
            }
        }


    }


    @RabbitListener(queues = "IngredientsAvailableQueue", ackMode = "MANUAL")
    public void ReceiveBatch(Long batchId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("batch bevestigd");
        System.out.println(batchId);
        channel.basicAck(tag, false);


    }


}
