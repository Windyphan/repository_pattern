package com.example.demo.controller;

import com.example.demo.service.JWTUtil;
import com.example.demo.DTOs.LoginRequest;
import com.example.demo.entity.User;
import com.example.demo.service.PasswordHasher;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHasher passwordHasher;
    // API endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // Retrieve the username and password from the LoginRequest object
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Check if the username or password is empty
        if(email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }

        User user = userService.findUserByEmail(email);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
        }

        // Check if the password provided matches the stored password for the user
        if(!passwordHasher.checkPassword(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password.");
        }

        // Check if the user is active or not
        if(!user.getIsActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not active");
        }

        // At this point, the user has successfully authenticated
        String jwtToken = jwtUtil.generateToken(user);
        //Set the cookie
        Cookie cookie = new Cookie("jwtToken", jwtToken);
        cookie.setPath("/");
        cookie.setMaxAge(1800); //Cookie expire in seconds = 15 mins

        response.addCookie(cookie);

        return ResponseEntity.ok("Login successful. JWT Token: " + jwtToken);

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token, HttpServletResponse response) {
        // Check if the Authorization header containing the JWT token is present
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        // Extract the actual token without the "Bearer " prefix
        String jwtToken = token.substring(7);

        // Call the revokeToken method from the TokenService class to invalidate the JWT token
        tokenService.revokeToken(jwtToken);

        //Set the cookie
        Cookie cookie = new Cookie("jwtToken", "");//Set value jwtToken into empty
        cookie.setPath("/");
        cookie.setMaxAge(0); //Set cookie expire in order to invalid the cookie

        response.addCookie(cookie);

        return ResponseEntity.ok("Logout successful. Token revoked.");
    }

}