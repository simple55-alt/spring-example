package com.example.springbootdi;


import com.example.springbootdi.auto.MyBeanFactoryPostProcessor;
import com.example.springbootdi.component.CalculateService;
import com.example.springbootdi.component.NoRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;


/**
 * 启动
 */

@SpringBootApplication
public class SpringBootDiApplication implements ApplicationRunner {

    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();

         context = builder.sources(SpringBootDiApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Value("${test.value}")
    private String name;


    @Autowired
    private CalculateService service;


    @Autowired
    private MyBeanFactoryPostProcessor postProcessor;


    @Autowired
    private NoRegisterService registerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==========启动之后================");

        System.out.println("service.getDesc() = " + service.getDesc());

        System.out.println("name = " + name);

        Map<String, Object> map = postProcessor.getMap();

        map.put("test.value", "修改");


        System.out.println("registerService.getValue() = " + registerService.getValue());
    }

}
