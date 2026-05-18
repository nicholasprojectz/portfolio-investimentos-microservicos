package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.dto.AtivoRequestDTO;
import com.portfolio.user_portfolio_service.dto.AtivoResponseDTO;
import com.portfolio.user_portfolio_service.model.Ativo;
import com.portfolio.user_portfolio_service.repository.AtivoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AtivoService {

    private final AtivoRepository ativoRepository;

    public AtivoService(AtivoRepository ativoRepository) {
        this.ativoRepository = ativoRepository;
    }

    public AtivoResponseDTO cadastrarAtivo(AtivoRequestDTO dto) {
        if (ativoRepository.findByTicker(dto.ticker().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Ativo com este ticker já existe.");
        }

        Ativo ativo = new Ativo();
        ativo.setTicker(dto.ticker().toUpperCase());
        ativo.setNome(dto.nome());
        ativo.setTipo(dto.tipo());

        Ativo salvo = ativoRepository.save(ativo);
        return new AtivoResponseDTO(salvo.getId(), salvo.getTicker(), salvo.getNome(), salvo.getTipo());
    }

    public List<AtivoResponseDTO> listarAtivos() {
        return ativoRepository.findAll().stream()
                .map(a -> new AtivoResponseDTO(a.getId(), a.getTicker(), a.getNome(), a.getTipo()))
                .toList();
    }
}