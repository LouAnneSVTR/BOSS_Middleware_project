package com.middleware.app.networking;

import com.middleware.app.models.players.Player;

public class PlayerHandler extends Thread {
    private final UDPGameWrapper udpWrapper;
    private final Player player;

    public PlayerHandler(UDPGameWrapper udpWrapper, Player player) {
        this.udpWrapper = udpWrapper;
        this.player = player;
    }

    @Override
    public void run() {
    }
}