package com.rydon.model;

public class Vehicle {
    private int id;
    private String brand;
    private String model;
    private String type;
    private double pricePerDay;
    private boolean available;

    // Getters
    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getType() { return type; }
    public double getPricePerDay() { return pricePerDay; }
    public boolean isAvailable() { return available; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setBrand(String brand) { this.brand = brand; }
    public void setModel(String model) { this.model = model; }
    public void setType(String type) { this.type = type; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public void setAvailable(boolean available) { this.available = available; }
}