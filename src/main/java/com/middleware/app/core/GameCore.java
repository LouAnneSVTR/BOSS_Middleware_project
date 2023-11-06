package com.middleware.app.core;

import com.middleware.app.core.worker.Worker;
import com.middleware.app.core.worker.WorkerBoss;
import com.middleware.app.core.worker.WorkerPlayer;
import com.middleware.app.models.bosses.Boss;
import com.middleware.app.models.bosses.IceDragon;
import com.middleware.app.models.players.Lightguardian;
import com.middleware.app.models.players.Player;

import java.util.ArrayList;

public class GameCore {

    private final ArrayList<Worker> listThread;

    public GameCore() {
        this.listThread = new ArrayList<>();

        Player player1 = new Lightguardian("Arch");
        Player player2 = new Lightguardian("Vautour");

        ArrayList<Player> players = new ArrayList<>();

        players.add(player1);
        players.add(player2);

        Boss boss = new IceDragon();

        this.listThread.add(new WorkerPlayer(player1, boss));
        this.listThread.add(new WorkerPlayer(player2, boss));
        this.listThread.add(new WorkerBoss(boss, players));
    }

    public void startGame() {
        for (Worker worker : this.listThread) {
            worker.start();
        }
    }

    // TO DO GAME LOGIC
    // CREATE A ROOM FOR 3 PLAYERS
    // WHEN 3 PLAYERS JOINED
    // PICK A RANDOM BOSS
    // START THREAD POOL AND SYNC 3 PLAYERS FOR THE FIGHT
    // GG
}
