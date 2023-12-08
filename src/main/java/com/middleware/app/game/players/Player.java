package com.middleware.app.game.players;

import com.middleware.app.game.abilities.AbilitiesRankPlayer;
import com.middleware.app.game.abilities.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Player {

    public static final int PLAYER_MAX_HEALTH = 100;
    private final String name;
    protected int health;
    protected Map<AbilitiesRankPlayer, Ability> abilities;

    public Player(String name) {
        this.name = name;
        this.health = PLAYER_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public abstract void receiveDamage(int damage);

    public void addAbility(AbilitiesRankPlayer id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract int activateAbility(AbilitiesRankPlayer id);

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

    public String getNameAbility(AbilitiesRankPlayer index) {
        return this.abilities.get(index).getName();
    }

    public Ability getAbility(int index) {
        return this.abilities.get(index);
    }

    public int randomAbility() {
        return new Random().nextInt(this.abilities.size()) + 1;
    }
}

