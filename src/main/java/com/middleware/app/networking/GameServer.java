package com.middleware.app.networking;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer {
    private static final Logger LOGGER = Logger.getLogger(GameServer.class.getName());
    private final UDPGameWrapper udpWrapper;
    private final Map<InetAddress, Integer> connectedPlayers;

    public GameServer(int port, int bufferSize) throws IOException {
        udpWrapper = new UDPGameWrapper(port, bufferSize);
        connectedPlayers = new ConcurrentHashMap<>();
        LOGGER.info("GameServer initialized on port " + port + " with buffer size " + bufferSize);
    }

    public void start() {
        LOGGER.info("GameServer started and waiting for connections...");

        while (true) {
            try {
                byte[] receivedData = udpWrapper.receivePacket();
                InetAddress playerAddress = InetAddress.getByAddress(Arrays.copyOfRange(receivedData, 0, 4));
                int playerPort = ByteBuffer.wrap(Arrays.copyOfRange(receivedData, 4, 6)).getShort();

                // Check if the player is connected
                if (!connectedPlayers.containsKey(playerAddress)) {
                    connectedPlayers.put(playerAddress, playerPort);
                    udpWrapper.sendObject(playerAddress, playerPort, "La connexion a été établie correctement.");
                    LOGGER.info("New player connected: " + playerAddress + ":" + playerPort);
                } else {
                    udpWrapper.sendObject(playerAddress, playerPort, "Le joueur était déjà connecté.");
                    LOGGER.warning("Player reconnection attempt: " + playerAddress + ":" + playerPort);
                }

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error while receiving or processing packet", e);
            }
        }
    }
}