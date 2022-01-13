package com.rabbitmqchat.config.model.config;

import lombok.Data;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
public class ConfigModel {
    @Value("${random.uuid}")
    private String queueName;
    private String exchangeName ;
    private String routingKey;

    @Bean
    public Queue controlQueue() {
        return new Queue(queueName, true, false, true);
    }
}
