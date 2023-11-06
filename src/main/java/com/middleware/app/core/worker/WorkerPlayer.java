package com.middleware.app.core.worker;

import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.ConsoleColors;

public class WorkerPlayer extends Worker {

    public WorkerPlayer(Player player, Boss boss) {
        super(player, boss);
    }

    public void attackBoss() {
        if (currentIndexAbilityUsed != -1) {
            int playerAttack = this.player.activateAbility(this.currentIndexAbilityUsed);

            if (this.player.isAlive() && playerAttack != -1) {
                this.boss.takeDamage(playerAttack);

                System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorGreen(" attacked ") + ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorBlue(" for ") + ConsoleColors.colorWhiteBright(Integer.toString(playerAttack)) + ConsoleColors.colorBlue(" damage with ability ") + ConsoleColors.colorWhiteBright(this.player.getNameAbility(this.currentIndexAbilityUsed)));
            }
        }
    }
    @Override
    public void run() {
        // Boucle des attaques du joueur (thread) sur le boss
        while (player.isAlive() && this.boss.isAlive()) {
            this.currentIndexAbilityUsed = this.player.randomAbility();
            attackBoss();
        }

        if (!this.boss.isAlive()) {
            System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorGreen(" win!"));
        } else {
            System.out.println(ConsoleColors.colorYellowBold(this.player.getName()) + ConsoleColors.colorRed(" has been defeated!"));
        }
    }
}
