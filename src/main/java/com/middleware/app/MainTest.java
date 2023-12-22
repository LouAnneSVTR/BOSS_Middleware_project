package com.middleware.app;

import com.middleware.app.game.bosses.Boss;
import com.middleware.app.game.players.Player;
import com.middleware.app.ui.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MainTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Créer la fenêtre principale
            JFrame frame = new JFrame("Game Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Créer le panel de jeu
            GamePanel gamePanel = new GamePanel(Boss.BOSS_MAX_HEALTH, Player.PLAYER_MAX_HEALTH);
            gamePanel.setPreferredSize(new Dimension(600, 400));

            // Ajouter le panel au frame
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Simuler quelques actions
            simulateGameActions(gamePanel);
        });
    }

    private static void simulateGameActions(GamePanel gamePanel) {
        // Simuler des logs et des mises à jour de vie
        SwingUtilities.invokeLater(() -> {
            gamePanel.addLogText("Le jeu commence...", Color.BLACK);
            gamePanel.updatePlayerLife(80); // Exemple: le joueur perd des points de vie
            gamePanel.updateBossLife(450); // Exemple: le boss perd des points de vie

            gamePanel.addLogText("Le joueur attaque le boss!", Color.BLUE);
            gamePanel.addLogText("Le boss réplique!", Color.RED);
        });
    }
}

