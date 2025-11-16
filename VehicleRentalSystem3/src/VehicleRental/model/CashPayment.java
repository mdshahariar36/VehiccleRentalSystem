/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


public class CashPayment extends Payment {
    private static int cashCounter = 1;

    public CashPayment(double amount) {
        super(amount);
        this.paymentId = "CASH" + String.format("%04d", cashCounter++);
    }

    @Override
    public boolean processPayment() {
        this.status = "Completed";
        return true;
    }
    
    @Override
    public String toString() {
        return "Cash Payment: " + paymentId + " - Amount: $" + amount;
    }
}