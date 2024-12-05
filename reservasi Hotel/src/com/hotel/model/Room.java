package com.hotel.model;

public class Room {
    private String roomNumber;
    private String roomType;
    private double pricePerNight;

    public Room(String roomNumber, String roomType, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + roomType + ") - Rp " + pricePerNight + "/night";
    }
}
