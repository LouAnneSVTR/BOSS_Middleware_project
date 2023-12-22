package com.middleware.app;

import com.middleware.app.network.NetworkPlayer;
import com.middleware.app.network.udp.GamePacket;
import com.middleware.app.network.udp.GamePacketQueue;
import com.middleware.app.network.udp.ServerUDP;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.*;

public class Core {
    private volatile boolean isRunning;
    private final GamePacketQueue packetQueue = new GamePacketQueue();
    private Thread sendThread;
    private Thread receiveThread;
    private final transient ServerUDP udpServer;

    private final EntityManager entityManager;

    public Core(Integer currentPlayerId, List<NetworkPlayer> players) throws SocketException {

        this.entityManager = new EntityManager(currentPlayerId, players);
        udpServer = new ServerUDP(entityManager.getCurrentPlayer().getUdpPort(), 1024);
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

            Thread.sleep(200);  // Adjust sleep time as needed
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
                    Integer targetId = packet.getPlayerId();

                    // Send packet using UDP
                    InetAddress targetAddr = InetAddress.getByName(entityManager.getPlayerById(targetId).getIpAddress());
                    int targetPort = entityManager.getPlayerById(targetId).getUdpPort();

                    udpServer.sendObject(targetAddr, targetPort, packet);
                }

                Thread.sleep(5);
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

