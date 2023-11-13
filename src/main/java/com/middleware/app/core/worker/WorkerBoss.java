package com.middleware.app.core.worker;

import com.middleware.app.models.abilities.AbilitiesRank;
import com.middleware.app.models.abilities.AbilitiesRankBoss;
import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.ConsoleColors;
import com.middleware.app.utils.TextFrame;

import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class WorkerBoss extends Worker {

    private final ArrayList<Player> players;

    public WorkerBoss(Boss boss, ArrayList<Player> players, TextFrame textFrame) {
        super(null, boss);

        this.players = players;

        textArea = textFrame;
    }
    private void attackPlayer() throws BadLocationException {
        int randomPlayerId = new Random().nextInt(this.players.size());
        Player randomPlayer = this.players.get(randomPlayerId);

        AbilitiesRankBoss indexAbility = this.boss.randomAbility();
        int damageDealt = this.boss.performAction(indexAbility);

        if (this.boss.isAlive() && damageDealt != -1) {
            randomPlayer.receiveDamage(damageDealt);
            System.out.println(ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorGreen(" attacked ") +
                            ConsoleColors.colorYellowBold(randomPlayer.getName()) +
                            ConsoleColors.colorBlue(" for ") +
                            ConsoleColors.colorWhiteBright(Integer.toString(damageDealt)) +
                            ConsoleColors.colorBlue(" damage with ability " +
                            ConsoleColors.colorWhiteBright(this.boss.getNameAbility(indexAbility))));

            TextFrame.colorText(this.boss.getName(), Color.MAGENTA);
            TextFrame.colorText(" attacked ", Color.GREEN);
            TextFrame.colorText(randomPlayer.getName(), Color.ORANGE);
            TextFrame.colorText(" for ", Color.BLUE);
            TextFrame.colorText(Integer.toString(damageDealt), Color.WHITE);
            TextFrame.colorText(" damage with ability ", Color.BLUE);
            TextFrame.colorText(this.boss.getNameAbility(indexAbility), Color.WHITE);
            TextFrame.lineBreak();
        }

        boolean isAlive = randomPlayer.isAlive();

        if (!isAlive) {
            this.players.remove(randomPlayerId);
        }
    }


    @Override
    public void run() {
        // Boucle des attaques (thread) envoyé sur les joueurs
        while (this.boss.isAlive() && !this.players.isEmpty()) {

            try {
                attackPlayer();
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(3000); // Delay between boss attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.boss.isAlive()) {
            System.out.println(ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorGreen(" win!"));
        } else {
            System.out.println(ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorRed(" has been defeated!"));
        }
    }
}
