package controller.strategyPattern.concreteFireModes;

import controller.DistanceManager;
import controller.GiveDmgOrMarks;
import controller.ServerInputController;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
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



public class ShootAtPrefixedDistance implements ShootAtPrefixedDistanceInterface {
    /**
     *
     * @param out output stream of server socket
     * @param in
     * @param weapon
     * @param shootingPlayer
     * @param numberOfTargets
     * @param fromStep
     * @param toStep
     * @param seeMode
     * @param damage
     * @param marks
     * @param move
     * @param distance
     * @return
     */
    @Override
    public Player shoot(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, int numberOfTargets, int fromStep, int toStep, boolean seeMode, int damage, int marks, String move, int distance) {
        int shootAnotherPlayer = 0;
        boolean reinsert;
        Player shootedPlayer=null;

        ArrayList<Player> playersYouCanSee = new ArrayList<>();
        playersYouCanSee.addAll(Game.getPlayersAlive());
        playersYouCanSee.remove(shootingPlayer);

        for (Player p:Game.getPlayersAlive()
             ) {
            if(!p.equals(shootingPlayer)) {
                if(!DistanceManager.canSee1(shootingPlayer,p))
                    playersYouCanSee.remove(p);
            }

        }





        if (seeMode) {
            if (!ShootController.canSeeSomeone(shootingPlayer, seeMode, false, Game.getPlayersAlive())) {
                out.println("cannotSeeAnyone");
                out.flush();
                Move.setPreviousSquare(shootingPlayer);
                shootingPlayer.setState(new Playing());
                shootingPlayer.nextAction(in, out, shootingPlayer);
                return null;
            }

             if(!DistanceManager.reachInAtLeastSteps(shootingPlayer, toStep, playersYouCanSee)) {
                out.println("noPlayersReachable");
                out.flush();
                Move.setPreviousSquare(shootingPlayer);
                shootingPlayer.setState(new Playing());
                shootingPlayer.nextAction(in, out, shootingPlayer);
                return null;
            }
        }
        else {

            if (fromStep == toStep) {
                if (!DistanceManager.reachInExactSteps(shootingPlayer, fromStep, Game.getPlayersAlive())) {
                    out.println("noPlayersReachable");
                    out.flush();
                    Move.setPreviousSquare(shootingPlayer);
                    shootingPlayer.setState(new Playing());
                    shootingPlayer.nextAction(in, out, shootingPlayer);
                    return null;
                }
            } else {

                if (!DistanceManager.reachInAtLeastSteps(shootingPlayer, toStep, Game.getPlayersAlive())) {
                    out.println("noPlayersReachable");
                    out.flush();
                    Move.setPreviousSquare(shootingPlayer);
                    shootingPlayer.setState(new Playing());
                    shootingPlayer.nextAction(in, out, shootingPlayer);
                    return null;
                }
            }
        }
        out.println("canUseWeapon");
        out.flush();
////////////////////////////preconditions////////////////////////////////////////////////////////////////////////////

        ArrayList<Player> remainingPlayers = new ArrayList<>();
        remainingPlayers.addAll(Game.getPlayersAlive());
        remainingPlayers.remove(shootingPlayer);
        ArrayList<Player> shootedPlayers = new ArrayList<>();

        for (int i = 0; i < numberOfTargets; i++) {
            if (i != 0 && weapon.getWeaponName().equals("Shockwave")) {
                if (DistanceManager.reachInExactSteps(shootingPlayer, toStep, remainingPlayers) &&atLeastOnePlayerOnDifferentSquare(remainingPlayers, shootedPlayers)) {
                    out.println("keepOnShooting");
                    out.flush();

                } else {
                    out.println("noPlayersRemainedToShoot");
                    out.flush();
                    return null;

                }
            }

            do {
                reinsert = false;
                ModelViewPlayersAlive.showAlivePlayers(out, in, shootingPlayer);
                shootedPlayer = inputAndCheckPlayer(out, in, fromStep, toStep, seeMode, shootingPlayer, remainingPlayers);


                if (weapon.getWeaponName().equals("Shockwave")) {

                    for (Player p : shootedPlayers) {
                        if (shootedPlayer.getSquare().equals(p.getSquare())) {
                            out.println("sameSquare");
                            out.flush();
                            reinsert = true;
                        }
                    }

                    if (!reinsert) {

                        out.println("okShoot");
                        out.flush();
                        shootedPlayers.add(shootedPlayer);
                        GiveDmgOrMarks.addDmg(shootedPlayer,shootingPlayer, damage);
                        GiveDmgOrMarks.addMark(shootedPlayer,shootingPlayer, marks);
                        weapon.setLoaded(false);
                        Notify.notifyShoot(shootingPlayer, "NotifyShoot", shootedPlayer, weapon, damage, marks);

                    }

                } else {

                    GiveDmgOrMarks.addDmg(shootedPlayer,shootingPlayer, damage);
                    GiveDmgOrMarks.addMark(shootedPlayer,shootingPlayer, marks);
                    weapon.setLoaded(false);

                    Notify.notifyShoot(shootingPlayer,"NotifyShoot",shootedPlayer,weapon,damage,marks);

                }

            } while (reinsert);


            remainingPlayers.remove(shootedPlayer);

            if (numberOfTargets > 1 && i != numberOfTargets - 1) {
                shootAnotherPlayer = ServerInputController.inputAndCheckInteger(0, 1, out, in, shootingPlayer, "shootAgain?");

                if (shootAnotherPlayer == 0) {
                    return shootedPlayer;
                }
            }


            if (i == numberOfTargets - 1) {
                if (move.equals("AskAndMoveTarget")) {
                    MoveController.moveShootedPlayer(out, in, shootingPlayer, shootedPlayer, move, distance);

                } else if (move.equals("MoveShootingPlayer")) {
                    shootingPlayer.setSquare(shootedPlayer.getSquare());
                    out.println("playerMoved");
                    out.flush();
                    Notify.notifyAll(shootingPlayer,"NotifyMoved",null);
                }

                return shootedPlayer;
            }


        }


        return null;

    }


    /**
     * Checks if player can be shooted.
     * 1st check: player has to be alive.
     * 2nd check: player has to be visible.
     **/


    private Player inputAndCheckPlayer(PrintWriter out, Scanner in, int fromStep, int toStep, boolean seeMode, Player shootingPlayer, ArrayList<Player> players)  {

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


                    if (!players.contains(shootedPlayer)) {

                        out.println("playerDeadOrShot");
                        out.flush();

                    } else if (seeMode && !DistanceManager.canSee1(shootingPlayer,shootedPlayer)) {
                        out.println("playerNotVisible");
                        out.flush();
                    }

                    else if (fromStep != toStep) {
                        if (!DistanceManager.reachableInLessSteps(shootingPlayer.getSquare(), shootedPlayer.getSquare(), 0, toStep - 1)) {
                            out.println("okInput");
                            out.flush();
                            return shootedPlayer;
                        } else {
                            out.println("noPlayersReachable");
                            out.flush();
                        }

                    } else {
                        if (DistanceManager.reachableInSteps(shootingPlayer.getSquare(), shootedPlayer.getSquare(), toStep)) {

                            out.println("okInput");
                            out.flush();
                            return shootedPlayer;


                        } else {
                            out.println("noPlayersReachable");
                            out.flush();
                        }
                    }
                } else {
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

        return inputAndCheckPlayer(out, in, fromStep, toStep, seeMode, shootingPlayer, players);
    }

    private boolean atLeastOnePlayerOnDifferentSquare(ArrayList<Player> remainedPlayers, ArrayList<Player> shootedPlayers) {

        int counter = 0;
        boolean flag;

        for (int i = 0; i <= remainedPlayers.size() - 1; i++) {

            counter=0;
            for (int j = 0; j <= shootedPlayers.size() - 1; j++) {
                if (!remainedPlayers.get(i).getSquare().equals(shootedPlayers.get(j).getSquare())) {
                    counter++;

                }

                if (j == shootedPlayers.size() - 1 && counter == shootedPlayers.size() ) {
                    return true;
                }

            }

        }
        return false;
    }

}