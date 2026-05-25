package com.portfolio.user_portfolio_service.dto;

import java.math.BigDecimal;

public record PosicaoAtualizadaDTO(
        String ticker,
        BigDecimal quantidade,
        BigDecimal precoMedio,
        BigDecimal precoAtualMercado,
        BigDecimal patrimonioTotal,
        BigDecimal rentabilidadeReais
) {}