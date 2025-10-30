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

import com.rydon.model.User;

public class CustomerDashboard extends JFrame implements ActionListener {

    private JPanel navigationPanel;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private User currentUser;

    private JButton browseButton;
    private JButton bookingsButton;
    private JButton logoutButton;

    private static final String BROWSE = "Browse";
    private static final String BOOKINGS = "Bookings";

    public CustomerDashboard(User user) {
        this.currentUser = user;

        setTitle("Customer Dashboard - Welcome, " + currentUser.getName());
        setSize(1000, 700);
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

        JLabel welcomeLabel = new JLabel("Welcome,");
        welcomeLabel.setFont(Theme.FONT_PLAIN_MEDIUM);
        welcomeLabel.setForeground(Theme.TEXT_SECONDARY);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 0));
        navigationPanel.add(welcomeLabel);

        JLabel nameLabel = new JLabel(currentUser.getName());
        nameLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        nameLabel.setForeground(Theme.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 30, 0));
        navigationPanel.add(nameLabel);

        browseButton = createNavButton("Browse & Rent");
        bookingsButton = createNavButton("My Bookings");
        
        navigationPanel.add(browseButton);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(bookingsButton);
        
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
        
        // 1. Create the panels
        // ** FIX: Passed 'currentUser' to the VehiclePanel constructor **
        VehiclePanel vehiclePanel = new VehiclePanel(false, currentUser); 
        BookingPanel bookingPanel = new BookingPanel(false, currentUser.getId()); 

        // 2. Link the panels
        vehiclePanel.setBookingPanelToRefresh(bookingPanel);

        // 3. Add to layout
        contentPanel.add(vehiclePanel, BROWSE);
        contentPanel.add(bookingPanel, BOOKINGS);

        cardLayout.show(contentPanel, BROWSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == browseButton) {
            cardLayout.show(contentPanel, BROWSE);
        } else if (e.getSource() == bookingsButton) {
            cardLayout.show(contentPanel, BOOKINGS);
        } else if (e.getSource() == logoutButton) {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        }
    }
}