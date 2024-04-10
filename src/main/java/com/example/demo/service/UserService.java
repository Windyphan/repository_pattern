package com.example.demo.service;

import com.example.demo.controller.AdminUserController;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;
    public void createUser(User user) {
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }
        if (user.getRole().equals(AdminUserController.UserRole.valueOf(String.valueOf(TEACHER)))) {
            user.setEmail("teacher@example.com");
            user.setPassword(passwordHasher.hashPassword("12345678"));
        } else {
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new IllegalArgumentException("User password is required");
            }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email is required");
        }
        if (user.getRole().equals(AdminUserController.UserRole.TEACHER)) {
            user.setEmail("teacher@example.com");
            user.setPassword("12345678");
        }
        if (user.getUserId() != null) {
            User existingUser = userRepository.findByUserId(user.getUserId());
            if (existingUser != null) {
                throw new IllegalArgumentException("User already existed");
            }
        }
        userRepository.save(user);


    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users == null) {
            return new ArrayList<>();
        }
        return users;
    }

    public User findUser(Long id) {
        User user = userRepository.findByUserId(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findByUserId(id);

        // Check if the existingUser has the same email as updatedUser,
        // or if the existingUser is null (email does not exist in the DB)
        if (existingUser != null) {
            // Update only the non-null fields in the existingUser
            if (updatedUser.getName() != null) {
                existingUser.setName(updatedUser.getName());
            }

            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(passwordHasher.hashPassword(updatedUser.getPassword()));
            }

            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getRole() != null) {
                // Convert the updatedUser role from JSON input string to uppercase
                String inputRole = updatedUser.getRole().name().toUpperCase();

                // Compare the inputRole with each enum constant name case-insensitively
                boolean isValidRole = Arrays.stream(UserRole.values())
                        .anyMatch(enumRole -> enumRole.name().equalsIgnoreCase(inputRole));

                if (isValidRole) {
                    existingUser.setRole(UserRole.valueOf(inputRole));
                } else {
                    throw new IllegalArgumentException("Invalid role value: " + inputRole);
                }
            }
            if (updatedUser.getIsActive() != null) {
                existingUser.setIsActive(updatedUser.getIsActive());
            } else  {
                existingUser.setIsActive(existingUser.getIsActive());
            }
            // Save the updated user
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User is not exist in database");
        }

    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findByUserId(id);
        if (existingUser != null) {
            existingUser.setIsActive(false);
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User is not exist in database");
        }
    }

}
