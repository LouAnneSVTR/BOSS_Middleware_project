package com.middleware.app.models.bosses;


import com.middleware.app.models.abilities.AbilitiesRankBoss;
import com.middleware.app.models.abilities.Ability;
import com.middleware.app.models.abilities.AbilityBoss;
import com.middleware.app.utils.ConsoleColors;

import static com.middleware.app.models.abilities.AbilitiesRankBoss.*;
import static com.middleware.app.utils.TextFrame.textArea;

public class IceDragon extends Boss {

    public IceDragon() {
        super("Ice Dragon");
        // Initialize Ice Dragon's abilities
        addAbility(FROST_BREATH_ID, new AbilityBoss(FROST_BREATH_ID, "Frost Breath", 30, 3, 0.10, 1.5)); // Damage ability with a small crit chance
        addAbility(BLIZZARD_ID, new AbilityBoss(BLIZZARD_ID, "Blizzard", 45, 5, 0.15, 1.5)); // Strong damage ability with crit chance
        addAbility(ICY_VEIL_ID, new AbilityBoss(ICY_VEIL_ID, "Icy Veil", 0, 6, 0.0, 1.0)); // Defensive ability, immune for the next attack
    }


    @Override
    public synchronized void takeDamage(int damage) {
        this.health -= damage;
        System.out.println(ConsoleColors.colorPurple(this.name) + ConsoleColors.colorRed(" took ") + ConsoleColors.colorWhiteBright(Integer.toString(damage)) + ConsoleColors.colorBlue(" damage. Health: ") + ConsoleColors.colorWhiteBright(Integer.toString(this.health)));
        textArea.updateBossLife(this.health);
    }

    @Override
    public int performAction(AbilitiesRankBoss id) {
        int resultAbility = -1;

        if (id != NO_ATTACK_BOSS) {
            Ability selectedAbility = this.abilities.get(id);

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

