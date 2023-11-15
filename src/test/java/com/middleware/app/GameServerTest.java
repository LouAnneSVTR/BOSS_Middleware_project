package com.middleware.app;

import com.middleware.app.networking.GameServer;
import com.middleware.app.networking.UDPGameWrapper;
import java.io.IOException;
import java.net.InetAddress;

public class GameServerTest {

    public static void main(String[] args) {
        int serverPort1 = 12346;
        int serverPort2 = 12345;
        int serverPort = 11111;
        int bufferSize = 1024;

      /*  try {

            //Double teste de serveur
            InetAddress serverAddress = InetAddress.getLocalHost();
            String message1 = "Hello, from Server 1!";
            String message2 = "Wesh from Server 2!";

            UDPGameWrapper udpWrapper1 = new UDPGameWrapper(serverPort1, bufferSize);
            UDPGameWrapper udpWrapper2 = new UDPGameWrapper(serverPort2, bufferSize);

            udpWrapper1.sendObject(serverAddress, serverPort2, message1);
            udpWrapper2.sendObject(serverAddress, serverPort1, message2);

            String responseData = (String) udpWrapper1.receiveObject();
            System.out.println("1 Receive packet from 2 : " + responseData);

            String responseData2 = (String) udpWrapper2.receiveObject();
            System.out.println("2 Receive packet from 1 : " + responseData2);

            udpWrapper1.close();
            udpWrapper2.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/


        //test GameServer
        try {
            GameServer gameServer = new GameServer(serverPort, bufferSize, "MyGameServer");
            gameServer.start();
            Thread.sleep(30000);

            UDPGameWrapper udpWrapper = new UDPGameWrapper(0, bufferSize);
            InetAddress serverAddress = InetAddress.getLocalHost();
            String connectionMessage = "Hello, Server! I'm a player.";

            udpWrapper.sendObject(serverAddress, serverPort, connectionMessage);
            String response = (String) udpWrapper.receiveObject();
            System.out.println("Server response: " + response);

            udpWrapper.close();
            gameServer.stop();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}