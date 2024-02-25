package kdg.be.RabbitMQ;


//Klasse om nieuwe recepies te zenden

import kdg.be.Managers.ProductManager;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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

    public void sendNewRecepy(Product product){

        System.out.println(product.getName());
      //Voo recepy gebruik een fanout en geen
        rabbitTemplate.convertAndSend("newRecepyExchange","newRecepiesQueue",product);

    }









}
