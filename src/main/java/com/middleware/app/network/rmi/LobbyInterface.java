package com.middleware.app.network.rmi;

import com.middleware.app.network.NetworkPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LobbyInterface extends Remote {
    int joinLobby(NetworkPlayer newPlayer) throws RemoteException;
    void waitForGameStart() throws RemoteException, InterruptedException;
    boolean isLobbyFull() throws RemoteException;
    List<NetworkPlayer> getPlayers() throws RemoteException;
}

