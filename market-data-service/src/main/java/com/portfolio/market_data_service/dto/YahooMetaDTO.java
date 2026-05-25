package com.portfolio.market_data_service.dto;
import java.math.BigDecimal;
public record YahooMetaDTO(String symbol, BigDecimal regularMarketPrice) {}