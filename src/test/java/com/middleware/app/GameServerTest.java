package com.middleware.app;

import com.middleware.app.networking.GameServer;
import com.middleware.app.networking.UDPGameWrapper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class GameServerTest {

    public static void main(String[] args) throws SocketException {
        int serverPort = 12345;
        int bufferSize = 1024;
        try {
            UDPGameWrapper udpWrapper = new UDPGameWrapper(serverPort, bufferSize);

            InetAddress serverAddress = InetAddress.getLocalHost();
            String message = "Hello, Server!";
            udpWrapper.sendObject(serverAddress, serverPort, message);

            byte[] responseData = udpWrapper.receivePacket();
            System.out.println("Réponse du serveur : " + new String(responseData));

            udpWrapper.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            GameServer gameServer = new GameServer(serverPort, bufferSize);
            gameServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




