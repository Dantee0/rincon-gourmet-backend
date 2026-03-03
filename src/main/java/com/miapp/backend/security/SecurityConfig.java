package com.miapp.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Rutas que NO piden pulsera (Login, Registrarse y Swagger)
                .requestMatchers("/api/auth/login", "/api/customers", "/api/customers/*/image", "/uploads/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                // TODO el resto de la app AHORA PIDE PULSERA SI O SI
                .anyRequest().authenticated() 
            )
            // Ponemos a nuestro Patovica (JwtFilter) antes del guardia por defecto de Spring
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}