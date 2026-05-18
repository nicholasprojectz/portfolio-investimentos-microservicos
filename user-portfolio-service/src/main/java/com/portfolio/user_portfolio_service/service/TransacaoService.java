package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.dto.TransacaoRequestDTO;
import com.portfolio.user_portfolio_service.dto.TransacaoResponseDTO;
import com.portfolio.user_portfolio_service.model.*;
import com.portfolio.user_portfolio_service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final PosicaoRepository posicaoRepository;
    private final CarteiraRepository carteiraRepository;
    private final AtivoRepository ativoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, PosicaoRepository posicaoRepository, 
                            CarteiraRepository carteiraRepository, AtivoRepository ativoRepository) {
        this.transacaoRepository = transacaoRepository;
        this.posicaoRepository = posicaoRepository;
        this.carteiraRepository = carteiraRepository;
        this.ativoRepository = ativoRepository;
    }

    @Transactional 
    public TransacaoResponseDTO registrarTransacao(TransacaoRequestDTO dto) {
        
        Carteira carteira = carteiraRepository.findById(dto.carteiraId())
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada."));
        
        Ativo ativo = ativoRepository.findById(dto.ativoId())
                .orElseThrow(() -> new IllegalArgumentException("Ativo não encontrado."));

        Transacao transacao = new Transacao();
        transacao.setCarteira(carteira);
        transacao.setAtivo(ativo);
        transacao.setTipoOperacao(dto.tipoOperacao());
        transacao.setQuantidade(dto.quantidade());
        transacao.setPrecoUnitario(dto.precoUnitario());
        transacao.setDataTransacao(dto.dataTransacao());
        Transacao transacaoSalva = transacaoRepository.save(transacao);

        atualizarPosicao(carteira, ativo, dto);

        return new TransacaoResponseDTO(
                transacaoSalva.getId(),
                carteira.getNome(),
                ativo.getTicker(),
                transacaoSalva.getTipoOperacao(),
                transacaoSalva.getQuantidade(),
                transacaoSalva.getPrecoUnitario(),
                transacaoSalva.getDataTransacao()
        );
    }

    private void atualizarPosicao(Carteira carteira, Ativo ativo, TransacaoRequestDTO dto) {
        Posicao posicao = posicaoRepository.findByCarteiraIdAndAtivoId(carteira.getId(), ativo.getId())
                .orElse(new Posicao());

        if (posicao.getId() == null) {
            if (dto.tipoOperacao() == TipoOperacao.VENDA) {
                throw new IllegalArgumentException("Não é possível vender um ativo que você não possui.");
            }
            posicao.setCarteira(carteira);
            posicao.setAtivo(ativo);
            posicao.setQuantidadeTotal(dto.quantidade());
            posicao.setPrecoMedioAtual(dto.precoUnitario());
        } else {
            BigDecimal qtdAntiga = posicao.getQuantidadeTotal();
            BigDecimal pmAntigo = posicao.getPrecoMedioAtual();

            if (dto.tipoOperacao() == TipoOperacao.COMPRA) {
                BigDecimal valorTotalAntigo = qtdAntiga.multiply(pmAntigo);
                BigDecimal valorTotalNovo = dto.quantidade().multiply(dto.precoUnitario());
                
                BigDecimal novaQuantidade = qtdAntiga.add(dto.quantidade());
                BigDecimal novoPM = valorTotalAntigo.add(valorTotalNovo)
                        .divide(novaQuantidade, 4, RoundingMode.HALF_UP);

                posicao.setQuantidadeTotal(novaQuantidade);
                posicao.setPrecoMedioAtual(novoPM);
                
            } else if (dto.tipoOperacao() == TipoOperacao.VENDA) {
                if (qtdAntiga.compareTo(dto.quantidade()) < 0) {
                    throw new IllegalArgumentException("Saldo insuficiente de ações para realizar a venda.");
                }
                posicao.setQuantidadeTotal(qtdAntiga.subtract(dto.quantidade()));
                
                if (posicao.getQuantidadeTotal().compareTo(BigDecimal.ZERO) == 0) {
                    posicao.setPrecoMedioAtual(BigDecimal.ZERO);
                }
            }
        }
        posicaoRepository.save(posicao);
    }

    public List<TransacaoResponseDTO> listarExtrato(Long carteiraId) {
        return transacaoRepository.findByCarteiraIdOrderByDataTransacaoDesc(carteiraId).stream()
                .map(t -> new TransacaoResponseDTO(
                        t.getId(), t.getCarteira().getNome(), t.getAtivo().getTicker(),
                        t.getTipoOperacao(), t.getQuantidade(), t.getPrecoUnitario(), t.getDataTransacao()
                )).toList();
    }
}