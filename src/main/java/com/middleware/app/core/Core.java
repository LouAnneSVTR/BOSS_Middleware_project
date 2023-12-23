package com.middleware.app.core;

import com.middleware.app.game.abilities.Abilities;
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

            Abilities ability = gamePanel.pollAbility();
            while (ability != null) {
                handleAbility(ability);
                ability = gamePanel.pollAbility();
            }

        }

        shutdownNetworkCommunication();
    }

    private void handleAbility(Abilities ability) {
        Player currentHero = entityManager.getCurrentHero();
        NetworkPlayer currentPlayer = entityManager.getCurrentPlayer();
        Boss boss = entityManager.getBoss();

        // Activate the ability and get the resulting value (damage, heal amount, etc.)
        int resultValue = currentHero.activateAbility(ability.ordinal());

        // Create a game packet with the ability action
        GamePacket packet = new GamePacket(currentPlayer.getPlayerId(), ability, resultValue);

        // Add additional logic based on ability type
        switch (ability) {
            case GUARDIAN_DIVINE_STRIKE:
                int life = boss.receiveDamage(resultValue);
                gamePanel.safeUpdateBossLife(life);
            case GUARDIAN_BLESSED_HEALING:
            case GUARDIAN_HOLY_SHIELD:
                gamePanel.addLogText("You used " + ability.name() + " with effect: " + resultValue, Color.CYAN);
                break;
        }

        packetQueue.enqueue(packet);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame(entityManager.getCurrentPlayer().getPseudo());
        gamePanel = new GamePanel(Boss.BOSS_MAX_HEALTH, Player.PLAYER_MAX_HEALTH); // Max life values for boss and player

        // Create the key listener
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add(gamePanel);

        frame.pack();
        frame.setVisible(true);
        frame.setFocusableWindowState(true);
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

                    // Broadcast to other players:
                    for(NetworkPlayer player : entityManager.getOtherPlayers()) {

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
        Player hero = entityManager.getHeroById(packet.getPlayerId());
        NetworkPlayer player = entityManager.getPlayerById(packet.getPlayerId());
        Boss boss = entityManager.getBoss();

        int value = packet.getValue();
        long timestamp = packet.getTimestamp();
        Abilities ability = packet.getAbility();

        switch (ability) {
            case DRAGON_FROST_BREATH:
            case DRAGON_BLIZZARD:
                boss.handleAbilityUsage(ability.ordinal(), value, timestamp);
                hero.receiveDamage(value);
                gamePanel.addLogText("Boss attacked " + player.getPseudo() + " for: " + value, Color.RED);
                break;
            case DRAGON_ICY_VEIL:
                boss.handleAbilityUsage(ability.ordinal(), value, timestamp);
                gamePanel.addLogText(boss.getName() + " is in Icy Veil and taking less dmg", Color.BLUE);
                break;
            case GUARDIAN_DIVINE_STRIKE:
                hero.handleAbilityUsage(ability.ordinal(), value, timestamp);
                gamePanel.safeUpdateBossLife(boss.receiveDamage(value));
                gamePanel.addLogText(player.getPseudo() + " attacked for: " + value, Color.RED);
                break;
            case GUARDIAN_HOLY_SHIELD:
                hero.handleAbilityUsage(ability.ordinal(), value, timestamp);
                gamePanel.addLogText(player.getPseudo() + " activated Holy Shield", Color.BLUE);
                break;
            case GUARDIAN_BLESSED_HEALING:
                hero.handleAbilityUsage(ability.ordinal(), value, timestamp);
                gamePanel.addLogText(player.getPseudo() + " healed for: " + value, Color.GREEN);
                break;
            default:
                System.err.println("Unknown ability in received packet");
        }
    }
}