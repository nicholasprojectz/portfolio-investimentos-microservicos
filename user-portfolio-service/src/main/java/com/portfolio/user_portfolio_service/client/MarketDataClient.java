package com.portfolio.user_portfolio_service.client;

import com.portfolio.user_portfolio_service.dto.CotacaoExternaDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MarketDataClient {

    private final RestTemplate restTemplate;
    // Aponta direto para o seu Oráculo
    private final String ORACULO_URL = "http://localhost:8083/api/mercado/cotacao/";

    public MarketDataClient() {
        this.restTemplate = new RestTemplate();
    }

    public CotacaoExternaDTO obterCotacao(String ticker) {
        try {
            // Faz a requisição HTTP interna para o outro microsserviço
            return restTemplate.getForObject(ORACULO_URL + ticker, CotacaoExternaDTO.class);
        } catch (Exception e) {
            System.err.println("Erro ao conectar com o Oráculo para o ticker: " + ticker);
            // Retorna um DTO zerado caso o Oráculo esteja fora do ar (Fallback de segurança)
            return new CotacaoExternaDTO(ticker, java.math.BigDecimal.ZERO);
        }
    }
}