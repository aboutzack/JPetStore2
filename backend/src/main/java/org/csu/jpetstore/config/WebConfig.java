package org.csu.jpetstore.config;


import org.csu.jpetstore.interceptor.JwtAuthorizationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有路径
        registry.addInterceptor(jwtAuthenticationInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public JwtAuthorizationInterceptor jwtAuthenticationInterceptor() {
        return new JwtAuthorizationInterceptor();
    }
}
