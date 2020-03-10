package model.maps;

import controller.enumerations.MapColors;
import model.Game;
import model.decks.ammoDeck.ammoTiles.AmmoTile;
import model.decks.weaponDeck.weapons.Weapon;

import java.util.ArrayList;

/**
 * Creates all Square attributes and methods to set and get them. In particular it draws one AmmoTile and sets it in the corresponding attribute
 */
public class Square {

    private int xPosition;
    private int yPosition;
    private MapColors roomColor;
    private AmmoTile ammo;
    private boolean playerSpawn;

    private int up;      //    Square sides:
    private int down;   //    0 --> Nothing
    private int left;    //    1 --> Door
    private int right;   //    2 --> Wall

    public Square(int xPosition, int yPosition, MapColors roomColor, boolean playerSpawn, int up, int down, int left, int right) {

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.roomColor = roomColor;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.playerSpawn = playerSpawn;

        if (playerSpawn == false) {
            ammo = Game.getAmmoDeck().drawAmmoTile();
        }

    }

    public int getX(){ return xPosition; }

    public int getY(){ return yPosition; }

    public MapColors getRoomColor(){
        return roomColor;
    }

    public AmmoTile getAmmoTile() {
        return ammo;
    }

    public boolean getPlayerSpawn() {
        return playerSpawn;
    }

    public int getUp(){
        return up;
    }

    public int getDown(){
        return down;
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public void setAmmo(AmmoTile ammo) {
        this.ammo = ammo;
    }

    public ArrayList<Weapon> getWeapons() {
        return null;
    };
}