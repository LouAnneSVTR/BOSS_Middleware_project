package com.middleware.app;

import com.middleware.app.networking.GameServer;
import java.io.IOException;

/*TODO Faire une classe magie pour gérer des attaques (attaques de zones pour le boss + resistence/faiblesse au type)
TODO Les dégats de zone doivent se syncroniser entre les threads
*/
public class Main {

    public static void main(String[] args) {

        try {
            GameServer server = new GameServer(11111);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
