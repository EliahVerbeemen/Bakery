package kdg.be.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import kdg.be.DTO.OrdersFromClientDTO;
import kdg.be.Managers.BatchManager;
import kdg.be.Managers.ProductManager;
import kdg.be.Managers.PurchaseOrderManager;
import kdg.be.Managers.PurchaseOrderProductManager;
import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;
import kdg.be.Models.Product;
import kdg.be.Models.PurchaseOrder.PurchaseOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RabbitConsumer {

    private final ProductManager productManager;
    private final BatchManager batchManager;
    private final PurchaseOrderManager purchaseOrderManager;
    private final PurchaseOrderProductManager purchaseOrderProductManager;

    private final Logger logger=LoggerFactory.getLogger(RabbitConsumer.class);

    public RabbitConsumer(ProductManager productManager, BatchManager batchManager, PurchaseOrderManager purchaseOrderManager, PurchaseOrderProductManager purchaseOrderProductManager){
    this.productManager=productManager;
    this.batchManager=batchManager;
        this.purchaseOrderManager = purchaseOrderManager;
        this.purchaseOrderProductManager = purchaseOrderProductManager;
    }

    @RabbitListener(queues ="${queue.purchaseOrder}")
    public void ReceiveMessage(String purchaseOrderJson){

       ObjectMapper objectMapper=new ObjectMapper();
       try {
        PurchaseOrder purchaseOrder=   objectMapper.readValue(purchaseOrderJson, PurchaseOrder.class);
      PurchaseOrder purchaseOrder1= purchaseOrderManager.saveOrUpdate(purchaseOrder);
System.out.println(purchaseOrder.getPurchaseOrderNumber());
       }catch (Exception ex){

           logger.warn("Deserialiseren van een purchaseOrder niet gelukt"+ex.getMessage());

       }
    }

    private Optional<OrdersFromClientDTO> deserializeOrder(String order) {
try {

    ObjectMapper objectMapper = new ObjectMapper();
    return Optional.ofNullable(objectMapper.readValue(order, OrdersFromClientDTO.class));

}catch(Exception ex)   {

    System.out.println(ex.getMessage());
    return Optional.of(new OrdersFromClientDTO());
}

    }


    @RabbitListener(queues = {"receiveOrder"})
    public void receiveOrder(String incomingOrder) {

Optional<OrdersFromClientDTO> optionalOrder=deserializeOrder(incomingOrder);


 if(optionalOrder.isPresent()) {
     OrdersFromClientDTO order=optionalOrder.get();
     Batch batch = null;
     List<Batch> batchOptional = this.batchManager.findBatchByState(BatchState.NotYetPrepared);
     if (batchOptional.size() == 1) {

         batch = batchOptional.get(0);


     } else {

         batch = batchManager.save(new Batch());
     }

     for (Map.Entry<Long, Integer> keyValuePair : order.getProducts().entrySet()) {
         Optional<Product> product = productManager.getProductById(keyValuePair.getKey());
         if (product.isPresent()) {
             if (batch.getProductsinBatch().containsKey(product.get())) {
                 batch.getProductsinBatch().replace(product.get(), (int) (batch.getProductsinBatch().get(product.get()) + keyValuePair.getValue()));

             } else {
                 batch.getProductsinBatch().put(product.get(), keyValuePair.getValue());

             }
         }
         try {
             batchManager.saveOrUpdate(batch);
         } catch (Exception ex) {

         }
     }

 }









    }
    @RabbitListener(queues = "IngredientsAvailableQueue",ackMode ="MANUAL" )
    public void ReceiveBatch(Long batchId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
System.out.println("batch bevestigd");
        System.out.println(batchId);
 channel.basicAck(tag, false);


    }


}
