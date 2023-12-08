package com.middleware.app;

import com.middleware.app.network.NetworkPlayer;
import java.util.Scanner;

public class Main {

    private static final int UDP_PORT = 6006;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();

        System.out.println("Do you want to (1) Host a game or (2) Join a game?");
        int choice = scanner.nextInt();

        NetworkPlayer player = new NetworkPlayer(playerName, UDP_PORT);

        try {

            if (choice == 1) {
                player.hostGame();
            } else if (choice == 2) {
                System.out.print("Enter the host's IP address: ");
                String host = scanner.nextLine();
                player.joinGame(host);
            } else {
                System.out.println("Invalid choice. Exiting.");
            }

        } catch (Exception e) {
            System.err.println("Game Failed: " + e);
        }

    }
}

