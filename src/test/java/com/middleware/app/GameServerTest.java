package com.middleware.app;

import com.middleware.app.networking.UDPGameWrapper;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class GameServerTest {

    public static void main(String[] args) throws SocketException {
        int serverPort = 12345;
        try {
            UDPGameWrapper udpWrapper = new UDPGameWrapper(serverPort, 1024);

            InetAddress serverAddress = InetAddress.getLocalHost();
            //InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
            String message = "Hello, Server!";
            udpWrapper.sendObject(serverAddress, serverPort, message);


            byte[] responseData = udpWrapper.receivePacket();
            System.out.println("Réponse du serveur : " + new String(responseData));

            //Serializable receivedObject = udpWrapper.receiveObject();
            //System.out.println("Received from server: " + receivedObject);
            udpWrapper.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




