package controller.modelView;

import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewPlayerWeapons {
    /**
     * Shows to the current player his weapons.
     */
    public static void showPlayerWeapons(Player player, PrintWriter out, Scanner in) {
        String line=in.nextLine();
        if(line.equals("viewPlayerWeapons")) {
            for (int i = 0; i < player.getWeapons().size(); i++) {
                out.println(i + ") " + player.getWeapons().get(i).getWeaponName());

            }

            out.println("exit");
            out.flush();

        }


    }
}
