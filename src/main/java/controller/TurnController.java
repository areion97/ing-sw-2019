/**
 * @author Andrea
 */
package controller;

import controller.enumerations.AmmoColors;
import controller.modelView.ModelViewAmmo;
import controller.modelView.ModelViewPlayerWeapons;
import controller.modelView.Notify;
import controller.statePattern.Spawning;
import model.Game;
import model.GameManager;
import model.decks.weaponDeck.weapons.Weapon;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.*;

public class TurnController {

    /**
     * At the end of the turn replaces ammo and weapons taken during a player's turn
     */
    public static void replaceAmmoAndWeapons() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Square s = Game.getMap().getSquare(i, j);
                if (s != null) {
                    if (s.getAmmoTile() == null && s.getPlayerSpawn() == false) {
                        s.setAmmo(Game.getAmmoDeck().drawAmmoTile());
                    } else if (s.getPlayerSpawn() == true && s.getWeapons().size() < 3) {
                        do {
                            if (!Game.getWeaponDeck().getDeck().isEmpty())
                                s.getWeapons().add(Game.getWeaponDeck().drawWeapon());
                            else
                                break;
                        }
                        while (s.getWeapons().size() < 3);
                    }
                }
            }
        }


    }


    public static void checkDeadPlayers(Scanner in,PrintWriter out) {

        int i = 0;

        PrintWriter outDead = null;
        Scanner inDead = null;

        for (Player deadPlayer : Game.getPlayerArrayList()) {

            if (!deadPlayer.getAlive()) {
                out.println("deadPlayer");
                out.flush();

                out.println(deadPlayer.getUsername() + " " + deadPlayer.getColor());
                out.flush();

                out.println("exit");
                out.flush();

                outDead = GameManager.getOut(deadPlayer.getPlayerID());
                inDead = GameManager.getIn(deadPlayer.getPlayerID());
                outDead.println("respawnDeadPlayer");
                outDead.flush();

                deadPlayer.setState(new Spawning());
                deadPlayer.nextAction(inDead, outDead, deadPlayer);

                deadPlayer.setAlive(true);
                Game.getPlayersAlive().add(deadPlayer.getPlayerID(),deadPlayer);

                Game.addKillShot(deadPlayer.getPlayerBoard().getDamageTaken().get(10));
                if (deadPlayer.getPlayerBoard().getDamageTaken().size() == 12) {
                    Game.addKillShot(deadPlayer.getPlayerBoard().getDamageTaken().get(10));
                    GiveDmgOrMarks.addMark(deadPlayer.getPlayerBoard().getDamageTaken().get(10),deadPlayer, 1); //gives one mark to the player who overkilled
                }


                giveScore(deadPlayer.getPlayerBoard().getDamageTaken(),deadPlayer);

                Player firstBloodPlayer = deadPlayer.getPlayerBoard().getDamageTaken().get(0);
                firstBloodPlayer.setScore(firstBloodPlayer.getScore() + 1);

                Game.decreaseSkullsNumber();

                deadPlayer.getPlayerBoard().getMarks().clear();
                deadPlayer.getPlayerBoard().getDamageTaken().clear();


                if (Game.getSkullsNumber() == 0) {

                    giveScore(Game.getKillShots(),null);

                 /*   for (Player p: Game.getPlayerArrayList()) {
                        if(!p.getPlayerBoard().getDamageTaken().isEmpty())
                            giveScore(p.getPlayerBoard().getDamageTaken(),p);
                    }*/

                }

                i++;

                Notify.notifyAll(Game.getCurrentPlayer(),"NotifyScore",null);
            }
        }

        if (i == 0) {
            out.println("noPlayerDead");
            out.flush();
            Notify.sendPlayerBoards(out);
        }
        else {
            Notify.sendPlayerBoards(out);
            Notify.sendScore(out);
        }
        //send scorebord to the player sho is passing turn


    }

    public static void pass(int currentPlayerId) {

        if (currentPlayerId + 1 < Game.getPlayerArrayList().size()) {
            Game.setCurrentPlayer(Game.getPlayerArrayList().get(currentPlayerId + 1));
        } else
            Game.setCurrentPlayer(Game.getPlayerArrayList().get(0));
    }

    public static void reloadWeapon(PrintWriter out, Scanner in, Player player) {

        boolean reinsert;
        boolean reloadAnotherOne;

        do {
            boolean canReload = false;
            for(int i=0;i<player.getWeapons().size();i++) {
                Weapon w = player.getWeapons().get(i);

                if (!w.getLoaded() && payCost(w, player, 0)) {
                    out.println("canReload");
                    out.flush();
                    canReload = true;
                    break;
                }
            }

            if(!canReload) {
                out.println("cannotReload");
                out.flush();
                return;
            }

            reloadAnotherOne=true;

            int reload = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "reloadWeapon?");

            if (reload == 1) {

                if (canReload) {

                    do {
                        reinsert = false;

                        ModelViewPlayerWeapons.showPlayerWeapons(player, out, in);
                        int weaponIndex = ServerInputController.inputAndCheckInteger(0, player.getWeapons().size(), out, in, player, "weaponToReload");
                        Weapon reloadingWeapon = player.getWeapons().get(weaponIndex);

                        if (reloadingWeapon.getLoaded()) {
                            out.println("weaponAlreadyLoaded");
                            out.flush();
                            reinsert = true;

                        } else if (!payCost(reloadingWeapon, player, 0)) {
                            out.println("cannotPayCost");
                            out.flush();
                            reinsert = true;
                        } else {
                            out.println(reloadingWeapon.getWeaponName());
                            out.flush();
                            payCost(reloadingWeapon, player, 1);
                            reloadingWeapon.setLoaded(true);
                            ModelViewAmmo.showPlayerAmmo(in, out, player);
                            reinsert = false;
                        }

                    } while (reinsert);

                } else {
                    out.println("cannotReload");
                    out.flush();
                    return;
                }

            }
            else
                return;

        } while (reloadAnotherOne);

    }


    private static boolean payCost (Weapon w, Player player,int set){
        int red = player.getAmmoRYB()[0];
        int yellow = player.getAmmoRYB()[1];
        int blue = player.getAmmoRYB()[2];

        if (w.getBaseCost().equals(AmmoColors.RED))
            red--;
        else if (w.getBaseCost().equals(AmmoColors.YELLOW))
            yellow--;
        else
            blue--;

        for (AmmoColors color : w.getAdditionalCost()) {
            if (color.equals(AmmoColors.RED))
                red--;
            else if (color.equals(AmmoColors.YELLOW))
                yellow--;
            else
                blue--;
        }


        if (blue < 0 || yellow < 0 || red < 0) {
            return false;
        }

        if (set == 1)
            player.setAmmos(red, yellow, blue);

        return true;
    }

    private static void giveScore(ArrayList<Player> playersToRank,Player deadPlayer) {

        HashMap<Player, Integer> dmgPlayerHashMap = new HashMap<>();
        ArrayList<Player> shootingPlayers = new ArrayList<>();
        int dmgFromOnePlayer;


        for (Player playerToGiveScore : playersToRank) {

            if ( !shootingPlayers.contains(playerToGiveScore)) {
                dmgFromOnePlayer = 0;

                shootingPlayers.add(playerToGiveScore); //players who shoot without repetitions

                for (int j = 0; j < playersToRank.size(); j++) {
                    if (playersToRank.get(j).equals(playerToGiveScore))
                        dmgFromOnePlayer++;
                }

                dmgPlayerHashMap.put(playerToGiveScore, dmgFromOnePlayer);
            }

        }

        ArrayList<Integer> sortedDmgGiven = new ArrayList<>(dmgPlayerHashMap.values());
        Collections.sort(sortedDmgGiven, Collections.reverseOrder());

        int dmgGiven = 0;
        int score = 0;
        int scoreIndex = 0;
        int[] rewardPoints= {8,6,4,2,1};
        if(deadPlayer!=null)
            rewardPoints= deadPlayer.getPlayerBoard().getRewardPoints();



        for (int points : rewardPoints) {
            if (points != 0) {
                break;
            }
            scoreIndex++;
        }


        do {
            for (int n = 0; n < shootingPlayers.size(); n++) {

                Player p = shootingPlayers.get(n);
                dmgGiven = sortedDmgGiven.get(0);

                if (dmgGiven == dmgPlayerHashMap.get(p)) {

                    if (scoreIndex < 4) {
                        if(deadPlayer!=null)
                            score = deadPlayer.getPlayerBoard().getRewardPoints()[scoreIndex];
                        else
                            score = rewardPoints[scoreIndex];

                    } else
                        score = 1;

                    p.setScore(p.getScore() + score);
                    sortedDmgGiven.remove(0);
                    dmgPlayerHashMap.remove(p, score);
                    shootingPlayers.remove(p);
                    scoreIndex++;

                    break;
                }

            }

        } while (!shootingPlayers.isEmpty());

        if(deadPlayer!=null) {

            for (scoreIndex = 0; scoreIndex < rewardPoints.length; scoreIndex++)
                if (rewardPoints[scoreIndex] != 0&&rewardPoints[scoreIndex]!=1) {
                    deadPlayer.getPlayerBoard().diminishRewards(scoreIndex);
                    break;
                }

        }

    }
}