package model.decks.weaponDeck.weapons;
import controller.enumerations.AmmoColors;
import controller.strategyPattern.concreteFireModes.ShootMoveToCanSeeSquare;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

    public class TractorBeam extends Weapon {
        private String move="moveTractorBeam";
        public TractorBeam(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets) {
            super(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, numberOfTargets,"ShootMoveToCanSeeSquare");
        }

        @Override
        public void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer) {
            ShootMoveToCanSeeSquare.checkAndShoot(out,in,this,shootingPlayer,1,0);
        }




    }
