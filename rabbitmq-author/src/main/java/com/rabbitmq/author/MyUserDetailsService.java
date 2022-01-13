package com.rabbitmq.author;

import com.rabbitmqdatabase.RabbitMQdatabaseApplication;
import com.rabbitmqdatabase.database.entity.User;
import com.rabbitmqdatabase.database.repository.UserRepository;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Import(value = {RabbitMQdatabaseApplication.class})
public class MyUserDetailsService implements UserDetailsService {
    final UserRepository repository;

    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUserName(userName);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        return user.map(MyUserDetails::new).get();
    }
}
