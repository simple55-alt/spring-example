package com.example.springwebflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class MessageController {


    @GetMapping("/get")
    public Flux<Message> allMessages(){
        return Flux.just(
            Message.builder().body("hello Spring 5").build(),
            Message.builder().body("hello Spring Boot 2").build()
        );
    }  
}
