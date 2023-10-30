package Entity;

import java.util.List;
import java.util.Random;

public class Boss extends Thread {
    private int health;
    private final List<Player> players;

    public Boss(int initialHealth, List<Player> players) {
        this.health = initialHealth;
        this.players = players;
    }

    public synchronized boolean takeDamage(int damage) {
        health -= damage;
        System.out.println("Boss took " + damage + " damage. Health: " + health);
        if (health <= 0) {
            System.out.println("Boss defeated!");
        }

        return this.health <= 0;
    }

    @Override
    public void run() {

        // Boucle des attaques (thread) envoyé sur les joueurs
        while (health > 0 && !players.isEmpty()) {
            int randomPlayerId = new Random().nextInt(players.size());
            Player randomPlayer = players.get(randomPlayerId);
            int damageDealt = new Random().nextInt(20) + 1; // Random damage
            boolean isAlive = randomPlayer.takeDamage(damageDealt);
            System.out.println("Boss attacked " + randomPlayer.getNamePlayer() + " for " + damageDealt + " damage.");

            if (!isAlive) {
                this.players.remove(randomPlayerId);
            }
            try {
                Thread.sleep(1500); // Delay between boss attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.health > 0) {
            System.out.println("Boss win!");
        } else {
            System.out.println("Boss has been defeated!");
        }

    }
}
