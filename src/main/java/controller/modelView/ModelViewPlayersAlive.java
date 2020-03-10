package controller.modelView;

import model.Game;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewPlayersAlive {

    public static void showAlivePlayers(PrintWriter out,Scanner in,Player shootingPlayer) {
        String line = in.nextLine();
        if (line.equals("viewPlayersAlive")) {
            for (Player player : Game.getPlayersAlive()) {
                if (!player.equals(shootingPlayer)) {
                    out.println(player.getPlayerID() + ") " + player.getColor());
                    out.flush();
                }
            }
            out.println("exit");
            out.flush();
        }
    }
}
