package com.miapp.backend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "food_orders")
public class FoodOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // Ej: "EN PREPARACION", "ENTREGADO"
    private Double totalAmount; // El precio total del pedido

    // Relación con el Cliente (Quién lo pidió)
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Relación con los Platos (Qué pidió)
    @ManyToMany
    @JoinTable(
        name = "order_dishes",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    public FoodOrder() {
    }

    public FoodOrder(String status, Double totalAmount, Customer customer, List<Dish> dishes) {
        this.status = status;
        this.totalAmount = totalAmount;
        this.customer = customer;
        this.dishes = dishes;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<Dish> getDishes() { return dishes; }
    public void setDishes(List<Dish> dishes) { this.dishes = dishes; }
}