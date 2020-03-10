/**
 * @author Andrea
 */
package model.players;

import controller.enumerations.AmmoColors;
import controller.enumerations.PlayerColors;
import model.Game;
import model.decks.ammoDeck.ammoTiles.AmmoTile;
import model.maps.Square;
import model.decks.powerupDeck.powerups.Powerup;
import model.decks.weaponDeck.weapons.Weapon;
import serverSocket.messageManager.ServerMessageManager;
import controller.statePattern.LoggedOut;
import controller.statePattern.PlayerState;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates all Player Model elements and methods to set and get them
 */
public class Player {
    private int playerID;
    private PlayerColors color;
    private String username;
    private int score;
    private boolean alive;
    private PlayerBoard playerBoard;
    private int kills;
    private int overkills;
    private int deaths;
    private int[] ammoRYB = new int[3];
    private int marksGiven;
    private Square square;
    private ArrayList<Powerup> powerups;
    private ArrayList<Weapon> weapons;
    private PlayerState currentState;
    private ServerMessageManager serverMessage;
    private AmmoColors spawningColor;

    public Player() {
        powerups = new ArrayList<Powerup>(3);
        this.currentState = new LoggedOut();
        serverMessage = new ServerMessageManager();
        weapons=new ArrayList<Weapon>(3);
        playerBoard=new PlayerBoard();
        alive=true;
        username="";
        ammoRYB[0]=3;
        ammoRYB[1]=3;
        ammoRYB[2]=3;
        marksGiven=0;
        score=0;
        color=null;

    }

    public void setSpawningColor(AmmoColors spawningColor) {
        this.spawningColor = spawningColor;
    }

    public AmmoColors getSpawningColor() {
        return spawningColor;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setColor(PlayerColors color) {
        this.color = color;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ServerMessageManager getServerMessage() {
        return serverMessage;
    }

    public PlayerColors getColor() {
        return color;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int[] getPlayerAmmo() {
        return ammoRYB;
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Powerup> getPowerups() {
        return powerups;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void setSquareXY(int x, int y) {
        this.square = Game.getMap().getSquare(x, y);
    }

    public void setSquare(Square s) {
        this.square = s;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PlayerBoard getBoard() {
        return playerBoard;
    }

    public Square getSquare() {
        return square;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    /**
     * Grabs ammos specified on the AmmoTile taken on the standing square
     * @return returns AmmoTile grabbed from the Square player is in
     */
    public AmmoTile grabAmmo() {
        AmmoTile ammo;
        ammo = this.getSquare().getAmmoTile();

        for (int i = 0; i < 3; i++) {
            if (ammoRYB[i] + ammo.getAmmoRYB()[i] >= 3)
                ammoRYB[i] = 3;
            else
                ammoRYB[i] += ammo.getAmmoRYB()[i];
        }

      return ammo;

    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setAmmos(int red,int yellow,int blue) {
    ammoRYB[0]=red;
    ammoRYB[1]=yellow;
    ammoRYB[2]=blue;
}

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * This function is used to check if a player has been shot more or equals than n times
     * @param n times shot
     * @return true if player is shot more than n times, otherwise false
     */
    public boolean shotNtimes(int n) {
        if (playerBoard.getDamageTaken().size() >= n)
            return true;
        else
            return false;
}

    public void move(int x, int y) {
        square = Game.getMap().getSquare(x, y);
    }

    public int[] getAmmoRYB() {
        return ammoRYB;
    }


    public void setState(PlayerState state) {
        currentState = state;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }


    public void nextAction(Scanner in, PrintWriter out,Player player) {
        this.currentState.nextAction(in, out, this);
    }

    public int getMarksGiven() {
        return marksGiven;
    }

    public void setMarksGiven(int marksGiven) {
        this.marksGiven = marksGiven;
    }
}