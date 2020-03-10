package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class  Whisper extends Weapon {
    private ShootAtPrefixedDistanceInterface basicEffect;

    public Whisper(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        basicEffect= new ShootAtPrefixedDistance();
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,shootingPlayer,1, 0,2,true,3,1,"",0);


    }


}
