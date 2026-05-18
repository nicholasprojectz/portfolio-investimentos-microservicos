package com.portfolio.user_portfolio_service.dto;

import com.portfolio.user_portfolio_service.model.TipoAtivo;

public record AtivoResponseDTO(Long id, String ticker, String nome, TipoAtivo tipo) {}