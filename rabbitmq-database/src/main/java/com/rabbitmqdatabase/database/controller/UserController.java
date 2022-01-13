package com.rabbitmqdatabase.database.controller;

import com.rabbitmqdatabase.database.entity.User;
import com.rabbitmqdatabase.database.repository.UserRepository;
import com.rabbitmqmodel.database.dto.UserDto;
import com.rabbitmqmodel.database.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public Object kaydet(@RequestBody UserDto userDto) {
        List<String> users = userRepository.findAll().stream().distinct().map(User::getUserName).collect(Collectors.toList());
        if (users.contains(userDto.getUserName())) {
            System.out.println("User Exists Already! ");
            return "User Exists!";
        } else {
            return ResponseEntity.ok(userService.save(userDto));
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> tumunuListele() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> sil(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
