package kdg.be.RabbitMQ;


//Klasse om nieuwe recepies te zenden

import kdg.be.Models.Batch;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitSender {


    private RabbitTemplate rabbitTemplate;

    private static final Logger logger= LoggerFactory.getLogger(RabbitSender.class);


    public RabbitSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate=rabbitTemplate;

    }

    public void sendNewRecipe(Product product){

        System.out.println("product verzonden");
       System.out.println(product.getComposition().size());
        System.out.println(product.getAmounts().size());
//product.setComposition(new ArrayList<>());

        //Voor recipe gebruik een fanout en geen
        rabbitTemplate.convertAndSend("newRecepyExchange","newRecepiesQueue",product);

    }

    public void sendBatch(Batch batch){
        Map<Ingredient,Double>ingredientList=new HashMap<>();

        batch.getProductsinBatch().forEach(((product, integer) -> {
    product.getComposition().forEach(ing->{
        if(!ingredientList.containsKey(ing)){

            ingredientList.put(ing, (double) 0);
        }

    });



}));

        batch.getProductsinBatch().forEach(((product, integer) -> {
            product.getComposition().forEach(ing->{

                int index=product.getComposition().indexOf(ing);
                ingredientList.replace(ing,ingredientList.get(ing)+product.getAmounts().get(index));


            });




                }));


ingredientList.put(new Ingredient("tt","ll"), 7.0);
        rabbitTemplate.convertSendAndReceive("batchExchange","batchKey",ingredientList.keySet().stream().toList());
    }









}
