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
import java.util.List;

public class PaymentFrame extends JFrame {
    private Customer currentCustomer;
    private List<Customer> customers;
    private Booking currentBooking;
    
    private JTabbedPane tabbedPane;
    private JPanel cashPanel, cardPanel;
    private JTextField cardNumberField, cardHolderField, expiryField, cvvField;
    private JLabel bookingSummaryLabel;

    public PaymentFrame(Customer customer, List<Customer> customers, Booking booking) {
        this.currentCustomer = customer;
        this.customers = customers;
        this.currentBooking = booking;
        initComponents();
    }

    private void initComponents() {
        setTitle("Payment - Vehicle Rental System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel titleLabel = new JLabel("Payment Processing", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Booking Summary
        bookingSummaryLabel = new JLabel();
        bookingSummaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updateBookingSummary();

        // Payment Methods Tabbed Pane
        tabbedPane = new JTabbedPane();
        createCashPanel();
        createCardPanel();
        
        tabbedPane.addTab("Cash Payment", cashPanel);
        tabbedPane.addTab("Card Payment", cardPanel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnProcessPayment = new JButton("Process Payment");
        JButton btnBack = new JButton("Back to Booking");

        btnProcessPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingFrame(currentCustomer, customers, currentBooking.getVehicles()).setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(btnProcessPayment);
        buttonPanel.add(btnBack);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(bookingSummaryLabel, BorderLayout.NORTH);
        centerPanel.add(tabbedPane, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateBookingSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><b>Booking Summary:</b><br>");
        sb.append("Booking ID: ").append(currentBooking.getBookingId()).append("<br>");
        sb.append("Customer: ").append(currentCustomer.getName()).append("<br>");
        sb.append("Vehicles: ").append(currentBooking.getVehicles().size()).append("<br>");
        sb.append("Total Amount: <font color='red'>$").append(String.format("%.2f", currentBooking.getTotalAmount())).append("</font>");
        
        if (currentCustomer.isReturningCustomer()) {
            sb.append("<br><font color='green'>10% returning customer discount applied!</font>");
        }
        sb.append("</html>");
        
        bookingSummaryLabel.setText(sb.toString());
    }

    private void createCashPanel() {
        cashPanel = new JPanel(new BorderLayout());
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel info1 = new JLabel("💵 Cash Payment", JLabel.CENTER);
        info1.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel info2 = new JLabel("Pay at our counter when picking up vehicles", JLabel.CENTER);
        JLabel info3 = new JLabel("Bring your booking ID: " + currentBooking.getBookingId(), JLabel.CENTER);
        info3.setFont(new Font("Arial", Font.BOLD, 14));
        info3.setForeground(Color.BLUE);
        
        infoPanel.add(info1);
        infoPanel.add(info2);
        infoPanel.add(info3);
        
        cashPanel.add(infoPanel, BorderLayout.CENTER);
    }

    private void createCardPanel() {
        cardPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        cardPanel.add(new JLabel("Card Number:"));
        cardNumberField = new JTextField();
        cardPanel.add(cardNumberField);

        cardPanel.add(new JLabel("Card Holder Name:"));
        cardHolderField = new JTextField();
        cardPanel.add(cardHolderField);

        cardPanel.add(new JLabel("Expiry Date (MM/YY):"));
        expiryField = new JTextField();
        cardPanel.add(expiryField);

        cardPanel.add(new JLabel("CVV:"));
        cvvField = new JTextField();
        cardPanel.add(cvvField);

        // Add some padding
        cardPanel.add(new JLabel());
        cardPanel.add(new JLabel());
    }

    private void processPayment() {
        int selectedTab = tabbedPane.getSelectedIndex();
        Payment payment = null;

        if (selectedTab == 0) { // Cash payment
            payment = new CashPayment(currentBooking.getTotalAmount());
        } else { // Card payment
            String cardNumber = cardNumberField.getText().trim();
            String cardHolder = cardHolderField.getText().trim();
            String expiry = expiryField.getText().trim();
            String cvv = cvvField.getText().trim();

            if (cardNumber.isEmpty() || cardHolder.isEmpty() || expiry.isEmpty() || cvv.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all card details!");
                return;
            }

            payment = new CardPayment(currentBooking.getTotalAmount(), cardNumber, cardHolder, expiry, cvv);
        }

        // Process payment
        if (payment.processPayment()) {
            currentBooking.setPayment(payment);
            currentBooking.setStatus("Confirmed");
            currentCustomer.addBooking(currentBooking);
            
            // Show success message
            StringBuilder successMsg = new StringBuilder();
            successMsg.append("🎉 Payment Successful! 🎉\n\n");
            successMsg.append("Booking ID: ").append(currentBooking.getBookingId()).append("\n");
            successMsg.append("Payment ID: ").append(payment.getPaymentId()).append("\n");
            successMsg.append("Amount Paid: $").append(String.format("%.2f", payment.getAmount())).append("\n");
            successMsg.append("Payment Method: ").append(payment instanceof CashPayment ? "Cash" : "Card").append("\n\n");
            
            successMsg.append("Vehicles Booked:\n");
            for (Vehicle vehicle : currentBooking.getVehicles()) {
                successMsg.append("• ").append(vehicle.getBrand()).append(" ").append(vehicle.getModel()).append("\n");
            }
            
            successMsg.append("\nPlease bring your booking ID when picking up vehicles.");
            
            JOptionPane.showMessageDialog(this, successMsg.toString(), "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            
            // Return to login
            new LogInFrame().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed! Please try again.", "Payment Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}