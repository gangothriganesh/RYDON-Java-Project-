package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rydon.dao.impl.UserDAOImpl;
import com.rydon.model.User;
import com.rydon.service.UserService;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backToLoginButton;
    private UserService userService;

    public RegisterFrame() {
        this.userService = new UserService(new UserDAOImpl());

        setTitle("RYDON - Register New User");
        setSize(800, 500); // Match LoginFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 1. Create Sidebar Panel
        add(createSidebarPanel(), BorderLayout.WEST);

        // 2. Create Register Form Panel
        add(createRegisterFormPanel(), BorderLayout.CENTER);
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setBackground(Theme.SIDEBAR_BACKGROUND);
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setLayout(new GridBagLayout());
        
        Box contentBox = Box.createVerticalBox();
        contentBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel("RYDON");
        logoLabel.setFont(Theme.FONT_BOLD_LARGE);
        logoLabel.setForeground(Theme.TEXT_PRIMARY);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel taglineLabel = new JLabel("Create Your Account");
        taglineLabel.setFont(Theme.FONT_PLAIN_MEDIUM);
        taglineLabel.setForeground(Theme.TEXT_SECONDARY);
        taglineLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentBox.add(logoLabel);
        contentBox.add(Box.createRigidArea(new Dimension(0, 10)));
        contentBox.add(taglineLabel);
        
        sidebar.add(contentBox);
        return sidebar;
    }

    private JPanel createRegisterFormPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        registerPanel.setBackground(Theme.CONTENT_BACKGROUND);
        registerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Box formBox = Box.createVerticalBox();

        JLabel title = new JLabel("Get Started");
        title.setFont(Theme.FONT_BOLD_LARGE);
        title.setForeground(Theme.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(title);
        formBox.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(nameLabel);
        
        nameField = new JTextField(25);
        nameField.setFont(Theme.FONT_PLAIN_MEDIUM);
        nameField.setPreferredSize(new Dimension(300, 40));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(nameField);
        formBox.add(Box.createRigidArea(new Dimension(0, 15)));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(Theme.FONT_BOLD_MEDIUM);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(emailLabel);
        
        emailField = new JTextField(25);
        emailField.setFont(Theme.FONT_PLAIN_MEDIUM);
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
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        formBox.add(passwordField);
        formBox.add(Box.createRigidArea(new Dimension(0, 30)));

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setBackground(Theme.CONTENT_BACKGROUND);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        registerButton = new JButton("Create Account");
        styleButton(registerButton, Theme.BUTTON_PRIMARY_BG, Theme.BUTTON_PRIMARY_FG);
        buttonPanel.add(registerButton);
        
        backToLoginButton = new JButton("Back to Login");
        styleButton(backToLoginButton, Theme.BUTTON_SECONDARY_BG, Theme.BUTTON_SECONDARY_FG);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(backToLoginButton);
        
        formBox.add(buttonPanel);

        // Add action listeners
        registerButton.addActionListener(e -> handleRegistration());
        backToLoginButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        registerPanel.add(formBox, gbc);
        
        return registerPanel;
    }
    
    private void styleButton(JButton button, Color bg, Color fg) {
        button.setFont(Theme.FONT_BOLD_MEDIUM);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void handleRegistration() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setRole("customer");

        try {
            userService.registerUser(newUser);
            JOptionPane.showMessageDialog(this, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginFrame().setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Registration failed. Email might already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}