package com.example.springwebflux.web;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
public class FirstWebFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        serverWebExchange.getAttributes().put("User", "jerry");
        return webFilterChain.filter(serverWebExchange);
    }


    public static void main(String[] args) {
        // 创建  - > 发布
        Flux.create(t -> {
            t.next("create");
            t.next("create1");
            t.complete();
        }).subscribe(System.out::println);



        //注意generate中next只能调用1次
        Flux.generate(t -> {
            t.next("generate");

            t.complete();
        }).subscribe(System.out::println);


        //单个元素
        Flux<String> just = Flux.just("just");

        //多个元素
        Flux.just("just", "just1", "just2").subscribe(System.out::println);
    }
}