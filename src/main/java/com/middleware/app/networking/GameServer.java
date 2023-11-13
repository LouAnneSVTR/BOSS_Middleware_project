package com.middleware.app.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {

    private final com.middleware.app.networking.UDPGameWrapper udpWrapper;
    private final Map<InetAddress, Integer> connectedPlayers;

    public GameServer(int port, int bufferSize) throws IOException {
        udpWrapper = new com.middleware.app.networking.UDPGameWrapper(port, bufferSize);
        connectedPlayers = new ConcurrentHashMap<>();
    }

    public void start() {
        while (true) {
            try {
                byte[] receivedData = udpWrapper.receivePacket();
                InetAddress playerAddress = InetAddress.getByAddress(Arrays.copyOfRange(receivedData, 0, 4)); // 4 bytes pour l'adresse IP
                int playerPort = ByteBuffer.wrap(Arrays.copyOfRange(receivedData, 4, 6)).getShort(); // 2 bytes pour le port

                //Check if the player is connected
                if (!connectedPlayers.containsKey(playerAddress)) {
                    connectedPlayers.put(playerAddress, playerPort);
                    udpWrapper.sendObject(playerAddress, playerPort, "La connexion a été établie correctement.");
                } else {
                    udpWrapper.sendObject(playerAddress, playerPort, "Le joueur été déjà connecté.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}