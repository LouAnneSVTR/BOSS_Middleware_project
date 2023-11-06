package com.middleware.app.core.worker;

import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.players.Player;
import com.middleware.app.utils.ConsoleColors;

import static com.middleware.app.models.players.Lightguardian.DIVINE_STRIKE_ID;

public abstract class Worker extends Thread {
    protected final Player player;
    protected final Boss boss;

    protected int currentIndexAbilityUsed;
    public Worker(Player player, Boss boss) {
        this.player     = player;
        this.boss       = boss;

        this.currentIndexAbilityUsed = DIVINE_STRIKE_ID;
    }

    @Override
    public abstract void run();
}
