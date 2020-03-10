package controller.strategyPattern.concreteFireModes;

import controller.DistanceManager;
import controller.GiveDmgOrMarks;
import controller.ServerInputController;
import controller.strategyPattern.fireModeInterfaces.ShootVisibleOrNotInterface;
import controller.actionController.MoveController;
import controller.actionController.ShootController;
import controller.modelView.ModelViewPlayersAlive;
import controller.modelView.Notify;
import controller.statePattern.Move;
import controller.statePattern.Playing;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShootVisibleOrNot implements ShootVisibleOrNotInterface {

    @Override
    public Player shoot(PrintWriter out, Scanner in, Weapon weapon, boolean canSeeModality, Player shootingPlayer, int numberOfTargets, int damage, int marks, String move, int distance) {

        int shootAnotherPlayer = 0;
        Player shootedPlayer = null;

        if ((weapon.getWeaponName().equals("RocketLauncher"))) {
            if (!ShootController.canSeeSomeone(shootingPlayer, true, true, Game.getPlayersAlive())) {
                out.println("playerInSquare");
                out.flush();
                Move.setPreviousSquare(shootingPlayer);
                shootingPlayer.setState(new Playing());
                shootingPlayer.nextAction(in, out, shootingPlayer);
                return shootedPlayer;
            }

        } else if (!ShootController.canSeeSomeone(shootingPlayer, canSeeModality, false, Game.getPlayersAlive())) {

            if (canSeeModality) {
                out.println("cannotSeeAnyone");
                out.flush();
                Move.setPreviousSquare(shootingPlayer);
                shootingPlayer.setState(new Playing());
                shootingPlayer.nextAction(in, out, shootingPlayer);
                return shootedPlayer;
            } else {
                out.println("canSeeEveryone");
                out.flush();
                Move.setPreviousSquare(shootingPlayer);
                shootingPlayer.setState(new Playing());
                shootingPlayer.nextAction(in, out, shootingPlayer);
                return shootedPlayer;
            }
        }

        out.println("canUseWeapon");
        out.flush();

        ArrayList<Player> remainingPlayers = new ArrayList<>();
        remainingPlayers.addAll(Game.getPlayersAlive());

        for (int i = 0; i < numberOfTargets; i++) {
            System.out.println(numberOfTargets);

            ModelViewPlayersAlive.showAlivePlayers(out, in, shootingPlayer);

            shootedPlayer = inputAndCheckPlayerYouCanSee(out, in, weapon, canSeeModality, shootingPlayer, remainingPlayers);

            GiveDmgOrMarks.addDmg(shootedPlayer,shootingPlayer, damage);
            GiveDmgOrMarks.addMark(shootedPlayer,shootingPlayer, marks);
            weapon.setLoaded(false);

            Notify.notifyShoot(shootingPlayer,"NotifyShoot",shootedPlayer,weapon,damage,marks);


            remainingPlayers.remove(shootedPlayer);



            for (Player p : Game.getPlayerArrayList()
            ) {
                System.out.println(p.getPlayerID() + " damage " + p.getPlayerBoard().getDamageTaken());
                System.out.println(p.getPlayerID() + " marks " + p.getPlayerBoard().getMarks());
            }


            if (numberOfTargets > 1 && i != numberOfTargets - 1) {

                System.out.println("Giocatori in gioco: " + Game.getPlayersAlive());
                System.out.println("Questi sono i giocatori che non sono stati ancora sparati " + remainingPlayers);

                if (!ShootController.canSeeSomeone(shootingPlayer, true, false, remainingPlayers)) {

                    out.println("noPlayersRemainedToShoot");
                    out.flush();
                    return null;
                } else {
                    out.println("keepOnShooting");
                    out.flush();
                }
            }

            if (numberOfTargets > 1 && i != numberOfTargets - 1) {
                shootAnotherPlayer = ServerInputController.inputAndCheckInteger(0, 1, out, in, shootingPlayer, "shootAgain");
                if (shootAnotherPlayer == 0) {
                    return shootedPlayer;


                }
            }

            if (i == numberOfTargets - 1) {
                if (move.equals("AskAndMoveTarget")) {
                    MoveController.moveShootedPlayer(out, in, shootingPlayer, shootedPlayer, move, distance);
                    return shootedPlayer;
                }
            }

        }

        return null;
    }







    /**
     * Checks if player can be shooted.
     * 1st check: player has to be alive.
     * 2nd check: player has to be visible.
     **/

    public Player inputAndCheckPlayerYouCanSee(PrintWriter out, Scanner in, Weapon weapon, boolean canSeeModality,Player shootingPlayer,ArrayList<Player> playersRemained) {
        int shootedPlayerId = 0;
        Player shootedPlayer = null;
        String line;

        line = in.nextLine();
        shootingPlayer.getServerMessage().deserialize(line);

        if (shootingPlayer.getServerMessage().getObject().equals("shootedPlayer")) {
            try {

                shootedPlayerId = Integer.parseInt(shootingPlayer.getServerMessage().getValue());

                if (shootedPlayerId < Game.getPlayerArrayList().size() && shootedPlayerId >= 0 && shootedPlayerId != shootingPlayer.getPlayerID()) {
                    shootedPlayer = Game.getPlayerArrayList().get(shootedPlayerId);

                    if(weapon.getWeaponName().equals("RocketLauncher")) {

                        if(shootedPlayer.getSquare().equals(shootingPlayer.getSquare())) {
                            out.println("playerInSquare");
                            out.flush();
                            return inputAndCheckPlayerYouCanSee(out, in,weapon, canSeeModality,shootingPlayer,playersRemained);

                        }
                    }

                    if (!playersRemained.contains(shootedPlayer)) {
                        System.out.println("player has been already shot");
                        out.println("playerDeadOrShot");
                        out.flush();
                        return inputAndCheckPlayerYouCanSee(out, in,weapon, canSeeModality,shootingPlayer,playersRemained);

                    }

                    if (canSeeModality) {
                        if (!DistanceManager.canSee1(shootingPlayer,shootedPlayer)) {

                            out.println("playerNotVisible");
                            out.flush();
                        } else {
                            out.println("okInput");
                            out.flush();
                            return shootedPlayer;
                        }
                    }
                    else if (!canSeeModality) {
                        if (DistanceManager.canSee1(shootingPlayer,shootedPlayer)) {

                            out.println("playerVisible");
                            out.flush();
                        } else {
                            out.println("okInput");
                            out.flush();
                            return shootedPlayer;
                        }
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
        }
        else {
            out.println("reinsert");
            out.flush();
        }

        return inputAndCheckPlayerYouCanSee(out, in,weapon, canSeeModality,shootingPlayer,playersRemained);
    }
}