package com.portfolio.user_portfolio_service.controller;

import com.portfolio.user_portfolio_service.dto.TransacaoRequestDTO;
import com.portfolio.user_portfolio_service.dto.TransacaoResponseDTO;
import com.portfolio.user_portfolio_service.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> registrarTransacao(
            @Valid @RequestBody TransacaoRequestDTO dto) {
        TransacaoResponseDTO transacao = transacaoService.registrarTransacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacao);
    }

    @GetMapping("/carteira/{carteiraId}")
    public ResponseEntity<List<TransacaoResponseDTO>> listarExtrato(@PathVariable Long carteiraId) {
        return ResponseEntity.ok(transacaoService.listarExtrato(carteiraId));
    }
}