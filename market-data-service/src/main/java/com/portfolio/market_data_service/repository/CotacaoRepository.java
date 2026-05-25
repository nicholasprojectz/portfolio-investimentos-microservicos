package com.portfolio.market_data_service.repository;

import com.portfolio.market_data_service.model.Cotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
    Optional<Cotacao> findByTicker(String ticker);
}