package com.middleware.app.network.rmi;

import com.middleware.app.network.NetworkPlayer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    private final List<NetworkPlayer> players;
    private static final int MAX_PLAYERS = 3;
    private final Object startGameMonitor = new Object();

    public Lobby() throws RemoteException {
        players = new ArrayList<>();
    }

    @Override
    public synchronized int joinLobby(NetworkPlayer newPlayer) throws RemoteException {

        if (players.size() >= MAX_PLAYERS) {
            return -1;
        }

        // To reflect changes on both sides as it will be done after by the calling player
        int newId = players.size() + 1;
        newPlayer.setPlayerId(newId);
        players.add(newPlayer);

        if (players.size() == MAX_PLAYERS) {
            synchronized (startGameMonitor) {
                startGameMonitor.notifyAll(); // Notify all waiting threads when the lobby is full
            }
        }

        return newId;
    }

    @Override
    public synchronized boolean isLobbyFull() throws RemoteException {
        return players.size() == MAX_PLAYERS;
    }

    @Override
    public synchronized List<NetworkPlayer> getPlayers() throws RemoteException {
        return new ArrayList<>(players);
    }

    @Override
    public void waitForGameStart() throws RemoteException, InterruptedException {
        synchronized (startGameMonitor) {
            while (!isLobbyFull()) {
                startGameMonitor.wait();
            }
        }
    }
}

