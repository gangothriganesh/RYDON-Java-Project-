package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.rydon.dao.impl.BookingDAOImpl;
import com.rydon.model.Booking;
import com.rydon.service.BookingService;

public class BookingPanel extends JPanel {

    private BookingService bookingService;
    private JTable bookingTable;
    private DefaultTableModel tableModel;

    public BookingPanel(boolean isAdmin, int userId) {
        this.bookingService = new BookingService(new BookingDAOImpl());
        
        setBackground(Theme.CONTENT_BACKGROUND);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String title = isAdmin ? "All Bookings" : "My Bookings";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_BOLD_LARGE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "User ID", "Vehicle ID", "Start Date", "End Date", "Total", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        bookingTable = new JTable(tableModel);
        
        styleTable();

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.getViewport().setBackground(Theme.CONTENT_BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add a "Book New Vehicle" button for customers
        if (!isAdmin) {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.setBackground(Theme.CONTENT_BACKGROUND);
            JButton bookButton = new JButton("Book a New Vehicle");
            styleButton(bookButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
            buttonPanel.add(bookButton);
            add(buttonPanel, BorderLayout.SOUTH);
            // In a real app, this button would switch to the Browse tab
        }

        loadBookingData(isAdmin, userId);
    }
    
    private void styleTable() {
        bookingTable.setFont(Theme.FONT_TABLE_ROW);
        bookingTable.setRowHeight(25);
        bookingTable.setGridColor(Theme.SIDEBAR_BACKGROUND);
        JTableHeader header = bookingTable.getTableHeader();
        header.setFont(Theme.FONT_TABLE_HEADER);
        header.setBackground(Theme.SIDEBAR_BACKGROUND);
        header.setForeground(Theme.TEXT_PRIMARY);
    }

    public void loadBookingData(boolean isAdmin, int userId) {
        tableModel.setRowCount(0);
        
        List<Booking> bookings = isAdmin ? bookingService.getAllBookings() : bookingService.getBookingsForUser(userId);

        for (Booking booking : bookings) {
            Object[] row = {
                booking.getId(), booking.getUserId(), booking.getVehicleId(),
                booking.getStartDate(), booking.getEndDate(),
                booking.getTotalAmount(), booking.getPaymentStatus()
            };
            tableModel.addRow(row);
        }
    }
    
    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(Theme.FONT_PLAIN_SMALL);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}