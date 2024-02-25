package kdg.be.RabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfiguratie {

    @Value("${queue.newReceptQueue}")
    private String newRecepyQueue;

    @Value("${queue.deactivating}")
    private String deactivateRecepyQueue;

    @Value("${exchange.newRecepyExchange}")
    private String exchangeName;



    //Kies de juiste import voor de queue


    @Bean
    public Queue queue(){

        return new Queue(newRecepyQueue,true);


    }

  @Qualifier(value = "deactivate")
  @Bean
    public Queue deactivate(){

        return new Queue(deactivateRecepyQueue,true);


    }
@Bean
    public FanoutExchange fanoutExchange(){

        return new FanoutExchange(exchangeName);


    }
    @Bean
    public TopicExchange topicExchange(){

        return new TopicExchange(deactivateRecepyQueue);


    }
    @Bean

    public Binding binding(Queue queue,FanoutExchange fanoutExchange){

        return    BindingBuilder.bind(queue).to(fanoutExchange);


    }
@Bean
   public Binding topicBinding(@Qualifier("deactivate") Queue Deactivate,TopicExchange topicExchange){
        return    BindingBuilder.bind(Deactivate).to(topicExchange).with("deactivate");

    }
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate= new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());

        return rabbitTemplate;
    }
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {

        ObjectMapper objectMapper=new ObjectMapper();


        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("sparrow.rmq.cloudamqp.com");
        connectionFactory.setUsername("umxeqqou");
        connectionFactory.setPassword("z-3ZVFLkp9m_3yaeEZCxKH0KkthMRDoY");
        connectionFactory.setVirtualHost("umxeqqou");

        return connectionFactory;
    }


}
