package com.portfolio.user_portfolio_service.dto;

import java.math.BigDecimal;

public record CotacaoExternaDTO(
        String ticker,
        BigDecimal precoAtual
) {
}