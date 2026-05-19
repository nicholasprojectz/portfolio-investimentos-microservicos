package com.portfolio.user_portfolio_service.messaging;

import com.portfolio.user_portfolio_service.config.RabbitMQConfig;
import com.portfolio.user_portfolio_service.dto.CarteiraRequestDTO;
import com.portfolio.user_portfolio_service.dto.UsuarioCriadoEventDTO;
import com.portfolio.user_portfolio_service.service.CarteiraService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class UsuarioCriadoListener {

    private final CarteiraService carteiraService;

    public UsuarioCriadoListener(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }

    @RabbitListener(queues = RabbitMQConfig.FILA_USUARIO_CRIADO)
    public void receberEventoUsuarioCriado(UsuarioCriadoEventDTO evento) {
        System.out.println("Evento recebido do RabbitMQ! Criando carteira padrão para o usuário ID: " + evento.usuarioId());
        
        CarteiraRequestDTO dto = new CarteiraRequestDTO("Carteira Principal");
        carteiraService.criarCarteira(evento.usuarioId(), dto);
    }
}