package com.example.springwebflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * TODO
 *
 * @date:2019/11/7 19:38
 * @author: <a href='mailto:fanhaodong516@qq.com'>Anthony</a>
 */
@RestController
public class Test {

    @Autowired
    private HttpServletRequest request;


    @PostMapping("/post")
    public void get(@RequestParam("name") String name, @RequestParam("age") String age, HttpServletRequest request) throws IOException {

        byte[] bytes = new byte[1024];



        System.out.println(name);

        System.out.println(age);

    }

}
