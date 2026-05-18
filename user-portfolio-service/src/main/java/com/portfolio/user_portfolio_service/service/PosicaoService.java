package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.dto.PosicaoResponseDTO;
import com.portfolio.user_portfolio_service.repository.PosicaoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PosicaoService {

    private final PosicaoRepository posicaoRepository;

    public PosicaoService(PosicaoRepository posicaoRepository) {
        this.posicaoRepository = posicaoRepository;
    }

    public List<PosicaoResponseDTO> listarPosicoes(Long carteiraId) {
        return posicaoRepository.findByCarteiraId(carteiraId).stream()
                .map(p -> new PosicaoResponseDTO(
                        p.getId(),
                        p.getAtivo().getTicker(),
                        p.getAtivo().getNome(),
                        p.getQuantidadeTotal(),
                        p.getPrecoMedioAtual()
                )).toList();
    }
}