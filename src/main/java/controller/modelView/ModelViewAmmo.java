package controller.modelView;

import controller.enumerations.AmmoColors;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewAmmo {
    /**
     * Shows to the current player his ammos.( Red, yellow and blue)
     */
    public static void showPlayerAmmo(Scanner in, PrintWriter out, Player player) {
        String line = in.nextLine();
        if (line.equals("viewAmmo")) {
            for (int i = 0; i < AmmoColors.values().length; i++) {
                out.println(AmmoColors.values()[i].toString() + ": " + player.getAmmoRYB()[i]);
            }
            out.println("exit");
            out.flush();
        }
    }
}
