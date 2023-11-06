package com.middleware.app.core.worker;

import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.ConsoleColors;

import java.util.ArrayList;
import java.util.Random;

public class WorkerBoss extends Worker {

    private final ArrayList<Player> players;
    public WorkerBoss(Boss boss, ArrayList<Player> players) {
        super(null, boss);

        this.players = players;
    }
    private void attackPlayer() {
        int randomPlayerId = new Random().nextInt(this.players.size());
        Player randomPlayer = this.players.get(randomPlayerId);

        int indexAbility = this.boss.randomAbility();
        int damageDealt = this.boss.performAction(indexAbility);

        if (this.boss.isAlive() && damageDealt != -1) {
            randomPlayer.receiveDamage(damageDealt);
            System.out.println(ConsoleColors.colorPurple(this.boss.getName()) + ConsoleColors.colorGreen(" attacked ") + ConsoleColors.colorYellowBold(randomPlayer.getName()) + ConsoleColors.colorBlue(" for ") + ConsoleColors.colorWhiteBright(Integer.toString(damageDealt)) + ConsoleColors.colorBlue(" damage with ability " + ConsoleColors.colorWhiteBright(this.boss.getNameAbility(indexAbility))));
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

            attackPlayer();

            try {
                Thread.sleep(1500); // Delay between boss attacks
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
