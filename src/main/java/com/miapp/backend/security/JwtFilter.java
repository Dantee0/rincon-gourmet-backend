package com.miapp.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 1. Buscamos la pulsera en el "Header" de la petición
        String authorizationHeader = request.getHeader("Authorization");

        // 2. Si trae la pulsera y empieza con "Bearer " (el formato estándar)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Cortamos la palabra "Bearer " para que quede solo el token

            // 3. Le preguntamos a la máquina si la pulsera es válida
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                // 4. Si es válida, le decimos al guardia general que lo deje pasar
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // 5. Que siga el flujo normal
        chain.doFilter(request, response);
    }
}