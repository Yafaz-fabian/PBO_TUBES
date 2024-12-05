package com.hotel.service;

import com.hotel.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private List<Reservation> reservations;

    public ReservationService() {
        this.reservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation added: " + reservation);
    }

    public void listReservations() {
        System.out.println("List of Reservations:");
        reservations.forEach(System.out::println);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
        System.out.println("Reservation removed: " + reservation);
    }
}
