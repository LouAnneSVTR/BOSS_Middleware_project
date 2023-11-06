package com.middleware.app.models.bosses;

import com.middleware.app.models.abilities.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Boss {

    protected final String name;
    public static final int BOSS_MAX_HEALTH = 100;
    protected int health;
    protected Map<Integer, Ability> abilities;

    public Boss(String name) {
        this.name = name;
        this.health = BOSS_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public abstract void takeDamage(int damage);

    public void addAbility(int id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract int performAction(int id); // Implement boss logic for choosing and using abilities

    protected int getHealth() {
        return health;
    }

    protected Map<Integer, Ability> getAbilities() {
        return abilities;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public Ability getAbility(int index) {
        return this.abilities.get(index);
    }

    public int randomAbility() {
        return new Random().nextInt(this.abilities.size()) + 1;
    }

    public String getNameAbility(int index) {
        return this.abilities.get(index).getName();
    }



    // TO DO...
}

