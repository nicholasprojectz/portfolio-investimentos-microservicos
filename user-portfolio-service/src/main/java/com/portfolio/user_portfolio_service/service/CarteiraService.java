package com.portfolio.user_portfolio_service.service;

import com.portfolio.user_portfolio_service.dto.CarteiraRequestDTO;
import com.portfolio.user_portfolio_service.dto.CarteiraResponseDTO;
import com.portfolio.user_portfolio_service.model.Carteira;
import com.portfolio.user_portfolio_service.repository.CarteiraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarteiraService {

    private final CarteiraRepository carteiraRepository;

    public CarteiraService(CarteiraRepository carteiraRepository) {
        this.carteiraRepository = carteiraRepository;
    }

    public CarteiraResponseDTO criarCarteira(Long usuarioId, CarteiraRequestDTO dto) {
        Carteira carteira = new Carteira();
        carteira.setUsuarioId(usuarioId);
        carteira.setNome(dto.nome());
        carteira.setDataCriacao(LocalDateTime.now()); // O sistema define a data

        Carteira carteiraSalva = carteiraRepository.save(carteira);

        return new CarteiraResponseDTO(
                carteiraSalva.getId(),
                carteiraSalva.getNome(),
                carteiraSalva.getDataCriacao()
        );
    }

    public List<CarteiraResponseDTO> listarCarteirasDoUsuario(Long usuarioId) {
        return carteiraRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(carteira -> new CarteiraResponseDTO(
                        carteira.getId(),
                        carteira.getNome(),
                        carteira.getDataCriacao()
                ))
                .toList();
    }
}