/**
 * @author Andrea
 */
package model.decks.ammoDeck;

import model.decks.DeckManager;
import model.decks.ammoDeck.ammoTiles.AmmoTile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

/**
 * His attributes are set from file initially and contains all methods to set and get at runtime
 */

public class AmmoDeck  extends DeckManager {
    private LinkedList<AmmoTile> deck;


    public AmmoDeck() {
            setDeckSize(36);
            deck=new LinkedList<>();
            try {
                FileReader fileAmmo = new FileReader(System.getProperty("user.home") + File.separatorChar + "ammoDeck.txt");
                BufferedReader readAmmoFile = new BufferedReader(fileAmmo);

                String line;
                readAmmoFile.readLine();
                while ((line = readAmmoFile.readLine()) != null) {
                    int numberOfTiles = Character.getNumericValue(line.charAt(0));
                    for (int i = 0; i < numberOfTiles; i++) {
                        {
                            deck.add(new AmmoTile(Character.getNumericValue(line.charAt(2)), Character.getNumericValue(line.charAt(4)), Character.getNumericValue(line.charAt(6)), Boolean.valueOf(line.substring(8))));
                        }
                    }
                }

                readAmmoFile.close();
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }

            Collections.shuffle(deck);


    }

    public LinkedList<AmmoTile> getDeck() {
        return deck;
    }

    /**
     * Returns first element of the deck and shuffles it when there are no cards left.
     * @return AmmoTile Object drawn from the deck
     */

    public AmmoTile drawAmmoTile() {
        incrementCountToShuffle();

        AmmoTile ammoTaken = deck.get(0);
        deck.remove(0);

        if(canShuffle()) {
            Collections.shuffle(deck);
            setDeckSize(deck.size());
        }

        return ammoTaken;
    }

    public void discardAmmoTile(AmmoTile ammoDiscarded) {
        deck.addLast(ammoDiscarded);
    }

}
