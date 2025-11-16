/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Booking {
    private static int bookingCounter = 1;
    
    private String bookingId;
    private Customer customer;
    private List<Vehicle> vehicles;
    private Date bookingDate;
    private Date startDate;
    private Date endDate;
    private int rentalDays;
    private double totalAmount;
    private String status;
    private Payment payment;
    private boolean discountApplied;

    public Booking(Customer customer, Date startDate, Date endDate) {
        this.bookingId = "B" + String.format("%04d", bookingCounter++);
        this.customer = customer;
        this.vehicles = new ArrayList<>();
        this.bookingDate = new Date();
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalDays = calculateRentalDays();
        this.status = "Pending";
        this.discountApplied = false;
    }

    private int calculateRentalDays() {
        long diff = endDate.getTime() - startDate.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle.isAvailable()) {
            vehicles.add(vehicle);
            vehicle.decreaseStock();
            calculateTotalAmount();
        }
    }

    public void removeVehicle(Vehicle vehicle) {
        if (vehicles.remove(vehicle)) {
            vehicle.increaseStock();
            calculateTotalAmount();
        }
    }

    private void calculateTotalAmount() {
        totalAmount = 0;
        for (Vehicle vehicle : vehicles) {
            totalAmount += vehicle.getPricePerDay() * rentalDays;
        }
        
        // Apply 10% discount for returning customers
        if (customer.isReturningCustomer() && !discountApplied) {
            totalAmount = totalAmount * 0.9;
            discountApplied = true;
        }
    }

    public void processReturn() {
        this.status = "Completed";
        for (Vehicle vehicle : vehicles) {
            vehicle.increaseStock();
        }
    }

    // Getters and setters
    public String getBookingId() { return bookingId; }
    public Customer getCustomer() { return customer; }
    public List<Vehicle> getVehicles() { return vehicles; }
    public Date getBookingDate() { return bookingDate; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public int getRentalDays() { return rentalDays; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public Payment getPayment() { return payment; }
    
    public void setStatus(String status) { this.status = status; }
    public void setPayment(Payment payment) { this.payment = payment; }
    
    @Override
    public String toString() {
        return "Booking " + bookingId + " - " + customer.getName() + " - $" + totalAmount + " - " + status;
    }
}