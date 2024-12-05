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
        // Set Look and Feel to system default
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

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Styling komponen
        JLabel titleLabel = new JLabel("Form Reservasi Hotel");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(50, 50, 50));

        JTextField nameField = createStyledTextField();
        JTextField contactField = createStyledTextField();
        JTextField nightsField = createStyledTextField();

        String[] roomTypes = {"Single", "Double", "Suite"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);
        roomTypeComboBox.setBackground(Color.WHITE);
        
        JButton submitButton = new JButton("Reservasi");
        submitButton.setBackground(new Color(70, 130, 180));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        
        // Hasil area
        JTextArea resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Layout komponen
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        addFormRow(formPanel, "Nama Tamu:", nameField, gbc, 1);
        addFormRow(formPanel, "Nomor Kontak:", contactField, gbc, 2);
        addFormRow(formPanel, "Tipe Kamar:", roomTypeComboBox, gbc, 3);
        addFormRow(formPanel, "Jumlah Malam:", nightsField, gbc, 4);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(submitButton, gbc);

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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
                        pricePerNight = 0; // Handle unexpected room type
                        JOptionPane.showMessageDialog(frame, "Tipe kamar tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Membuat reservasi
                Guest guest = new Guest(name, contact);
                Room room = new Room(roomType, roomType, pricePerNight);
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