package com.example.springwebflux.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Arrays;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * 全局router
 */
@Configuration
public class DemoRouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/"), request -> ok()
                .body(
                        BodyInserters.fromObject(
                                Arrays.asList(
                                        Message.builder().body("hello Spring 5").build(),
                                        Message.builder().body("hello Spring Boot 2.0.4").build(),
                                        Message.builder().body("hello Spring WebFlux").build()
                                )
                        )
                )
        );
    }
}
