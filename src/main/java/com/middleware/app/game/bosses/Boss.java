package com.middleware.app.game.bosses;

import com.middleware.app.game.abilities.Abilities;
import com.middleware.app.game.abilities.Ability;
import java.util.Random;
import com.middleware.app.game.abilities.DefensiveAbility;

import java.util.*;

public abstract class Boss {

    protected final String name;
    public static final int BOSS_MAX_HEALTH = 500;
    protected int health;
    protected Map<Abilities, Ability> abilities;
    private final Random random = new Random();

    public Boss(String name) {
        this.name = name;
        this.health = BOSS_MAX_HEALTH;
        this.abilities = new HashMap<>();
    }

    public void addAbility(Abilities id, Ability ability) {
        abilities.put(id, ability);
    }

    public abstract int performAction(Abilities id); // Implement boss logic for choosing and using abilities

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public Ability getAbility(Abilities key) {
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

    public void handleAbilityUsage(Abilities abilityId, long timestamp) {
        Ability ability = abilities.get(abilityId);
        if (ability != null) {
            ability.setLastUsedTimestamp(timestamp);

            if (ability instanceof DefensiveAbility) {
                // Activate defensive mode or apply a defensive buff
                ((DefensiveAbility) ability).activateDefense();
            }

        }
    }

    public Abilities randomAbility() {

        List<Abilities> availableAbilities = new ArrayList<>();

        for (Abilities abilityId : abilities.keySet()) {
            if (abilities.get(abilityId).isAvailable()) {
                availableAbilities.add(abilityId);
            }
        }

        if (!availableAbilities.isEmpty()) {
            return availableAbilities.get(random.nextInt(availableAbilities.size()));
        }

        return null;
    }
}

