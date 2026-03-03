package com.miapp.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Usamos una clave secreta fija (tiene que ser larga por seguridad)
    private static final String CLAVE_SECRETA = "MiClaveSuperSecretaParaElFinalDeProgramacion2";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(CLAVE_SECRETA.getBytes());
    
    // El token durará 1 día (en milisegundos)
    private static final long EXPIRATION_TIME = 86400000;

    // Función para crear la pulsera (Token)
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Función para leer el email que está escondido adentro del Token
    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Función para verificar que el Token sea original y no esté vencido
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Si falla, la pulsera es trucha
        }
    }
}