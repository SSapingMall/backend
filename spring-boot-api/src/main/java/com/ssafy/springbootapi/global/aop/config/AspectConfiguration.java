package com.ssafy.springbootapi.global.aop.config;

import com.ssafy.springbootapi.global.aop.aspect.UserExceptionAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
    @Bean
    public UserExceptionAspect aspect(){
        return new UserExceptionAspect();
    }
}
