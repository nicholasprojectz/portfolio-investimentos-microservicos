package com.portfolio.user_portfolio_service.dto;

import java.math.BigDecimal;

public record PosicaoResponseDTO(
        Long id,
        String tickerAtivo,
        String nomeAtivo,
        BigDecimal quantidadeTotal,
        BigDecimal precoMedioAtual
) {}