package com.tripleseven.frontapi.config.web;

import com.tripleseven.frontapi.config.interceptor.LoginStatusInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginStatusInterceptor loginStatusInterceptor;

    public WebConfig(LoginStatusInterceptor loginStatusInterceptor) {
        this.loginStatusInterceptor = loginStatusInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginStatusInterceptor)
                .addPathPatterns("/**");
    }
}