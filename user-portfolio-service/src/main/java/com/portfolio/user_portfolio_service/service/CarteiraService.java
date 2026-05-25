package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.dto.CarteiraRequestDTO;
import com.portfolio.user_portfolio_service.repository.AtivoRepository;
import org.springframework.stereotype.Service;

@Service
public class CarteiraService {

    private final AtivoRepository ativoRepository;

    public CarteiraService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    public void criarCarteira(Long usuarioId, CarteiraRequestDTO dto) {
        System.out.println("Criando carteira " + dto.nome() + " para o usuario ID: " + usuarioId);
        // Lógica futura de salvamento no banco
    }
}