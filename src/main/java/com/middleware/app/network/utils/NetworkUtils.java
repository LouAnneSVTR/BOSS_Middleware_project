package com.middleware.app.network.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkUtils {

    public static String getLocalHostAddress() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "localhost";
        }
    }
}
