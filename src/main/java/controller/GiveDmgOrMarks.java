package controller;

import controller.modelView.Notify;
import model.Game;
import model.GameManager;
import model.decks.powerupDeck.powerups.Powerup;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GiveDmgOrMarks {

    public static void addDmg(Player shootedPlayer, Player shootingPlayer, int damage) {

        ArrayList<Player> shootedPlayerMarks= shootedPlayer.getPlayerBoard().getMarks();
        ArrayList<Player> shootedPlayerDmg= shootedPlayer.getPlayerBoard().getDamageTaken();
        if(damage!=0) {

            for (int i = 0; i < shootedPlayer.getPlayerBoard().getMarks().size(); i++) {

                if (shootedPlayerMarks.get(i).equals(shootingPlayer) && shootedPlayerDmg.size() < 12) {
                    shootedPlayerDmg.add(shootingPlayer);
                    shootedPlayerMarks.remove(shootedPlayerMarks.get(i));
                    shootingPlayer.setMarksGiven(shootingPlayer.getMarksGiven() - 1);
                }

            }
/*
            for (int i = 0; i < shootedPlayer.getPowerups().size(); i++) {

                Powerup p = shootedPlayer.getPowerups().get(i);
                if (p.getName().equals("TagbackGrenade")) {
                    PrintWriter tagBackOut = GameManager.getOut(shootedPlayer.getPlayerID());
                    Scanner tagBackIn = GameManager.getIn(shootedPlayer.getPlayerID());
                    tagBackOut.println("youHaveTagbackGrenade");
                    tagBackOut.flush();
                    int useTagBack = ServerInputController.inputAndCheckInteger(0, 1, tagBackOut, tagBackIn, shootedPlayer, "useTagbackGrenade?");
                    System.out.println("use tagbackk " + useTagBack);
                    if (useTagBack == 1) {
                        GiveDmgOrMarks.addMark(shootingPlayer, shootedPlayer, 1);

                        System.out.println("fatto");

                        Notify.notifyAll(shootedPlayer, "NotifyTagbackGrenade", null);
                        Game.getPowerupDeck().discardPowerup(p);
                        shootedPlayer.getPowerups().remove(i);
                        return;

                    } else
                        return;
                }
            }
*/
            for (int i = 0; i < damage && shootedPlayerDmg.size() < 12; i++) {
                shootedPlayerDmg.add(shootingPlayer);
            }

            if (shootedPlayerDmg.size() >=11) {
                shootedPlayer.setAlive(false);
                Game.getPlayersAlive().remove(shootedPlayer);
                Notify.notifyAll(shootingPlayer,"NotifyDeadPlayer",shootedPlayer);
            }
        }
    }

    public static void addMark(Player shootedPlayer, Player shootingPlayer, int numberOfMarks) {
        ArrayList<Player> shootedPlayerMarks= shootedPlayer.getPlayerBoard().getMarks();

        if (numberOfMarks != 0) {

            if (shootingPlayer.getPlayerBoard().getDamageTaken().size() == 12) {
                shootedPlayerMarks.add(shootingPlayer);
                shootingPlayer.setMarksGiven(shootingPlayer.getMarksGiven() + 1);
                return;
            }

            for (int i = 0; i < numberOfMarks; i++)
                if (shootedPlayer.getPlayerBoard().getMarks().size() < 3 && shootingPlayer.getMarksGiven() < 3) {
                    shootedPlayerMarks.add(shootingPlayer);
                    shootingPlayer.setMarksGiven(shootingPlayer.getMarksGiven() + 1);

                }
        }
    }
}
