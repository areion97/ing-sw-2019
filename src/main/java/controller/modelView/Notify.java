package controller.modelView;

import model.Game;
import model.GameManager;
import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Notify {
    public static void notifyAll(Player player, String object, Player movedPlayer) {
        boolean playerIsCurrent;
        for (int i = 0; i < Game.getPlayerArrayList().size(); i++) {

            Player p = Game.getPlayerArrayList().get(i);


            playerIsCurrent = p.equals(Game.getCurrentPlayer());

            if (!playerIsCurrent) {

                PrintWriter out = GameManager.getOut(p.getPlayerID());

                out.println(object);
                out.flush();

                if (object.equals("NotifyUserPlaying")) {
                    out.println(Game.getCurrentPlayer().getUsername() + " " + Game.getCurrentPlayer().getColor() + ", is playing...");
                } else if (object.equals("NotifyMovedBeforeGrabbing")) {

                    out.println(player.getUsername() + " " + player.getColor() + ", moved in X=" + player.getSquare().getX() + ", Y=" + player.getSquare().getY() + " and is now grabbing.");

                } else if (object.equals("NotifyMoved")) {
                    out.println(player.getUsername() + " " + player.getColor() + ", moved in X=" + player.getSquare().getX() + ", Y=" + player.getSquare().getY());

                } else if (object.equals("NotifyMovedBeforeShooting")) {
                    out.println(player.getUsername() + " " + player.getColor() + ", moved in X=" + player.getSquare().getX() + ", Y=" + player.getSquare().getY() + " and is shooting.");

                } else if (object.equals("NotifyAmmoGrabbed")) {
                    out.println(player.getUsername() + " " + player.getColor() + ", has grabbed ammo.");

                } else if (object.equals("NotifyWeaponGrabbed")) {
                    out.println(player.getUsername() + " " + player.getColor() + ", has grabbed weapon.");

                } else if (object.equals("NotifyUpdateDamageAndMarks")) {
                    out.println();
                    out.println("Updated player boards:");

                    sendPlayerBoards(out);

                } else if (object.equals("NotifyMovedBy")) {
                    if (!p.equals(movedPlayer))
                        out.println(movedPlayer.getUsername() + " " + movedPlayer.getColor() + ", has been moved in X=" + movedPlayer.getSquare().getX() + ", Y=" + movedPlayer.getSquare().getY());
                    else
                        out.println("You've been moved by " + player.getUsername() + " " + player.getColor() + " in X=" + movedPlayer.getSquare().getX() + ", Y=" + movedPlayer.getSquare().getY());


                } else if (object.equals("NotifyDeadPlayer")) {
                    if (p.equals(movedPlayer))
                        out.println("You've been killed by: " + player.getUsername() + " " + player.getColor());
                    else
                        out.println(movedPlayer.getUsername() + " " + movedPlayer.getColor() + " is dead! Killer: " + player.getUsername() + " " + player.getColor());

                } else if (object.equals("NotifyScore")) {
                    out.println();
                    out.println("Score: ");

                    sendScore(out);
                } else if (object.equals("NotifyRanking")) {
                    out.println();
                    out.println("Ranking: ");

                    sendRank(out);
                } else if (object.equals("endGame")) ; //mandi end game a tutti

                else if (object.equals("NotifyWinningPlayer")) {
                    out.println(player.getUsername() + " " + player.getColor());
                } else if (object.equals("NotifyMovePowerup")) {
                    if (!p.equals(movedPlayer))
                        out.println(player.getUsername() + " " + player.getColor() + " teleported in X=" + player.getSquare().getX() + ", Y=" + player.getSquare().getY());

                    else
                        out.println("You've been teleported by " + player.getUsername() + " " + player.getColor() + " in X=" + movedPlayer.getSquare().getX() + ", Y=" + movedPlayer.getSquare().getY());


                } else if (object.equals("NotifyTagbackGrenade")) {
                    if (!p.equals(Game.getCurrentPlayer()) && !p.equals(player))
                        out.println(player.getUsername() + " " + player.getColor() + "has used TagbackGrenade, inflicting 1 mark to " + Game.getCurrentPlayer().getUsername() + " " + Game.getCurrentPlayer().getColor());

                    else if (p.equals(Game.getCurrentPlayer()))
                        out.println(player.getUsername() + " " + player.getColor() + "has used TagbackGrenade, inflicting you 1 mark");
                }


                if (!object.equals("NotifyUpdateDamageAndMarks") && !object.equals("NotifyScore") && !object.equals("NotifyRanking") && !object.equals("endGame") && !object.equals("allConnected")) {
                    out.flush();
                    out.println("exit");
                    out.flush();
                }
            }
        }
    }


    public static void sendPlayerBoards(PrintWriter out) {

        for (Player p1 : Game.getPlayerArrayList()) {
            out.print(p1.getUsername() + " " + p1.getColor() + " Damage ");
            for (Player p2 : p1.getPlayerBoard().getDamageTaken())
                out.print(p2.getColor().toString() + " ");
            out.println(p1.getPlayerBoard().getDamageTaken().size());

            out.print(p1.getUsername() + " " + p1.getColor() + " Marks ");
            for (Player p2 : p1.getPlayerBoard().getMarks())
                out.print(p2.getColor().toString() + " ");
            out.println(p1.getPlayerBoard().getMarks().size());
            out.println();
        }

        out.flush();
        out.println("exit");
        out.flush();
    }

    public static void sendScore(PrintWriter out) {

        for (Player p1 : Game.getPlayerArrayList()) {
            out.println(p1.getUsername() + "   " + p1.getColor() + "       " + p1.getScore());
            out.flush();
        }

        out.println("exit");
        out.flush();

    }

    public static Player sendRank(PrintWriter out) {


        HashMap<Player, Integer> playerScoreHashMap = new HashMap<>();

        for (Player player : Game.getPlayerArrayList()) {

            playerScoreHashMap.put(player, player.getScore());
        }

        ArrayList<Integer> sortedScore = new ArrayList<>(playerScoreHashMap.values());

        Collections.sort(sortedScore, Collections.reverseOrder());
        int score;

        ArrayList<Player> playersWhoKilled = new ArrayList<>();     //giocatori in ordine di uccisioni temporale

        //elimina duplicati dall'array  KILLSHOTS
        for (Player p : Game.getKillShots()) {
            if (!playersWhoKilled.contains(p))
                playersWhoKilled.add(p);
        }
        //aggiunge i giocatori che non hanno ucciso
        for (Player p : Game.getPlayerArrayList()) {
            if (!playersWhoKilled.contains(p))
                playersWhoKilled.add(p);
        }
        Player winningPlayer = null;
        boolean flag = true;
        do {
            for (int n = 0; n < playersWhoKilled.size(); n++) {

                Player p = playersWhoKilled.get(n);
                score = sortedScore.get(0);
                //     blue red yellow         5 4 1

                if (score == playerScoreHashMap.get(p)) {
                    if (flag) {
                        winningPlayer = p;
                        flag = false;
                    }

                    out.println(p.getUsername() + " " + p.getColor() + "       " + score);
                    out.flush();
                    sortedScore.remove(0);
                    playerScoreHashMap.remove(p, score);
                    playersWhoKilled.remove(p);
                    break;
                }
            }
        } while (!sortedScore.isEmpty());


        out.println("exit");
        out.flush();

        return winningPlayer;

    }


    public static void sendWinningPlayer(Player winningPlayer, PrintWriter out) {
        out.println(winningPlayer.getUsername() + " " + winningPlayer.getColor());
        out.flush();
        out.println("exit");
        out.flush();
    }

    public static void notifyShoot(Player shootingPlayer, String object, Player shootedPlayer, Weapon weapon, int damage, int marks) {

        for (int i = 0; i < Game.getPlayerArrayList().size(); i++) {

            Player p = Game.getPlayerArrayList().get(i);
            if (!p.equals(Game.getCurrentPlayer())) {

                PrintWriter out = GameManager.getOut(p.getPlayerID());

                out.println(object);
                out.flush();


                if (object.equals("NotifyShoot")) {
                    if (p.equals(shootedPlayer))
                        out.println(shootingPlayer.getUsername() + " " + shootingPlayer.getColor() + ", shooted you" + ", with " + weapon.getWeaponName() + ", inflicting " + damage + " damage and " + marks + " marks.");
                    else
                        out.println(shootingPlayer.getUsername() + " " + shootingPlayer.getColor() + ", has shot " + shootedPlayer.getUsername() + " " + shootedPlayer.getColor() + ", with " + weapon.getWeaponName() + ", inflicting " + damage + " damage and " + marks + " marks.");

                }

                out.flush();
                out.println("exit");
                out.flush();

            }
        }


    }


}
