package com.middleware.app.game.players;

import com.middleware.app.game.abilities.Abilities;
import com.middleware.app.game.abilities.Ability;
import com.middleware.app.game.abilities.DefensiveAbility;
import com.middleware.app.game.abilities.HealingAbility;

import java.util.HashMap;
import java.util.Map;

public abstract class Player {

    public static final int PLAYER_MAX_HEALTH = 100;
    private final String name;
    protected int health;
    protected Map<Abilities, Ability> abilities;

    public Player(String name) {
        this.name = name;
        this.health = PLAYER_MAX_HEALTH;
        this.abilities = new HashMap<>();
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

    public void addAbility(Abilities id, Ability ability) {
        abilities.put(id, ability);
    }

    public void restoreHealth(int heal) {
        health += heal;
        if(health > PLAYER_MAX_HEALTH) health = PLAYER_MAX_HEALTH;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public abstract int activateAbility(Abilities id);

    public Ability getAbility(Abilities key) {
        return this.abilities.get(key);
    }

    public void handleAbilityUsage(Abilities abilityId, int effectValue, long timestamp) {
        Ability ability = abilities.get(abilityId);
        if (ability != null) {
            ability.setLastUsedTimestamp(timestamp);

            if (ability instanceof HealingAbility) {
                restoreHealth(effectValue);
            } else if (ability instanceof DefensiveAbility) {
                ((DefensiveAbility) ability).activateDefense();
            }
        }
    }
}

