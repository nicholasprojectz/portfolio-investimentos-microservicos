package com.portfolio.user_portfolio_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record CarteiraConsolidadaDTO(
        BigDecimal patrimonioTotal,
        BigDecimal totalInvestido,
        BigDecimal rentabilidadeReais,
        BigDecimal rentabilidadePercentual,
        List<PosicaoAtualizadaDTO> posicoes
) {}