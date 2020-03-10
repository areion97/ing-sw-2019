package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootAtPrefixedDistance;
import controller.strategyPattern.fireModeInterfaces.ShootAtPrefixedDistanceInterface;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Shotgun extends Weapon {
    private ShootAtPrefixedDistanceInterface basicEffect;
    private String move = "AskAndMoveTarget";

    public Shotgun(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName,baseCost,additionalCost,optionalCost,alternativeCost,numberOfTargets,"ShootAtPrefixedDistance");
        this.basicEffect=new ShootAtPrefixedDistance();

    }


    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
        basicEffect.shoot(out,in,this,shootingPlayer,this.getNumberOfTargets(), 0,0, false,3,0,move,1);
    }


    @Override
    public String getMove() {
        return move;
    }
}

