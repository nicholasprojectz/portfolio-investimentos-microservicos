package com.portfolio.market_data_service.service;

import com.portfolio.market_data_service.dto.YahooResponseDTO;
import com.portfolio.market_data_service.model.Cotacao;
import com.portfolio.market_data_service.repository.CotacaoRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MarketDataService {

    private final CotacaoRepository cotacaoRepository;
    private final RestTemplate restTemplate;
    
    // Endpoint interno do Yahoo Finance
    private final String YAHOO_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    public MarketDataService(CotacaoRepository cotacaoRepository) {
        this.cotacaoRepository = cotacaoRepository;
        this.restTemplate = new RestTemplate();
    }

    public Cotacao buscarEAtualizarPreco(String ticker) {
        // Adiciona .SA para o padrão da B3 exigido pelo Yahoo
        String tickerFormatado = ticker.toUpperCase() + ".SA";
        
        try {
            String url = YAHOO_URL + tickerFormatado;

            // Criando o "disfarce" de navegador para não ser bloqueado
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Troca o getForObject pelo exchange para permitir o envio de headers
            ResponseEntity<YahooResponseDTO> response = restTemplate.exchange(
                    url, 
                    HttpMethod.GET, 
                    entity, 
                    YahooResponseDTO.class
            );

            YahooResponseDTO body = response.getBody();

            if (body != null && body.chart() != null && body.chart().result() != null) {
                BigDecimal precoDaInternet = body.chart().result().get(0).meta().regularMarketPrice();

                Cotacao cotacao = cotacaoRepository.findByTicker(ticker)
                        .orElse(new Cotacao(ticker, precoDaInternet));

                cotacao.setPrecoAtual(precoDaInternet);
                cotacao.setDataUltimaAtualizacao(LocalDateTime.now());
                
                return cotacaoRepository.save(cotacao);
            }
        } catch (Exception e) {
            System.err.println("Erro na API do Yahoo para " + ticker + " - Motivo: " + e.getMessage());
        }
        
        return cotacaoRepository.findByTicker(ticker)
                .orElseThrow(() -> new IllegalArgumentException("Cotação não encontrada e API indisponível."));
    }
}