package com.middleware.app;

import com.middleware.app.game.bosses.IceDragon;
import com.middleware.app.game.players.LightGuardian;
import com.middleware.app.network.NetworkPlayer;
import com.middleware.app.network.udp.GamePacket;
import com.middleware.app.network.udp.GamePacketQueue;
import com.middleware.app.network.udp.ServerUDP;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;

public class Core {
    private final Map<NetworkPlayer, LightGuardian> otherPlayersMap;
    private final NetworkPlayer currentPlayer;
    private final LightGuardian currentCharacter;
    private final IceDragon boss;
    private volatile boolean isRunning;
    private final GamePacketQueue packetQueue = new GamePacketQueue();
    private Thread sendThread;
    private Thread receiveThread;
    private final transient ServerUDP udpServer;

    public Core(List<NetworkPlayer> players, NetworkPlayer currentPlayer) throws SocketException {

        this.currentPlayer = currentPlayer;
        this.currentCharacter = new LightGuardian();

        otherPlayersMap = new HashMap<>();

        for (NetworkPlayer player : players) {
            if (!Objects.equals(player.getPseudo(), currentPlayer.getPseudo())) {
                LightGuardian lightGuardian = new LightGuardian();
                otherPlayersMap.put(player, lightGuardian);
            }
        }

        boss = new IceDragon();
        udpServer = new ServerUDP(currentPlayer.getUdpPort(), 1024);
    }

    public synchronized void startGameLoop() throws InterruptedException {
        if (isRunning) {
            throw new IllegalStateException("Game loop already running");
        }

        isRunning = true;

        startNetworkCommunication();

        while (isRunning) {
            // Game logic goes here
            // ...

            Thread.sleep(2000);  // Adjust sleep time as needed
        }

        shutdownNetworkCommunication();
    }

    private void startNetworkCommunication() {
        sendThread = new Thread(this::sendData);
        receiveThread = new Thread(this::receiveData);

        sendThread.start();
        receiveThread.start();
    }

    private void shutdownNetworkCommunication() {
        isRunning = false;
        sendThread.interrupt();
        receiveThread.interrupt();
    }

    private void sendData() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                if (!packetQueue.isEmpty()) {
                    GamePacket packet = packetQueue.dequeue();
                    // Send packet using UDP
                  //  udpServer.sendObject(/* target address */, /* target port */, packet);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
        } catch (Exception e) {
            System.err.println("Error in sending data: " + e.getMessage());
        }
    }

    private void receiveData() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                // Receive packet
                GamePacket packet = (GamePacket) udpServer.receiveObject();
                // Process packet or enqueue it for processing
                processReceivedPacket(packet);
            }
        } catch (Exception e) {
            System.err.println("Error in receiving data: " + e.getMessage());
        }
    }

    private void processReceivedPacket(GamePacket packet) {
        // Implement packet processing logic
    }
}

