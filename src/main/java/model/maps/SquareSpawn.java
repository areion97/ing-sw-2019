/**
 * @author Andrea
 */
package model.maps;

import controller.enumerations.MapColors;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;

import java.util.ArrayList;

/**
 * Subclass of Square, it contains max 3 Weapons that are drawn from Weapon Deck and set when Game is created
 */
public class SquareSpawn extends Square {
    private ArrayList<Weapon> weapons;

    public SquareSpawn(int xPosition, int yPosition, MapColors roomColor, boolean playerSpawn, int up, int down, int left, int right) {
        super(xPosition,yPosition,roomColor,playerSpawn,up,down,left,right);
        weapons = new ArrayList<Weapon>(3);
        weapons.add(Game.getWeaponDeck().drawWeapon());
        weapons.add(Game.getWeaponDeck().drawWeapon());
        weapons.add(Game.getWeaponDeck().drawWeapon());
    }
    @Override
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
}
