package com.ciccone.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // APIs + Postman: CSRF off (we’ll do proper JWT later)
            .csrf(csrf -> csrf.disable())

            // Allow your POST for now (so you can test)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.PUT).permitAll()
                .requestMatchers(HttpMethod.DELETE).permitAll()
                .anyRequest().authenticated()
            )

            // Keep the browser login screen working
            .formLogin(Customizer.withDefaults())

            // Enable Basic Auth (Postman can use this too)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}