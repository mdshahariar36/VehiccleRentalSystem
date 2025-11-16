/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.gui;

/**
 *
 * @author FLS
 */

import VehicleRental.model.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeFrame extends JFrame {
    private Customer currentCustomer;
    private List<Customer> customers;

    public HomeFrame(Customer customer, List<Customer> customers) {
        this.currentCustomer = customer;
        this.customers = customers;
        initComponents();
    }

    private void initComponents() {
        setTitle("Home - Vehicle Rental System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + currentCustomer.getName() + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel infoLabel = new JLabel("Customer ID: " + currentCustomer.getCustomerId() + 
                                    " | Email: " + currentCustomer.getEmail(), JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        if (currentCustomer.isReturningCustomer()) {
            JLabel discountLabel = new JLabel("🎉 You qualify for 10% returning customer discount!", JLabel.CENTER);
            discountLabel.setFont(new Font("Arial", Font.BOLD, 16));
            discountLabel.setForeground(Color.RED);
            welcomePanel.add(discountLabel, BorderLayout.NORTH);
        }

        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        welcomePanel.add(infoLabel, BorderLayout.SOUTH);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));

        JButton btnViewVehicles = new JButton("View Available Vehicles");
        JButton btnBookingHistory = new JButton("View Booking History");
        JButton btnLogout = new JButton("Logout");

        btnViewVehicles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VehicleListFrame(currentCustomer, customers).setVisible(true);
                dispose();
            }
        });

        btnBookingHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HomeFrame.this, 
                    "Booking history feature will be implemented here.");
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(HomeFrame.this,
                    "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    new LogInFrame().setVisible(true);
                    dispose();
                }
            }
        });

        // Style buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        btnViewVehicles.setFont(buttonFont);
        btnBookingHistory.setFont(buttonFont);
        btnLogout.setFont(buttonFont);

        btnViewVehicles.setBackground(new Color(70, 130, 180));
        btnViewVehicles.setForeground(Color.WHITE);
        
        btnBookingHistory.setBackground(new Color(34, 139, 34));
        btnBookingHistory.setForeground(Color.WHITE);
        
        btnLogout.setBackground(new Color(220, 20, 60));
        btnLogout.setForeground(Color.WHITE);

        buttonPanel.add(btnViewVehicles);
        buttonPanel.add(btnBookingHistory);
        buttonPanel.add(btnLogout);

        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}