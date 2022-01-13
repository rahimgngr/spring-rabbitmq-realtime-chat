package com.rabbitmq.author;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.rabbitmqdatabase"})
@EntityScan(basePackages = {"com.rabbitmqdatabase"})
public class RabbitMQauthorApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQauthorApplication.class, args);
    }
}
