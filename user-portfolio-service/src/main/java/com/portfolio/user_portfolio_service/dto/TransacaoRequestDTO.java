package com.portfolio.user_portfolio_service.dto;

import com.portfolio.user_portfolio_service.model.TipoOperacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRequestDTO(
        @NotNull Long carteiraId,
        @NotNull Long ativoId,
        @NotNull TipoOperacao tipoOperacao,
        @NotNull @Positive BigDecimal quantidade,
        @NotNull @Positive BigDecimal precoUnitario,
        @NotNull LocalDate dataTransacao
) {}