package com.example.demo.service;

import com.example.demo.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.security.Key;
@Service
public class JWTUtil {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(User user) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + (1000 * 60 * 15); // Token expires in 15 mins
        return Jwts.builder()
                .setSubject(user.getName())
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            // Parse the JWT token
            Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Exception handling for invalid tokens
            return false;
        }
    }

    public boolean isJWTTokenExpired(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date()); // Check if token is not expired
        } catch (JwtException | IllegalArgumentException e) {
            // Exception handling for invalid tokens
            return false;
        }
    }
}
