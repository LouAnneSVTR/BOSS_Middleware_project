package com.middleware.app.utils;

import com.middleware.app.core.worker.WorkerPlayer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.middleware.app.models.abilities.AbilitiesRank.*;
import static com.middleware.app.models.abilities.AbilitiesRank.LIGHTS_JUDGMENT_ID;
import static com.middleware.app.models.bosses.Boss.BOSS_MAX_HEALTH;
import static com.middleware.app.models.players.Player.PLAYER_MAX_HEALTH;

public class GamePanel extends JTextPane {

    private final WorkerPlayer player;
    private int bossLife;
    private int playerLife;
    private final Point positionBossLife;
    private final Point positionPlayerLife;
    private final int sizeFont;

    public GamePanel(WorkerPlayer player) {
        this.player = player;

        this.bossLife = BOSS_MAX_HEALTH;
        this.playerLife = PLAYER_MAX_HEALTH;

        this.positionBossLife = new Point(650, 50);
        this.positionPlayerLife = new Point(650, 150);

        this.sizeFont = 50;
    }
    public void addText(String text, Color color) throws BadLocationException {
        StyledDocument doc = this.getStyledDocument();

        Style style = this.addStyle("ColorText", null);
        StyleConstants.setForeground(style, color);
        doc.insertString(doc.getLength(), text, style);
    }

    public void updateBossLife(int newBossLife) {
        this.bossLife = newBossLife;
    }

    public void updatePlayerLife(int newPlayerLife) {
        this.playerLife = newPlayerLife;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        displayBossLife(g);
        displayPlayerLife(g);
        repaint();
    }

    private void displayBossLife(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.PLAIN,this.sizeFont));
        g.drawString(String.valueOf(this.bossLife), this.positionBossLife.x, this.positionBossLife.y);
    }
    private void displayPlayerLife(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.PLAIN,this.sizeFont));
        g.drawString(String.valueOf(this.playerLife), this.positionPlayerLife.x, this.positionPlayerLife.y);
    }


    public void handleKeyPress(KeyEvent e) throws BadLocationException {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_A:
            {
                this.player.setCurrentIndexAbilityUsed(DIVINE_STRIKE_ID);
                //System.out.println(ConsoleColors.colorWhiteBright("Touche A : ") + ConsoleColors.colorRed(String.valueOf(this.player.getCurrentIndexAbilityUsed())));
                break;
            }
            case KeyEvent.VK_Z:
            {
                this.player.setCurrentIndexAbilityUsed(HOLY_SHIELD_ID);
                //System.out.println(ConsoleColors.colorWhiteBright("Touche Z : ") + ConsoleColors.colorRed(String.valueOf(this.player.getCurrentIndexAbilityUsed())));
                break;
            }
            case KeyEvent.VK_E:
            {
                this.player.setCurrentIndexAbilityUsed(BLESSED_HEALING_ID);
                //System.out.println(ConsoleColors.colorWhiteBright("Touche E : ") + ConsoleColors.colorRed(String.valueOf(this.player.getCurrentIndexAbilityUsed())));
                break;
            }
            case KeyEvent.VK_R:
            {
                this.player.setCurrentIndexAbilityUsed(LIGHTS_JUDGMENT_ID);
                //System.out.println(ConsoleColors.colorWhiteBright("Touche R : ") + ConsoleColors.colorRed(String.valueOf(this.player.getCurrentIndexAbilityUsed())));
                break;
            }
        }
        this.player.attackBoss();
    }
}
