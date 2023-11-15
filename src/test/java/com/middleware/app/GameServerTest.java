package com.middleware.app;

import com.middleware.app.networking.GameServer;
import com.middleware.app.networking.UDPGameWrapper;
import java.io.IOException;
import java.net.InetAddress;

public class GameServerTest {

    public static void main(String[] args) {
        int serverPort1 = 12346;
        int serverPort2 = 12345;
        int bufferSize = 1024;

        try {
            InetAddress serverAddress = InetAddress.getLocalHost();
            String message1 = "Hello, from Server 1!";
            String message2 = "Wesh from Server 2!";

            UDPGameWrapper udpWrapper1 = new UDPGameWrapper(serverPort1, bufferSize);
            UDPGameWrapper udpWrapper2 = new UDPGameWrapper(serverPort2, bufferSize);

            udpWrapper1.sendObject(serverAddress, serverPort2, message1);
            udpWrapper2.sendObject(serverAddress, serverPort1, message2);

            byte[] responseData = udpWrapper1.receivePacket();
            System.out.println("1 Receive packet from 2 : " + new String(responseData));

            byte[] responseData2 = udpWrapper2.receivePacket();
            System.out.println("2 Receive packet from 1 : " + new String(responseData2));

            udpWrapper1.close();
            udpWrapper2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}