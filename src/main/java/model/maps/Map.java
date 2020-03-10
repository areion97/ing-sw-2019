package model.maps;

import controller.enumerations.AmmoColors;
import controller.enumerations.MapColors;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;

import java.io.*;

/**
 * The map of the game, it is set when all players vote for map. His attributes are set from file.
 */
public class Map {

    private Square[][] mapGrid;

    public Map(int mapNumber) throws IOException {
        mapGrid = new Square[3][4];

        FileReader fileMaps;
        String line;
        boolean playerSpawn;
        MapColors mapColor;
        int indexFirstSpace;
        int i;
        int j;
        int a=0;

         fileMaps = new FileReader(System.getProperty("user.home") + File.separatorChar + "map"+String.valueOf(mapNumber)+".txt");

         BufferedReader readMaps = new BufferedReader(fileMaps);
         readMaps.readLine();

        for (i = 0; i < 3; i++) {

            for (j = 0; j < 4; j++) {

                line = readMaps.readLine();

                 if (!(line.equals("null"))) {

                     indexFirstSpace=line.indexOf(" ");
                     mapColor= MapColors.valueOf(line.substring(0,indexFirstSpace));
                    if (line.charAt(indexFirstSpace+1) == '1') {
                        playerSpawn = true;
                        mapGrid[i][j] = new SquareSpawn(i, j, mapColor, playerSpawn, Character.getNumericValue(line.charAt(indexFirstSpace+3)), Character.getNumericValue(line.charAt(indexFirstSpace+5)), Character.getNumericValue(line.charAt(indexFirstSpace+7)), Character.getNumericValue(line.charAt(indexFirstSpace+9)));

                    } else {
                        playerSpawn = false;
                       mapGrid[i][j] = new Square (i, j, mapColor, playerSpawn, Character.getNumericValue(line.charAt(indexFirstSpace+3)), Character.getNumericValue(line.charAt(indexFirstSpace+5)), Character.getNumericValue(line.charAt(indexFirstSpace+7)), Character.getNumericValue(line.charAt(indexFirstSpace+9)));
                    }
                }

            }
        }
        readMaps.close();
    }

    /**
     * Gets square of coordinates x and y
     * @param x coordinate
     * @param y coordinate
     * @return square (x,y) if it exists, otherwise, null
     */
    public Square getSquare(int x, int y) {

        try {
            return mapGrid[x][y];
        } catch (Exception e) {
            return null;
        }
    }

    public Square[][] getMapGrid() {
        return mapGrid;
    }

    /**
     * Returns the Square Spawn corresponding to the color passed as parameter
     * @param color color of square spawn
     * @return square spawn of a certain color
     */
    public Square getSquareSpawn(AmmoColors color) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Square s = mapGrid[i][j];

                    if (s!= null && s.getPlayerSpawn() == true && s.getRoomColor().toString().equals(color.toString())) {
                        return s;
                    }
                }
        }
        return null;
    }

}
