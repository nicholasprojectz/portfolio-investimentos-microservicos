package com.portfolio.user_portfolio_service.controller;

import com.portfolio.user_portfolio_service.service.CarteiraService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carteira")
public class CarteiraController {

    private final CarteiraService carteiraService;

    public CarteiraController(CarteiraService carteiraService) {
        this.carteiraService = carteiraService;
    }
    
    // Os métodos futuros da carteira (como listar e criar via API) vão aqui.
}