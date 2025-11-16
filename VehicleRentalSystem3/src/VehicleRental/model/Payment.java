/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


public abstract class Payment {
    protected String paymentId;
    protected double amount;
    protected String paymentDate;
    protected String status;

    public Payment(double amount) {
        this.amount = amount;
        this.paymentDate = new java.util.Date().toString();
        this.status = "Pending";
    }

    public abstract boolean processPayment();
    
    // Getters and setters
    public String getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getPaymentDate() { return paymentDate; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}
