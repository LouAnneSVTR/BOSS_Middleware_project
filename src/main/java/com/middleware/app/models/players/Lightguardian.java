package com.middleware.app.models.players;

import com.middleware.app.models.abilities.Ability;

public class Lightguardian extends Player {

    public static final int DIVINE_STRIKE_ID = 1;
    private static final int HOLY_SHIELD_ID = 2;
    private static final int BLESSED_HEALING_ID = 3;
    private static final int LIGHTS_JUDGMENT_ID = 4;

    public Lightguardian(String name) {
        super(name);

        // Initialize with standard Paladin abilities, assigning an ID to each one
        // For demonstration purposes, the critChance is 20% for damage abilities
        addAbility(DIVINE_STRIKE_ID, new Ability(DIVINE_STRIKE_ID, "Divine Strike", 25, 2, 0.20, 1.5)); // Damage ability with crit
        addAbility(HOLY_SHIELD_ID, new Ability(HOLY_SHIELD_ID, "Holy Shield", 0, 4, 0.0, 1.0)); // Defensive ability, no crit
        addAbility(BLESSED_HEALING_ID, new Ability(BLESSED_HEALING_ID, "Blessed Healing", 20, 3, 0.0, 1.0)); // Healing ability, no crit
        addAbility(LIGHTS_JUDGMENT_ID, new Ability(LIGHTS_JUDGMENT_ID, "Light's Judgment", 40, 5, 0.20, 2.0)); // Strong damage ability with crit
    }

    @Override
    public int activateAbility(int id) {

        Ability selectedAbility = abilities.get(id);

        if (selectedAbility == null) {
            throw new IllegalArgumentException("Ability with ID " + id + " does not exist");
        }

        if (selectedAbility.isAvailable()) {
            return selectedAbility.useAbility();
        } else {
            // TO DO: Handle case where ability is on cooldown
            return -1;
        }
    }
}

