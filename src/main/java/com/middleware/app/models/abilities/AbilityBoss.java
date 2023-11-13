package com.middleware.app.models.abilities;

public class AbilityBoss extends Ability {
    private final AbilitiesRankBoss id;
    public AbilityBoss(AbilitiesRankBoss id, String name, int power, int cooldown, double critChance, double critMultiplier) {
        super(name, power, cooldown, critChance, critMultiplier);

        this.id = id;
    }

    public AbilitiesRankBoss getId() {
        return id;
    }
}
