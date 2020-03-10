package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.enumerations.MapColors;
import controller.strategyPattern.concreteFireModes.ShootInVisibleRoom;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Furnace extends Weapon {


    public Furnace(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootInVisibleRoom");

    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        String room=in.nextLine();
        room=room.toUpperCase();

        for(int i=0;i<MapColors.values().length;i++) {
            if(room.equals(MapColors.values()[i].toString())) {
                ShootInVisibleRoom.checkAndShoot(out,in,this,shootingPlayer,room,1,0);
                return;
            }
        }
        out.println("wrongInput");
        out.flush();
        shootWithBasicAttack(out, in, shootingPlayer);

    }

}

