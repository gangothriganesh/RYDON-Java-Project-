package com.rydon.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.rydon.dao.impl.UserDAOImpl;
import com.rydon.model.User;
import com.rydon.service.UserService;

public class CustomerPanel extends JPanel {

    private JTable customerTable;
    private DefaultTableModel tableModel;
    private UserService userService;

    public CustomerPanel() {
        this.userService = new UserService(new UserDAOImpl());
        
        setBackground(Theme.CONTENT_BACKGROUND);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Manage Customers");
        titleLabel.setFont(Theme.FONT_BOLD_LARGE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Email", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        
        styleTable();

        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.getViewport().setBackground(Theme.CONTENT_BACKGROUND);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Theme.CONTENT_BACKGROUND);
        JButton deleteButton = new JButton("Delete Selected Customer");
        styleButton(deleteButton, Theme.BUTTON_DANGER_BG, Theme.BUTTON_DANGER_FG);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadCustomerData();
    }
    
    private void styleTable() {
        customerTable.setFont(Theme.FONT_TABLE_ROW);
        customerTable.setRowHeight(25);
        customerTable.setGridColor(Theme.SIDEBAR_BACKGROUND);
        JTableHeader header = customerTable.getTableHeader();
        header.setFont(Theme.FONT_TABLE_HEADER);
        header.setBackground(Theme.SIDEBAR_BACKGROUND);
        header.setForeground(Theme.TEXT_PRIMARY);
    }

    private void loadCustomerData() {
        tableModel.setRowCount(0);
        try {
            List<User> users = userService.getAllUsers();
            for (User user : users) {
                Object[] row = {
                    user.getId(), user.getName(), user.getEmail(), user.getRole()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load customers.", "Error", JOptionPane.ERROR_MESSAGE);
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