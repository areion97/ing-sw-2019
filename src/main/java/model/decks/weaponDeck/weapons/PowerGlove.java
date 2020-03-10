package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PowerGlove extends Weapon {
    private ShootAtPrefixedDistanceInterface basicEffect;
    private final String move = "MoveShootingPlayer";

    public PowerGlove(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        basicEffect= new ShootAtPrefixedDistance();
    }


    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,shootingPlayer,this.getNumberOfTargets(),1,1,false,1,2,move,0);
    }



    @Override
    public String getMove() {
        return move;
    }
}
