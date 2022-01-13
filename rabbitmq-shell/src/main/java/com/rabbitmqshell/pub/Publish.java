package com.rabbitmqshell.pub;


import com.rabbitmqchat.config.model.message.Message;

import com.rabbitmqchat.config.service.CustomMessageListener;
import com.rabbitmqchat.config.service.CustomMessageSender;
import com.rabbitmqshell.model.ShellModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@ComponentScan(basePackages = {"com.rabbitmqchat"})
public class Publish {

    @Autowired
    ShellModel shellModel;
    @Autowired
    CustomMessageSender sender;
    @Autowired
    CustomMessageListener listener;
    @Autowired
    Message message;


    @ShellMethod("Publish message to queue")
    public void pub(@NotNull String msg) {
        sender.sendMessage(Message.textMessage(msg));
    }
}