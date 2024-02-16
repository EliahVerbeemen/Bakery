package com.example.bakkery;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableIntegration
@EnableRabbit
@EnableScheduling
public class BakkeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(BakkeryApplication.class, args);
    }

}
