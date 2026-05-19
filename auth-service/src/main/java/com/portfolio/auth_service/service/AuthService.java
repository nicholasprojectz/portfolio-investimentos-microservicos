package com.portfolio.auth_service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.portfolio.auth_service.dto.LoginRequestDTO;
import com.portfolio.auth_service.dto.RegisterRequestDTO;
import com.portfolio.auth_service.model.AuthUser;
import com.portfolio.auth_service.repository.AuthUserRepository;
import com.portfolio.auth_service.dto.TokenResponseDTO;

import com.portfolio.auth_service.config.RabbitMQConfig;
import com.portfolio.auth_service.dto.UsuarioCriadoEventDTO;
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
    private final RabbitTemplate rabbitTemplate;

    public void register(RegisterRequestDTO request) {
        Optional<AuthUser> existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Este email já está em uso.");
        }

        AuthUser newUser = new AuthUser();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password())); 

        AuthUser savedUser = userRepository.save(newUser);

        UsuarioCriadoEventDTO evento = new UsuarioCriadoEventDTO(savedUser.getId(), savedUser.getEmail());
        
        rabbitTemplate.convertAndSend(RabbitMQConfig.FILA_USUARIO_CRIADO, evento);
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