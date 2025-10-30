package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class MaintenancePanel extends JPanel {

    public MaintenancePanel() {
        setBackground(Theme.CONTENT_BACKGROUND);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Vehicle Maintenance Log");
        titleLabel.setFont(Theme.FONT_BOLD_LARGE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(titleLabel, BorderLayout.NORTH);

        JTextArea notes = new JTextArea("Maintenance logging and table to be implemented here.\n" +
                "This will require MaintenanceDAO and MaintenanceService.");
        notes.setEditable(false);
        notes.setFont(Theme.FONT_PLAIN_MEDIUM);
        notes.setBackground(Theme.CONTENT_BACKGROUND);
        notes.setForeground(Theme.TEXT_SECONDARY);
        add(new JScrollPane(notes), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Theme.CONTENT_BACKGROUND);
        JButton logButton = new JButton("Log Maintenance");
        logButton.setFont(Theme.FONT_PLAIN_SMALL);
        logButton.setBackground(Theme.BUTTON_PRIMARY_BG);
        logButton.setForeground(Theme.BUTTON_PRIMARY_FG);
        logButton.setFocusPainted(false);
        logButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(logButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}