package com.middleware.app.network.udp;

import com.middleware.app.game.abilities.Abilities;

import java.io.Serializable;

public class GamePacket implements Serializable, Comparable<GamePacket> {
    private final int playerId;
    private final Abilities ability;
    private final int value;
    private final long timestamp;  // Added timestamp

    public GamePacket(int playerId, Abilities ability, int value) {
        this.playerId = playerId;
        this.ability = ability;
        this.value = value;
        this.timestamp = System.currentTimeMillis(); // Assign the current time as the timestamp
    }

    // Getters
    public int getPlayerId() { return playerId; }
    public Abilities getAbility() { return ability; }
    public int getValue() { return value; }
    public long getTimestamp() { return timestamp; }

    // Compare GamePackets based on timestamp
    @Override
    public int compareTo(GamePacket other) {
        return Long.compare(this.timestamp, other.timestamp);
    }
}
