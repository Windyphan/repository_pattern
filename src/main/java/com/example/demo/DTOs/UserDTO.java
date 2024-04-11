package com.example.demo.DTOs;

import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;

public class UserDTO {
    private Long userId;
    private String name;
    private String email;
    private String password;
    private String role;
    private Boolean isActive;
    public UserDTO() {
    }

    public User mapUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getUserId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setIsActive(userDTO.getIsActive());
        String inputRole = userDTO.getRole().toUpperCase();
        user.setRole(UserRole.valueOf(inputRole));
        return user;
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setIsActive(user.getIsActive());
        String role = user.getRole().name().toUpperCase();
        userDTO.setRole(role);
        return userDTO;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}
