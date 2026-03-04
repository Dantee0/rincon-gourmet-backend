package com.miapp.backend.controllers;

import com.miapp.backend.models.Reservation;
import com.miapp.backend.services.ReservationService;
import com.miapp.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PutMapping("/{id}/confirmar")
    public org.springframework.http.ResponseEntity<?> confirmarReserva(@PathVariable Long id) {
        java.util.Optional<Reservation> res = reservationRepository.findById(id);
        
        if (res.isPresent()) {
            Reservation reserva = res.get();
            reserva.setStatus("CONFIRMADA");
            reservationRepository.save(reserva);
            return org.springframework.http.ResponseEntity.ok().build();
        } else {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> deleteReservation(@PathVariable Long id) {
        try {
            reservationRepository.deleteById(id);
            return org.springframework.http.ResponseEntity.ok().build();
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.badRequest().build();
        }
    }
}