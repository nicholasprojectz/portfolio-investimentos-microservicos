package com.portfolio.api_gateway.filter;

import com.portfolio.api_gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    // Construtor injetando o seu validador matemático
    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. A LISTA VIP: Rotas de registro e login não precisam de token.
        // Se a pessoa está tentando criar conta ou logar, abra a porta e deixe passar.
        if (path.startsWith("/auth/")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Se o ingresso não existir (null) ou não for do tipo correto ("Bearer "), barramos na hora.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return barrarRequisicao(exchange); // Faltou o ingresso ou tá no formato errado!
        }

        // Cortamos a palavra "Bearer " para sobrar só o token limpo
        String tokenLimpo = authHeader.substring(7);

        // 4. A MATEMÁTICA: Passamos o token limpo para a sua classe JwtUtil.
        try {
            jwtUtil.validateToken(tokenLimpo);
        } catch (Exception e) {
            System.out.println("Tentativa de invasão ou token expirado: " + e.getMessage());
            return barrarRequisicao(exchange); // Token falso ou vencido!
        }

        // 5. O SUCESSO: Se o código chegou até aqui sem cair em nenhum bloqueio, repasse a requisição.
        return chain.filter(exchange);
    }

    // Método auxiliar para devolver o Erro 401 (Não Autorizado) e fechar a porta na cara.
    private Mono<Void> barrarRequisicao(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}