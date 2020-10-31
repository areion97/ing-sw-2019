/**
 * @author Andrea
 */
package model.decks.weaponDeck;

import controller.enumerations.AmmoColors;
import model.decks.DeckManager;
import model.decks.weaponDeck.weapons.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class WeaponDeck extends DeckManager {
    private LinkedList<Weapon> deck;


    public LinkedList<Weapon> getDeck() {
        return deck;
    }

    public WeaponDeck() {
        deck = new LinkedList<Weapon>();
        setDeckSize(21);

        try {

            FileReader fileWeapon = new FileReader(System.getProperty("user.home") + File.separatorChar + "weapons.txt");
            BufferedReader readWeaponFile = new BufferedReader(fileWeapon);
            int firstBarIndex, secondBarIndex, thirdBarIndex;
            String line;
            AmmoColors baseCost;
            ArrayList<AmmoColors> additionalCost = new ArrayList<>();
            ArrayList<AmmoColors> optionalCost = new ArrayList<>();
            ArrayList<AmmoColors> alternativeCost = new ArrayList<>();
            String weaponName;
            int firstSpaceIndex;
            readWeaponFile.readLine();
            while ((line = readWeaponFile.readLine()) != null) {

                firstSpaceIndex = line.indexOf(' ');
                firstBarIndex = line.indexOf('|');
                secondBarIndex = line.indexOf('|', firstBarIndex + 1);
                thirdBarIndex = line.indexOf('|', secondBarIndex + 1);
                if (additionalCost.size() != 0)
                    additionalCost.clear();
                if (optionalCost.size() != 0)
                    optionalCost.clear();
                if (alternativeCost.size() != 0)
                    alternativeCost.clear();

                weaponName = line.substring(0, firstSpaceIndex);


                baseCost = AmmoColors.valueOf(line.substring(firstSpaceIndex + 1, firstBarIndex));

                if ((line.charAt(firstBarIndex + 1) != ' ')) {
                    additionalCost.addAll(setArrays(line.substring(firstBarIndex + 1, secondBarIndex)));
                } else
                    additionalCost.clear();

                if (line.charAt(secondBarIndex + 1) != ' ') {
                    optionalCost.addAll(setArrays(line.substring(secondBarIndex + 1, thirdBarIndex)));
                } else
                    optionalCost.clear();

                if (thirdBarIndex != line.length() - 1) {
                    alternativeCost.addAll(setArrays(line.substring(thirdBarIndex + 1)));

                } else {
                    alternativeCost.clear();
                }

                if (line.contains("PlasmaGun"))
                    deck.add(new PlasmaGun(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Thor"))
                    deck.add(new Thor(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Railgun"))
                    deck.add(new Railgun(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Zx2"))
                    deck.add(new Zx2(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("PowerGlove"))
                   deck.add(new PowerGlove(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Electroscythe"))
                    deck.add(new Electroscythe(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Hellion"))
                    deck.add(new Hellion(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("GrenadeLauncher"))
                    deck.add(new GrenadeLauncher(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Shotgun"))
                    deck.add(new Shotgun(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Furnace"))
                    deck.add(new Furnace(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("VortexCannon"))
                    deck.add(new VortexCannon(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("RocketLauncher"))
                    deck.add(new RocketLauncher(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Shockwave"))
                    deck.add(new Shockwave(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 3));
                else if (line.contains("MachineGun"))
                   deck.add(new MachineGun(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 2));
                else if (line.contains("FlameThrower"))
                    deck.add(new FlameThrower(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("LockRifle"))
                    deck.add(new LockRifle(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("TractorBeam"))
                    deck.add(new TractorBeam(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else  if (line.contains("SledgeHammer"))
                    deck.add(new SledgeHammer(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Heatseeker"))
                    deck.add(new Heatseeker(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Whisper"))
                    deck.add(new Whisper(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));
                else if (line.contains("Cyberblade"))
                    deck.add(new Cyberblade(weaponName, baseCost, additionalCost, optionalCost, alternativeCost, 1));

            }
            Collections.shuffle(deck.subList(0,8));
            readWeaponFile.close();
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public ArrayList<AmmoColors> setArrays(String string) {
        ArrayList<AmmoColors> vector = new ArrayList<>();
        int from = 0;
        int space;
        String substring;
        do {
            space = string.indexOf(' ', from);
            if (space != -1) {
                substring = string.substring(from, space);
                from = space + 1;
            } else {
                substring = string.substring(from);
            }
            vector.add(AmmoColors.valueOf(substring));
        } while (space != -1);
        return vector;
    }

    /**
     * Returns first element of the deck and shuffles it when there are no cards left.
     *
     * @return Weapon Object drawn from the deck
     */
    public Weapon drawWeapon() {
        Weapon weapon;

        incrementCountToShuffle();
        weapon = deck.get(0);
        deck.remove(0);

       if(canShuffle()) {
           Collections.shuffle(deck);
           setDeckSize(deck.size());
       }

       return weapon;
    }


    public void printWeapons() {
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(i);
            System.out.println(deck.get(i).getWeaponName());
            System.out.println(deck.get(i).getBaseCost());
            System.out.print("additional cost:");

            System.out.println(deck.get(i).getAdditionalCost());
            System.out.print("optional cost:");
            System.out.println(deck.get(i).getOptionalCost());

            System.out.print("alternative cost:");
            System.out.println(deck.get(i).getAlternativeCost());
            System.out.println("------------------------------");
        }
    }
}

