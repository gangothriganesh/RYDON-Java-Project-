package com.rydon.dao;

import java.util.List;

import com.rydon.model.Booking;

public interface BookingDAO {
    void addBooking(Booking booking);
    List<Booking> getBookingsByUserId(int userId);
    List<Booking> getAllBookings();
    // Other methods like updateBookingStatus, isVehicleAvailable(id, dateRange) etc.
}