package com.miapp.backend.repositories;

import com.miapp.backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Esta función mágica busca a un cliente por su email en la base de datos
    Optional<Customer> findByEmail(String email);
    
}