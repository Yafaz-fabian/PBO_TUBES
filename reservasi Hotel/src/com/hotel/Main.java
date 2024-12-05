package com.hotel;

import com.hotel.model.Guest;
import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.ReservationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private static ReservationService reservationService = new ReservationService();

    public static void main(String[] args) {
        // Membuat frame utama
        JFrame frame = new JFrame("Sistem Reservasi Hotel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 2));

        // Komponen input
        JLabel nameLabel = new JLabel("Nama Tamu:");
        JTextField nameField = new JTextField();

        JLabel contactLabel = new JLabel("Nomor Kontak:");
        JTextField contactField = new JTextField();

        JLabel roomTypeLabel = new JLabel("Tipe Kamar:");
        String[] roomTypes = {"Single (Rp 200.000)", "Double (Rp 400.000)", "Suite (Rp 800.000)"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);

        JLabel nightsLabel = new JLabel("Jumlah Malam:");
        JTextField nightsField = new JTextField();

        JButton submitButton = new JButton("Reservasi");
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);

        // Tambahkan komponen ke frame
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(contactLabel);
        frame.add(contactField);
        frame.add(roomTypeLabel);
        frame.add(roomTypeComboBox);
        frame.add(nightsLabel);
        frame.add(nightsField);
        frame.add(submitButton);
        frame.add(new JLabel()); // Placeholder
        frame.add(new JLabel("Hasil Reservasi:"));
        frame.add(new JScrollPane(resultArea));

        // Event untuk tombol reservasi
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String contact = contactField.getText();
                String roomType = (String) roomTypeComboBox.getSelectedItem();
                int nights;

                // Validasi input
                try {
                    nights = Integer.parseInt(nightsField.getText());
                    if (nights <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Jumlah malam harus berupa angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Menentukan harga berdasarkan tipe kamar
                double pricePerNight;
                if (roomType.contains("Single")) {
                    pricePerNight = 200000;
                } else if (roomType.contains("Double")) {
                    pricePerNight = 400000;
                } else {
                    pricePerNight = 800000;
                }

                // Membuat reservasi
                Guest guest = new Guest(name, contact);
                Room room = new Room("Tipe", roomType, pricePerNight);
                Reservation reservation = new Reservation(guest, room, nights);
                reservationService.addReservation(reservation);

                // Tampilkan hasil di area teks
                resultArea.setText("");
                for (Reservation r : reservationService.getAllReservations()) {
                    resultArea.append(r.toString() + "\n");
                }

                // Bersihkan input
                nameField.setText("");
                contactField.setText("");
                nightsField.setText("");
            }
        });

        // Tampilkan frame
        frame.setVisible(true);
    }
}