package com.rydon.model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int bookingId;
    private double amount;
    private LocalDateTime paymentDate;
    private String method;

    // Getters
    public int getId() { return id; }
    public int getBookingId() { return bookingId; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public String getMethod() { return method; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public void setMethod(String method) { this.method = method; }
}