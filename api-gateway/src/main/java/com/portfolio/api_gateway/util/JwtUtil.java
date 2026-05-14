package com.portfolio.api_gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSignInKey() {
        byte[] keyBytes = io.jsonwebtoken.io.Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método focado puramente em checar se a matemática da assinatura bate
    // e se o prazo de validade não expirou.
    public void validateToken(final String token) {
        Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token);
    }
}