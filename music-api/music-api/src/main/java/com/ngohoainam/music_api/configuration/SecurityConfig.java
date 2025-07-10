package com.ngohoainam.music_api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    private static final String[] PUBLIC_ENDPOINTS ={
            "/songs",

    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(request -> request
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

}
