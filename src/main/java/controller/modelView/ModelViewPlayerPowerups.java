package controller.modelView;

import model.decks.powerupDeck.powerups.Powerup;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewPlayerPowerups {
    /**
     * Shows to the current player his powerups.
     */
    public static void showPlayerPowerups(Scanner in, PrintWriter out, Player player) {
        String line = in.nextLine();

        if (line.equals("viewPowerups")) {
            for (int i = 0; i < player.getPowerups().size(); i++) {
                Powerup p = player.getPowerups().get(i);
                out.println(i + ") " + p.getName() + " " + p.getAmmoColor());
                out.flush();
            }

            out.println("exit");
            out.flush();
        }
    }
}
