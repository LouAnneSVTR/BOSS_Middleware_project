package com.middleware.app.network.udp;

import java.io.Serializable;

public class GamePacket implements Serializable {
    private final int playerId;
    private final OperationType operation;
    private final int value;

    public enum OperationType {
        BOSS_DO_DMG, TAKE_DMG
    }

    public GamePacket(int playerId, OperationType operation, int value) {
        this.playerId = playerId;
        this.operation = operation;
        this.value = value;
    }

    // Getters
    public int getPlayerId() { return playerId; }
    public OperationType getOperation() { return operation; }
    public int getValue() { return value; }
}


