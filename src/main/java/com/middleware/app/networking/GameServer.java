package com.middleware.app.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {

    private networking.UDPGameWrapper udpWrapper;
    private Map<InetAddress, Integer> connectedPlayers;

    public GameServer(int port) throws IOException {
        udpWrapper = new networking.UDPGameWrapper(port);
        connectedPlayers = new ConcurrentHashMap<>();
    }
}