package com.hotel.model;

public class Reservation {
    private Guest guest;
    private Room room;
    private int nights;

    public Reservation(Guest guest, Room room, int nights) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    public double calculateTotalCost() {
        return room.getPricePerNight() * nights;
    }

    @Override
    public String toString() {
        return "Reservation: " + guest.getName() + " - Room " + room.getRoomNumber() +
                " (" + room.getRoomType() + "), " + nights + " night(s), Total: Rp " +
                calculateTotalCost();
    }
}
