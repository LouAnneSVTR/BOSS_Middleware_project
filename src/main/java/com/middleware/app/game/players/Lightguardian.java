package com.middleware.app.game.players;

import com.middleware.app.game.abilities.AbilitiesRankPlayer;
import com.middleware.app.game.abilities.Ability;
import com.middleware.app.game.abilities.AbilityPlayer;
import com.middleware.app.others.TextFrame;

public class Lightguardian extends Player {

    public Lightguardian() {
        super("Light Guardian");
        // Initialize with standard Paladin abilities, assigning an ID to each one
        // For demonstration purposes, the critChance is 20% for damage abilities
        addAbility(AbilitiesRankPlayer.DIVINE_STRIKE_ID, new AbilityPlayer(AbilitiesRankPlayer.DIVINE_STRIKE_ID, "Divine Strike", 25, 2, 0.20, 1.5)); // Damage ability with crit
        addAbility(AbilitiesRankPlayer.HOLY_SHIELD_ID, new AbilityPlayer(AbilitiesRankPlayer.HOLY_SHIELD_ID, "Holy Shield", 0, 4, 0.0, 1.0)); // Defensive ability, no crit
        addAbility(AbilitiesRankPlayer.BLESSED_HEALING_ID, new AbilityPlayer(AbilitiesRankPlayer.BLESSED_HEALING_ID, "Blessed Healing", 20, 3, 0.0, 1.0)); // Healing ability, no crit
        addAbility(AbilitiesRankPlayer.LIGHTS_JUDGMENT_ID, new AbilityPlayer(AbilitiesRankPlayer.LIGHTS_JUDGMENT_ID, "Light's Judgment", 40, 5, 0.20, 2.0)); // Strong damage ability with crit
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
        if (health < 0) health = 0;
        TextFrame.updateLifePlayer(this.health);

    }

    @Override
    public int activateAbility(AbilitiesRankPlayer id) {
        int resultAbility = -1;
        if (id != AbilitiesRankPlayer.NO_ATTACK_PLAYER) {
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

