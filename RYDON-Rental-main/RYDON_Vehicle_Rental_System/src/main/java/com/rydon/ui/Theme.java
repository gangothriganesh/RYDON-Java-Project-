package com.rydon.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * A central class to define the application's soft pastel color scheme and fonts.
 * This makes it easy to manage the UI theme from one place.
 */
public class Theme {

    // --- Soft Pastel Color Palette ---

    // A soft, light blue for sidebars and headers
    public static final Color SIDEBAR_BACKGROUND = new Color(210, 224, 239);

    // A clean, almost-white for main content areas
    public static final Color CONTENT_BACKGROUND = new Color(248, 249, 250);

    // A dark, soft gray for primary text (instead of harsh black)
    public static final Color TEXT_PRIMARY = new Color(52, 58, 64);
    
    // A medium gray for secondary text
    public static final Color TEXT_SECONDARY = new Color(108, 117, 125);

    // A gentle green for primary actions (Login, Add)
    public static final Color BUTTON_PRIMARY_BG = new Color(200, 230, 201);
    public static final Color BUTTON_PRIMARY_FG = new Color(52, 58, 64);

    // A soft red for danger actions (Logout, Delete)
    public static final Color BUTTON_DANGER_BG = new Color(248, 200, 201);
    public static final Color BUTTON_DANGER_FG = new Color(52, 58, 64);

    // A neutral color for secondary buttons (Register)
    public static final Color BUTTON_SECONDARY_BG = new Color(222, 226, 230);
    public static final Color BUTTON_SECONDARY_FG = new Color(52, 58, 64);

    // --- Fonts ---
    public static final Font FONT_BOLD_LARGE = new Font("SansSerif", Font.BOLD, 28);
    public static final Font FONT_BOLD_MEDIUM = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_PLAIN_MEDIUM = new Font("SansSerif", Font.PLAIN, 16);
    public static final Font FONT_PLAIN_SMALL = new Font("SansSerif", Font.PLAIN, 14);
    
    public static final Font FONT_TABLE_HEADER = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_TABLE_ROW = new Font("SansSerif", Font.PLAIN, 14);
}