package com.portfolio.user_portfolio_service.dto;

import com.portfolio.user_portfolio_service.model.TipoOperacao;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoResponseDTO(
        Long id,
        String nomeCarteira,
        String tickerAtivo,
        TipoOperacao tipoOperacao,
        BigDecimal quantidade,
        BigDecimal precoUnitario,
        LocalDate dataTransacao
) {}