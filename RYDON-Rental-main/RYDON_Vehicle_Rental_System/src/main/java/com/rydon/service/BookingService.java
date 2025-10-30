package com.rydon.service;

import java.util.List;

import com.rydon.dao.BookingDAO;
import com.rydon.model.Booking;

public class BookingService {
    private BookingDAO bookingDAO;

    public BookingService(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    public void createBooking(Booking booking) {
        // Add business logic here (check availability, calculate total)
        booking.setPaymentStatus("Pending");
        bookingDAO.addBooking(booking);
        // Could also update vehicle availability
    }

    public List<Booking> getBookingsForUser(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }
    
    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings();
    }
}