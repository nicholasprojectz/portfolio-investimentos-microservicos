package com.portfolio.user_portfolio_service.repository;

import com.portfolio.user_portfolio_service.model.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PosicaoRepository extends JpaRepository<Posicao, Long> {
    List<Posicao> findByCarteiraId(Long carteiraId);
    
    Optional<Posicao> findByCarteiraIdAndAtivoId(Long carteiraId, Long ativoId);
}