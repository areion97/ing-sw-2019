package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Shockwave extends Weapon {
    private ShootAtPrefixedDistance basicEffect;

    public Shockwave(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        basicEffect= new ShootAtPrefixedDistance();

    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,shootingPlayer,3,1,1,false,1,0,"",0);
    }

}
