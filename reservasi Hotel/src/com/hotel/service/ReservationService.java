package com.hotel.service;

import com.hotel.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private List<Reservation> reservations;

    public ReservationService() {
        reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added: " + reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    public void listReservations() {
        System.out.println("List of Reservations:");
        reservations.forEach(System.out::println);
    }
}