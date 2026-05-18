package com.portfolio.auth_service.service;

import com.portfolio.auth_service.dto.LoginRequestDTO;
import com.portfolio.auth_service.dto.RegisterRequestDTO;
import com.portfolio.auth_service.model.AuthUser;
import com.portfolio.auth_service.repository.AuthUserRepository;
import com.portfolio.auth_service.dto.TokenResponseDTO;
// Removi o import do JwtService pois estão no mesmo pacote.

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor 
public class AuthService {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 

    // O construtor manual foi APAGADO.

    public void register(RegisterRequestDTO request) {
        Optional<AuthUser> existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Este email já está em uso.");
        }

        AuthUser newUser = new AuthUser();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password())); 

        userRepository.save(newUser);
    }

    public TokenResponseDTO login(LoginRequestDTO request) {
        AuthUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }

        String tokenGerado = jwtService.generateToken(user);
        return new TokenResponseDTO(tokenGerado);
    }
}