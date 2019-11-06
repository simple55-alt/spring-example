package com.example.springbootdi.auto;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  自动装配  {@link EnableAutoConfiguration} , 需要实现这个 ........
 *
 * @date:2019/11/6 19:05
 * @author: <a href='mailto:fanhaodong516@qq.com'>Anthony</a>
 */
@Configuration
// 需要加上  auto.processor=true , 才可以实现自动注入
@ConditionalOnProperty(prefix="auto",name = "processor", havingValue = "true")
@ConditionalOnMissingBean(MyBeanFactoryPostProcessor.class)
public class EnableAutoMyBeanPostProcessor {
    @Bean
    public MyBeanFactoryPostProcessor configPropertySourcesProcessor() {
        return new MyBeanFactoryPostProcessor();
    }
}
