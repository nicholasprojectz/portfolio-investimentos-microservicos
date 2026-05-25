package com.portfolio.market_data_service.controller;

import com.portfolio.market_data_service.model.Cotacao;
import com.portfolio.market_data_service.service.MarketDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mercado")
public class MarketDataController {

    private final MarketDataService marketDataService;

    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping("/cotacao/{ticker}")
    public ResponseEntity<Cotacao> obterCotacao(@PathVariable String ticker) {
        Cotacao cotacao = marketDataService.buscarEAtualizarPreco(ticker);
        return ResponseEntity.ok(cotacao);
    }
}