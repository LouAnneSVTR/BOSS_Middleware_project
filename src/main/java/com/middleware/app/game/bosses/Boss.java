package com.middleware.app.game.bosses;

import com.middleware.app.game.abilities.Ability;
import com.middleware.app.game.abilities.DamageAbility;
import com.middleware.app.game.abilities.DefensiveAbility;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Boss {

    protected final String name;
    public static final int BOSS_MAX_HEALTH = 500;
    protected int health;
    protected Map<Integer, Ability> abilities;

    public Boss(String name) {
        this.name = name;
        this.health = BOSS_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public void addAbility(Integer id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract int performAction(Integer id); // Implement boss logic for choosing and using abilities

    public int getHealth() {
        return health;
    }

    protected Map<Integer, Ability> getAbilities() {
        return abilities;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public Ability getAbility(int key) {
        return this.abilities.get(key);
    }

    public int receiveDamage(int damage) {
        double damageMultiplier = 1.0;

        // Check for active defensive abilities
        for (Ability ability : abilities.values()) {
            if (ability instanceof DefensiveAbility) {
                damageMultiplier -= ((DefensiveAbility) ability).getDamageReduction();
            }
        }

        damageMultiplier = Math.max(0.0, damageMultiplier); // Ensure it's not less than 0
        int finalDamage = (int) (damage * damageMultiplier);

        this.health -= finalDamage;
        if (this.health < 0) this.health = 0;

        return this.health;
    }

    public void handleAbilityUsage(int abilityId, int effectValue, long timestamp) {
        Ability ability = abilities.get(abilityId);
        if (ability != null) {
            ability.setLastUsedTimestamp(timestamp);

            if (ability instanceof DefensiveAbility) {
                // Activate defensive mode or apply a defensive buff
                ((DefensiveAbility) ability).activateDefense();
            }

        }
    }
}

