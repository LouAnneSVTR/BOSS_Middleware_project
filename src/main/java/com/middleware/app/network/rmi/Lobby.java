package com.middleware.app.network.rmi;

import com.middleware.app.network.NetworkPlayer;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lobby extends UnicastRemoteObject implements LobbyInterface {
    private final List<NetworkPlayer> players;
    private final Set<String> pseudos;
    private static final int MAX_PLAYERS = 3;
    private final Object startGameMonitor = new Object();

    public Lobby(NetworkPlayer hostPlayer) throws RemoteException {
        players = new ArrayList<>();
        players.add(hostPlayer);

        pseudos = new HashSet<>();
        pseudos.add(hostPlayer.getPseudo());
    }

    @Override
    public synchronized boolean joinLobby(NetworkPlayer newPlayer) throws RemoteException {

        if (players.size() >= MAX_PLAYERS || !pseudos.add(newPlayer.getPseudo())) {
            return false;
        }

        players.add(newPlayer);

        if (players.size() == MAX_PLAYERS) {
            synchronized (startGameMonitor) {
                startGameMonitor.notifyAll(); // Notify all waiting threads when the lobby is full
            }
        }

        return true;
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
