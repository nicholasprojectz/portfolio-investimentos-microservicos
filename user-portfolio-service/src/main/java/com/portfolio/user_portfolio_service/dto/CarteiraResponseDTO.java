package com.portfolio.user_portfolio_service.dto;

import java.time.LocalDateTime;

public record CarteiraResponseDTO(
        Long id,
        String nome,
        LocalDateTime dataCriacao
) {
}