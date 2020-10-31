

/**
 * @author Andrea
 */
package controller.actionController;
import controller.ServerInputController;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Manages move subaction
 */

public class MoveController {
    public static void moveShootedPlayer(PrintWriter out,Scanner in,Player shootingPlayer,Player shootedPlayer,String move, int distance) {

            int moveTarget = ServerInputController.inputAndCheckInteger(0, 1, out, in, shootingPlayer, "moveTarget?");

            if (moveTarget == 0)
                return;

        if (!move.equals("")) {
            ServerInputController.moveShootedPlayerValidate(out, in, shootingPlayer,shootedPlayer, distance,distance, "coordinates");

        }
    }
}

