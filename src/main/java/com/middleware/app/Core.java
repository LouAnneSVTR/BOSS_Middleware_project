package com.middleware.app;

import com.middleware.app.game.abilities.AbilitiesRankPlayer;
import com.middleware.app.game.bosses.IceDragon;
import com.middleware.app.game.players.LightGuardian;
import com.middleware.app.network.NetworkPlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Core {
    private final Map<NetworkPlayer, LightGuardian> otherPlayersMap;
    private final NetworkPlayer currentPlayer;
    private final LightGuardian currentPlayerCharacter;
    private IceDragon boss;
    private boolean isRunning;
    private final ExecutorService executorService;

    public Core(List<NetworkPlayer> players, NetworkPlayer currentPlayer) {

        this.currentPlayer = currentPlayer;
        this.currentPlayerCharacter = new LightGuardian();

        otherPlayersMap = new HashMap<>();

        for (NetworkPlayer player : players) {
            if (!player.equals(currentPlayer)) {
                LightGuardian lightGuardian = new LightGuardian();
                otherPlayersMap.put(player, lightGuardian);
            }
        }
        System.out.println("Other Players Size: " + otherPlayersMap.size());

        boss = new IceDragon();
        executorService = Executors.newFixedThreadPool(players.size());
    }

    public synchronized void startGameLoop() throws InterruptedException {

        if (isRunning) {
            throw new IllegalStateException("Game loop already running");
        }
        isRunning = true;

        startNetworkCommunication(otherPlayersMap.keySet());
        // Game logic goes here...

        while (isRunning) {
            // Example: Send test message from currentPlayer to others
            String message = "Test message from: " + currentPlayer.getPseudo();

            for (NetworkPlayer otherPlayer : otherPlayersMap.keySet()) {
                currentPlayer.sendUdpObj(otherPlayer.getIpAddress(), otherPlayer.getUdpPort(), message);
            }

            // ===============================
            // TO DO: VOIR COMMENT ON ORGANISE LECHANGE DE DONNÉES ET LA GAME LOGIQUE
            int dmg = currentPlayerCharacter.activateAbility(AbilitiesRankPlayer.DIVINE_STRIKE_ID);
            boss.takeDamage(dmg);
            // ===============================

            Thread.sleep(2000);
        }

        shutdownNetworkCommunication();
    }

    private void startNetworkCommunication(Set<NetworkPlayer> players) {
        for (NetworkPlayer player : players) {
            executorService.submit(() -> handleNetworkCommunication(player));
        }
    }

    private void shutdownNetworkCommunication() {
        executorService.shutdownNow();
    }

    private void handleNetworkCommunication(NetworkPlayer player) {
        try {

            while (!Thread.currentThread().isInterrupted()) {

                // Receive and process messages
                String test = (String) player.rcvUdpObj();
                System.out.println("Received: " + test);
            }

        } catch (Exception e) {
            System.err.println("UDP Communication Error: " + e.getMessage());
        }
    }
}

