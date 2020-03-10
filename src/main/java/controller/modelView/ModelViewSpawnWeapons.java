package controller.modelView;

import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewSpawnWeapons {
    /**
     *Lists weapons in current player's square
     */
    public static void showSpawnWeapons(Scanner in, Player player, PrintWriter out) {
        Square s = player.getSquare();
        String line=in.nextLine();
        if(line.equals("viewSpawnWeapons")) {
            for (int i = 0; i < s.getWeapons().size(); i++)
                out.println(i + ") " + player.getSquare().getWeapons().get(i).getWeaponName());
            out.println("exit");
            out.flush();
        }
    }
}
