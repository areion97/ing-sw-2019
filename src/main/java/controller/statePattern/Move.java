package controller.statePattern;

import controller.DistanceManager;
import controller.ServerInputController;
import controller.modelView.Notify;
import model.Game;
import model.maps.Map;
import model.maps.Square;
import model.players.Player;
import controller.statePattern.PlayerState;

import java.io.PrintWriter;
import java.util.Scanner;


public class Move implements PlayerState {
    private int numberOfAction;
    private static Square previousSquare=null;

    public Move(int numberOfAction) {
        this.numberOfAction=numberOfAction;
    }

    @Override
    public void nextAction(Scanner in, PrintWriter out, Player player) {
        Square toSquare =  ServerInputController.moveInputValidate(out, in, player, "coordinates");
        if(numberOfAction==0) {
            if (player.shotNtimes(3)) {

                if (DistanceManager.reachableInLessSteps(player.getSquare(), toSquare, 0,2)) {
                    previousSquare=player.getSquare();
                    player.setSquare(toSquare);
                    out.println("okMove");
                    out.flush();
                    Notify.notifyAll(player,"NotifyMovedBeforeGrabbing",null);
                    return;
                }

            } else {
                if (DistanceManager.reachableInLessSteps(player.getSquare(), toSquare, 0,1)) {
                    player.setSquare(toSquare);
                    Notify.notifyAll(player,"NotifyMovedBeforeGrabbing",null);
                    out.println("okMove");
                    out.flush();
                    return;
                }

                out.println("tooFar");
                out.flush();
                nextAction(in,out,player);
                return;

            }

        }

        else if(numberOfAction==1) {
            if (DistanceManager.reachableInLessSteps(player.getSquare(), toSquare, 0,3)) {
                player.setSquare(toSquare);
                out.println("okMove");
                out.flush();
                Notify.notifyAll(player,"NotifyMoved",null);
                return;
            }

        }

        else if(numberOfAction==2) {

            if (DistanceManager.reachableInLessSteps(player.getSquare(), toSquare, 0,1)) {
                previousSquare=player.getSquare();
                player.setSquare(toSquare);
                Notify.notifyAll(player,"NotifyMovedBeforeShooting",null);
                out.println("okMove");
                out.flush();
                return;
            }


        }
        out.println("tooFar");
        out.flush();
        nextAction(in,out,player);

    }

    public static void setPreviousSquare(Player shootingPlayer) {
        if(previousSquare!=null) {
            shootingPlayer.setSquare(previousSquare);
            previousSquare=null;
        }

    }
}