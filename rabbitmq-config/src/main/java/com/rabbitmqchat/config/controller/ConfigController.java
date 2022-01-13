package com.rabbitmqchat.config.controller;

import com.rabbitmqchat.config.model.config.ConfigModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
@Configuration
@Data
public class ConfigController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    ConfigModel model;

    @Autowired
    RabbitAdmin rabbitAdmin;

    @GetMapping("/exchange/{exchangeName}")
    public Exchange exchange(@PathVariable String exchangeName) {
        Exchange exchange = new DirectExchange(exchangeName, true, false);
        model.setExchangeName(exchangeName);
        log.info("Declaring exchange: {}", exchangeName);
        rabbitAdmin.declareExchange(exchange);

        return exchange;
    }

    @GetMapping("/queue/{queueName}")
    public Queue queue(@PathVariable String queueName) {
        Queue queue = new Queue(queueName, true, false, false);
        model.setQueueName(queueName);
        log.info("Declaring queue: {}", queueName);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @GetMapping("/bind/{routingKey}")
    public Binding binding(@PathVariable String routingKey) {
        Binding binding = new Binding(model.getQueueName(), Binding.DestinationType.QUEUE, model.getExchangeName(), routingKey, null);
        model.setRoutingKey(routingKey);
        log.info("Declaring binding: {} -> {} -> {}", binding.getExchange(), binding.getDestination(), binding.getRoutingKey());
        rabbitAdmin.declareBinding(binding);

        return binding;
    }

    //Pub & sub methods
/*
    @PostMapping
    public String pubMsg(@RequestBody Message message) {
        message.setId(UUID.randomUUID().toString());
        message.setDateTime(Date.from(Instant.now()));
        MessageStatus messageStatus = new MessageStatus(message, "PROCCESS", "Message sent");
        log.info("Publishing message: {} with ExchangeName: {} and RoutingKey: {}", messageStatus, model.getExchangeName(), model.getRoutingKey());
        rabbitTemplate.convertAndSend(model.getExchangeName(), model.getRoutingKey(), messageStatus);
        sbsMsg(messageStatus);
        return "sent! ";
    }

    @RabbitListener(queues = "#{configModel.getQueueName()}")
    public void sbsMsg(MessageStatus messageStatus) {
        log.info("Received message: {} from Queue: {}", messageStatus, model.getQueueName());
    }*/
}
