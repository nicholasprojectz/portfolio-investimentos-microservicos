package com.portfolio.user_portfolio_service.controller;

import com.portfolio.user_portfolio_service.dto.AtivoRequestDTO;
import com.portfolio.user_portfolio_service.dto.AtivoResponseDTO;
import com.portfolio.user_portfolio_service.service.AtivoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ativos")
public class AtivoController {

    private final AtivoService ativoService;

    public AtivoController(AtivoService ativoService) {
        this.ativoService = ativoService;
    }

    @PostMapping
    public ResponseEntity<AtivoResponseDTO> cadastrarAtivo(@Valid @RequestBody AtivoRequestDTO dto) {
        AtivoResponseDTO novoAtivo = ativoService.cadastrarAtivo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtivo);
    }

    @GetMapping
    public ResponseEntity<List<AtivoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(ativoService.listarAtivos());
    }
}