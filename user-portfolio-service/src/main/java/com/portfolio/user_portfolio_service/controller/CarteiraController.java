package com.portfolio.user_portfolio_service.controller;

import com.portfolio.user_portfolio_service.dto.CarteiraRequestDTO;
import com.portfolio.user_portfolio_service.dto.CarteiraResponseDTO;
import com.portfolio.user_portfolio_service.service.CarteiraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @PostMapping
    public ResponseEntity<CarteiraResponseDTO> criarCarteira(
            @RequestHeader("X-User-Id") Long usuarioId,
            @Valid @RequestBody CarteiraRequestDTO dto) {
        CarteiraResponseDTO novaCarteira = carteiraService.criarCarteira(usuarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCarteira);
    }

    @GetMapping
    public ResponseEntity<List<CarteiraResponseDTO>> listarCarteiras(
            @RequestHeader("X-User-Id") Long usuarioId) {
        return ResponseEntity.ok(carteiraService.listarCarteirasDoUsuario(usuarioId));
    }
}