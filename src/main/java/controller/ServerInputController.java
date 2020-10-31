/**
 * @author Andrea
 */
package controller;

import controller.enumerations.PlayerColors;
import controller.modelView.ModelViewColors;
import controller.modelView.Notify;
import model.Game;
import model.maps.Map;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Checks different types of inputs: if check returns false, the user must insert input again.
 */
public class ServerInputController {


    /**
     * User is asked to insert an integer input: if it's between minInputRange and maxInputRange (extremes included)
     * then the input is valid, otherwise the user must insert the input again.
     *
     * @param minInputRange minimum value of the range
     * @param maxInputRange maximum value of the range
     * @return returns the input inserted
     */

    public static int inputAndCheckInteger(int minInputRange, int maxInputRange, PrintWriter out, Scanner in, Player player, String object) {

        int numericalInput = 0;
        String line;

            line = in.nextLine();
            player.getServerMessage().deserialize(line);

        if (player.getServerMessage().getObject().equals(object)) {

            try {
                numericalInput = Integer.parseInt(player.getServerMessage().getValue());
                if (numericalInput < minInputRange || numericalInput > maxInputRange) {
                    out.println("outOfRange");
                    out.flush();
                } else {
                    out.println("okInput");
                    out.flush();
                    return numericalInput;
                }

            } catch (NumberFormatException e) {
                out.println("reinsert");
                out.flush();
            }
        }
        else {
            out.println("reinsert");
            out.flush();
        }

        return  inputAndCheckInteger(minInputRange,maxInputRange,out,in,player,object);
    }


    public static String inputAndCheckUsername(PrintWriter out, Scanner in, Player player, String object) {
        boolean reinsert = false;
        String line;
        String username = "";
        do {
            line = in.nextLine();
            player.getServerMessage().deserialize(line);
            if (player.getServerMessage().getObject().equals(object)) {
                username = player.getServerMessage().getValue();

                for (int i = 0; i < Game.getPlayerArrayList().size(); i++) {
                    if (Game.getPlayerArrayList().get(i).getUsername().equals(username)) {
                        reinsert = true;
                        out.println("reinsertUsername");
                        out.flush();
                        i = Game.getPlayerArrayList().size();
                    }
                    else {
                        if (i == Game.getPlayerArrayList().size() - 1) {
                            player.setUsername(username);
                            out.println("okInput");
                            out.flush();
                            reinsert = false;
                        }
                    }
                }
            } else
                reinsert = true;

        } while (reinsert);

        return username;

    }

    /**
     * User is asked to insert x and y coordinates of the square in which he'd want to move,
     * if x,y coordinates do not match any square, then the user is asked to reinsert coordinates
     */

    public static Player moveShootedPlayerValidate(PrintWriter out, Scanner in, Player shootingPlayer,Player shootedPlayer,int fromStep,int toStep, String object) {
        String line;

        line = in.nextLine();

        if (shootingPlayer.getServerMessage().deserializeMoveAction(line)) {

            if (shootingPlayer.getServerMessage().getObject().equals(object)) {

                Square toSquare = shootingPlayer.getServerMessage().getToSquare();

                if (fromStep == toStep) {
                    if (DistanceManager.reachableInSteps(shootedPlayer.getSquare(), toSquare, toStep)) {

                        shootedPlayer.setSquare(toSquare);
                        out.println("okMove");
                        out.flush();
                        Notify.notifyAll(shootingPlayer,"NotifyMovedBy",shootedPlayer);
                        return shootedPlayer;
                    }
                } else {

                    if(!DistanceManager.canSee2(shootingPlayer.getSquare(),toSquare)) {
                        out.println("cannotSeeToSquare");
                        out.flush();
                        return moveShootedPlayerValidate(out, in, shootingPlayer,shootedPlayer, fromStep,toStep, object);

                    }
                    else if (DistanceManager.reachableInLessSteps(shootedPlayer.getSquare(),toSquare,fromStep,toStep )) {

                        shootedPlayer.setSquare(toSquare);
                        out.println("okMove");
                        out.flush();
                        Notify.notifyAll(shootingPlayer,"NotifyMovedBy",shootedPlayer);
                        return shootedPlayer;
                    }
                }

                out.println("tooFar");
                out.flush();

            }
            else {
                out.println("reinsert");
                out.flush();
            }
        } else {
            out.println("reinsert");
            out.flush();
        }
        return moveShootedPlayerValidate(out, in, shootingPlayer,shootedPlayer, fromStep,toStep, object);
    }



    public static int inputAndCheckColor(int minInputRange, int maxInputRange, PrintWriter out, Scanner in, Player player, String object) {
        String line;
        line = in.nextLine();
        player.getServerMessage().deserialize(line);
        if (player.getServerMessage().getObject().equals(object)) {

            try {
                int colorIndex = Integer.parseInt(player.getServerMessage().getValue());

                if (colorIndex < minInputRange || colorIndex >=maxInputRange) {
                    out.println("outOfRange");
                    out.flush();
                }
                else if (Game.getColorsAvailable().get(colorIndex).equals(PlayerColors.X)) {
                    out.println("colorAlreadyTaken");
                    out.flush();
                    ModelViewColors.showColors(in, out);

                }
                else {
                    player.setColor(Game.getColorsAvailable().get(colorIndex));
                    Game.getColorsAvailable().set(colorIndex,PlayerColors.X);
                    out.println("okInput");
                    out.flush();
                    return colorIndex;
                }

            } catch (NumberFormatException e) {
                out.println("reinsert");
                out.flush();
            }
        }
        else {
            out.println("reinsert");
            out.flush();
        }
        return  inputAndCheckColor(minInputRange,maxInputRange,out,in,player,object);
    }


    public static Square moveInputValidate(PrintWriter out, Scanner in, Player shootingPlayer,String object) {
        String line;

        line = in.nextLine();
        Map m = Game.getMap();
        if (shootingPlayer.getServerMessage().deserializeMoveAction(line)) {

            if (shootingPlayer.getServerMessage().getObject().equals(object)) {
                Square toSquare = shootingPlayer.getServerMessage().getToSquare();

                return toSquare;

            } else {
                out.println("reinsert");
                out.flush();
            }

        } else {
            out.println("reinsert");
            out.flush();
        }
        return moveInputValidate(out, in, shootingPlayer,object);
    }
}