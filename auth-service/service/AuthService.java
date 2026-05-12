package com.portfolio.authservice.service;

import com.portfolio.authservice.dto.LoginRequestDTO;
import com.portfolio.authservice.dto.RegisterRequestDTO;
import com.portfolio.authservice.model.AuthUser;
import com.portfolio.authservice.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDTO request) {
        Optional<AuthUser> existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Este email já está em uso.");
        }

        AuthUser newUser = new AuthUser();
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password())); // Criptografa antes de salvar

        userRepository.save(newUser);
    }

    public void login(LoginRequestDTO request) {
        AuthUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("Email ou senha inválidos.");
        }

        // TODO: A senha está correta. O próximo passo é gerar e retornar o Token JWT.
    }
}