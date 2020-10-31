package controller.actionController;

import controller.DistanceManager;
import model.players.Player;
import java.util.ArrayList;


public class ShootController {


    public static boolean canSeeSomeone(Player shootingPlayer, boolean canSeeModality, boolean canSeeNotInSquare, ArrayList<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            if (!p.equals(shootingPlayer)) {
                if (canSeeModality) {

                    if (DistanceManager.canSee1(shootingPlayer,p)) {

                        if (canSeeNotInSquare) {

                            if (!p.getSquare().equals(shootingPlayer.getSquare())) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }
                } else {
                    if (!DistanceManager.canSee1(shootingPlayer,p)) {
                        System.out.println("i " + i);
                        if (i == players.size())
                            return false;
                        else
                            return true;
                    }
                }
            }
        }
        return false;
    }

}