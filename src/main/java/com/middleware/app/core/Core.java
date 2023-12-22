package com.middleware.app.core;

import com.middleware.app.game.abilities.AbilitiesRankPlayer;
import com.middleware.app.game.bosses.Boss;
import com.middleware.app.game.bosses.IceDragon;
import com.middleware.app.game.players.LightGuardian;
import com.middleware.app.game.players.Player;
import com.middleware.app.network.NetworkPlayer;
import com.middleware.app.network.udp.GamePacket;
import com.middleware.app.network.udp.GamePacketQueue;
import com.middleware.app.network.udp.ServerUDP;
import com.middleware.app.ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

public class Core {
    private volatile boolean isRunning;
    private final GamePacketQueue packetQueue = new GamePacketQueue();
    private Thread sendThread;
    private Thread receiveThread;
    private final transient ServerUDP udpServer;

    private final EntityManager entityManager;
    private GamePanel gamePanel;

    public Core(Integer currentPlayerId, List<NetworkPlayer> players) throws SocketException {

        this.entityManager = new EntityManager(currentPlayerId, players);
        udpServer = new ServerUDP(entityManager.getCurrentPlayer().getUdpPort(), 1024);
    }

    public synchronized void startGameLoop() throws InterruptedException {
        if (isRunning) {
            throw new IllegalStateException("Game loop already running");
        }

        isRunning = true;

       createAndShowGUI();
        startNetworkCommunication();

        while (isRunning) {

            // TO DO !!!!!
            // Example le joueur fait des degats au boss:
            // if(abilityKeyBInd == divineStrike)

            LightGuardian guardian = entityManager.getCurrentHero();
            int dmg = guardian.activateAbility(AbilitiesRankPlayer.DIVINE_STRIKE_ID);
            IceDragon boss = (IceDragon) entityManager.getBoss();

            gamePanel.addLogText("You used: Divined Strike that did : " + dmg, Color.BLUE);
            gamePanel.safeUpdateBossLife(boss.receiveDamage(dmg));

            // Send to others:
            GamePacket packet = new GamePacket(entityManager.getCurrentPlayer().getPlayerId(), GamePacket.OperationType.PLAYER_DO_DMG, dmg);
            packetQueue.enqueue(packet);

            Thread.sleep(2000);  // Adjust sleep time as needed
        }

        shutdownNetworkCommunication();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Game");
        gamePanel = new GamePanel(Boss.BOSS_MAX_HEALTH, Player.PLAYER_MAX_HEALTH); // Max life values for boss and player

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setVisible(true);
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

                    // Broadcast to other players:
                    for(NetworkPlayer player : entityManager.getOtherPlayers()) {

                        // Send packet using UDP
                        InetAddress targetAddr = InetAddress.getByName(player.getIpAddress());
                        int targetPort = player.getUdpPort();

                        udpServer.sendObject(targetAddr, targetPort, packet);
                    }
                }
            }
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
        int playerId = packet.getPlayerId();
        GamePacket.OperationType operation = packet.getOperation();
        int damage = packet.getValue();

        switch (operation) {
            case BOSS_DO_DMG:
                Player player = entityManager.getHeroById(playerId);
                player.receiveDamage(damage);
                break;
            case PLAYER_DO_DMG:
                IceDragon boss = (IceDragon) entityManager.getBoss();
                boss.receiveDamage(damage);

                gamePanel.addLogText(entityManager.getPlayerById(playerId).getPseudo() + " used: Divined Strike that did : " + damage, Color.ORANGE);
                gamePanel.safeUpdateBossLife(boss.receiveDamage(damage));
                break;
            default:
                System.err.println("Unknown operation in received packet");
        }
    }
}