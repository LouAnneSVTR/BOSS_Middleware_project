package com.middleware.app.entities;

import com.middleware.app.abilities.Ability;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends Thread {
    private final String name;
    private int health;
    private Boss boss;

    private final List<Ability> abilities;

    public Player(String name, int health) {
        this.name       = name;
        this.health     = health;

        this.abilities  = new ArrayList<>();
    }

    // ############################ GETTER & SETTER ############################
    public String getNamePlayer() {
        return name;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public void addAbility(Ability ability) {
        this.abilities.add(ability);
    }

    public boolean isAlivePlayer() {
        return this.health > 0;
    }

    // ############################ FUNCTION ############################
    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println(name + " took " + damage + " damage. Health: " + this.health);
    }
    public int activateAbility(int indexAbility) {
        Ability ability = this.abilities.get(indexAbility);

        ability.useAbility();
        return ability.getPower();
    }

    public void restoreHealth(int heal) {
        this.health += heal;
        System.out.println(this.name + " is healed " + heal + " pv. Health: " + this.health);
    }

    public void attackBoss() {
        int indexAbility = new Random().nextInt(this.abilities.size());
        int playerAttack = activateAbility(indexAbility);

        this.boss.takeDamage(playerAttack);
        System.out.println(name + " attacked the boss for " + playerAttack + " damage.");

    }

    // ############################ RUN ############################
    @Override
    public void run() {
        // Boucle des attaques du joueur (thread) sur le boss
        while (isAlivePlayer() && this.boss.isAliveBoss()) {

            attackBoss();

            try {
                Thread.sleep(1000); // Delay between player attacks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!this.boss.isAliveBoss()) {
            System.out.println(this.name + " win!");
        } else {
            System.out.println(this.name + " has been defeated!");
        }
    }
}
