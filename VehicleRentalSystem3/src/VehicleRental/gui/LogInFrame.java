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
import java.util.ArrayList;
import java.util.List;

public class LogInFrame extends JFrame {
    private JTextField txtCustomerId, txtName, txtEmail, txtPhone;
    private List<Customer> customers;
    private Customer currentCustomer;

    public LogInFrame() {
        customers = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Customer Login - Vehicle Rental System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Customer Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        formPanel.add(new JLabel("Customer ID:"));
        txtCustomerId = new JTextField();
        formPanel.add(txtCustomerId);

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnLogin = new JButton("Register & Continue");
        JButton btnExistingCustomer = new JButton("Existing Customer");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCustomer();
            }
        });

        btnExistingCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showExistingCustomers();
            }
        });

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExistingCustomer);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void registerCustomer() {
        String customerId = txtCustomerId.getText().trim();
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        if (customerId.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if customer already exists
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                currentCustomer = customer;
                JOptionPane.showMessageDialog(this, "Welcome back, " + customer.getName() + "! You get 10% discount as returning customer.");
                openHomeFrame();
                return;
            }
        }

        // Create new customer
        currentCustomer = new Customer(customerId, name, email, phone);
        customers.add(currentCustomer);
        JOptionPane.showMessageDialog(this, "Registration successful! Welcome " + name);
        openHomeFrame();
    }

    private void showExistingCustomers() {
        if (customers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existing customers found!");
            return;
        }

        String[] customerArray = customers.stream()
                .map(c -> c.getCustomerId() + " - " + c.getName())
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(this,
                "Select existing customer:", "Existing Customers",
                JOptionPane.QUESTION_MESSAGE, null, customerArray, customerArray[0]);

        if (selected != null) {
            String selectedId = selected.split(" - ")[0];
            for (Customer customer : customers) {
                if (customer.getCustomerId().equals(selectedId)) {
                    currentCustomer = customer;
                    if (customer.isReturningCustomer()) {
                        JOptionPane.showMessageDialog(this, "Welcome back! You get 10% discount as returning customer.");
                    }
                    openHomeFrame();
                    break;
                }
            }
        }
    }

    private void openHomeFrame() {
        dispose();
        new HomeFrame(currentCustomer, customers).setVisible(true);
    }
}