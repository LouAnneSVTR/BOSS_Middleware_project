package com.middleware.app.game.bosses;

import com.middleware.app.game.abilities.*;

public class IceDragon extends Boss {
    public IceDragon() {
        super("Ice Dragon");

        // Initialize Ice Dragon's abilities
        addAbility(Abilities.DRAGON_FROST_BREATH.ordinal(), new DamageAbility("Frost Breath", 30, 2, 0.10, 1.5));
        addAbility(Abilities.DRAGON_BLIZZARD.ordinal(), new DamageAbility("Blizzard", 45, 5, 0.15, 1.5));
        addAbility(Abilities.DRAGON_ICY_VEIL.ordinal(), new DefensiveAbility("Icy Veil", 6, 0.5)); // 50% damage reduction
    }

    @Override
    public int performAction(Integer id) {
        int result = -1;
        Ability ability = getAbility(id);

        if (ability != null && ability.isAvailable()) {
            if (ability instanceof DamageAbility) {
                result = ((DamageAbility) ability).useAbility();
                // Apply damage to players
            } else if (ability instanceof DefensiveAbility) {
                ((DefensiveAbility) ability).activateDefense();
                result = 0;
            }
            // Note: IceDragon does not have healing abilities in this setup
        }

        return result;
    }
}
