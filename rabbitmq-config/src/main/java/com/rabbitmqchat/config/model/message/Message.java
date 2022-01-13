package com.rabbitmqchat.config.model.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class Message implements Serializable {
    String id;
    Date dateTime;
    String content;

    public static Message textMessage(String content) {
        return new Message(UUID.randomUUID().toString(), Date.from(java.time.ZonedDateTime.now().toInstant()), content);

    }

    @JsonCreator
    public Message message(@JsonProperty("id") String id, @JsonProperty("dateTime") Date dateTime, @JsonProperty("content") String content) {
        return new Message(id, dateTime, content);
    }
}
