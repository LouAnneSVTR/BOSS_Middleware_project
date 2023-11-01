import java.util.ArrayList;
import java.util.List;

import entities.Boss;
import entities.Player;

/*TODO Faire une classe magie pour gérer des attaques (attaques de zones pour le boss + resistence/faiblesse au type)
TODO Les dégats de zone doivent se syncroniser entre les threads
*/
public class Main {
    private static Boss boss;

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();

        Player player1 = new Player("Player 1", 100);
        Player player2 = new Player("Player 2", 120);
        players.add(player1);
        players.add(player2);

        boss = new Boss(500, players);

        player1.setBoss(boss);
        player2.setBoss(boss);

        boss.start(); // Start the boss thread

        for (Player player : players) {
            player.start(); // Start player threads
        }
    }

    public static Boss getBoss() {
        return boss;
    }
}
