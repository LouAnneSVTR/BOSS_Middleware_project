package com.middleware.app;

import com.middleware.app.core.Core;
import com.middleware.app.network.NetworkPlayer;
import com.middleware.app.network.rmi.Lobby;
import com.middleware.app.network.rmi.LobbyInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Main {

    public static final int UDP_PORT = 6006;
    public static final int LOBBY_PORT = 5006;

    public static void main(String[] args) {

        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your player name: ");
            String playerName = scanner.nextLine();

            System.out.println("Do you want to (1) Host a game or (2) Join a game?");
            int choice = Integer.parseInt(scanner.nextLine());

            NetworkPlayer player = new NetworkPlayer(playerName, UDP_PORT);
            Core gameInitialized;

            if (choice == 1) {
                gameInitialized = hostGame(player);
            } else if (choice == 2) {
                System.out.print("Enter the host's IP address: ");
                String host = scanner.nextLine();
                gameInitialized = joinGame(host, player);
            } else {
                throw new RuntimeException("Invalid choice. Exiting.");
            }

            System.out.println("Starting Game ...");
            gameInitialized.startGameLoop();

        } catch (Exception e) {
            System.err.println("Game Failed: " + e);
        }
    }

    public static Core hostGame(NetworkPlayer player) throws RuntimeException {

        try {
            Lobby lobby = new Lobby();
            Registry registry = LocateRegistry.createRegistry(LOBBY_PORT);
            registry.rebind("Lobby", lobby);

            int response = lobby.joinLobby(player);

            if(response < 0) {
                throw new RuntimeException("Failed to join lobby ...");
            }

            player.setPlayerId(response);

            System.out.printf("[%d] Hosting Lobby ! Waiting for the game to start...\n", player.getPlayerId());
            lobby.waitForGameStart();

            return new Core(player.getPlayerId(), lobby.getPlayers(), true);

        } catch (Exception e) {
            System.err.println("Host exception: " + e);
            throw new RuntimeException("Failed to Host Lobby");
        }
    }

    public static Core joinGame(String host, NetworkPlayer player) throws RuntimeException {
        try {

            Registry registry = LocateRegistry.getRegistry(host, LOBBY_PORT);
            LobbyInterface lobby = (LobbyInterface) registry.lookup("Lobby");

            int response = lobby.joinLobby(player);

            if(response < 0) {
                throw new RuntimeException("Failed to join lobby ...");
            }

            player.setPlayerId(response);

            System.out.printf("[%d] Lobby Joined ! Waiting for the game to start...\n", player.getPlayerId());
            lobby.waitForGameStart();

            return new Core(player.getPlayerId(), lobby.getPlayers(), false);

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            throw new RuntimeException("Failed to Join Lobby ...");
        }
    }
}

