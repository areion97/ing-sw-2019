package controller.statePattern;

import controller.ServerInputController;
import controller.modelView.ModelViewSquare;
import controller.modelView.Notify;
import model.Game;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class Playing implements PlayerState {

    @Override
    public void nextAction(Scanner in, PrintWriter out, Player player) {

        int actionInput;
        int moveInput;
        ModelViewSquare.showSquare(out,in,player);



        actionInput = ServerInputController.inputAndCheckInteger(0, 2, out, in, player, "action");

        if (actionInput == 0) {
            moveInput = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "move?");
            if (moveInput == 1) {
                player.setState(new Move(actionInput));
                player.nextAction(in,out,player);
            }

            if (!player.getSquare().getPlayerSpawn()) {
                out.println("grabAmmo");
                out.flush();
                if (player.getSquare().getAmmoTile() == null) {
                    out.println("ammoAlreadyTaken");
                    out.flush();
                    player.setState(new Playing());
                    player.nextAction(in,out,player);
                    return;
                } else {

                    out.println("ammoGrabbed");
                    out.flush();
                }
            } else {
                out.println("grabWeapon");
                out.flush();
            }

            player.setState(new Grab());
            player.nextAction(in,out,player);


        } else if (actionInput == 1) {

            player.setState(new Move(actionInput));
            player.nextAction(in, out, player);

        } else {

            if (player.getWeapons().isEmpty()) {
                out.println("noWeapons");
                out.flush();
                nextAction(in, out, player);
                return;
            }

            else if(Game.getPlayersAlive().isEmpty()) {
                out.println("allPlayersDead");
                out.flush();
                nextAction(in, out, player);
                return;
            }

            out.println("canShoot");
            out.flush();

            moveInput = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "move?");

             if (moveInput == 1) {


                 if (player.shotNtimes(6)) {
                     out.println("shooted6Times");
                     out.flush();
                     player.setState(new Move(2));
                     player.nextAction(in, out, player);

                 } else {

                     out.println("notShot6Times");
                     out.flush();
                     player.setState(new Playing());
                     player.nextAction(in, out, player);
                     return;
                 }


             }
             player.setState(new Shoot());
             player.nextAction(in, out, player);
        }
    }
}