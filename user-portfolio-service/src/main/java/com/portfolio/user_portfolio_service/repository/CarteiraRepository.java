package com.portfolio.user_portfolio_service.repository;

import com.portfolio.user_portfolio_service.model.Carteira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarteiraRepository extends JpaRepository<Carteira, Long> {
    List<Carteira> findByUsuarioId(Long usuarioId);
}