package com.middleware.app.models.bosses;

import com.middleware.app.models.abilities.Ability;

import java.util.HashMap;
import java.util.Map;

public abstract class Boss {

    private final String name;
    public static final int BOSS_MAX_HEALTH = 100;
    private int health;
    private Map<Integer, Ability> abilities;

    public Boss(String name) {
        this.name = name;
        this.health = BOSS_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public abstract void takeDamage(int damage);

    public void addAbility(int id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract void performAction(); // Implement boss logic for choosing and using abilities

    protected int getHealth() {
        return health;
    }

    protected Map<Integer, Ability> getAbilities() {
        return abilities;
    }

    // TO DO...
}

