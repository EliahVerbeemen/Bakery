package kdg.be.Controllers;

import kdg.be.Managers.ProductManager;
import kdg.be.Models.Batch;
import kdg.be.Models.Ingredient;
import kdg.be.Models.Product;
import kdg.be.RabbitMQ.RabbitSender;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class TestController {

    private ProductManager productManager;
    private RabbitSender rabbitSender;
    public TestController(ProductManager productManager, RabbitSender rabbitSender){

        this.productManager=productManager;
        this.rabbitSender=rabbitSender;

    }
    @GetMapping("/test")
    public List<Product> producten(){


        return productManager.getAllProducts();
    }

 /*   @GetMapping("/message")
    public void testM(){

rabbitSender.sendBatch(new Batch());

    }*/



}
