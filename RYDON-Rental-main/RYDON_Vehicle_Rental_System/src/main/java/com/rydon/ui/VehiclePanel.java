package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.rydon.dao.impl.VehicleDAOImpl;
import com.rydon.model.User;
import com.rydon.model.Vehicle;
import com.rydon.service.VehicleService;

/**
 * A panel for both viewing and managing vehicles.
 * Used in both Admin and Customer dashboards.
 * Now includes "Rent" functionality for customers.
 */
public class VehiclePanel extends JPanel {

    private VehicleService vehicleService;
    private JTable vehicleTable;
    private DefaultTableModel tableModel;
    private boolean isAdmin;
    
    // New fields for booking
    private User currentUser;
    private BookingPanel bookingPanelToRefresh; // To refresh "My Bookings"

    /**
     * Constructor for the VehiclePanel.
     * @param isAdmin True if admin is viewing (shows all vehicles + controls)
     * @param user The current logged-in user (null if admin)
     */
    public VehiclePanel(boolean isAdmin, User user) {
        this.isAdmin = isAdmin;
        this.currentUser = user; // Store the user
        this.vehicleService = new VehicleService(new VehicleDAOImpl());
        
        setBackground(Theme.CONTENT_BACKGROUND);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String title = isAdmin ? "Manage Vehicles" : "Browse & Rent Vehicles";
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Theme.FONT_BOLD_LARGE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Brand", "Model", "Type", "Price/Day", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            // Make table cells not editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vehicleTable = new JTable(tableModel);
        
        styleTable();

        JScrollPane scrollPane = new JScrollPane(vehicleTable);
        scrollPane.getViewport().setBackground(Theme.CONTENT_BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);

        // Show different buttons for Admin vs Customer
        if (isAdmin) {
            add(createAdminPanel(), BorderLayout.SOUTH);
        } else {
            add(createCustomerPanel(), BorderLayout.SOUTH);
        }

        loadVehicleData();
    }

    /**
     * Links this panel to the customer's BookingPanel to allow for refreshing.
     * @param bookingPanel The BookingPanel instance from CustomerDashboard.
     */
    public void setBookingPanelToRefresh(BookingPanel bookingPanel) {
        this.bookingPanelToRefresh = bookingPanel;
    }
    
    private void styleTable() {
        vehicleTable.setFont(Theme.FONT_TABLE_ROW);
        vehicleTable.setRowHeight(25);
        vehicleTable.setGridColor(Theme.SIDEBAR_BACKGROUND);
        vehicleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only one row
        JTableHeader header = vehicleTable.getTableHeader();
        header.setFont(Theme.FONT_TABLE_HEADER);
        header.setBackground(Theme.SIDEBAR_BACKGROUND);
        header.setForeground(Theme.TEXT_PRIMARY);
    }

    private void loadVehicleData() {
        tableModel.setRowCount(0); 
        List<Vehicle> vehicles = isAdmin ? vehicleService.getAllVehicles() : vehicleService.getAvailableVehicles();
        
        for (Vehicle vehicle : vehicles) {
            Object[] row = {
                vehicle.getId(), vehicle.getBrand(), vehicle.getModel(),
                vehicle.getType(), vehicle.getPricePerDay(), vehicle.isAvailable()
            };
            tableModel.addRow(row);
        }
    }

    // --- ADMIN METHODS ---

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Theme.CONTENT_BACKGROUND);
        
        panel.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField(10);
        panel.add(brandField);
        
        panel.add(new JLabel("Model:"));
        JTextField modelField = new JTextField(10);
        panel.add(modelField);
        
        panel.add(new JLabel("Type:"));
        JTextField typeField = new JTextField(8);
        panel.add(typeField);
        
        panel.add(new JLabel("Price:"));
        JTextField priceField = new JTextField(6);
        panel.add(priceField);
        
        JCheckBox availableBox = new JCheckBox("Available", true);
        availableBox.setBackground(Theme.CONTENT_BACKGROUND);
        panel.add(availableBox);
        
        JButton addButton = new JButton("Add Vehicle");
        styleButton(addButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
        panel.add(addButton);
        
        JButton deleteButton = new JButton("Delete Selected");
        styleButton(deleteButton, Theme.BUTTON_DANGER_BG, Theme.BUTTON_DANGER_FG);
        panel.add(deleteButton);

        addButton.addActionListener(e -> addVehicleAction(brandField, modelField, typeField, priceField, availableBox));
        deleteButton.addActionListener(e -> deleteVehicleAction());
        
        return panel;
    }
    
    private void addVehicleAction(JTextField brandField, JTextField modelField, JTextField typeField, JTextField priceField, JCheckBox availableBox) {
        try {
            Vehicle v = new Vehicle();
            v.setBrand(brandField.getText());
            v.setModel(modelField.getText());
            v.setType(typeField.getText());
            v.setPricePerDay(Double.parseDouble(priceField.getText()));
            v.setAvailable(availableBox.isSelected());
            
            vehicleService.addVehicle(v);
            JOptionPane.showMessageDialog(this, "Vehicle added!");
            loadVehicleData();
            
            // Clear fields
            brandField.setText("");
            modelField.setText("");
            typeField.setText("");
            priceField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding vehicle: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteVehicleAction() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow >= 0) {
            int vehicleId = (int) tableModel.getValueAt(selectedRow, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Delete selected vehicle?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                vehicleService.deleteVehicle(vehicleId);
                JOptionPane.showMessageDialog(this, "Vehicle deleted!");
                loadVehicleData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to delete.");
        }
    }
    
    // --- CUSTOMER METHODS ---

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Theme.CONTENT_BACKGROUND);
        
        JButton rentButton = new JButton("Rent Selected Vehicle");
        styleButton(rentButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
        panel.add(rentButton);

        rentButton.addActionListener(e -> rentVehicleAction());
        
        return panel;
    }

    private void rentVehicleAction() {
        int selectedRow = vehicleTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to rent.", "No Vehicle Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get vehicle data from the table
        int vehicleId = (int) tableModel.getValueAt(selectedRow, 0);
        String brand = (String) tableModel.getValueAt(selectedRow, 1);
        String model = (String) tableModel.getValueAt(selectedRow, 2);
        double pricePerDay = (double) tableModel.getValueAt(selectedRow, 4);
        
        // Create a partial Vehicle object to pass to the dialog
        Vehicle selectedVehicle = new Vehicle();
        selectedVehicle.setId(vehicleId);
        selectedVehicle.setBrand(brand);
        selectedVehicle.setModel(model);
        selectedVehicle.setPricePerDay(pricePerDay);
        
        // Find the parent JFrame
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);

        // Open the modal booking dialog
        NewBookingDialog dialog = new NewBookingDialog(parentFrame, currentUser, selectedVehicle);
        dialog.setVisible(true); // This will block until the dialog is closed

        // After the dialog is closed, check if a booking was made
        if (dialog.isBookingSuccessful()) {
            // 1. Refresh this vehicle list (to show it's no longer available)
            loadVehicleData();
            
            // 2. Refresh the "My Bookings" panel
            if (bookingPanelToRefresh != null) {
                bookingPanelToRefresh.loadBookingData(false, currentUser.getId());
            }
        }
    }
    
    // --- COMMON METHODS ---

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(Theme.FONT_PLAIN_SMALL);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}