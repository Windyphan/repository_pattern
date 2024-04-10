package com.example.demo.configuration;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultDataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordHasher passwordHasher;


    @Override
    public void run(String... args) throws Exception {
        List<User> users = new ArrayList<>();
        User user1 = new User("123@gmail.com", passwordHasher.hashPassword("123456"), UserRole.TEACHER);
        user1.setName("admin");
        user1.setUserId(1L);
        user1.setIsActive(true);

        User user2 = new User("1234@gmail.com", passwordHasher.hashPassword("123456"), UserRole.STUDENT);
        user2.setName("student");
        user2.setUserId(2L);
        user2.setIsActive(true);

        users.add(user1);
        users.add(user2);
        userRepository.saveAll(users);
    }
}
