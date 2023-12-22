package com.middleware.app.network;

import com.middleware.app.Core;
import com.middleware.app.network.rmi.Lobby;
import com.middleware.app.network.rmi.LobbyInterface;
import com.middleware.app.network.utils.NetworkUtils;
import java.io.Serializable;
import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkPlayer implements Serializable {

    public static final int LOBBY_PORT = 5006;
    private final String pseudo;
    private final String ipAddress;
    private final int udpPort;

    // Add a serialVersionUID (recommended for Serializable classes)
    private static final long serialVersionUID = 1L;

    public NetworkPlayer(String pseudo, int udpPort) throws SocketException {
        this.pseudo = pseudo;
        this.udpPort = udpPort;
        this.ipAddress = NetworkUtils.getLocalHostAddress();
    }

    // Getters and Setters
    public String getPseudo() { return pseudo; }
    public String getIpAddress() { return ipAddress; }
    public int getUdpPort() { return udpPort; }

    public Core hostGame() throws RuntimeException {

        try {
            Lobby lobby = new Lobby(this);
            Registry registry = LocateRegistry.createRegistry(LOBBY_PORT);
            registry.rebind("Lobby", lobby);

            System.out.println("Waiting for players ...");

            // Lobby is ready
            lobby.waitForGameStart();
            return new Core(lobby.getPlayers(), this);

        } catch (Exception e) {
            System.err.println("Host exception: " + e);
            throw new RuntimeException("Failed to Host Lobby");
        }
    }

    public Core joinGame(String host) throws RuntimeException {
        try {

            Registry registry = LocateRegistry.getRegistry(host, LOBBY_PORT);
            LobbyInterface lobby = (LobbyInterface) registry.lookup("Lobby");

            boolean response = lobby.joinLobby(this);

            if(!response) {
                throw new RuntimeException("Failed to join lobby ...");
            }

            System.out.println("Lobby Joined ! Waiting for the game to start...");
            lobby.waitForGameStart();

            return new Core(lobby.getPlayers(), this);

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            throw new RuntimeException("Failed to Join Lobby ...");
        }
    }
}

