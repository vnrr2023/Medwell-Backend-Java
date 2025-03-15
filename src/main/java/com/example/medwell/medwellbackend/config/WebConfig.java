package com.example.medwell.medwellbackend.config;

import com.example.medwell.medwellbackend.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowCredentials(true)
//                .allowedHeaders("*")
//                .maxAge(3600)
//                .allowedOrigins("http://localhost:8000");
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/doctor_service/*","/marketting/*","/appointment/**","/doctor/data/*")
                .excludePathPatterns("/marketting/generate_mail_body");
    }
}
