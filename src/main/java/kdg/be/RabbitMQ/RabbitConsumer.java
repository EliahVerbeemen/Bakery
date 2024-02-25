package kdg.be.RabbitMQ;

import kdg.be.DTO.IngredientDTO;
import kdg.be.DTO.OrdersFromClient;
import kdg.be.Managers.ProductManager;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

    private ProductManager productManager;
public RabbitConsumer(ProductManager productManager){
    this.productManager=productManager;

}

    @RabbitListener(queues ="${queue.incomming}")
    public void ReceiveMessage(IngredientDTO message){

//Dit werkt
System.out.println(message.getDepartmentOfStorage());


    }

    @RabbitListener(queues = {"receiveOrder"})
    public void receiveOrder(OrdersFromClient order) {

System.out.println(order);

    }



}
