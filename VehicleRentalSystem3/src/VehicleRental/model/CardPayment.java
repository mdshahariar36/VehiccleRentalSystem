/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


public class CardPayment extends Payment {
    private static int cardCounter = 1;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CardPayment(double amount, String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        super(amount);
        this.paymentId = "CARD" + String.format("%04d", cardCounter++);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean processPayment() {
        // Simulate card payment processing
        if (isValidCard()) {
            this.status = "Completed";
            return true;
        }
        this.status = "Failed";
        return false;
    }

    private boolean isValidCard() {
        return cardNumber != null && cardNumber.length() == 16 &&
               cardHolderName != null && !cardHolderName.isEmpty() &&
               expiryDate != null && expiryDate.matches("\\d{2}/\\d{2}") &&
               cvv != null && cvv.length() == 3;
    }

    public String getCardNumber() { return cardNumber; }
    public String getCardHolderName() { return cardHolderName; }
    public String getExpiryDate() { return expiryDate; }
    
    @Override
    public String toString() {
        return "Card Payment: " + paymentId + " - Amount: $" + amount + " - Card: ****" + cardNumber.substring(12);
    }
}