package com.ms.ms_security.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Component("authRouter")
public class AuthRouter {

    @Bean
    public RouterFunction<?> routeProduct(AuthHandler authHandler){
        return route()
                .POST("/auth/login", accept(MediaType.APPLICATION_JSON), authHandler::login)
                .POST("/auth/join", accept(MediaType.APPLICATION_JSON), authHandler::join)
                .POST("/auth/delete", accept(MediaType.APPLICATION_JSON), authHandler::delete)
                .POST("/auth/update", accept(MediaType.APPLICATION_JSON), authHandler::update)
                .GET("/auth/check", authHandler::check)
                .GET("/auth/info/{id}", authHandler::info)
                .build();
    }
}
