/**
 * @author Andrea
 */
package model.decks.powerupDeck.powerups;

import controller.enumerations.AmmoColors;

/**
 * Abstract class of Powerups, contains methods to set and get attributes, instances of his subclasses are used to create PowerupDeck
 */
public abstract class Powerup {
    private AmmoColors powerupAmmoColor;
    private String name;

    public Powerup(AmmoColors powerupAmmoColor,String name) {
      this.powerupAmmoColor=powerupAmmoColor;
      this.name=name;

    }

    public AmmoColors getAmmoColor(){

        return powerupAmmoColor;
    }
    public  String getName()  {
        return name;
    }

}





