package com.rydon.model;

import java.time.LocalDate;

public class Booking {
    private int id;
    private int userId;
    private int vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalAmount;
    private String paymentStatus;

    // Getters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getVehicleId() { return vehicleId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentStatus() { return paymentStatus; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setVehicleId(int vehicleId) { this.vehicleId = vehicleId; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}