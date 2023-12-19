package com.middleware.app.core.worker;

import com.middleware.app.game.abilities.AbilitiesRankPlayer;
import com.middleware.app.game.bosses.Boss;
import com.middleware.app.game.players.Player;
import com.middleware.app.others.TextFrame;

public abstract class Worker extends Thread {
    protected final Player player;
    protected final Boss boss;
    protected TextFrame textArea;

    protected AbilitiesRankPlayer currentIndexAbilityUsed;

    public Worker(Player player, Boss boss) {
        this.player     = player;
        this.boss       = boss;

        this.currentIndexAbilityUsed = AbilitiesRankPlayer.NO_ATTACK_PLAYER;



    }

    public AbilitiesRankPlayer getCurrentIndexAbilityUsed() {
        return currentIndexAbilityUsed;
    }

    public void setCurrentIndexAbilityUsed(AbilitiesRankPlayer currentIndexAbilityUsed) {
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
