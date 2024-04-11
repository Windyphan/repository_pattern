package com.example.demo.service;

import com.example.demo.DTOs.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.entity.UserRole.TEACHER;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordHasher passwordHasher;
    public void createUser(UserDTO userDTO) {
        if (userDTO.getRole() == null) {
            throw new IllegalArgumentException("User role is required");
        }
        if (userDTO.getRole().equals(UserRole.valueOf(String.valueOf(TEACHER)))) {
            userDTO.setEmail("teacher@example.com");
            userDTO.setPassword(passwordHasher.hashPassword("12345678"));
        } else {
            if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
                throw new IllegalArgumentException("User password is required");
            }

            if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
                throw new IllegalArgumentException("User email is required");
            }
        }

        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }

        if (userDTO.getUserId() != null) {
            User existingUser = userRepository.findByUserId(userDTO.getUserId());
            if (existingUser != null) {
                throw new IllegalArgumentException("User id already existed");
            }
        }
        User existingUserEmail = userRepository.findByEmail(userDTO.getEmail());
        if (existingUserEmail != null) {
            throw new IllegalArgumentException("User email must be unique");
        }
        userDTO.setPassword(passwordHasher.hashPassword(userDTO.getPassword()));
        userDTO.setIsActive(true);
        User newUser = userDTO.mapUserDTOToUser(userDTO);
        userRepository.save(newUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users == null) {
            return new ArrayList<>();
        }
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO = userDTO.mapUserToUserDTO(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public UserDTO findUser(Long id) {
        User user = userRepository.findByUserId(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        UserDTO userDTO = new UserDTO();
        userDTO = userDTO.mapUserToUserDTO(user);
        return userDTO;
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
                User user = userRepository.findByEmail(updatedUser.getEmail());
                if (user == null || user.getEmail().equals(existingUser.getEmail())){
                    existingUser.setEmail(updatedUser.getEmail());
                } else {
                    throw new IllegalArgumentException("Email must be unique");
                }
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
