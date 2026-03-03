package com.miapp.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reservationDate; // Fecha y hora
    private Integer numberOfPeople; // Cantidad de personas
    private String status; // Ej: "CONFIRMADA", "CANCELADA"

    // ¡Acá está la magia de la relación!
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Reservation() {
    }

    public Reservation(LocalDateTime reservationDate, Integer numberOfPeople, String status, Customer customer) {
        this.reservationDate = reservationDate;
        this.numberOfPeople = numberOfPeople;
        this.status = status;
        this.customer = customer;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDateTime reservationDate) { this.reservationDate = reservationDate; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}