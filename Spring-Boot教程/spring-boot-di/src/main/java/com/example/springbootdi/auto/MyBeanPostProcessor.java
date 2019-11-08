package com.example.springbootdi.auto;

import com.example.springbootdi.component.CalculateService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 *   {@link BeanPostProcessor}   在 bean 初试完以后 , 调用这个方法
 *
 * @date:2019/11/6 19:19
 * @author: <a href='mailto:fanhaodong516@qq.com'>Anthony</a>
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    // 实例化、依赖注入完毕，在调用显示的初始化之前完成一些定制的初始化任务
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equals("calculateService")) {
            if (bean instanceof CalculateService) {
                System.out.println("==========MyBeanPostProcessor    postProcessBeforeInitialization=============");
                CalculateService bean1 = (CalculateService) bean;
                bean1.setDesc("MyBeanPostProcessor 设置的属性");
            }
        }
        return bean;
    }


    //实例化、依赖注入、初始化完毕时执行
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if (beanName.equals("calculateService")) {
            if (bean instanceof CalculateService) {
                System.out.println("==========MyBeanPostProcessor    postProcessAfterInitialization=============");
            }
        }
        return bean;
    }
}
