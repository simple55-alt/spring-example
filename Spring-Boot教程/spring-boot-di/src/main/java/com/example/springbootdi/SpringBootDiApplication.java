package com.example.springbootdi;


import com.example.springbootdi.component.CalculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


/**
 * 启动
 */
@SpringBootApplication
public class SpringBootDiApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();

        builder.sources(SpringBootDiApplication.class)
                .bannerMode(Banner.Mode.OFF)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Value("${test.value}")
    private String name;


    @Autowired
    private CalculateService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("==========启动之后================");

        System.out.println("service.getDesc() = " + service.getDesc());

        System.out.println("name = " + name);
    }

}
