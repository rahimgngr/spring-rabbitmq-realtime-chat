package com.rabbitmqmodel.database.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto {
    private Long id;
    private String userName;
    private String userPass;
    private String role;
}
