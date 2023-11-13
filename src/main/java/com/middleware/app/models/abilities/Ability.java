package com.middleware.app.models.abilities;

import java.util.Random;

public abstract class Ability {
    private final String name;
    private final int power;
    private final int cooldown; // Cooldown in seconds
    private final double critChance; // Chance to perform a critical hit (0.0 to 1.0 where 1.0 is 100%)
    private final double critMultiplier; // Multiplier for critical hits
    private long lastUsedTimestamp;
    private static final Random random = new Random();

    public Ability(String name, int power, int cooldown, double critChance, double critMultiplier) {
        this.name = name;
        this.power = power;
        this.cooldown = cooldown;
        this.critChance = critChance;
        this.critMultiplier = critMultiplier;
        this.lastUsedTimestamp = 0; // Not used yet
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int useAbility() {
        lastUsedTimestamp = System.currentTimeMillis();

        if (isCriticalHit()) {
            return (int)(power * critMultiplier);
        }
        return power;
    }

    private boolean isCriticalHit() {
        return random.nextDouble() < critChance;
    }

    public boolean isAvailable() {
        long currentTime = System.currentTimeMillis();
        long cooldownInMillis = cooldown * 1000L;
        return (currentTime - lastUsedTimestamp) >= cooldownInMillis;
    }
}
