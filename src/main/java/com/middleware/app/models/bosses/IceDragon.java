package com.middleware.app.models.bosses;

import com.middleware.app.models.abilities.Ability;

public class IceDragon extends Boss {
    private static final int FROST_BREATH_ID = 1;
    private static final int BLIZZARD_ID = 2;
    private static final int ICY_VEIL_ID = 3;

    public IceDragon() {
        super("Ice Dragon");
        // Initialize Ice Dragon's abilities
        addAbility(FROST_BREATH_ID, new Ability(FROST_BREATH_ID, "Frost Breath", 30, 3, 0.10, 1.5)); // Damage ability with a small crit chance
        addAbility(BLIZZARD_ID, new Ability(BLIZZARD_ID, "Blizzard", 45, 5, 0.15, 1.5)); // Strong damage ability with crit chance
        addAbility(ICY_VEIL_ID, new Ability(ICY_VEIL_ID, "Icy Veil", 0, 6, 0.0, 1.0)); // Defensive ability, immune for the next attack
    }


    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public void performAction() {

    }
}

