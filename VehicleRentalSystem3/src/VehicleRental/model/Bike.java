/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


public class Bike extends Vehicle {
    private String bikeType;
    private int engineCapacity;

    public Bike(String vehicleId, String brand, String model, double pricePerDay, int stock, String bikeType, int engineCapacity) {
        super(vehicleId, brand, model, pricePerDay, stock);
        this.bikeType = bikeType;
        this.engineCapacity = engineCapacity;
    }

    public String getBikeType() { return bikeType; }
    public int getEngineCapacity() { return engineCapacity; }
    
    @Override
    public String getType() {
        return "Bike";
    }
    
    @Override
    public String toString() {
        return brand + " " + model + " (" + bikeType + ", " + engineCapacity + "cc) - $" + pricePerDay + "/day" + " (Stock: " + stock + ")";
    }
}