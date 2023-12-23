package com.middleware.app.game.abilities;

public class HealingAbility extends Ability {
    public HealingAbility(String name, int healAmount, int cooldown) {
        super(name, healAmount, cooldown, 0.0, 1.0);
    }

    public int heal() {
        setLastUsedTimestamp(System.currentTimeMillis());
        return getPower();
    }
}
