package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    @Autowired
    private JWTUtil jwtUtil;
    public boolean isUserAuthenticated(String token){
        // Validate the JWT token
        return jwtUtil.validateToken(token);
    }
    public void authenticateToken(String token){
        // Check if the Authorization header containing the JWT token is present
        if (token == null || !token.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        // Extract the actual token without the "Bearer " prefix
        String jwtToken = token.substring(7);
        // Check if the token is expired
        if (jwtUtil.isJWTTokenExpired(jwtToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired");
        }
        // Check if user is logged in and has TEACHER role
        if (!isUserAuthenticated(jwtToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied");
        }
    }
}
