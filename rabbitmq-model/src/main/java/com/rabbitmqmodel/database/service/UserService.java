package com.rabbitmqmodel.database.service;

import com.rabbitmqmodel.database.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto save(UserDto userDto);

    void delete(Long id);

    List<UserDto> getAll();
}
