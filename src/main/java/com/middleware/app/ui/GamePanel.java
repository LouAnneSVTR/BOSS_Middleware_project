package com.middleware.app.ui;

import com.middleware.app.game.abilities.Abilities;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class GamePanel extends JPanel {
    private final JTextPane logTextPane;
    private int bossLife;
    private int playerLife;
    private final Object lifeLock = new Object();
    private final BlockingQueue<Abilities> abilityQueue; // Queue for abilities

    public GamePanel(int maxBossLife, int maxPlayerLife) {

        this.abilityQueue = new LinkedBlockingQueue<>();

        synchronized (lifeLock) {
            this.bossLife = maxBossLife;
            this.playerLife = maxPlayerLife;
        }

        setLayout(new BorderLayout());

        // Configure JTextPane for logs
        logTextPane = new JTextPane();
        logTextPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logTextPane);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel lifeBarPanel = getLifeBarPanel(maxBossLife, maxPlayerLife);
        bottomPanel.add(lifeBarPanel, BorderLayout.NORTH);

        JPanel abilityPanel = new JPanel(new FlowLayout());
        addAbilityButton(abilityPanel, "Divine Strike", Abilities.GUARDIAN_DIVINE_STRIKE);
        addAbilityButton(abilityPanel, "Blessed Healing", Abilities.GUARDIAN_BLESSED_HEALING);
        addAbilityButton(abilityPanel, "Holy Shield", Abilities.GUARDIAN_HOLY_SHIELD);

        bottomPanel.add(abilityPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    private JPanel getLifeBarPanel(int maxBossLife, int maxPlayerLife) {
        JPanel lifeBarPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                int localBossLife, localPlayerLife;

                synchronized (lifeLock) {
                    localBossLife = bossLife;
                    localPlayerLife = playerLife;
                }

                super.paintComponent(g);

                // Display life bars using local variables
                displayLifeBar(g, localPlayerLife, maxPlayerLife, 10, 5, Color.GREEN, "Player");
                displayLifeBar(g, localBossLife, maxBossLife, 10, 35, Color.RED, "Boss");
            }
        };
        lifeBarPanel.setPreferredSize(new Dimension(600, 60));
        return lifeBarPanel;
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

    public void safeUpdateBossLife(int newBossLife) {
        SwingUtilities.invokeLater(() -> updateBossLife(newBossLife));
    }

    public void safeUpdatePlayerLife(int newPlayerLife) {
        SwingUtilities.invokeLater(() -> updatePlayerLife(newPlayerLife));
    }

    private void displayLifeBar(Graphics g, int currentLife, int maxLife, int x, int y, Color color, String label) {
        int barWidth = 200;
        int barHeight = 20;

        // Calculate the width of the filled part of the bar based on the life ratio
        double lifeRatio = (double) currentLife / maxLife;
        int filledWidth = (int) (lifeRatio * barWidth);

        filledWidth = Math.max(0, Math.min(filledWidth, barWidth));

        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);

        String lifeText = label + ": " + currentLife + "/" + maxLife;
        g.drawString(lifeText, x + 5, y + barHeight - 5);
    }

    private void updateBossLife(int newBossLife) {
        synchronized (lifeLock) {
            bossLife = newBossLife;
        }
        repaint();
    }

    private void updatePlayerLife(int newPlayerLife) {
        synchronized (lifeLock) {
            playerLife = newPlayerLife;
        }
        repaint();
    }

    private void addAbilityButton(JPanel panel, String buttonText, Abilities abilityType) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> {
            try {
                abilityQueue.put(abilityType); // Enqueue the ability
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(button);
    }

    // Method to poll an ability from the queue (to be called from another thread)
    public Abilities pollAbility() {
        return abilityQueue.poll();
    }
}
