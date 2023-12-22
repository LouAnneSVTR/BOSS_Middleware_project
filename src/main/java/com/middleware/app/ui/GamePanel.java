package com.middleware.app.ui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final JTextPane logTextPane;
    private final int maxBossLife;
    private final int maxPlayerLife;
    private int bossLife;
    private int playerLife;

    public GamePanel(int maxBossLife, int maxPlayerLife) {
        this.maxBossLife = maxBossLife;
        this.maxPlayerLife = maxPlayerLife;
        this.bossLife = maxBossLife;
        this.playerLife = maxPlayerLife;

        setLayout(new BorderLayout());

        // Configure JTextPane for logs
        logTextPane = new JTextPane();
        logTextPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextPane);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Life bars will be drawn in a separate panel at the bottom
        JPanel lifeBarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Display life bars
                displayLifeBar(g, playerLife, maxPlayerLife, 10, 5, Color.GREEN, "Player");
                displayLifeBar(g, bossLife, maxBossLife, 10, 35, Color.RED, "Boss");
            }
        };
        lifeBarPanel.setPreferredSize(new Dimension(600, 60));
        add(lifeBarPanel, BorderLayout.SOUTH);
    }

    // Method to add text to the log
    public void addLogText(String text, Color color) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = logTextPane.getStyledDocument();
                Style style = logTextPane.addStyle("ColorText", null);
                StyleConstants.setForeground(style, color);
                doc.insertString(doc.getLength(), text + "\n", style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateBossLife(int newBossLife) {
        bossLife = newBossLife;
        repaint();
    }

    public void updatePlayerLife(int newPlayerLife) {
        playerLife = newPlayerLife;
        repaint();
    }

    private void displayLifeBar(Graphics g, int currentLife, int maxLife, int x, int y, Color color, String label) {
        int barWidth = 200;
        int barHeight = 20;

        // Calculate the width of the filled part of the bar based on the life ratio
        double lifeRatio = (double) currentLife / maxLife;
        int filledWidth = (int) (lifeRatio * barWidth);

        // Ensure that filledWidth is within the bounds of the bar
        filledWidth = Math.max(0, Math.min(filledWidth, barWidth));

        // Draw the filled part of the bar
        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);

        // Draw the border of the bar
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);

        // Draw the label and the life text
        String lifeText = label + ": " + currentLife + "/" + maxLife;
        g.drawString(lifeText, x + 5, y + barHeight - 5);
    }
}
