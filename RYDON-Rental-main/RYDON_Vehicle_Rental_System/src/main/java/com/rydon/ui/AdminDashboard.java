package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AdminDashboard extends JFrame implements ActionListener {

    private JPanel navigationPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    private JButton dashboardButton;
    private JButton vehiclesButton;
    private JButton customersButton;
    private JButton bookingsButton;
    private JButton maintenanceButton;
    private JButton logoutButton;

    private static final String DASHBOARD = "Dashboard";
    private static final String VEHICLES = "Vehicles";
    private static final String CUSTOMERS = "Customers";
    private static final String BOOKINGS = "Bookings";
    private static final String MAINTENANCE = "Maintenance";

    public AdminDashboard() {
        setTitle("Admin Dashboard - RYDON");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createNavigationPanel();
        add(navigationPanel, BorderLayout.WEST);

        createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }

    private void createNavigationPanel() {
        navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBackground(Theme.SIDEBAR_BACKGROUND);
        navigationPanel.setPreferredSize(new Dimension(200, 0));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel logoLabel = new JLabel("RYDON");
        logoLabel.setFont(Theme.FONT_BOLD_LARGE);
        logoLabel.setForeground(Theme.TEXT_PRIMARY);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 30, 0));
        navigationPanel.add(logoLabel);

        dashboardButton = createNavButton("Dashboard");
        vehiclesButton = createNavButton("Vehicles");
        customersButton = createNavButton("Customers");
        bookingsButton = createNavButton("Bookings");
        maintenanceButton = createNavButton("Maintenance");
        
        navigationPanel.add(dashboardButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(vehiclesButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(customersButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(bookingsButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(maintenanceButton);
        
        navigationPanel.add(Box.createVerticalGlue()); 
        
        logoutButton = new JButton("Logout");
        logoutButton.setFont(Theme.FONT_BOLD_MEDIUM);
        logoutButton.setBackground(Theme.BUTTON_DANGER_BG);
        logoutButton.setForeground(Theme.BUTTON_DANGER_FG);
        logoutButton.setFocusPainted(false);
        logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.addActionListener(this);
        navigationPanel.add(logoutButton);
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(Theme.FONT_BOLD_MEDIUM);
        button.setForeground(Theme.TEXT_PRIMARY);
        button.setBackground(Theme.SIDEBAR_BACKGROUND);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(this);
        return button;
    }

    private void createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Theme.CONTENT_BACKGROUND);

        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Theme.CONTENT_BACKGROUND);
        homePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel homeLabel = new JLabel("Welcome Admin!");
        homeLabel.setFont(Theme.FONT_BOLD_LARGE);
        homeLabel.setForeground(Theme.TEXT_PRIMARY);
        homePanel.add(homeLabel, BorderLayout.NORTH);
        
        // ** FIX: Passed 'null' for the user, as admins don't need it **
        VehiclePanel vehiclePanel = new VehiclePanel(true, null);
        
        CustomerPanel customerPanel = new CustomerPanel();
        BookingPanel bookingPanel = new BookingPanel(true, 0);
        MaintenancePanel maintenancePanel = new MaintenancePanel();

        contentPanel.add(homePanel, DASHBOARD);
        contentPanel.add(vehiclePanel, VEHICLES);
        contentPanel.add(customerPanel, CUSTOMERS);
        contentPanel.add(bookingPanel, BOOKINGS);
        contentPanel.add(maintenancePanel, MAINTENANCE);

        cardLayout.show(contentPanel, DASHBOARD);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            cardLayout.show(contentPanel, DASHBOARD);
        } else if (e.getSource() == vehiclesButton) {
            cardLayout.show(contentPanel, VEHICLES);
        } else if (e.getSource() == customersButton) {
            cardLayout.show(contentPanel, CUSTOMERS);
        } else if (e.getSource() == bookingsButton) {
            cardLayout.show(contentPanel, BOOKINGS);
        } else if (e.getSource() == maintenanceButton) {
            cardLayout.show(contentPanel, MAINTENANCE);
        } else if (e.getSource() == logoutButton) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        }
    }
}