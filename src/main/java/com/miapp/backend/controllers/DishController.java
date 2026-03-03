package com.miapp.backend.controllers;

import com.miapp.backend.models.Dish;
import com.miapp.backend.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.miapp.backend.repositories.DishRepository;

@RestController
@RequestMapping("/api/dishes")
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DishService dishService;

    @PostMapping
    public Dish createDish(@RequestBody Dish dish) {
        return dishService.createDish(dish);
    }

    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    // Endpoint exclusivo para borrar un plato por su ID
    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> deleteDish(@PathVariable Long id) {
        try {
            dishRepository.deleteById(id);
            return org.springframework.http.ResponseEntity.ok().build();
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.badRequest().build();
        }
    }
}