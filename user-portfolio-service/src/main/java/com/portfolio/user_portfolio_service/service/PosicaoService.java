package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.client.MarketDataClient;
import com.portfolio.user_portfolio_service.dto.CotacaoExternaDTO;
import com.portfolio.user_portfolio_service.dto.PosicaoAtualizadaDTO;
import com.portfolio.user_portfolio_service.dto.PosicaoResponseDTO;
import com.portfolio.user_portfolio_service.model.Posicao;
import com.portfolio.user_portfolio_service.repository.PosicaoRepository;
import org.springframework.stereotype.Service;
import com.portfolio.user_portfolio_service.dto.CarteiraConsolidadaDTO;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PosicaoService {

    private final PosicaoRepository posicaoRepository;
    private final MarketDataClient marketDataClient;

    public PosicaoService(PosicaoRepository posicaoRepository, MarketDataClient marketDataClient) {
        this.posicaoRepository = posicaoRepository;
        this.marketDataClient = marketDataClient;
    }

    // O seu método original restaurado
    public List<PosicaoResponseDTO> listarPosicoes(Long carteiraId) {
        // Presumindo que você tenha o findByCarteiraId no repository (padrão do Spring Data)
        return posicaoRepository.findByCarteiraId(carteiraId).stream()
                .map(p -> new PosicaoResponseDTO(
                        p.getId(),
                        p.getAtivo().getTicker(),
                        p.getAtivo().getNome(),
                        p.getQuantidadeTotal(),
                        p.getPrecoMedioAtual()
                )).toList();
    }

    // O método de rentabilidade com os getters gerados pelo Lombok
    public PosicaoAtualizadaDTO calcularRentabilidade(Long posicaoId) {
        Posicao posicao = posicaoRepository.findById(posicaoId)
                .orElseThrow(() -> new IllegalArgumentException("Posição não encontrada com ID: " + posicaoId));

        String ticker = posicao.getAtivo().getTicker();

        CotacaoExternaDTO cotacao = marketDataClient.obterCotacao(ticker);
        BigDecimal precoDeHoje = cotacao.precoAtual();

        BigDecimal quantidade = posicao.getQuantidadeTotal();
        BigDecimal patrimonioTotal = precoDeHoje.multiply(quantidade);
        
        BigDecimal totalInvestido = posicao.getPrecoMedioAtual().multiply(quantidade);
        BigDecimal lucroOuPrejuizo = patrimonioTotal.subtract(totalInvestido);

        return new PosicaoAtualizadaDTO(
                ticker,
                quantidade,
                posicao.getPrecoMedioAtual(),
                precoDeHoje,
                patrimonioTotal,
                lucroOuPrejuizo
        );
    }

    public CarteiraConsolidadaDTO calcularRentabilidadeCarteira(Long carteiraId) {
        // 1. Busca todas as posições da carteira
        List<Posicao> posicoes = posicaoRepository.findByCarteiraId(carteiraId);
        
        BigDecimal patrimonioTotal = BigDecimal.ZERO;
        BigDecimal totalInvestido = BigDecimal.ZERO;
        List<PosicaoAtualizadaDTO> posicoesAtualizadas = new java.util.ArrayList<>();

        // 2. Roda a matemática individual para cada ativo
        for (Posicao posicao : posicoes) {
            PosicaoAtualizadaDTO dto = calcularRentabilidade(posicao.getId());
            posicoesAtualizadas.add(dto);
            
            patrimonioTotal = patrimonioTotal.add(dto.patrimonioTotal());
            
            BigDecimal investidoPosicao = dto.precoMedio().multiply(dto.quantidade());
            totalInvestido = totalInvestido.add(investidoPosicao);
        }

        // 3. Calcula o lucro e o percentual geral
        BigDecimal rentabilidadeReais = patrimonioTotal.subtract(totalInvestido);
        BigDecimal rentabilidadePercentual = BigDecimal.ZERO;
        
        if (totalInvestido.compareTo(BigDecimal.ZERO) > 0) {
            rentabilidadePercentual = rentabilidadeReais
                    .divide(totalInvestido, 4, java.math.RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
        }

        return new CarteiraConsolidadaDTO(
                patrimonioTotal,
                totalInvestido,
                rentabilidadeReais,
                rentabilidadePercentual,
                posicoesAtualizadas
        );
    }
}