/**
 * @author Andrea
 */
package model.decks.powerupDeck;

import controller.enumerations.AmmoColors;
import model.decks.DeckManager;
import model.decks.powerupDeck.powerups.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
/**
 * His attributes are set from file initially and contains all methods to set and get at runtime
 */

public class PowerupDeck extends DeckManager {

    private LinkedList<Powerup> deck;

    public PowerupDeck()  {
        setDeckSize(24);

        deck = new LinkedList<Powerup>();
        try {
            FileReader filePowerup = new FileReader(System.getProperty("user.home") + File.separatorChar + "powerup.txt");
            BufferedReader readPowerupFile = new BufferedReader(filePowerup);

            String riga;
            String powerupTag;
            AmmoColors colorTag;

            readPowerupFile.readLine();
            while ((riga = readPowerupFile.readLine()) != null) {
                powerupTag = riga.substring(0, riga.indexOf(" "));
                int indexSpace = riga.indexOf(" ");
                int indexSecondSpace = riga.indexOf(" ", indexSpace + 1);
                colorTag = AmmoColors.valueOf(riga.substring(indexSpace + 1, indexSecondSpace));
                for (int i = 0; i < Character.getNumericValue(riga.charAt(riga.length() - 1)); i++) {
                    if (powerupTag.equals("Newton"))
                        deck.add(new Newton(colorTag, powerupTag));
                    else if (powerupTag.equals("TagbackGrenade"))
                        deck.add(new TagbackGrenade(colorTag, powerupTag));
                    else if (powerupTag.equals("TargetingScope"))
                        deck.add(new TargetingScope(colorTag, powerupTag));
                    else if (powerupTag.equals("Teleporter"))
                        deck.add(new Teleporter(colorTag, powerupTag));
                    else
                        System.out.println("No powerup found to create, please correct the powerup.txt using the correct syntax (powerupName color quantity)");

                }
            }
            readPowerupFile.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        Collections.shuffle(deck);
    }

    public LinkedList<Powerup> getDeck() {
        return deck;
    }

    /**
     * Returns first element of the deck and shuffles it when there are no cards left.
     * @return Powerup Object drawn from the deck
     */

    public Powerup drawPowerup() {

        incrementCountToShuffle();
        Powerup powerup;
        powerup= deck.get(0);
        deck.remove(0);

        if(canShuffle()) {
            Collections.shuffle(deck);
            setDeckSize(deck.size());
        }

        return powerup;
    }

    public void discardPowerup(Powerup powerupToDiscard) {
        deck.addLast(powerupToDiscard);
    }
}