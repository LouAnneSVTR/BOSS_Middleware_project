package com.middleware.app.network;

import com.middleware.app.network.rmi.Lobby;
import com.middleware.app.network.rmi.LobbyInterface;
import com.middleware.app.network.utils.NetworkUtils;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkPlayer {

    private static final int LOBBY_PORT = 5006;
    private final String pseudo;
    private final String ipAddress;
    private final int udpPort;

    public NetworkPlayer(String pseudo, int udpPort) {
        this.pseudo = pseudo;
        this.udpPort = udpPort;
        this.ipAddress = NetworkUtils.getLocalHostAddress();
    }

    // Getters and Setters
    public String getPseudo() { return pseudo; }
    public String getIpAddress() { return ipAddress; }
    public int getUdpPort() { return udpPort; }

    public void hostGame() {

        try {
            Lobby lobby = new Lobby(this);
            Registry registry = LocateRegistry.createRegistry(LOBBY_PORT);
            registry.rebind("Lobby", lobby);

            // Lobby is ready

        } catch (Exception e) {
            System.err.println("Host exception: " + e.toString());
        }
    }

    public void joinGame(String host) throws NotBoundException, RemoteException, InterruptedException {
        try {

            Registry registry = LocateRegistry.getRegistry(host, LOBBY_PORT);
            LobbyInterface lobby = (LobbyInterface) registry.lookup("Lobby");

            boolean response = lobby.joinLobby(this);

            if(!response) {
                System.err.println("Failed to join lobby ...");
                throw new RuntimeException("Failed to join lobby ...");
            }

            System.out.println("Waiting for the game to start...");
            lobby.waitForGameStart();

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            throw e;
        }
    }
}

