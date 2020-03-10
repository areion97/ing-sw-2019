package controller.strategyPattern.concreteFireModes;

import controller.GiveDmgOrMarks;
import controller.ServerInputController;
import controller.modelView.ModelViewPlayersAlive;
import controller.modelView.Notify;
import controller.statePattern.Move;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShootMoveToCanSeeSquare {

    public static void checkAndShoot(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, int damage, int marks) {


        ModelViewPlayersAlive.showAlivePlayers(out, in, shootingPlayer);
        Player shootedPlayer = inputAndCheckPlayer(out, in, shootingPlayer);


        ServerInputController.moveShootedPlayerValidate(out, in, shootingPlayer, shootedPlayer, 0, 2, "moveTractorBeam");

        GiveDmgOrMarks.addDmg(shootedPlayer, shootingPlayer, damage);
        GiveDmgOrMarks.addMark(shootedPlayer, shootingPlayer, marks);
        weapon.setLoaded(false);
        Notify.notifyShoot(shootingPlayer, "NotifyShoot", shootedPlayer, weapon, damage, marks);

        for (Player p : Game.getPlayerArrayList()) {
            System.out.println(p.getPlayerID() + " damage " + p.getPlayerBoard().getDamageTaken());
            System.out.println(p.getPlayerID() + " marks " + p.getPlayerBoard().getMarks());
        }

    }


    public static Player inputAndCheckPlayer(PrintWriter out, Scanner in, Player shootingPlayer) {

        int shootedPlayerId = 0;
        Player shootedPlayer = null;
        String line;
        ArrayList<Player> playersAlive = Game.getPlayersAlive();

        line = in.nextLine();


        shootingPlayer.getServerMessage().deserialize(line);

        if (shootingPlayer.getServerMessage().getObject().equals("shootedPlayer")) {
            try {
                shootedPlayerId = Integer.parseInt(shootingPlayer.getServerMessage().getValue());
                if (shootedPlayerId < Game.getPlayerArrayList().size() && shootedPlayerId >= 0 && shootedPlayerId != shootingPlayer.getPlayerID()) {
                    shootedPlayer = Game.getPlayerArrayList().get(shootedPlayerId);

                    System.out.println(!playersAlive.contains(shootedPlayer));

                    if (!playersAlive.contains(shootedPlayer)) {
                        System.out.println("player dead");
                        out.println("playerDeadOrShot");
                        out.flush();
                    }
                    else {
                        System.out.println("ok input");
                        out.println("okInput");
                        out.flush();
                        return shootedPlayer;
                    }
                }
                else {
                    out.println("reinsert");
                    out.flush();
                }
            }catch (NumberFormatException e) {
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