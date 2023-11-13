package com.middleware.app.core.worker;

import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.ConsoleColors;
import com.middleware.app.utils.TextFrame;

import javax.swing.text.BadLocationException;
import java.awt.*;


public class WorkerPlayer extends Worker {

    public WorkerPlayer(Player player, Boss boss, boolean displayFrame) {
        super(player, boss);

        if (displayFrame) {
            this.textArea = new TextFrame(this);
        }
    }

    public void attackBoss() throws BadLocationException {
        int playerAttack = this.player.activateAbility(this.currentIndexAbilityUsed);

        if (this.player.isAlive() && playerAttack != -1) {
            this.boss.takeDamage(playerAttack);

            System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorGreen(" attacked ") + ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorBlue(" for ") + ConsoleColors.colorWhiteBright(Integer.toString(playerAttack)) + ConsoleColors.colorBlue(" damage with ability ") + ConsoleColors.colorWhiteBright(this.player.getNameAbility(this.currentIndexAbilityUsed)));
            TextFrame.colorText(this.player.getName(), Color.ORANGE);
            TextFrame.colorText(" attacked ", Color.GREEN);
            TextFrame.colorText(this.boss.getName(), Color.MAGENTA);
            TextFrame.colorText(" for ", Color.BLUE);
            TextFrame.colorText(Integer.toString(playerAttack), Color.WHITE);
            TextFrame.colorText(" damage with ability ", Color.BLUE);
            TextFrame.colorText(this.player.getNameAbility(this.currentIndexAbilityUsed), Color.WHITE);
            TextFrame.lineBreak();
        }
    }

    @Override
    public void run() {
        // Boucle des attaques du joueur (thread) sur le boss
        while (player.isAlive() && this.boss.isAlive()) {
            /*try {
                attackBoss();
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }*/
        }

        if (!this.boss.isAlive()) {
            System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorGreen(" win!"));
        } else {
            System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorRed(" has been defeated!"));
        }
    }


}
