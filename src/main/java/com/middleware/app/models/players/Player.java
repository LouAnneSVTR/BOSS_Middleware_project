package com.middleware.app.models.players;

import com.middleware.app.models.abilities.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Player {

    public static final int PLAYER_MAX_HEALTH = 100;
    private final String name;
    private int health;
    protected Map<Integer, Ability> abilities; // Using a HashMap with ID as the key

    public Player(String name) {
        this.name = name;
        this.health = PLAYER_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public void addAbility(int id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract int activateAbility(int id);

    public void receiveDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public void restoreHealth(int heal) {
        health += heal;
        if(health > PLAYER_MAX_HEALTH) health = PLAYER_MAX_HEALTH;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public String getNameAbility(int index) {
        return this.abilities.get(index).getName();
    }

    public Ability getAbility(int index) {
        return this.abilities.get(index);
    }

    public int randomAbility() {
        int i = new Random().nextInt(this.abilities.size()) + 1;
        return i;
    }

    // TO DO
}

