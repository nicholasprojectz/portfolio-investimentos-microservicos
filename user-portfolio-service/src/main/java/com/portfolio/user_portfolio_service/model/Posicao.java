package com.portfolio.user_portfolio_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_posicoes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"carteira_id", "ativo_id"})
})
@Getter
@Setter
@NoArgsConstructor
public class Posicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carteira_id", nullable = false)
    private Carteira carteira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ativo_id", nullable = false)
    private Ativo ativo;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantidadeTotal;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal precoMedioAtual;
}