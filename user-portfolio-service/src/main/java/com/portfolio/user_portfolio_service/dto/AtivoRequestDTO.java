package com.portfolio.user_portfolio_service.dto;

import com.portfolio.user_portfolio_service.model.TipoAtivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtivoRequestDTO(
        @NotBlank(message = "O ticker é obrigatório") String ticker,
        @NotBlank(message = "O nome é obrigatório") String nome,
        @NotNull(message = "O tipo do ativo é obrigatório") TipoAtivo tipo
) {}