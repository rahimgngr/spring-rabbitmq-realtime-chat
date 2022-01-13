package com.rabbitmqdatabase.database.service;

import com.rabbitmqdatabase.database.entity.User;
import com.rabbitmqdatabase.database.repository.UserRepository;
import com.rabbitmqmodel.database.dto.UserDto;
import com.rabbitmqmodel.database.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional //bütün işlemler bu blokta olup bitmesi için
    public UserDto save(UserDto userDto) {
        User user = new User();

        List<String> users = userRepository.findAll().stream().distinct().map(User::getUserName).collect(Collectors.toList());

        user.setUserName(userDto.getUserName());
        user.setUserPass(userDto.getUserPass());
        user.setRole(userDto.getRole());

        final User userDb = userRepository.save(user);
        userDto.setId(userDb.getId());

        return userDto;
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        users.forEach(it -> {
            UserDto userDto = new UserDto();
            userDto.setId(it.getId());
            userDto.setUserName(it.getUserName());
            userDto.setUserPass(it.getUserPass());
            userDto.setRole(it.getRole());
            userDtos.add(userDto);
        });

        return userDtos;
    }
}
