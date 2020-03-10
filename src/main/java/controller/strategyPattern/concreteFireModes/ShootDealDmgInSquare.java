package controller.strategyPattern.concreteFireModes;

import controller.GiveDmgOrMarks;
import controller.modelView.Notify;
import controller.statePattern.Move;
import controller.statePattern.Playing;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ShootDealDmgInSquare {

    public static void checkAndShootAllInSquare(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, Square s, int damage, int mark) {

        if(!existencePlayerInSquare(shootingPlayer,s)) {

            out.println("noPlayersInSquare");
            out.flush();
            Move.setPreviousSquare(shootingPlayer);
            shootingPlayer.setState(new Playing());
            shootingPlayer.nextAction(in,out,shootingPlayer);

        }


        else {
            out.println("canShoot");
            out.flush();
            dealDmgAllInSquare(shootingPlayer,s,weapon,damage,mark);
            System.out.println("You shot!");
            weapon.setLoaded(false);


            for (Player p : Game.getPlayerArrayList()) {
                System.out.println(p.getPlayerID() + " damage " + p.getPlayerBoard().getDamageTaken());
                System.out.println(p.getPlayerID() + " marks " + p.getPlayerBoard().getMarks());
            }
        }
    }


    public static void dealDmgAllInSquare(Player shootingPlayer, Square s, Weapon weapon,int damage, int marks) {
        for (Player p : Game.getPlayersAlive()) {
            if (!p.equals(shootingPlayer)&& p.getSquare().equals(s)) {
                GiveDmgOrMarks.addDmg(p,shootingPlayer, damage);
                GiveDmgOrMarks.addMark(p,shootingPlayer, marks);
                Notify.notifyShoot(shootingPlayer,"NotifyShoot",p,weapon,damage,marks);

                }
            }
        }


    private static boolean existencePlayerInSquare(Player shootingPlayer, Square s) {
        for (Player p : Game.getPlayersAlive()) {
            if (!p.equals(shootingPlayer)&&p.getSquare().equals(s)) {
                    return true;
                }
            }
        return false;
    }
}