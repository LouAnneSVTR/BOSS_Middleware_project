package com.middleware.app.core.worker;

import com.middleware.app.models.abilities.AbilitiesRank;
import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.TextFrame;

import static com.middleware.app.models.abilities.AbilitiesRank.NO_ATTACK_PLAYER;


public abstract class Worker extends Thread {
    protected final Player player;
    protected final Boss boss;
    protected TextFrame textArea;

    protected AbilitiesRank currentIndexAbilityUsed;

    public Worker(Player player, Boss boss) {
        this.player     = player;
        this.boss       = boss;

        this.currentIndexAbilityUsed = NO_ATTACK_PLAYER;



    }

    public AbilitiesRank getCurrentIndexAbilityUsed() {
        return currentIndexAbilityUsed;
    }

    public void setCurrentIndexAbilityUsed(AbilitiesRank currentIndexAbilityUsed) {
        this.currentIndexAbilityUsed = currentIndexAbilityUsed;
    }

    @Override
    public abstract void run();

    public TextFrame getTextArea() {
        return textArea;
    }

    public int getLifeBoss() {
        return this.boss.getHealth();
    }
}
