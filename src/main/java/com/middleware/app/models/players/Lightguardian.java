package com.middleware.app.models.players;

import com.middleware.app.models.abilities.AbilitiesRank;
import com.middleware.app.models.abilities.Ability;
import com.middleware.app.models.abilities.AbilityPlayer;
import com.middleware.app.utils.TextFrame;

import static com.middleware.app.models.abilities.AbilitiesRank.*;

public class Lightguardian extends Player {

    public Lightguardian(String name) {
        super(name);

        // Initialize with standard Paladin abilities, assigning an ID to each one
        // For demonstration purposes, the critChance is 20% for damage abilities
        addAbility(DIVINE_STRIKE_ID, new AbilityPlayer(DIVINE_STRIKE_ID, "Divine Strike", 25, 2, 0.20, 1.5)); // Damage ability with crit
        addAbility(HOLY_SHIELD_ID, new AbilityPlayer(HOLY_SHIELD_ID, "Holy Shield", 0, 4, 0.0, 1.0)); // Defensive ability, no crit
        addAbility(BLESSED_HEALING_ID, new AbilityPlayer(BLESSED_HEALING_ID, "Blessed Healing", 20, 3, 0.0, 1.0)); // Healing ability, no crit
        addAbility(LIGHTS_JUDGMENT_ID, new AbilityPlayer(LIGHTS_JUDGMENT_ID, "Light's Judgment", 40, 5, 0.20, 2.0)); // Strong damage ability with crit
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        if (health < 0) health = 0;
        TextFrame.updateLifePlayer(this.health);

    }

    @Override
    public int activateAbility(AbilitiesRank id) {
        int resultAbility = -1;
        if (id != NO_ATTACK_PLAYER) {
            Ability selectedAbility = abilities.get(id);

            if (selectedAbility == null) {
                throw new IllegalArgumentException("Ability with ID " + id + " does not exist");
            }

            if (selectedAbility.isAvailable()) {
                resultAbility = selectedAbility.useAbility();
            }
        }

        return resultAbility;
    }
}

