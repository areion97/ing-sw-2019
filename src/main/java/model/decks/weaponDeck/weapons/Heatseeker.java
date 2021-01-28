package model.decks.weaponDeck.weapons;

import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootVisibleOrNot;
import controller.strategyPattern.fireModeInterfaces.ShootVisibleOrNotInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Heatseeker extends Weapon {
    private ShootVisibleOrNotInterface basicEffect;

    public Heatseeker(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, numberOfTargets,"ShootVisibleOrNot");
        basicEffect=new ShootVisibleOrNot();
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out, in, this,false, shootingPlayer, this.getNumberOfTargets(), 3, 0,"",0);
    }

}
