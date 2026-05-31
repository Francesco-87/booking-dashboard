package com.ciccone.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Spring configuration class that defines security settings for the entire application
@Configuration
public class SecurityConfig {

    // Bean that creates and configures the security filter chain responsible for handling HTTP request authorization
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disables CSRF (Cross-Site Request Forgery) protection - necessary for API endpoints; JWT will handle token-based security later
            .csrf(csrf -> csrf.disable())
            // Enables CORS (Cross-Origin Resource Sharing) with default settings to allow requests from the frontend
            .cors(Customizer.withDefaults())

            // Configures authorization rules for different HTTP methods - currently allows all requests for development/testing
            .authorizeHttpRequests(auth -> auth
                // Permits all POST requests without authentication
                .requestMatchers(HttpMethod.POST).permitAll()
                // Permits all GET requests without authentication
                .requestMatchers(HttpMethod.GET).permitAll()
                // Permits all PUT requests without authentication
                .requestMatchers(HttpMethod.PUT).permitAll()
                // Permits all PATCH requests without authentication
                .requestMatchers(HttpMethod.PATCH).permitAll()
                // Permits all DELETE requests without authentication
                .requestMatchers(HttpMethod.DELETE).permitAll()
                // Requires authentication for any other request types
                .anyRequest().authenticated()
            )

            // Enables form-based login so users can authenticate through a browser login screen
            .formLogin(Customizer.withDefaults())

            // Enables HTTP Basic Authentication, allowing clients like Postman to send credentials in the request header
            .httpBasic(Customizer.withDefaults());

        // Builds and returns the fully configured security filter chain
        return http.build();
    }
}