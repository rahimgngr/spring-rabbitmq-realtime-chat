package com.rabbitmqchat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component("appSettings")
@ConfigurationProperties("app.settings")
@Configuration
@EnableConfigurationProperties
@Data
public class AppSettings {
    Rabbit rabbit;

    @Data
    public static class Rabbit {
        String host;
        int port;
        String user;
        String pass;

        String routingKey;
        String exchange;
        String queue;

        Integer maxConnections;
        Integer maxConcurrentConsumers;
    }
}

