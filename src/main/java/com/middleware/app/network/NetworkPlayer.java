package com.middleware.app.network;

import com.middleware.app.core.Core;
import com.middleware.app.network.rmi.Lobby;
import com.middleware.app.network.rmi.LobbyInterface;
import com.middleware.app.network.utils.NetworkUtils;
import java.io.Serializable;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkPlayer implements Serializable {

    private final String pseudo;
    private Integer playerId;
    private final String ipAddress;
    private final int udpPort;

    // Add a serialVersionUID (recommended for Serializable classes)
    private static final long serialVersionUID = 1L;

    public NetworkPlayer(String pseudo, int udpPort) throws SocketException {
        this.pseudo = pseudo;
        this.udpPort = udpPort;
        this.ipAddress = NetworkUtils.getLocalHostAddress();
    }

    // Getters and Setters
    public String getPseudo() { return pseudo; }
    public String getIpAddress() { return ipAddress; }
    public int getUdpPort() { return udpPort; }

    public void setPlayerId(Integer playerId) { this.playerId = playerId; }
    public Integer getPlayerId() {
        return playerId;
    }
}

