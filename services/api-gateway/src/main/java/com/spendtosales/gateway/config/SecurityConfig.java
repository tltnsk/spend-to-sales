package com.spendtosales.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                // CSRF is not needed because theres no browser session
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                        // anyone can register and log in
                        .pathMatchers("/api/auth/register", "/api/auth/login").permitAll()
                        // all other actions need a valid token
                        .anyExchange().authenticated()
                )
                // check the token's signature with identity-service's public key
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}