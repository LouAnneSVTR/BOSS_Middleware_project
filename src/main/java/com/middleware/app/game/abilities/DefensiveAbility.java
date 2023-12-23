package com.middleware.app.game.abilities;

public class DefensiveAbility extends Ability {
    private long activationTimestamp;
    private final double damageReduction; // A value between 0.0 and 1.0

    public DefensiveAbility(String name, int durationSeconds, double damageReduction) {
        super(name, 0, durationSeconds, 0.0, 1.0);
        this.damageReduction = damageReduction;
        this.activationTimestamp = -1; // Not activated initially
    }

    public void activateDefense() {
        this.activationTimestamp = System.currentTimeMillis();
    }

    public boolean isDefenseActive() {
        if (activationTimestamp < 0) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long durationMillis = getCooldown() * 1000L;
        return (currentTime - activationTimestamp) < durationMillis;
    }

    public double getDamageReduction() {
        return isDefenseActive() ? damageReduction : 0.0;
    }
}
