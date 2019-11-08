package com.example.springbootdi.annotation;

import com.example.springbootdi.spi.ApolloConfigRegistrarHelper;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ApolloConfigRegistrar implements ImportBeanDefinitionRegistrar {

  /**
   * 可以获取 使用了 {@link EnableApolloConfig} 的@Configuration的类的元信息
   * @param importingClassMetadata
   * @param registry
   */
  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    System.out.println("==========ApolloConfigRegistrar    registerBeanDefinitions=============");

    // SPI 接口调用 , 调用 ApolloConfigRegistrarHelper 的实现类
    ServiceLoader<ApolloConfigRegistrarHelper> loader = ServiceLoader.load(ApolloConfigRegistrarHelper.class);

    Iterator<ApolloConfigRegistrarHelper> iterator = loader.iterator();

    iterator.forEachRemaining(e-> e.registerBeanDefinitions(importingClassMetadata, registry));
  }
}