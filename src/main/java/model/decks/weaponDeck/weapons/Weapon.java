package model.decks.weaponDeck.weapons;


import controller.enumerations.AmmoColors;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class  Weapon {
    private String weaponName;
    private AmmoColors baseCost;
    private ArrayList<AmmoColors> additionalCost;
    private ArrayList<AmmoColors> optionalCost;
    private ArrayList<AmmoColors> alternativeCost;
    private String basicAttackName;
    private int numberOfTargets = 0;
    private boolean loaded;


    public Weapon(String weaponName, AmmoColors baseCost, ArrayList<AmmoColors> additionalCost, ArrayList<AmmoColors> optionalCost, ArrayList<AmmoColors> alternativeCost, int numberOfTargets,String basicAttackName) {

        this.weaponName = weaponName;
        this.baseCost = baseCost;
        this.additionalCost = new ArrayList<>();
        this.alternativeCost = new ArrayList<>();
        this.optionalCost = new ArrayList<>();
        this.additionalCost.addAll(additionalCost);
        this.optionalCost.addAll(optionalCost);
        this.alternativeCost.addAll(alternativeCost);
        this.numberOfTargets = numberOfTargets;
        this.loaded = true;
        this.basicAttackName=basicAttackName;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
    public boolean getLoaded() {
        return loaded;
    }

    public AmmoColors getBaseCost() {
        return baseCost;
    }

    public ArrayList<AmmoColors> getAdditionalCost() {
        return additionalCost;
    }

    public ArrayList<AmmoColors> getAlternativeCost() {
        return alternativeCost;
    }

    public ArrayList<AmmoColors> getOptionalCost() {
        return optionalCost;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public int getNumberOfTargets() {
        return numberOfTargets;
    }

    public abstract void shootWithBasicAttack(PrintWriter out, Scanner in, Player shootingPlayer);

    public String getBasicAttackInterface() {
        return basicAttackName;
    }


    public String getMove() {
        return "";
    }


}
