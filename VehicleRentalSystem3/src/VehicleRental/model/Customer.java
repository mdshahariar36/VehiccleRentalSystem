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
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private List<Booking> bookingHistory;
    private boolean isReturningCustomer;

    public Customer(String customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookingHistory = new ArrayList<>();
        this.isReturningCustomer = false;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public List<Booking> getBookingHistory() { return bookingHistory; }
    public boolean isReturningCustomer() { return isReturningCustomer; }
    
    public void setReturningCustomer(boolean returningCustomer) { 
        this.isReturningCustomer = returningCustomer; 
    }
    
    public void addBooking(Booking booking) {
        bookingHistory.add(booking);
        updateReturningCustomerStatus();
    }
    
    private void updateReturningCustomerStatus() {
        long completedBookings = bookingHistory.stream()
            .filter(b -> b.getStatus().equals("Completed"))
            .count();
        this.isReturningCustomer = completedBookings > 0;
    }
    
    public boolean hasPreviousBookings() {
        return !bookingHistory.isEmpty();
    }
    
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }
}