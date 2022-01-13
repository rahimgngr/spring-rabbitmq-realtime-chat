package com.rabbitmqchat.config.config;

import com.rabbitmqchat.config.AppSettings;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableRabbit
public class RabbitConfig {


    @Bean
    @Autowired
    public ConnectionFactory connectionFactory(@Qualifier("appSettings") @NotNull AppSettings settings, @Qualifier("rabbitConnection") TaskExecutor executor) {
        final AppSettings.Rabbit rabbitSettings = settings.getRabbit();
        final CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitSettings.getHost(), rabbitSettings.getPort());
        connectionFactory.setUsername(rabbitSettings.getUser());
        connectionFactory.setPassword(rabbitSettings.getPass());
        connectionFactory.setExecutor(executor);
        return connectionFactory;
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    @Autowired
    public DirectRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("appSettings") @NotNull AppSettings settings,
                                                                               ConnectionFactory connectionFactory,
                                                                               @Qualifier("rabbitListener") TaskExecutor executor) {

        final DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        /*factory.setConcurrentConsumers(settings.getRabbit().getMaxConcurrentConsumers());
        factory.setMaxConcurrentConsumers(settings.getRabbit().getMaxConcurrentConsumers());*/
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setTaskExecutor(executor);
        return factory;
    }

}