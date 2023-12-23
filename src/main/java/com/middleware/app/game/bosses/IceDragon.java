package com.middleware.app.game.bosses;

import com.middleware.app.game.abilities.*;

public class IceDragon extends Boss {
    public IceDragon() {
        super("Ice Dragon");
        addAbility(Abilities.DRAGON_FROST_BREATH, new DamageAbility("Frost Breath", 2, 10, 0.10, 1.5));
        addAbility(Abilities.DRAGON_BLIZZARD, new DamageAbility("Blizzard", 10, 10, 0.15, 1.5));
    }

    @Override
    public int performAction(Abilities id) {
        int result = -1;
        Ability ability = getAbility(id);

        if (ability != null && ability.isAvailable()) {
            if (ability instanceof DamageAbility) {
                result = ability.useAbility();
                // Apply damage to players
            } else if (ability instanceof DefensiveAbility) {
                ((DefensiveAbility) ability).activateDefense();
                result = 0;
            }
        }

        return result;
    }
}
