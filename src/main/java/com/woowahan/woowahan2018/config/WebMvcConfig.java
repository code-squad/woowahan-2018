package com.woowahan.woowahan2018.config;

import com.woowahan.woowahan2018.interceptor.LoggerInterceptor;
import com.woowahan.woowahan2018.security.SignedInUserHandlerMethodArgumentResolver;
import com.woowahan.woowahan2018.support.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private LoggerInterceptor loggerInterceptor;

    @Autowired
    private SignedInUserHandlerMethodArgumentResolver signedInUserHandlerMethodArgumentResolver;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor)
                .addPathPatterns("/**");
    }

    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(signedInUserHandlerMethodArgumentResolver);
    }
}
