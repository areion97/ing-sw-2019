package controller.powerupController;


import client.messageManager.ClientInputController;
import controller.DistanceManager;
import controller.ServerInputController;
import controller.modelView.ModelViewPlayersAlive;
import controller.modelView.Notify;
import controller.strategyPattern.concreteFireModes.ShootMoveToCanSeeSquare;
import model.Game;
import model.decks.powerupDeck.powerups.Powerup;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PowerupController {

    public static void checkAndUseTeleporter(PrintWriter out, Scanner in, Player player) {
        for (int i = 0; i < player.getPowerups().size(); i++) {
            Powerup p = player.getPowerups().get(i);
            if (p.getName().equals("Teleporter")) {
                out.println("youHaveTeleporter");
                out.flush();
                int useTeleporter = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "useTeleporter?");
                if (useTeleporter == 1) {
                    Square s = ServerInputController.moveInputValidate(out, in, player, "moveTeleporter");
                    player.setSquare(s);
                    out.println("okMove");
                    out.flush();
                    Notify.notifyAll(player, "NotifyMovePowerup", null);
                    Game.getPowerupDeck().discardPowerup(p);
                    player.getPowerups().remove(i);
                    return;
                }
                else
                    return;
            }
        }

        out.println("noTeleporter");
        out.flush();
    }


    public static void useTargetingScope(PrintWriter out, Scanner in, Player player) {

    }

    public static void checkAndUseNewton(PrintWriter out, Scanner in, Player player) {

        for (int i = 0; i < player.getPowerups().size(); i++) {
            Powerup p = player.getPowerups().get(i);
            if (p.getName().equals("Newton")) {
                out.println("youHaveNewton");
                out.flush();
                int useNewton = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "useNewton?");
                if (useNewton == 1) {
                    ModelViewPlayersAlive.showAlivePlayers(out, in, player);
                    Player playerToMove = inputAndCheckPlayer(out,in,player);

                    do {
                        Square toSquare = ServerInputController.moveInputValidate(out, in, player, "moveNewton");

                        if ((DistanceManager.reachableInSteps(playerToMove.getSquare(), toSquare, 1)||DistanceManager.reachableInSteps(playerToMove.getSquare(), toSquare, 2))&&!playerToMove.getSquare().equals(toSquare)) {
                            if ((playerToMove.getSquare().getX() == toSquare.getX()) || playerToMove.getSquare().getY() == toSquare.getY()) {
                                playerToMove.setSquare(toSquare);
                                out.println("okMove");
                                out.flush();
                                Notify.notifyAll(player, "NotifyMovedBy", playerToMove);
                                Game.getPowerupDeck().discardPowerup(p);
                                player.getPowerups().remove(i);
                                return;
                            }
                        }

                        out.println("tooFar");
                        out.flush();

                    } while (true);
                }
                else
                    return;
            }
        }
        out.println("noNewton");
        out.flush();
    }

    public static void useTagbackGrenade() {

    }

    private static Player inputAndCheckPlayer(PrintWriter out, Scanner in, Player shootingPlayer) {

        int shootedPlayerId = 0;
        Player shootedPlayer = null;
        String line;
        ArrayList<Player> playersAlive = Game.getPlayersAlive();

        line = in.nextLine();

        shootingPlayer.getServerMessage().deserialize(line);

        if (shootingPlayer.getServerMessage().getObject().equals("playerToMove")) {
            try {
                shootedPlayerId = Integer.parseInt(shootingPlayer.getServerMessage().getValue());
                if (shootedPlayerId < Game.getPlayerArrayList().size() && shootedPlayerId >= 0 && shootedPlayerId != shootingPlayer.getPlayerID()) {
                    shootedPlayer = Game.getPlayerArrayList().get(shootedPlayerId);

                    if (!playersAlive.contains(shootedPlayer)) {
                        out.println("playerDeadOrShot");
                        out.flush();
                    }
                    else {
                        out.println("okInput");
                        out.flush();
                        return shootedPlayer;
                    }
                }
                else {
                    out.println("reinsert");
                    out.flush();
                }
            } catch (NumberFormatException e) {
                out.println("reinsert");
                out.flush();
            }

        } else {
            out.println("reinsert");
            out.flush();
        }

        return inputAndCheckPlayer(out, in,  shootingPlayer);
    }
}



