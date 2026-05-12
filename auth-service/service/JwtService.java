package com.portfolio.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Chave secreta convertida em Base64. Em produção, isso JAMAIS fica no código, fica em variáveis de ambiente.
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // 1 dia em milissegundos
    private long jwtExpiration;

    private SecretKey getSignInKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // O dono do token
                .issuedAt(new Date(System.currentTimeMillis())) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Data de expiração
                .signWith(getSignInKey()) // Assinatura digital
                .compact();
    }
}