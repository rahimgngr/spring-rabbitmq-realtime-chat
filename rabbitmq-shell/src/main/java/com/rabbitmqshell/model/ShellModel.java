package com.rabbitmqshell.model;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
public class ShellModel {

    private String queueName;
    private String exchangeName;
    private String routingKey;
}
