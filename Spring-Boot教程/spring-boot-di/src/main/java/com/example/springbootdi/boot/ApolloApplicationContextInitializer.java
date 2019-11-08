package com.example.springbootdi.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;


public class ApolloApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = 0;


    private int order = DEFAULT_ORDER;

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("==========ApolloApplicationContextInitializer    postProcessEnvironment=============");

        CompositePropertySource composite = new CompositePropertySource("ApolloBootstrapPropertySources");

        environment.getPropertySources().addFirst(composite);
    }


    /**
     * Initialize the given application context.
     *
     *
     * @param applicationContext the application to configure
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("==========ApolloApplicationContextInitializer    applicationContext================");
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return order;
    }
}