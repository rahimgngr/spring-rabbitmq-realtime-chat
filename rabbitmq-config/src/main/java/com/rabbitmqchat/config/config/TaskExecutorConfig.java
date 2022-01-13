package com.rabbitmqchat.config.config;

import com.rabbitmqchat.config.AppSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class TaskExecutorConfig {

    @Bean
    @Qualifier("rabbitConnection")
    public TaskExecutor rabbitConnectionExecutor(@Qualifier("appSettings") AppSettings settings) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(settings.getRabbit().getMaxConnections());
        executor.setMaxPoolSize(settings.getRabbit().getMaxConnections());
        executor.setThreadNamePrefix("RabbitConnection");
        executor.afterPropertiesSet();
        return executor;
    }

    @Bean
    @Qualifier("rabbitListener")
    public TaskExecutor rabbitListenerExecutor(@Qualifier("appSettings") AppSettings settings) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(settings.getRabbit().getMaxConcurrentConsumers());
        executor.setMaxPoolSize(settings.getRabbit().getMaxConcurrentConsumers());
        executor.setThreadNamePrefix("RabbitListener");
        executor.afterPropertiesSet();
        return executor;
    }

}

