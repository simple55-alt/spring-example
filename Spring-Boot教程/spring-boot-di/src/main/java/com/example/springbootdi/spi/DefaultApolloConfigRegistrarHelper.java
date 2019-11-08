package com.example.springbootdi.spi;

import com.example.springbootdi.annotation.EnableApolloConfig;
import com.example.springbootdi.auto.MyBeanFactoryPostProcessor;
import com.example.springbootdi.component.NoRegisterService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 设置属性  ---- >
 */
public class DefaultApolloConfigRegistrarHelper implements ApolloConfigRegistrarHelper {

    private static final String CLASSNAME = EnableApolloConfig.class.getName();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        System.out.println("importingClassMetadata = " + importingClassMetadata);





        // 获取注解  key - value
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(CLASSNAME);


        // 将注解的属性 设置为 一个 MAP
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationAttributes);


        int order = attributes.getNumber("order");


        String[] namespaces = attributes.getStringArray("value");


        // 放入到  MyBeanFactoryPostProcessor 中
        MyBeanFactoryPostProcessor.addNamespaces(Lists.newArrayList(namespaces), order);



        // 这里可以传入一个bean
        BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(NoRegisterService.class).getBeanDefinition();

        registry.registerBeanDefinition("noRegisterService", beanDefinition);

    }


}
