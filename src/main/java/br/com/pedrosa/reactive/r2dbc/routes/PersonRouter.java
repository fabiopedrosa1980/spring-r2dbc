package br.com.pedrosa.reactive.r2dbc.routes;

import br.com.pedrosa.reactive.r2dbc.handler.PersonHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PersonRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(PersonHandler personHandler) {
        return route(GET("/person"), personHandler::all)
                .andRoute(POST("/person"), personHandler::save)
                .andRoute(PUT("/person/{id}"), personHandler::update)
                .andRoute(GET("/person/{id}"), personHandler::get)
                .andRoute(DELETE("/person/{id}"), personHandler::delete);

    }

}