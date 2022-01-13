package com.rabbitmqchat.config.service;

import com.rabbitmqchat.config.model.config.ConfigModel;
import com.rabbitmqchat.config.model.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomMessageListener {
    @Autowired
    ConfigModel model;

    @RabbitListener(queues = "#{configModel.getQueueName()}")
    public void receiveMessage(Message message) {
        //System.out.println(model.getQueueName());
        log.info("Received message: {}", message.toString());
        System.out.println(model.getRoutingKey() + " :" + message.getContent());

    }
}