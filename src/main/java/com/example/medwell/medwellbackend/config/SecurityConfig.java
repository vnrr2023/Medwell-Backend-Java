package com.example.medwell.medwellbackend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configureSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(customizer -> customizer.disable());
        return httpSecurity.build();
    }
}
