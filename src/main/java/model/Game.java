/**
 * @author Andrea
 */
package model;

import controller.enumerations.PlayerColors;
import model.decks.ammoDeck.AmmoDeck;
import model.maps.Map;
import model.players.Player;
import model.decks.powerupDeck.PowerupDeck;
import model.decks.weaponDeck.WeaponDeck;
import java.io.IOException;
import java.util.*;

public class Game {

    private static ArrayList<Player> playerArrayList = new ArrayList<>();
    private static ArrayList<Player> playersAlive = new ArrayList<>();
    private static Map map = null;
    private static PowerupDeck powerupDeck = new PowerupDeck();
    private static WeaponDeck weaponDeck = new WeaponDeck();
    private static AmmoDeck ammoDeck = new AmmoDeck();
    private static Player currentPlayer=null;
    private static ArrayList<PlayerColors> colorsAvailable = new ArrayList<>();
    private static int playersConnected = 0;
    private static HashMap<Integer, Integer> mapVotes = new HashMap<>();
    private static ArrayList<Player> killShots = new ArrayList<>();
    private static int skullsNumber = 8;


    public Game() {
        for (int i = 0; i < PlayerColors.values().length - 1; i++)
            colorsAvailable.add(PlayerColors.values()[i]);

        for (int j = 0; j < 4; j++)
            mapVotes.put(j, 0);

    }

    public static void setMap(int mapNumber) throws IOException {
        map=new Map(mapNumber);
    }

    public static void decreaseSkullsNumber() {
        skullsNumber--;
    }

    public static ArrayList<Player> getKillShots() {
        return killShots;
    }

    public static int getSkullsNumber() {
        return skullsNumber;
    }

    public static void addKillShot(Player p) {
        killShots.add(p);
    }

    public static ArrayList<Player> getPlayersAlive() {
        return playersAlive;
    }

    public static void setPlayerArrayList(ArrayList<Player> playerArrayList) {
        Game.playerArrayList = playerArrayList;
    }

    public static ArrayList<PlayerColors> getColorsAvailable() {
        return colorsAvailable;
    }

    public static ArrayList<Player> getPlayerArrayList() {
        return playerArrayList;
    }

    public static int getPlayersConnected() {
        return playersConnected;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Map getMap() {
        return map;
    }

    public static PowerupDeck getPowerupDeck() {
        return powerupDeck;
    }

    public static AmmoDeck getAmmoDeck() {
        return ammoDeck;
    }

    public static WeaponDeck getWeaponDeck() {
        return weaponDeck;
    }


    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    public static int addPlayer() {
        Player player = new Player();
        playerArrayList.add(player);
        playersAlive.add(player);
        playersConnected++;
        player.setPlayerID(playersConnected - 1);
        return playersConnected - 1;
    }

    public static HashMap<Integer, Integer> getMapVotes() {
        return mapVotes;
    }


    public static void addVote(int map) {
        int previousVotes = mapVotes.get(map - 1);
        mapVotes.replace(map - 1, previousVotes+1);
    }

}