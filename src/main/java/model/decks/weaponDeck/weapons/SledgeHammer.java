package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SledgeHammer extends Weapon {
    private ShootAtPrefixedDistanceInterface basicEffect;

    public SledgeHammer(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        basicEffect=new ShootAtPrefixedDistance();
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,shootingPlayer,1, 0,0,false,2,0,"",0);
    }


}
