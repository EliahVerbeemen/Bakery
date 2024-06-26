package kdg.be.RabbitMQ;


//Klasse om nieuwe recepies te zenden

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kdg.be.DTO.BatchForWarehouse;
import kdg.be.Deserializers.JsonSerializer;
import kdg.be.Models.Batch;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitSender {

    private static final Logger logger = LoggerFactory.getLogger(RabbitSender.class);
    JsonSerializer jsonSerializer;
    private final RabbitTemplate rabbitTemplate;

    public RabbitSender(RabbitTemplate rabbitTemplate, JsonSerializer jsonSerializer) {
        this.rabbitTemplate = rabbitTemplate;
        this.jsonSerializer = jsonSerializer;
    }

    public void sendNewRecipe(Product product) throws JsonProcessingException {
        logger.info("new product send");
        String jsonProduct = jsonSerializer.productToJson(product);
        rabbitTemplate.convertAndSend("newRecepyExchange", "warehouseQueu", jsonProduct);
        rabbitTemplate.convertSendAndReceive("newRecepyExchange", "newRecepiesQueue", jsonProduct);
    }

    private String BatchSerializer(BatchForWarehouse batch) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(batch);
        } catch (JsonProcessingException ex) {
            logger.warn("Batch couldn't been received");
            return "";
        }
    }

    private BatchForWarehouse prepareBatch(Batch batch) {
        Map<Ingredient, Double> ingredients = new HashMap<>();
        batch.getProductsinBatch().forEach((product, amount) -> {
            for (int i = 0; i < product.getComposition().size(); i++) {
                Ingredient ing = product.getComposition().get(i);
                Double quantity = product.getAmounts().get(i);
                ingredients.putIfAbsent(ing, 0D);
                ingredients.replace(ing, ingredients.get(ing) + quantity * amount);
            }
        });
        return new BatchForWarehouse(ingredients.keySet().stream().toList(), ingredients.values().stream().toList(), batch.getBatchId());
    }


    public void sendBatch(Batch batch) {
        logger.info("A batch has been send");
        BatchForWarehouse batchForWarehouse = prepareBatch(batch);
        String jsonBatch = BatchSerializer(batchForWarehouse);
        rabbitTemplate.convertSendAndReceive("batchExchange", "batchKey", jsonBatch);
    }


}
