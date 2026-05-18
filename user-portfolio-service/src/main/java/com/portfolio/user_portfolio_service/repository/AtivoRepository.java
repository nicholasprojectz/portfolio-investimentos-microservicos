package com.portfolio.user_portfolio_service.repository;

import com.portfolio.user_portfolio_service.model.Ativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtivoRepository extends JpaRepository<Ativo, Long> {
    Optional<Ativo> findByTicker(String ticker);
}