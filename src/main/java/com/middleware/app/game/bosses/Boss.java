package com.middleware.app.game.bosses;

import com.middleware.app.game.abilities.AbilitiesRankBoss;
import com.middleware.app.game.abilities.Ability;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Boss {

    protected final String name;
    public static final int BOSS_MAX_HEALTH = 500;
    protected int health;
    protected Map<AbilitiesRankBoss, Ability> abilities;

    public Boss(String name) {
        this.name = name;
        this.health = BOSS_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public void addAbility(AbilitiesRankBoss id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract void receiveDamage(int damage);

    public abstract int performAction(AbilitiesRankBoss id); // Implement boss logic for choosing and using abilities

    public int getHealth() {
        return health;
    }

    protected Map<AbilitiesRankBoss, Ability> getAbilities() {
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

    public AbilitiesRankBoss randomAbility() {
        return AbilitiesRankBoss.values()[new Random().nextInt(this.abilities.size()) + 1];
    }

    public String getNameAbility(AbilitiesRankBoss index) {
        return this.abilities.get(index).getName();
    }





    // TO DO...
}

