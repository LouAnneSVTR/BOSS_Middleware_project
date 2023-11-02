package com.middleware.app;

import com.middleware.app.abilities.Ability;
import com.middleware.app.entities.Boss;
import com.middleware.app.entities.Player;

import java.util.ArrayList;
import java.util.List;

/*TODO Faire une classe magie pour gérer des attaques (attaques de zones pour le boss + resistence/faiblesse au type)
TODO Les dégats de zone doivent se syncroniser entre les threads
*/
public class Main {

    public static void main(String[] args) {

        // Create abilities
        Ability paladinStrike   = new Ability("Paladin Strike", 30, 2);
        Ability healingTouch    = new Ability("Healing Touch", 20, 3);
        Ability dragonFire      = new Ability("Dragon Fire", 35, 2);
        Ability dragonRoar      = new Ability("Dragon Roar", 25, 3);

        // Initialize player and boss
        List<Player> players = new ArrayList<>();

        Player player1 = new Player("Knight of Light", 100);
        player1.addAbility(paladinStrike);
        player1.addAbility(healingTouch);

        Player player2 = new Player("Artorias", 100);
        player2.addAbility(paladinStrike);
        player2.addAbility(healingTouch);

        players.add(player1);
        players.add(player2);

        Boss boss = new Boss("Emerald Dragon", 200, players);
        boss.addAbility(dragonFire);
        boss.addAbility(dragonRoar);

        player1.setBoss(boss);
        player2.setBoss(boss);

        boss.start(); // Start the boss thread

        for (Player player : players) {
            player.start(); // Start player threads
        }
    }
}
