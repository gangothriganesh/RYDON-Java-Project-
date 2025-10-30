package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.rydon.controller.LoginController;
import com.rydon.dao.impl.UserDAOImpl;
import com.rydon.model.User;
import com.rydon.service.UserService;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private LoginController loginController;

    public LoginFrame() {
        setTitle("RYDON - Login");
        setSize(800, 500); // New size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loginController = new LoginController(new UserService(new UserDAOImpl()));

        // 1. Create Sidebar Panel
        add(createSidebarPanel(), BorderLayout.WEST);

        // 2. Create Login Form Panel
        add(createLoginFormPanel(), BorderLayout.CENTER);
    }

    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.SIDEBAR_BACKGROUND);
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setLayout(new GridBagLayout()); // To center content vertically
        
        Box contentBox = Box.createVerticalBox();
        contentBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Add Image Logo Above "RYDON" ---
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/rydon_logo.jpg"));
        Image scaledImage = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the image to the content box first
        contentBox.add(imageLabel);
        contentBox.add(Box.createRigidArea(new Dimension(0, 15))); // spacing

        JLabel logoLabel = new JLabel("RYDON");
        logoLabel.setFont(Theme.FONT_BOLD_LARGE);
        logoLabel.setForeground(Theme.TEXT_PRIMARY);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("Vehicle Rental System");
        taglineLabel.setFont(Theme.FONT_PLAIN_MEDIUM);
        taglineLabel.setForeground(Theme.TEXT_SECONDARY);
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentBox.add(logoLabel);
        contentBox.add(Box.createRigidArea(new Dimension(0, 10)));
        contentBox.add(taglineLabel);
        
        sidebar.add(contentBox); // Add the box to the panel
        return sidebar;
    }

    private JPanel createLoginFormPanel() {
    // Custom panel with background image
    JPanel loginPanel = new JPanel(new GridBagLayout()) {
        private Image backgroundImage = new ImageIcon(getClass().getResource("/images/background.jpg")).getImage();

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

        // Use GridBagLayout to center the form components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form content in its own box
        Box formBox = Box.createVerticalBox();

        JLabel loginTitle = new JLabel("Welcome Back!");
        loginTitle.setFont(Theme.FONT_BOLD_LARGE);
        loginTitle.setForeground(Theme.TEXT_PRIMARY);
        loginTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(loginTitle);
        
        JLabel loginSubtitle = new JLabel("Please login to your account.");
        loginSubtitle.setFont(Theme.FONT_PLAIN_MEDIUM);
        loginSubtitle.setForeground(Theme.TEXT_SECONDARY);
        loginSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(loginSubtitle);
        formBox.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(emailLabel);
        
        emailField = new JTextField(25); // Set preferred size
        emailField.setFont(Theme.FONT_PLAIN_MEDIUM);
        emailField.setMinimumSize(new Dimension(300, 40));
        emailField.setPreferredSize(new Dimension(300, 40));
        emailField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(emailField);
        formBox.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(passwordLabel);

        passwordField = new JPasswordField(25);
        passwordField.setFont(Theme.FONT_PLAIN_MEDIUM);
        passwordField.setMinimumSize(new Dimension(300, 40));
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(passwordField);
        formBox.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setBackground(Theme.CONTENT_BACKGROUND);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton = new JButton("Login");
        styleButton(loginButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
        buttonPanel.add(loginButton);
        
        registerButton = new JButton("Register");
        styleButton(registerButton, Theme.BUTTON_SECONDARY_BG, Theme.BUTTON_SECONDARY_FG);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(registerButton);
        
        formBox.add(buttonPanel);

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> handleRegister());

        // Add the formBox to the main login panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(formBox, gbc);
        
        return loginPanel;
    }
    
    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(Theme.FONT_BOLD_MEDIUM);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        if (email.isEmpty() || password.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Email and Password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
             return;
        }

        User user = loginController.login(email, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getName(), "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            if ("admin".equals(user.getRole())) {
                new AdminDashboard().setVisible(true);
            } else {
                new CustomerDashboard(user).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRegister() {
        new RegisterFrame().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        // Run the UI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}