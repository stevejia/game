package com.gongyu.application.distribute.game.config;

import com.gongyu.application.distribute.game.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class LoginInterceptorConfig {

    @Bean
    public MethodValidationPostProcessor getBean() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}
