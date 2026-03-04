package com.miapp.backend.controllers;

import com.miapp.backend.models.FoodOrder;
import com.miapp.backend.services.FoodOrderService;
import com.miapp.backend.repositories.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class FoodOrderController {

    @Autowired
    private FoodOrderService foodOrderService;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @PostMapping
    public FoodOrder createOrder(@RequestBody FoodOrder order) {
        return foodOrderService.createOrder(order);
    }

    @GetMapping
    public List<FoodOrder> getAllOrders() {
        return foodOrderService.getAllOrders();
    }

    @PutMapping("/{id}/entregar")
    public org.springframework.http.ResponseEntity<?> entregarPedido(@PathVariable Long id) {
        java.util.Optional<FoodOrder> orderOpt = foodOrderRepository.findById(id);
        if (orderOpt.isPresent()) {
            FoodOrder pedido = orderOpt.get();
            pedido.setStatus("ENTREGADO");
            foodOrderRepository.save(pedido);
            return org.springframework.http.ResponseEntity.ok().build();
        } else {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            foodOrderRepository.deleteById(id);
            return org.springframework.http.ResponseEntity.ok().build();
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.badRequest().build();
        }
    }
}