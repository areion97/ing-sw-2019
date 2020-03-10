package controller.modelView;

import model.decks.powerupDeck.powerups.Powerup;


import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewTwoPowerups {
    public static void showPowerups(Scanner in, PrintWriter out, Powerup p1, Powerup p2) {


        String line = in.nextLine();
        if (line.equals("viewTwoPowerups")) {

            out.println("0) " + p1.getName() + " " + p1.getAmmoColor());
            out.flush();
            out.println("1) " + p2.getName() + " " + p2.getAmmoColor());
            out.flush();

        }
        out.println("exit");
        out.flush();
    }
}
