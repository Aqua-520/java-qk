package com.wcy.config;

import com.wcy.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 实现spring提供的webConfig接口
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 需要将拦截器对象传入到配置对象的属性身上生效
    @Autowired
    private LoginInterceptor loginInterceptor;

    // 重写添加拦截器的方法

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截规则
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login",
                        "/register",
                        "/error",              // Spring Boot 错误页
                        "/favicon.ico",
                        "/doc.html",           // knife4j
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/webjars/**",
                        "/static/**",
                        "/public/**"
                );
    }
}
