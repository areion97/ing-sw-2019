package controller.strategyPattern.concreteFireModes;

import controller.GiveDmgOrMarks;
import controller.modelView.Notify;
import controller.statePattern.Move;
import controller.statePattern.Playing;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class ShootInVisibleRoom {
    public static void checkAndShoot(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, String room, int damage, int mark) {

        if(!existencePlayerInRoom(shootingPlayer,room)||room.equals(shootingPlayer.getSquare().getRoomColor().toString())) {
            System.out.println("non esistono giocatori in quella stanza");
            out.println("noPlayersInRoom");
            out.flush();
            Move.setPreviousSquare(shootingPlayer);
            shootingPlayer.setState(new Playing());
            shootingPlayer.nextAction(in,out,shootingPlayer);
        }


        else {
            System.out.println("puoi sparare");
            out.println("canUseWeapon");
            out.flush();
            dealDmgInRoom(shootingPlayer,weapon,room,damage,mark);

            for (Player p : Game.getPlayerArrayList()) {
                System.out.println(p.getSquare().getRoomColor().toString() + "  "+p.getPlayerID() + " damage " + p.getPlayerBoard().getDamageTaken());
                System.out.println(p.getPlayerID() + " marks " + p.getPlayerBoard().getMarks());
            }
        }
    }


    public static void dealDmgInRoom(Player shootingPlayer,Weapon weapon, String room,int damage, int marks) {
        for (Player p : Game.getPlayersAlive()) {
            if (!p.equals(shootingPlayer)&& p.getSquare().getRoomColor().toString().equals(room)) {
                GiveDmgOrMarks.addDmg(p,shootingPlayer, damage);
                GiveDmgOrMarks.addMark(p,shootingPlayer, marks);

                weapon.setLoaded(false);
                Notify.notifyShoot(shootingPlayer,"NotifyShoot",p,weapon,damage,marks);
            }
        }
    }


    private static boolean existencePlayerInRoom(Player shootingPlayer, String room) {
        for (Player p : Game.getPlayersAlive()) {
            if (!p.equals(shootingPlayer)&&room.equals(p.getSquare().getRoomColor().toString())) {
                return true;
            }
        }
        return false;
    }
}
