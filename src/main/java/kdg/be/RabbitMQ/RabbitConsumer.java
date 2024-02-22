package kdg.be.RabbitMQ;

import kdg.be.Models.Product;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {



    @RabbitListener(queues ="${queue.incomming}")
    public void ReceiveMessage(String message){


System.out.println(message);


    }



}
