package com.middleware.app.networking;

import com.middleware.app.models.abilities.AbilitiesRank;
import com.middleware.app.models.players.Player;

public class ConcretePlayer extends Player {

    public ConcretePlayer(String name) {
        super(name);
    }

    @Override
    public void receiveDamage(int damage) {

    }

    @Override
    public int activateAbility(AbilitiesRank id) {
        return 0;
    }
}