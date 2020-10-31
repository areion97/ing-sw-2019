package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootVisibleOrNot;
import controller.strategyPattern.fireModeInterfaces.ShootVisibleOrNotInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Thor extends Weapon {
    private ShootVisibleOrNotInterface basicEffect;
    public Thor( String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootVisibleOrNot");
        basicEffect=new ShootVisibleOrNot();
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,true,shootingPlayer,this.getNumberOfTargets(), 2, 0,"",0);
    }

}
