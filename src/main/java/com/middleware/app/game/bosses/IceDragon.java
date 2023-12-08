package com.middleware.app.game.bosses;


import com.middleware.app.game.abilities.AbilitiesRankBoss;
import com.middleware.app.game.abilities.Ability;
import com.middleware.app.game.abilities.AbilityBoss;
import com.middleware.app.others.ConsoleColors;

import static com.middleware.app.others.TextFrame.textArea;

public class IceDragon extends Boss {

    public IceDragon() {
        super("Ice Dragon");
        // Initialize Ice Dragon's abilities
        addAbility(AbilitiesRankBoss.FROST_BREATH_ID, new AbilityBoss(AbilitiesRankBoss.FROST_BREATH_ID, "Frost Breath", 30, 3, 0.10, 1.5)); // Damage ability with a small crit chance
        addAbility(AbilitiesRankBoss.BLIZZARD_ID, new AbilityBoss(AbilitiesRankBoss.BLIZZARD_ID, "Blizzard", 45, 5, 0.15, 1.5)); // Strong damage ability with crit chance
        addAbility(AbilitiesRankBoss.ICY_VEIL_ID, new AbilityBoss(AbilitiesRankBoss.ICY_VEIL_ID, "Icy Veil", 0, 6, 0.0, 1.0)); // Defensive ability, immune for the next attack
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

        if (id != AbilitiesRankBoss.NO_ATTACK_BOSS) {
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

