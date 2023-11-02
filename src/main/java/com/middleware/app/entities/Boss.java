package com.middleware.app.entities;

import com.middleware.app.abilities.Ability;
import com.middleware.app.colors.ConsoleColors;

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

    public String getNameBoss() {
        return name;
    }

    // ############################ FUNCTION ############################
    public synchronized void takeDamage(int damage, int i) {
        this.health -= damage;
        System.out.println(ConsoleColors.colorWhiteBright(i + " : ") + ConsoleColors.colorPurple(this.name) + ConsoleColors.colorRed(" took ") + ConsoleColors.colorWhiteBright(Integer.toString(damage)) + ConsoleColors.colorBlue(" damage. Health: ") + ConsoleColors.colorWhiteBright(Integer.toString(this.health)));
    }

    public int activateAbility(int indexAbility, int i) {
        Ability ability = this.abilities.get(indexAbility);

        boolean isUsed = ability.useAbility(this.name, i);

        if (isUsed) {
            return ability.getPower();
        } else {
            return -1;
        }
    }

    private void attackPlayer(int i) {
        int randomPlayerId = new Random().nextInt(this.players.size());
        Player randomPlayer = this.players.get(randomPlayerId);

        int indexAbility = new Random().nextInt(this.abilities.size());
        int damageDealt = activateAbility(indexAbility, i);

        if (isAliveBoss() && damageDealt != -1) {
            randomPlayer.takeDamage(damageDealt, i);
            System.out.println(ConsoleColors.colorWhiteBright(i + " : ") + ConsoleColors.colorPurple(this.name) + ConsoleColors.colorGreen(" attacked ") + ConsoleColors.colorYellowBold(randomPlayer.getNamePlayer()) + ConsoleColors.colorBlue(" for ") + ConsoleColors.colorWhiteBright(Integer.toString(damageDealt)) + ConsoleColors.colorBlue(" damage with ability " + ConsoleColors.colorWhiteBright(this.abilities.get(indexAbility).getName())));
        }

        boolean isAlive = randomPlayer.isAlivePlayer();

        if (!isAlive) {
            this.players.remove(randomPlayerId);
        }
    }

    // ############################ RUN ############################
    @Override
    public void run() {
        int i = 1;
        // Boucle des attaques (thread) envoyé sur les joueurs
        while (this.health > 0 && !this.players.isEmpty()) {

            attackPlayer(i);

            try {
                Thread.sleep(1500); // Delay between boss attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }

        if (this.health > 0) {
            System.out.println(ConsoleColors.colorPurple(this.name) + ConsoleColors.colorGreen(" win!"));
        } else {
            System.out.println(ConsoleColors.colorPurple(this.name) + ConsoleColors.colorRed(" has been defeated!"));
        }

    }
}
