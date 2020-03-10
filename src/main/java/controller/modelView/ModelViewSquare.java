package controller.modelView;

import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ModelViewSquare {

    public static void showSpawnSquare (PrintWriter out, Scanner in, Player p) {

        String line = in.nextLine();

        if (line.equals("viewSpawnSquare")) {
            out.println("You have spawned in square: X="+p.getSquare().getX()+", Y="+p.getSquare().getY());
            out.flush();

        }
        out.println("exit");
        out.flush();
}

    public static void showSquare (PrintWriter out, Scanner in, Player p) {

        String line = in.nextLine();
        if (line.equals("viewSquare")) {
            out.println("You are now in: X=" + p.getSquare().getX() + ", Y=" + p.getSquare().getY());
            out.flush();

        }
        out.println("exit");
        out.flush();
    }
}
