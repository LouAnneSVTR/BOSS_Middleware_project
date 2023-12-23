package com.middleware.app.game.players;

import com.middleware.app.game.abilities.*;

public class LightGuardian extends Player {
    public LightGuardian() {
        super("Light Guardian");

        // Initialize abilities
        addAbility(Abilities.GUARDIAN_DIVINE_STRIKE, new DamageAbility("Divine Strike", 10, 0, 0.20, 1.5));
        addAbility(Abilities.GUARDIAN_HOLY_SHIELD, new DefensiveAbility("Holy Shield", 4, 6,0.5));
        addAbility(Abilities.GUARDIAN_BLESSED_HEALING, new HealingAbility("Blessed Healing", 20, 10));
    }

    @Override
    public int activateAbility(Abilities id) {
        int result = -1;
        Ability ability = getAbility(id);

        if (ability != null && ability.isAvailable()) {
            if (ability instanceof DamageAbility) {
                result = ability.useAbility();
            } else if (ability instanceof HealingAbility) {
                int healAmount = ((HealingAbility) ability).heal();
                restoreHealth(healAmount);
                result = healAmount;
            } else if (ability instanceof DefensiveAbility) {
                ((DefensiveAbility) ability).activateDefense();
                result = 0; // No damage/healing value
            }
        }

        return result;
    }
}
