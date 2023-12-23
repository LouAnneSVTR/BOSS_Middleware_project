package com.middleware.app.game.abilities;

public class DefensiveAbility extends Ability {
    private long lastActivatedTimestamp;
    private final double damageReduction; // A value between 0.0 and 1.0
    private final int durationSeconds; // Duration of the ability effect

    public DefensiveAbility(String name, int durationSeconds, int cooldownSeconds, double damageReduction) {
        super(name, 0, cooldownSeconds, 0.0, 1.0);
        this.damageReduction = damageReduction;
        this.durationSeconds = durationSeconds;
        this.lastActivatedTimestamp = -1; // Not activated initially
    }

    public void activateDefense() {
        if (isAvailable()) {
            this.lastActivatedTimestamp = System.currentTimeMillis();
            setLastUsedTimestamp(System.currentTimeMillis()); // Set the last used timestamp for cooldown tracking
        }
    }

    public boolean isDefenseActive() {
        if (lastActivatedTimestamp < 0) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastActivatedTimestamp) < durationSeconds * 1000L;
    }

    public double getDamageReduction() {
        return isDefenseActive() ? damageReduction : 0.0;
    }

    @Override
    public boolean isAvailable() {
        long currentTime = System.currentTimeMillis();
        long cooldownMillis = getCooldown() * 1000L;
        return (currentTime - getLastUsedTimestamp()) >= cooldownMillis;
    }
}
