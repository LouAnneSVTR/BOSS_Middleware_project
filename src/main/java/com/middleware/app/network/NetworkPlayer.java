package com.middleware.app.network;

import com.middleware.app.Core;
import com.middleware.app.network.rmi.Lobby;
import com.middleware.app.network.rmi.LobbyInterface;
import com.middleware.app.network.udp.ServerUDP;
import com.middleware.app.network.utils.NetworkUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NetworkPlayer {

    public static final int LOBBY_PORT = 5006;
    private final String pseudo;
    private final String ipAddress;
    private final int udpPort;

    private final ServerUDP udpServer;

    public NetworkPlayer(String pseudo, int udpPort) throws SocketException {
        this.pseudo = pseudo;
        this.udpPort = udpPort;
        this.ipAddress = NetworkUtils.getLocalHostAddress();
        this.udpServer = new ServerUDP(udpPort, 1024);
    }

    // Getters and Setters
    public String getPseudo() { return pseudo; }
    public String getIpAddress() { return ipAddress; }
    public int getUdpPort() { return udpPort; }

    public Core hostGame() throws RemoteException, InterruptedException {

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
            throw e;
        }
    }

    public Core joinGame(String host) throws NotBoundException, RemoteException, InterruptedException {
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

            return new Core(lobby.getPlayers(), this);

        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            throw e;
        }
    }

    public Serializable rcvUdpObj() {

        try {
            return udpServer.receiveObject();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Failed to receive Object: " + e);
        }

        return null;
    }

    public boolean sendUdpObj(String destAddr, int destPort, Serializable data) {

        try {

            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            udpServer.sendObject(inetAddress, destPort, data);
            return true;

        } catch (IOException e)  {
            System.err.println("Failed to send Object: " + e);
        }

        return false;
    }





}

