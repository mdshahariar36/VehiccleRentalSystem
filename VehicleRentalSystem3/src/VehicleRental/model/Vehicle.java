/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VehicleRental.model;

/**
 *
 * @author FLS
 */


public abstract class Vehicle {
    protected String vehicleId;
    protected String brand;
    protected String model;
    protected double pricePerDay;
    protected boolean available;
    protected int stock;

    public Vehicle(String vehicleId, String brand, String model, double pricePerDay, int stock) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = true;
        this.stock = stock;
    }

    public String getVehicleId() { return vehicleId; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available && stock > 0; }
    public int getStock() { return stock; }
    
    public void setAvailable(boolean available) { this.available = available; }
    public void setStock(int stock) { this.stock = stock; }
    
    public void decreaseStock() {
        if (stock > 0) {
            stock--;
            if (stock == 0) {
                available = false;
            }
        }
    }
    
    public void increaseStock() {
        stock++;
        if (stock > 0) {
            available = true;
        }
    }
    
    public abstract String getType();
    
    @Override
    public String toString() {
        return brand + " " + model + " - $" + pricePerDay + "/day" + " (Stock: " + stock + ")";
    }
}
