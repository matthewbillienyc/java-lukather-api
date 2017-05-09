package com.billie.lukather.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.validation.BeanValidationProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Created by matthew on 5/9/17.
 */
@Configuration
@EnableAsync
@EnableConfigurationProperties(AppProperties.class)
@ComponentScan({"com.billie.lukather"})
public class AppConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public BeanValidationProvider validationProvider() {
        return new BeanValidationProvider(validator());
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();

        jsonProvider.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return jsonProvider;
    }
}
