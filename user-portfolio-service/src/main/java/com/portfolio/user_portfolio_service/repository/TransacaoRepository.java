package com.portfolio.user_portfolio_service.repository;

import com.portfolio.user_portfolio_service.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByCarteiraIdOrderByDataTransacaoDesc(Long carteiraId);
}