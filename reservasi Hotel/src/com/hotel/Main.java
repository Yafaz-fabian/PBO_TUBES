package com.hotel;

import com.hotel.model.Guest;
import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.ReservationService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationService reservationService = new ReservationService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== SISTEM RESERVASI HOTEL ===");

        System.out.print("Masukkan nama tamu: ");
        String guestName = scanner.nextLine();
        System.out.print("Masukkan nomor kontak tamu: ");
        String contactNumber = scanner.nextLine();
        Guest guest = new Guest(guestName, contactNumber);

        System.out.println("\nPilih tipe kamar:");
        System.out.println("1. Single (Rp 200.000 per malam)");
        System.out.println("2. Double (Rp 400.000 per malam)");
        System.out.println("3. Suite  (Rp 800.000 per malam)");
        System.out.print("Masukkan pilihan (1/2/3): ");
        int roomChoice = scanner.nextInt();

        String roomType;
        double pricePerNight;
        switch (roomChoice) {
            case 1 -> {
                roomType = "Single";
                pricePerNight = 200000;
            }
            case 2 -> {
                roomType = "Double";
                pricePerNight = 400000;
            }
            case 3 -> {
                roomType = "Suite";
                pricePerNight = 800000;
            }
            default -> {
                System.out.println("Pilihan tidak valid. Menggunakan tipe Single secara default.");
                roomType = "Single";
                pricePerNight = 200000;
            }
        }

        System.out.print("Masukkan jumlah malam: ");
        int nights = scanner.nextInt();
        scanner.nextLine(); 

        Room room = new Room("Pilihan", roomType, pricePerNight);
        Reservation reservation = new Reservation(guest, room, nights);
        reservationService.addReservation(reservation);

        System.out.println("\n=== DAFTAR RESERVASI ===");
        reservationService.listReservations();
    }
}
