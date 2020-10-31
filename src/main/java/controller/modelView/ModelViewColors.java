package controller.modelView;

import controller.enumerations.PlayerColors;
import model.Game;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ModelViewColors {
    /**
     * Shows the user the list of enumerations that haven't been taken yet.
     */
    public static void showColors(Scanner in, PrintWriter out) {
        String line=in.nextLine();
        int number=0;
        if(line.equals("viewColor")) {
            ArrayList<PlayerColors> colors = Game.getColorsAvailable();
            for (int i = 0; i < colors.size(); i++) {
                    out.println(i + ") " + colors.get(i));
                    out.flush();
                }
            }
            out.println("exit");
            out.flush();
        }
}
