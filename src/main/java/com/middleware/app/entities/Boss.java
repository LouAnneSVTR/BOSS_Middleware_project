package com.middleware.app.entities;

import com.middleware.app.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Thread {

    private final String name;
    private int health;
    private final List<Player> players;

    private final List<Ability> abilities;

    public Boss(String name, int initialHealth, List<Player> players) {
        this.name       = name;
        this.health     = initialHealth;
        this.players    = players;

        this.abilities  = new ArrayList<>();
    }

    // ############################ GETTER & SETTER ############################
    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public boolean isAliveBoss() {
        return this.health > 0;
    }

    // ############################ FUNCTION ############################
    public synchronized void takeDamage(int damage) {
        this.health -= damage;
        System.out.println(this.name + " took " + damage + " damage. Health: " + this.health);
        if (this.health <= 0) {
            System.out.println("Boss defeated!");
        }
    }

    public int activateAbility(int indexAbility) {
        Ability ability = this.abilities.get(indexAbility);

        ability.useAbility();
        return ability.getPower();
    }

    private void attackPlayer() {
        int randomPlayerId = new Random().nextInt(this.players.size());
        Player randomPlayer = this.players.get(randomPlayerId);

        int indexAbility = new Random().nextInt(this.abilities.size());
        int damageDealt = activateAbility(indexAbility);

        randomPlayer.takeDamage(damageDealt);
        System.out.println(this.name + " attacked " + randomPlayer.getNamePlayer() + " for " + damageDealt + " damage.");

        boolean isAlive = randomPlayer.isAlivePlayer();

        if (!isAlive) {
            this.players.remove(randomPlayerId);
        }
    }

    // ############################ RUN ############################
    @Override
    public void run() {
        // Boucle des attaques (thread) envoyé sur les joueurs
        while (this.health > 0 && !this.players.isEmpty()) {

            attackPlayer();

            try {
                Thread.sleep(1500); // Delay between boss attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (this.health > 0) {
            System.out.println(this.name + " win!");
        } else {
            System.out.println(this.name + " has been defeated!");
        }

    }
}
