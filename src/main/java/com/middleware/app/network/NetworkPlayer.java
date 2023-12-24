package com.middleware.app.network;

import com.middleware.app.network.utils.NetworkUtils;

import java.io.Serializable;


public class NetworkPlayer implements Serializable {

    private final String pseudo;
    private Integer playerId;
    private final String ipAddress;
    private final int udpPort;

    // Add a serialVersionUID (recommended for Serializable classes)
    private static final long serialVersionUID = 1L;

    public NetworkPlayer(String pseudo, int udpPort) {
        this.pseudo = pseudo;
        this.udpPort = udpPort;
        this.ipAddress = NetworkUtils.getLocalHostAddress();
    }

    public String getPseudo() { return pseudo; }
    public String getIpAddress() { return ipAddress; }
    public int getUdpPort() { return udpPort; }

    public void setPlayerId(Integer playerId) { this.playerId = playerId; }
    public Integer getPlayerId() {
        return playerId;
    }
}

