package controller.modelView;

import controller.enumerations.AmmoColors;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewMap {
    /**
     * Shows to the current player map
     */
    public static void showMap(Scanner in, PrintWriter out) {
        String line = in.nextLine();
        if (line.equals("viewMap")) {

            for(int i=0;i<3;i++) {

                for (int j = 0; j < 4; j++) {

                    Square s = Game.getMap().getSquare(i,j);
                    if (s != null && s.getAmmoTile() != null) {
                        out.print(s.getX() + " " + s.getY() + "     " + s.getAmmoTile().getAmmoRYB()[0]);
                        out.print(s.getAmmoTile().getAmmoRYB()[1]);
                        out.print(s.getAmmoTile().getAmmoRYB()[2]);
                        out.println(" " +s.getRoomColor());
                    } else if (s != null && s.getPlayerSpawn() == true) {
                        out.print(s.getX() + " " + s.getY()+"    ");

                        for (Weapon w:s.getWeapons()) {
                            out.print(w.getWeaponName() + " ");
                        }
                        out.println();


                    }
                }
            }

            out.println("exit");
            out.flush();

        }

    }
}
