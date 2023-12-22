package com.middleware.app;

import com.middleware.app.network.NetworkPlayer;
import java.util.Scanner;

public class Main {

    private static final int UDP_PORT = 6006;

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
                gameInitialized = player.hostGame();
            } else if (choice == 2) {
                System.out.print("Enter the host's IP address: ");
                String host = scanner.nextLine();
                gameInitialized = player.joinGame(host);
            } else {
                throw new RuntimeException("Invalid choice. Exiting.");
            }

            System.out.println("Starting Game ...");
            gameInitialized.startGameLoop();

        } catch (Exception e) {
            System.err.println("Game Failed: " + e);
        }
    }
}

