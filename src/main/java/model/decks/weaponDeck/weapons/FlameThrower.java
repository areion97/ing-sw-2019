package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class FlameThrower extends Weapon {

    private String move = "moveTractorBeam";

    public FlameThrower(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
        super(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, numberOfTargets, "ShootMoveToCanSeeSquare");
    }

    @Override
    public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {

    }


}
