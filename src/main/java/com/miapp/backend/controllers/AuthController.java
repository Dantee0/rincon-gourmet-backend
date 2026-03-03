package com.miapp.backend.controllers;

import com.miapp.backend.dto.LoginRequest;
import com.miapp.backend.models.Customer;
import com.miapp.backend.repositories.CustomerRepository;
import com.miapp.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // 1. Buscamos al usuario por su email en la base de datos
        Optional<Customer> customerOpt = customerRepository.findByEmail(loginRequest.getEmail());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            // 2. Comparamos la contraseña (Ojo: en producción esto va encriptado)
            if (customer.getPassword().equals(loginRequest.getPassword())) {
                // 3. Si coincide, le fabricamos el Token y se lo damos
                String token = jwtUtil.generateToken(customer.getEmail());
                return ResponseEntity.ok(token);
            }
        }
        // Si algo falla, le decimos que no puede entrar
        return ResponseEntity.status(401).body("Credenciales incorrectas");
    }
}