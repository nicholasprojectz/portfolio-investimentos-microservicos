package com.portfolio.auth_service.service;
import com.portfolio.auth_service.model.AuthUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") 
    private long jwtExpiration;

    private SecretKey getSignInKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(AuthUser user) {
        return Jwts.builder()
                .subject(user.getEmail()) 
                .issuedAt(new Date(System.currentTimeMillis())) 
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) 
                .signWith(getSignInKey()) 
                .compact();
    }
}