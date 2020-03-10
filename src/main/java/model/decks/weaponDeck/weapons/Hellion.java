package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import controller.strategyPattern.concreteFireModes.ShootDealDmgInSquare;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Hellion extends Weapon {

    private ShootAtPrefixedDistanceInterface basicEffect;

    public Hellion(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost,int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        basicEffect=new ShootAtPrefixedDistance();
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        Player shootedPlayer= basicEffect.shoot(out,in,this,shootingPlayer,1, 0,1,true,1,0,"",0);

        if(shootedPlayer!=null) {
            ShootDealDmgInSquare.dealDmgAllInSquare(shootingPlayer, shootedPlayer.getSquare(), this,0, 1);
        }

    }


}
