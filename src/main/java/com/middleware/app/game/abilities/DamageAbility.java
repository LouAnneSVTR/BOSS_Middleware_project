package com.middleware.app.game.abilities;

public class DamageAbility extends Ability {
    // Constructor
    public DamageAbility(String name, int power, int cooldown, double critChance, double critMultiplier) {
        super(name, power, cooldown, critChance, critMultiplier);
    }

    @Override
    public int useAbility() {
        setLastUsedTimestamp(System.currentTimeMillis());
        return calculateDamage();
    }
}

