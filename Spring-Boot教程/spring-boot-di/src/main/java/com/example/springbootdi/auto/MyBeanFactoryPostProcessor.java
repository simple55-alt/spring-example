package com.example.springbootdi.auto;

import com.example.springbootdi.component.NoRegisterService;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.*;
import org.springframework.util.StringValueResolver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * {@link BeanFactoryPostProcessor} 可以获取  BeanFactory 对象 操作元信息 {@link BeanDefinition} , 在 bean 初始化之前操作的
 *
 * {@link EnvironmentAware} 可以获取环境上下文 , 其实就是我们的配置文件的信息
 *
 * {@link PriorityOrdered}  设置Bean的加载顺序 , 我们设置的是最高优先级
 * @date:2019/11/6 16:26
 * @author: <a href='mailto:fanhaodong516@qq.com'>Anthony</a>
 */
public class MyBeanFactoryPostProcessor implements EnvironmentAware, PriorityOrdered, BeanFactoryPostProcessor {

    private ConfigurableEnvironment environment;

    // source name
    private final String proName = "AnthonyPropertySources";


    private final Map<String, Object> map = new ConcurrentHashMap<>();


    public Map<String, Object> getMap() {
        return map;
    }

    private static final Multimap<Integer, String> NAMESPACE_NAMES = LinkedHashMultimap.create();


    public static boolean addNamespaces(Collection<String> namespaces, int order) {

        return NAMESPACE_NAMES.putAll(order,namespaces);
    }


    /**
     * Modify the application context's internal bean factory after its standard
     * initialization. All bean definitions will have been loaded, but no beans
     * will have been instantiated yet. This allows for overriding or adding
     * properties even to eager-initializing beans.
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        Object bean = beanFactory.getBean("noRegisterService");

        if (bean instanceof NoRegisterService) {
            NoRegisterService noRegisterService = (NoRegisterService) bean;
            noRegisterService.setValue("hhhhhhhhhhhhhhh");
        }


        System.out.println("==========PropertySourcesProcessor    postProcessBeanFactory=============");

        Collection<String> strings = NAMESPACE_NAMES.get(Ordered.LOWEST_PRECEDENCE);

        // namespace 输出
        strings.forEach(e->{
//            System.out.println("namespace : "+e);
        });

        // 获取pro
        getPro(beanFactory);

        // 设置配置属性 , 可以直接设置 对象的属性  , 不依靠spring
        setPro(beanFactory);


        // 初始化 bean , 用存在的source 初始化对象
        initWithHivingSource();


        // 可以自己创建一个  source  , 这里有问题, 好多source不可以使用 ,会出现一堆问题
        initWittCustomSource();


        // 判断类型
        /**
         * {@link DefaultListableBeanFactory}  存储了所有的元信息  spring的大家族所有存储的信息 都在里面
         */
        if (beanFactory instanceof DefaultListableBeanFactory) {
            System.out.println("-----YES , is  DefaultListableBeanFactory--------");
        }

    }


    /**
     * 初始化
     * {@link OriginTrackedMapPropertySource} 是  application.properties 的默认加载配置 ,它是最低优先级 ,
     *
     * 其实我们可以在第一个设置 ,可惜是私有的 : {@link ConfigurationPropertySourcesPropertySource} 它是最高优先级 , {name='configurationProperties'} ,但是人家是私有的
     *
     *  {@link MapPropertySource}   {name='systemProperties'}
     *
     * // spring - boot
     *  OriginTrackedMapPropertySource {name='applicationConfig: [classpath:/application.properties]'}
     *
     *  // spring -cloud
     *  OriginTrackedMapPropertySource {name='applicationConfig: [classpath:/bootstrap.properties]'}
     */
    void initWithHivingSource(){

        System.out.println("--------initWithHivingSource-----------");

        PropertySource<?> applicationConfig = environment.getPropertySources().get("applicationConfig: [classpath:/application.properties]");

/*
        // 这个优先级高
        PropertySource<?> systemProperties = environment.getPropertySources().get("systemProperties");
        if (systemProperties instanceof MapPropertySource) {
            MapPropertySource propertySource = (MapPropertySource) systemProperties;
            propertySource.getSource().put("test.value", "我又改了");
        }*/


        // 这个优先级低
        if (applicationConfig instanceof OriginTrackedMapPropertySource) {
            OriginTrackedMapPropertySource source = (OriginTrackedMapPropertySource) applicationConfig;
            source.getSource().put("test.value", "333333333333");
        }
    }


    /**
     * 定义自己的 source , 这里很有点坑 , {@link PropertySource}不能随意的定义 ,
     * 我这里使用的是 {@link MapPropertySource} 可以使用, 我使用 Apollo开发的时候,
     * 发现 {@link CompositePropertySource} 不能使用
     */
    private void initWittCustomSource() {

        System.out.println("----------initWittCustomSource------------");

        // 存在我们就不操作了
        if (environment.getPropertySources().contains(proName)) {
            //already initialized
            return ;
        }


        /**
         * 创建一个   {@link MapPropertySource}  这个可以么问题 ,但是其他的却有问题 .
         * CompositePropertySource
         */
        // name 是这个 map的名字 ---- , key 是这个map的 属性 , 跟name 么联系 ,
        // 比如 test.key=key  就是这么用 , 不需要加 proName.test.key=key

        MapPropertySource propertySource = new MapPropertySource(proName,map);

        map.put("test.value", "我比你application.properties优先级高");
        map.put("test.key", "key");

        map.put("server.port", 8098);


        // 设置环境 , 可以随意设置
        // environment.getPropertySources().addBefore("applicationConfig: [classpath:/application.properties]", propertySource);

        // 设置环境 , she置在第一位
        environment.getPropertySources().addFirst(propertySource);
    }






    /**
     * 获取 bean 的元信息
     * {@link BeanDefinition}
     * @param beanFactory
     */
    private void getBeanDefinition(ConfigurableListableBeanFactory beanFactory) {
        // 获取名字
        String[] definitionNames = beanFactory.getBeanDefinitionNames();

        for (String definitionName : definitionNames) {
            // 通过名字获取 definition
            BeanDefinition definition = beanFactory.getBeanDefinition(definitionName);
        }
    }




    /**
     * 过滤 bean 的字段
     * @param beanFactory
     */
    private void filterBean(ConfigurableListableBeanFactory beanFactory) {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            BeanDefinition definition = beanFactory.getBeanDefinition(beanName);
            StringValueResolver valueResolver = new StringValueResolver() {
                @Override
                public String resolveStringValue(String strVal) {
                    return strVal;
                }
            };
            BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
            // 处理 bean 的配置信息
            visitor.visitBeanDefinition(definition);
        }
    }



    /**
     * 设置属性 , 已知 bean 才用
     * @param beanFactory
     */
    private void setPro(ConfigurableListableBeanFactory beanFactory) {

        AbstractBeanDefinition abstractBeanDefinition = (AbstractBeanDefinition) beanFactory.getBeanDefinition("calculateService");

        System.out.println("BeanFactoryPostProcessor  : setPro");

        MutablePropertyValues pv =  abstractBeanDefinition.getPropertyValues();

        pv.addPropertyValue("desc", "Desc is changed from bean factory post processor");


        abstractBeanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
    }



    /**
     * 获取属性 , 已知 bean 才用
     * @param beanFactory
     */
    private void getPro(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("calculateService");
        System.out.println("BeanFactoryPostProcessor  : getPro");


        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
        PropertyValue[] propertyValues1 = propertyValues.getPropertyValues();

        for (PropertyValue propertyValue : propertyValues1) {
           //
        }
    }


    /**
     * 设置环境属性   你要知道 spring由于是 单例对象 , 你的私有成员变量的 改变也会改变源对象的.
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("==========PropertySourcesProcessor    setEnvironment=============");

        if (environment instanceof ConfigurableEnvironment) {
            this.environment = (ConfigurableEnvironment) environment;
        }
    }


    /**
     * 设置优先级 , 不管最高最低都会加载的
     * @return
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
