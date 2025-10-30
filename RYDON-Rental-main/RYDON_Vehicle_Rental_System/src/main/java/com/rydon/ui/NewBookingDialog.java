package com.rydon.ui;

import com.rydon.dao.impl.BookingDAOImpl;
import com.rydon.dao.impl.VehicleDAOImpl;
import com.rydon.model.Booking;
import com.rydon.model.User;
import com.rydon.model.Vehicle;
import com.rydon.service.BookingService;
import com.rydon.service.VehicleService;

// --- JDatePicker Imports ---
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
// ---------------------------

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

// ** FIX: These imports are required for the listener **
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * A dialog window for creating a new booking.
 * Now uses JDatePicker for a calendar pop-up.
 */
public class NewBookingDialog extends JDialog {

    // Replaced JTextFields with JDatePicker
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;
    
    private JLabel priceLabel;

    private User user;
    private Vehicle vehicle;

    private BookingService bookingService;
    private VehicleService vehicleService;
    
    private boolean bookingSuccessful = false;

    public NewBookingDialog(Frame parent, User user, Vehicle vehicle) {
        super(parent, "New Booking", true);
        this.user = user;
        this.vehicle = vehicle;
        
        this.bookingService = new BookingService(new BookingDAOImpl());
        this.vehicleService = new VehicleService(new VehicleDAOImpl());

        setSize(450, 300); // Made slightly wider
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Theme.CONTENT_BACKGROUND);
        
        add(createFormPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Theme.CONTENT_BACKGROUND);

        // Vehicle Info
        panel.add(new JLabel("Vehicle:"));
        JLabel vehicleLabel = new JLabel(vehicle.getBrand() + " " + vehicle.getModel());
        vehicleLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        panel.add(vehicleLabel);

        // --- Start Date Picker ---
        panel.add(new JLabel("Start Date:"));
        UtilDateModel startModel = new UtilDateModel();
        startDatePicker = createDatePicker(startModel);
        panel.add(startDatePicker);

        // --- End Date Picker ---
        panel.add(new JLabel("End Date:"));
        UtilDateModel endModel = new UtilDateModel();
        endDatePicker = createDatePicker(endModel);
        panel.add(endDatePicker);

        // --- Total Price ---
        panel.add(new JLabel("Estimated Price:"));
        priceLabel = new JLabel("$0.00");
        priceLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        panel.add(priceLabel);
        
        // ** THE FIX IS HERE **
        // We now explicitly create a PropertyChangeListener
        PropertyChangeListener dateListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                // Check if the "value" (the date) changed
                if ("value".equals(e.getPropertyName())) {
                    updatePrice();
                }
            }
        };

        startModel.addPropertyChangeListener(dateListener);
        endModel.addPropertyChangeListener(dateListener);

        return panel;
    }
    
    /**
     * Helper method to create and configure a JDatePicker
     */
    private JDatePickerImpl createDatePicker(UtilDateModel model) {
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setBackground(Theme.CONTENT_BACKGROUND); // Style it
        
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBackground(Theme.CONTENT_BACKGROUND);
        return datePicker;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Theme.CONTENT_BACKGROUND);

        JButton confirmButton = new JButton("Confirm Booking");
        styleButton(confirmButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
        confirmButton.addActionListener(e -> confirmBooking());
        
        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton, Theme.BUTTON_SECONDARY_BG, Theme.BUTTON_SECONDARY_FG);
        cancelButton.addActionListener(e -> dispose());

        panel.add(cancelButton);
        panel.add(confirmButton);
        return panel;
    }
    
    private void updatePrice() {
        // Get java.util.Date from pickers
        Date selectedStartDate = (Date) startDatePicker.getModel().getValue();
        Date selectedEndDate = (Date) endDatePicker.getModel().getValue();

        // Don't calculate if dates are missing
        if (selectedStartDate == null || selectedEndDate == null) {
            priceLabel.setText("$0.00");
            return;
        }

        // Convert to java.time.LocalDate for modern API
        LocalDate startDate = selectedStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = selectedEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
            priceLabel.setText("Invalid dates");
            return;
        }
        
        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        double total = days * vehicle.getPricePerDay();
        priceLabel.setText(String.format("$%.2f", total));
    }

    private void confirmBooking() {
        Date selectedStartDate = (Date) startDatePicker.getModel().getValue();
        Date selectedEndDate = (Date) endDatePicker.getModel().getValue();

        if (selectedStartDate == null || selectedEndDate == null) {
            JOptionPane.showMessageDialog(this, "Please select both a start and end date.", "Missing Dates", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LocalDate startDate = selectedStartDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = selectedEndDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        try {
            if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(this, "End date must be on or after start date.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (startDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Start date cannot be in the past.", "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
            double totalAmount = days * vehicle.getPricePerDay();

            // Create Booking
            Booking booking = new Booking();
            booking.setUserId(user.getId());
            booking.setVehicleId(vehicle.getId());
            booking.setStartDate(startDate);
            booking.setEndDate(endDate);
            booking.setTotalAmount(totalAmount);
            booking.setPaymentStatus("Pending");

            // Save booking
            bookingService.createBooking(booking);
            
            // Mark vehicle as unavailable
            vehicleService.updateVehicleAvailability(vehicle.getId(), false);

            JOptionPane.showMessageDialog(this, "Booking confirmed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.bookingSuccessful = true;
            dispose(); // Close the dialog

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Booking failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean isBookingSuccessful() {
        return this.bookingSuccessful;
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(Theme.FONT_BOLD_MEDIUM);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    /**
     * A helper class required by JDatePicker to format the date in the text field.
     */
    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String DATE_PATTERN = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }
}