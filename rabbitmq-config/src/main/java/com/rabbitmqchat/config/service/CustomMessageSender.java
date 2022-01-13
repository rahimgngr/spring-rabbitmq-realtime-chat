package com.rabbitmqchat.config.service;


import com.rabbitmqchat.config.model.config.ConfigModel;
import com.rabbitmqchat.config.model.message.Message;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class CustomMessageSender {
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    ConfigModel model;
    public CustomMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Message msg) {
        final var message = new Message(rabbitTemplate.getUUID(), Date.from(Instant.now()), msg.getContent());
        log.info("Publishing msg: {}", message);
        rabbitTemplate.convertAndSend(model.getExchangeName(), model.getRoutingKey(), msg);
    }
}