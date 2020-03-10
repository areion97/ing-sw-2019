/**
 * @author Andrea
 */
package model.decks.ammoDeck.ammoTiles;

/**
 * Contains all methods to set and get at runtime, instances of this class are used to create AmmoDeck
 */
public class AmmoTile {

    private int[] ammoRYB = new int[3];
    private boolean powerupPresence;

    public AmmoTile(int red, int yellow, int blue, boolean powerup) {

        ammoRYB[0] = red;                 //0    red
        ammoRYB[1] = yellow;              //1    yellow
        ammoRYB[2] = blue;                //2    blue
        powerupPresence = powerup;
    }

    public boolean getPowerup() {
        return powerupPresence;
    }
    public int [] getAmmoRYB (){
        return ammoRYB;

    }


}



