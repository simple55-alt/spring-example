package com.example.springwebflux.web;

import org.springframework.context.annotation.Bean;


import java.util.Arrays;



/**
 * 全局router
 */
//@Configuration
public class DemoRouterConfig {
   /* @Bean
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
    }*/
}
