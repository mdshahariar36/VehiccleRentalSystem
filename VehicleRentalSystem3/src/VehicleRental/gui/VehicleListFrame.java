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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VehicleListFrame extends JFrame {
    private Customer currentCustomer;
    private List<Customer> customers;
    private List<Vehicle> vehicles;
    private List<Vehicle> selectedVehicles;
    private JTable vehicleTable;
    private DefaultTableModel tableModel;
    private JLabel selectedCountLabel;

    public VehicleListFrame(Customer customer, List<Customer> customers) {
        this.currentCustomer = customer;
        this.customers = customers;
        this.selectedVehicles = new ArrayList<>();
        initializeVehicles();
        initComponents();
    }

    private void initializeVehicles() {
        vehicles = new ArrayList<>();
        
        // Add sample cars
        vehicles.add(new Car("CAR001", "Toyota", "Camry", 50.0, 3, 5, "Petrol"));
        vehicles.add(new Car("CAR002", "Honda", "Civic", 45.0, 2, 5, "Petrol"));
        vehicles.add(new Car("CAR003", "Tesla", "Model 3", 80.0, 1, 5, "Electric"));
        
        // Add sample bikes
        vehicles.add(new Bike("BIKE001", "Yamaha", "MT-15", 25.0, 4, "Sports", 155));
        vehicles.add(new Bike("BIKE002", "Royal Enfield", "Classic 350", 30.0, 3, "Cruiser", 349));
        
        // Add sample scooters
        vehicles.add(new Scooter("SCOOT001", "Honda", "Activa", 15.0, 5, false, 150));
        vehicles.add(new Scooter("SCOOT002", "Ola", "S1 Pro", 20.0, 2, true, 120));
    }

    private void initComponents() {
        setTitle("Available Vehicles - Vehicle Rental System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("Available Vehicles for Rent", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Selected vehicles info
        JPanel infoPanel = new JPanel(new FlowLayout());
        selectedCountLabel = new JLabel("Selected Vehicles: 0");
        selectedCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        selectedCountLabel.setForeground(Color.BLUE);
        infoPanel.add(selectedCountLabel);

        // Table
        String[] columns = {"Select", "Vehicle ID", "Type", "Brand", "Model", "Price/Day", "Stock", "Details"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class;
            }
        };
        
        vehicleTable = new JTable(tableModel);
        loadVehicleData();
        
        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        vehicleTable.setFillsViewportHeight(true);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnBookSelected = new JButton("Book Selected Vehicles");
        JButton btnBack = new JButton("Back to Home");

        btnBookSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookSelectedVehicles();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeFrame(currentCustomer, customers).setVisible(true);
                dispose();
            }
        });

        buttonPanel.add(btnBookSelected);
        buttonPanel.add(btnBack);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadVehicleData() {
        tableModel.setRowCount(0);
        for (Vehicle vehicle : vehicles) {
            Object[] row = {
                false,
                vehicle.getVehicleId(),
                vehicle.getType(),
                vehicle.getBrand(),
                vehicle.getModel(),
                "$" + vehicle.getPricePerDay(),
                vehicle.getStock(),
                getVehicleDetails(vehicle)
            };
            tableModel.addRow(row);
        }
    }

    private String getVehicleDetails(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            Car car = (Car) vehicle;
            return car.getSeats() + " seats, " + car.getFuelType();
        } else if (vehicle instanceof Bike) {
            Bike bike = (Bike) vehicle;
            return bike.getEngineCapacity() + "cc, " + bike.getBikeType();
        } else if (vehicle instanceof Scooter) {
            Scooter scooter = (Scooter) vehicle;
            return (scooter.isElectric() ? "Electric" : "Petrol") + ", " + scooter.getRange() + "km range";
        }
        return "";
    }

    private void bookSelectedVehicles() {
        selectedVehicles.clear();
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean selected = (Boolean) tableModel.getValueAt(i, 0);
            if (selected != null && selected) {
                String vehicleId = (String) tableModel.getValueAt(i, 1);
                Vehicle vehicle = findVehicleById(vehicleId);
                if (vehicle != null && vehicle.isAvailable()) {
                    selectedVehicles.add(vehicle);
                }
            }
        }

        if (selectedVehicles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one available vehicle!");
            return;
        }

        // Show booking frame
        new BookingFrame(currentCustomer, customers, selectedVehicles).setVisible(true);
        dispose();
    }

    private Vehicle findVehicleById(String vehicleId) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleId().equals(vehicleId)) {
                return vehicle;
            }
        }
        return null;
    }
}
