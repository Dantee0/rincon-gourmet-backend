package com.miapp.backend.controllers;

import com.miapp.backend.models.Customer;
import com.miapp.backend.services.CustomerService;
import com.miapp.backend.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private FileService fileService;

    @Autowired
    private com.miapp.backend.repositories.CustomerRepository customerRepository;
    
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public org.springframework.http.ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        java.util.Optional<Customer> customer = customerRepository.findById(id);
        
        if (customer.isPresent()) {
            return org.springframework.http.ResponseEntity.ok(customer.get());
        } else {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{id}/image", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public Customer uploadProfileImage(@PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            String fileName = fileService.saveFile(file);

            customer.setProfileImage(fileName);
            return customerRepository.save(customer);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la imagen: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/image")
    public org.springframework.http.ResponseEntity<byte[]> getCustomerImage(@PathVariable Long id) {
        java.util.Optional<Customer> customerOpt = customerRepository.findById(id);
        
        if (customerOpt.isPresent() && customerOpt.get().getProfileImage() != null) {
            try {
                String fileName = customerOpt.get().getProfileImage();
                
                java.nio.file.Path imagePath = java.nio.file.Paths.get("uploads/" + fileName); 
                
                byte[] imageBytes = java.nio.file.Files.readAllBytes(imagePath);

                return org.springframework.http.ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                        .body(imageBytes);
            } catch (Exception e) {
                return org.springframework.http.ResponseEntity.notFound().build();
            }
        } else {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}/password")
    public org.springframework.http.ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody java.util.Map<String, String> body) {
        java.util.Optional<Customer> customerOpt = customerRepository.findById(id);
        
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            String newPassword = body.get("newPassword");
            
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                customer.setPassword(newPassword);
                customerRepository.save(customer);
                return org.springframework.http.ResponseEntity.ok().body("Contraseña actualizada con éxito");
            } else {
                return org.springframework.http.ResponseEntity.badRequest().body("La contraseña no puede estar vacía");
            }
        }
        return org.springframework.http.ResponseEntity.notFound().build();
    }

}