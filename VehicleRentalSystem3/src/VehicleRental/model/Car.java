/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */




public class Car extends Vehicle {
    private int seats;
    private String fuelType;

    public Car(String vehicleId, String brand, String model, double pricePerDay, int stock, int seats, String fuelType) {
        super(vehicleId, brand, model, pricePerDay, stock);
        this.seats = seats;
        this.fuelType = fuelType;
    }

    public int getSeats() { return seats; }
    public String getFuelType() { return fuelType; }
    
    @Override
    public String getType() {
        return "Car";
    }
    
    @Override
    public String toString() {
        return brand + " " + model + " (" + fuelType + ", " + seats + " seats) - $" + pricePerDay + "/day" + " (Stock: " + stock + ")";
    }
}