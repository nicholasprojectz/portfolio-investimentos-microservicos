package com.portfolio.user_portfolio_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CarteiraRequestDTO(
        @NotBlank(message = "O nome da carteira é obrigatório") 
        String nome
) {
}