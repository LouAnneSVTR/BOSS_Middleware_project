package Entity;

import java.util.Random;

public class Player extends Thread {
    private final String name;
    private int health;
    private Boss boss;

    private boolean bossIsDead; //Permet de vérifier si le boss est mort

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
        this.bossIsDead = false;
    }

    public String getNamePlayer() {
        return name;
    }

    public boolean takeDamage(int damage) {
        health -= damage;
        System.out.println(name + " took " + damage + " damage. Health: " + health);

        return this.health > 0;
    }

    @Override
    public void run() {
        // Boucle des attaques du joueur (thread) sur le boss
        while (health > 0 && !this.bossIsDead) {
            Boss boss = this.boss;
            int playerAttack = new Random().nextInt(15) + 1; // Random damage
            this.bossIsDead = boss.takeDamage(playerAttack);
            System.out.println(name + " attacked the boss for " + playerAttack + " damage.");
            try {
                Thread.sleep(1000); // Delay between player attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.bossIsDead) {
            System.out.println(this.name + " win!");
        } else {
            System.out.println(name + " has been defeated!");
        }
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
