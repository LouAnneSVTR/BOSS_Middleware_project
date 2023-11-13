package com.middleware.app.models.abilities;

public class AbilityPlayer extends Ability {
    private final AbilitiesRank id;
    public AbilityPlayer(AbilitiesRank id, String name, int power, int cooldown, double critChance, double critMultiplier) {
        super(name, power, cooldown, critChance, critMultiplier);

        this.id = id;
    }

    public AbilitiesRank getId() {
        return id;
    }

}
