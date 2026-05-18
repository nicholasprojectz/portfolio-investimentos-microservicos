package com.portfolio.user_portfolio_service.controller;

import com.portfolio.user_portfolio_service.dto.PosicaoResponseDTO;
import com.portfolio.user_portfolio_service.service.PosicaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posicoes")
public class PosicaoController {

    private final PosicaoService posicaoService;

    public PosicaoController(PosicaoService posicaoService) {
        this.posicaoService = posicaoService;
    }

    // Retorna a "foto" atual da carteira para montar o gráfico no frontend
    @GetMapping("/carteira/{carteiraId}")
    public ResponseEntity<List<PosicaoResponseDTO>> listarPosicoes(@PathVariable Long carteiraId) {
        return ResponseEntity.ok(posicaoService.listarPosicoes(carteiraId));
    }
}