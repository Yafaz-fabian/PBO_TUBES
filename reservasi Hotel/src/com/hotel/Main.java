package com.hotel;

import com.hotel.model.Guest;
import com.hotel.model.Reservation;
import com.hotel.model.Room;
import com.hotel.service.ReservationService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main {
    private static ReservationService reservationService = new ReservationService();

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Sistem Reservasi Hotel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 245));


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel titleLabel = new JLabel("Form Reservasi Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));

        JTextField nameField = createStyledTextField();
        JTextField contactField = createStyledTextField();
        JTextField nightsField = createStyledTextField();

        String[] roomTypes = {"Single", "Double", "Suite"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);
        roomTypeComboBox.setBackground(Color.WHITE);
        
        // Menambahkan label untuk menampilkan harga kamar
        JLabel priceLabel = new JLabel("Harga: Rp 200.000/malam");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Update harga saat tipe kamar berubah
        roomTypeComboBox.addActionListener(e -> {
            String selectedType = (String) roomTypeComboBox.getSelectedItem();
            switch (selectedType) {
                case "Single":
                    priceLabel.setText("Harga: Rp 200.000/malam");
                    break;
                case "Double":
                    priceLabel.setText("Harga: Rp 400.000/malam");
                    break;
                case "Suite":
                    priceLabel.setText("Harga: Rp 800.000/malam");
                    break;
            }
        });
        
        JButton submitButton = new JButton("Reservasi");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        

        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));


        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        addFormRow(formPanel, "Nama Tamu:", nameField, gbc, 1);
        addFormRow(formPanel, "Nomor Kontak:", contactField, gbc, 2);
        addFormRow(formPanel, "Tipe Kamar:", roomTypeComboBox, gbc, 3);
        
        // Menambahkan label harga
        gbc.gridx = 1;
        gbc.gridy = 4;
        formPanel.add(priceLabel, gbc);
        
        addFormRow(formPanel, "Jumlah Malam:", nightsField, gbc, 5);


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(240, 240, 245));
        JTextField searchField = createStyledTextField();
        JButton searchButton = new JButton("Cari");
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);
        
        searchPanel.add(new JLabel("Cari Reservasi:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);


        JButton clearButton = new JButton("Reset");
        clearButton.setBackground(new Color(180, 70, 70));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 245));
        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);


        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);


        clearButton.addActionListener(e -> {
            nameField.setText("");
            contactField.setText("");
            nightsField.setText("");
            roomTypeComboBox.setSelectedIndex(0);
        });


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String contact = contactField.getText().trim();
                

                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, 
                        "Nama dan nomor kontak harus diisi!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }


                if (!contact.matches("\\d+")) {
                    JOptionPane.showMessageDialog(frame, 
                        "Nomor kontak hanya boleh berisi angka!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String roomType = (String) roomTypeComboBox.getSelectedItem();
                int nights;


                try {
                    nights = Integer.parseInt(nightsField.getText());
                    if (nights <= 0) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Jumlah malam harus berupa angka positif!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Menentukan harga tp kamar htl
                double pricePerNight;
                switch (roomType) {
                    case "Single":
                        pricePerNight = 200000;
                        break;
                    case "Double":
                        pricePerNight = 400000;
                        break;
                    case "Suite":
                        pricePerNight = 800000;
                        break;
                    default:
                        pricePerNight = 0; 
                        JOptionPane.showMessageDialog(frame, "Tipe kamar tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Membuat reservasi htl
                Guest guest = new Guest(name, contact);
                Room room = new Room(roomType, roomType, pricePerNight);
                Reservation reservation = new Reservation(guest, room, nights);
                reservationService.addReservation(reservation);


                resultArea.setText("");
                for (Reservation r : reservationService.getAllReservations()) {
                    resultArea.append(r.toString() + "\n");
                }


                nameField.setText("");
                contactField.setText("");
                nightsField.setText("");
            }
        });


        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText().trim().toLowerCase();
            resultArea.setText("");
            for (Reservation r : reservationService.getAllReservations()) {
                if (r.getGuest().getName().toLowerCase().contains(searchTerm) ||
                    r.getGuest().getContactNumber().contains(searchTerm) ||
                    r.getRoom().getRoomType().toLowerCase().contains(searchTerm)) {
                    resultArea.append(r.toString() + "\n");
                }
            }
        });

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        return field;
    }

    private static void addFormRow(JPanel panel, String labelText, JComponent component, 
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }
}