package com.rydon.ui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReportPanel extends JPanel {
    public ReportPanel() {
        setLayout(new BorderLayout());
        add(new JLabel("Reports (To be implemented)"), BorderLayout.CENTER);
        // You would add JTables or charts here, fed by the service/DAO layers.
    }
}