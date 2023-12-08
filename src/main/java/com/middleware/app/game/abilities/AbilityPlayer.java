package com.middleware.app.game.abilities;

public class AbilityPlayer extends Ability {
    private final AbilitiesRankPlayer id;
    public AbilityPlayer(AbilitiesRankPlayer id, String name, int power, int cooldown, double critChance, double critMultiplier) {
        super(name, power, cooldown, critChance, critMultiplier);

        this.id = id;
    }

    public AbilitiesRankPlayer getId() {
        return id;
    }

}
