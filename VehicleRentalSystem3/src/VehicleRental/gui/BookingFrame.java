/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.gui;

/**
 *
 * @author FLS
 */


import VehicleRental.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingFrame extends JFrame {
    private Customer currentCustomer;
    private List<Customer> customers;
    private List<Vehicle> selectedVehicles;
    private Booking currentBooking;
    
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;
    private JLabel totalAmountLabel;
    private JTextArea vehicleDetailsArea;

    public BookingFrame(Customer customer, List<Customer> customers, List<Vehicle> selectedVehicles) {
        this.currentCustomer = customer;
        this.customers = customers;
        this.selectedVehicles = selectedVehicles;
        initComponents();
        calculateTotalAmount();
    }

    private void initComponents() {
        setTitle("Booking Details - Vehicle Rental System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Booking Details", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Vehicle Details
        JPanel vehiclePanel = new JPanel(new BorderLayout());
        vehiclePanel.setBorder(BorderFactory.createTitledBorder("Selected Vehicles"));
        
        vehicleDetailsArea = new JTextArea(8, 40);
        vehicleDetailsArea.setEditable(false);
        vehicleDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        updateVehicleDetails();
        
        JScrollPane scrollPane = new JScrollPane(vehicleDetailsArea);
        vehiclePanel.add(scrollPane, BorderLayout.CENTER);

        // Date Selection
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datePanel.setBorder(BorderFactory.createTitledBorder("Rental Period"));

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date nextWeek = calendar.getTime();

        startDateSpinner = new JSpinner(new SpinnerDateModel(today, null, null, Calendar.DAY_OF_MONTH));
        endDateSpinner = new JSpinner(new SpinnerDateModel(nextWeek, today, null, Calendar.DAY_OF_MONTH));

        startDateSpinner.setEditor(new JSpinner.DateEditor(startDateSpinner, "dd/MM/yyyy"));
        endDateSpinner.setEditor(new JSpinner.DateEditor(endDateSpinner, "dd/MM/yyyy"));

        datePanel.add(new JLabel("Start Date:"));
        datePanel.add(startDateSpinner);
        datePanel.add(new JLabel("End Date:"));
        datePanel.add(endDateSpinner);

        // Total Amount
        JPanel amountPanel = new JPanel(new FlowLayout());
        amountPanel.setBorder(BorderFactory.createTitledBorder("Payment Summary"));
        
        totalAmountLabel = new JLabel("Total Amount: $0.00");
        totalAmountLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalAmountLabel.setForeground(Color.RED);
        amountPanel.add(totalAmountLabel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnCalculate = new JButton("Calculate Total");
        JButton btnProceed = new JButton("Proceed to Payment");
        JButton btnBack = new JButton("Back to Vehicles");

        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTotalAmount();
            }
        });

        btnProceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedToPayment();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VehicleListFrame(currentCustomer, customers).setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnProceed);
        buttonPanel.add(btnBack);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(vehiclePanel, BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(datePanel, BorderLayout.NORTH);
        southPanel.add(amountPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateVehicleDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Selected Vehicles:\n");
        sb.append("==================\n");
        
        for (Vehicle vehicle : selectedVehicles) {
            sb.append("• ").append(vehicle.getBrand()).append(" ").append(vehicle.getModel())
              .append(" (").append(vehicle.getType()).append(")\n");
            sb.append("  Price: $").append(vehicle.getPricePerDay()).append("/day\n");
            sb.append("  Stock: ").append(vehicle.getStock()).append("\n\n");
        }
        
        vehicleDetailsArea.setText(sb.toString());
    }

    private void calculateTotalAmount() {
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();

        if (endDate.before(startDate)) {
            JOptionPane.showMessageDialog(this, "End date cannot be before start date!");
            return;
        }

        long diff = endDate.getTime() - startDate.getTime();
        int rentalDays = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

        double total = 0;
        for (Vehicle vehicle : selectedVehicles) {
            total += vehicle.getPricePerDay() * rentalDays;
        }

        // Apply discount for returning customers
        if (currentCustomer.isReturningCustomer()) {
            total = total * 0.9; // 10% discount
        }

        totalAmountLabel.setText(String.format("Total Amount: $%.2f (%d days, %d vehicles%s)", 
            total, rentalDays, selectedVehicles.size(), 
            currentCustomer.isReturningCustomer() ? " - 10% discount applied!" : ""));
    }

    private void proceedToPayment() {
        Date startDate = (Date) startDateSpinner.getValue();
        Date endDate = (Date) endDateSpinner.getValue();

        if (endDate.before(startDate)) {
            JOptionPane.showMessageDialog(this, "End date cannot be before start date!");
            return;
        }

        // Create booking
        currentBooking = new Booking(currentCustomer, startDate, endDate);
        for (Vehicle vehicle : selectedVehicles) {
            currentBooking.addVehicle(vehicle);
        }

        // Open payment frame
        new PaymentFrame(currentCustomer, customers, currentBooking).setVisible(true);
        dispose();
    }
}