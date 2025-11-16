/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */

public class Scooter extends Vehicle {
    private boolean electric;
    private double range;

    public Scooter(String vehicleId, String brand, String model, double pricePerDay, int stock, boolean electric, double range) {
        super(vehicleId, brand, model, pricePerDay, stock);
        this.electric = electric;
        this.range = range;
    }

    public boolean isElectric() { return electric; }
    public double getRange() { return range; }
    
    @Override
    public String getType() {
        return "Scooter";
    }
    
    @Override
    public String toString() {
        String type = electric ? "Electric" : "Petrol";
        return brand + " " + model + " (" + type + ", " + range + "km range) - $" + pricePerDay + "/day" + " (Stock: " + stock + ")";
    }
}