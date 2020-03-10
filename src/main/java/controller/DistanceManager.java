package controller;

import model.Game;
import model.maps.Square;
import model.players.Player;

import java.util.ArrayList;

public class DistanceManager {



    public static boolean reachInSteps(Player shootingPlayer, int fromStep, int toStep, ArrayList<Player> players) {

        int i = 0;
        if (fromStep == toStep) {
            for (Player p : players) {

                if (!p.equals(shootingPlayer)) {

                    if (reachableInSteps(shootingPlayer.getSquare(), p.getSquare(), fromStep)) {
                        return true;
                    }
                }
            }
            return false;
        }

        else {

            for (Player p1 : players) {
                if (!p1.equals(shootingPlayer)) {
                    if (reachableInLessSteps(shootingPlayer.getSquare(), p1.getSquare(), 0, toStep-1)) {

                        i++;
                    }
                }

            }


            if (i == players.size() ) {
                return false;

            } else {
                return true;
            }
        }

    }

        public static boolean reachInExactSteps (Player shootingPlayer,int steps, ArrayList<Player > players){
            return reachInSteps(shootingPlayer, steps, steps, players);
        }

        public static boolean reachInAtLeastSteps (Player shootingPlayer,int steps, ArrayList<Player > players){
            return reachInSteps(shootingPlayer, 0, steps, players);
        }



    /**
     * Checks if a square can reach another in (@param steps) steps
     * @param s1 start Square
     * @param s2 destination Square
     * @param steps number of steps
     * @return true if s1 can reach s2 in @param steps
     * */
    public static boolean reachableInSteps (Square s1, Square s2, int steps) {

        boolean availableDistance = false;
        if (steps == 0) {
            if (s1.getX() == s2.getX() && s1.getY() == s2.getY()) {
                return true;
            }
            return false;
        }
        if (s1.getUp() == 1 || s1.getUp() == 0) {
            availableDistance = reachableInSteps(Game.getMap().getMapGrid()[s1.getX() - 1][s1.getY()], s2, steps - 1);
        }
        if (availableDistance == true) {
            return availableDistance;
        }
        if (s1.getDown() == 1 || s1.getDown() == 0) {
            availableDistance = reachableInSteps(Game.getMap().getMapGrid()[s1.getX() + 1][s1.getY()], s2, steps - 1);
        }
        if (availableDistance == true) {
            return availableDistance;
        }
        if (s1.getLeft() == 1 || s1.getLeft() == 0) {
            availableDistance = reachableInSteps(Game.getMap().getMapGrid()[s1.getX()][s1.getY() - 1], s2, steps - 1);
        }
        if (availableDistance == true) {
            return availableDistance;
        }
        if (s1.getRight() == 1 || s1.getRight() == 0) {
            availableDistance = reachableInSteps(Game.getMap().getMapGrid()[s1.getX()][s1.getY() + 1], s2, steps - 1);
        }
        return availableDistance;
    }

    /**
     * Checks if a square can reach another square in a number of steps less or equal than @param steps
     * @param s1 start square
     * @param s2 destination square
     * @return returns true if s1 can reach s2 in a number of steps less or equal than @param steps
     * */
    public static boolean reachableInLessSteps(Square s1, Square s2, int initialStep,int finalStep) {
        boolean canReach;
        for(int i=initialStep;i<=finalStep;i++) {
            canReach=reachableInSteps(s1, s2, i);
            if(canReach==true)
                return canReach;
        }
        return false;
    }



    public static boolean canSee2(Square s1, Square s2){
        Square map [][]= Game.getMap().getMapGrid();
        if(s1.getRoomColor()==s2.getRoomColor()){ return true; }
        if((s1.getUp()==1)&&(s2.getRoomColor()==map[s1.getX()-1][s1.getY()].getRoomColor())){ return true; }
        if((s1.getDown()==1)&&(s2.getRoomColor()==map[s1.getX()+1][s1.getY()].getRoomColor())){ return true; }
        if((s1.getLeft()==1)&&(s2.getRoomColor()==map[s1.getX()][s1.getY()-1].getRoomColor())){ return true; }
        if((s1.getRight()==1)&&(s2.getRoomColor()==map[s1.getX()][s1.getY()+1].getRoomColor())){ return true; }

        return false;
    }
    public static boolean canSee1(Player player, Player playerSeen){
        Square s1 = player.getSquare();
        Square s2=playerSeen.getSquare();
        Square[][] mapGrid=Game.getMap().getMapGrid();
        if(s1.getRoomColor()==s2.getRoomColor()){ return true; }
        if((s1.getUp()==1)&&(s2.getRoomColor()==mapGrid[s1.getX()-1][s1.getY()].getRoomColor())){ return true; }
        if((s1.getDown()==1)&&(s2.getRoomColor()==mapGrid[s1.getX()+1][s1.getY()].getRoomColor())){ return true; }
        if((s1.getLeft()==1)&&(s2.getRoomColor()==mapGrid[s1.getX()][s1.getY()-1].getRoomColor())){ return true; }
        if((s1.getRight()==1)&&(s2.getRoomColor()==mapGrid[s1.getX()][s1.getY()+1].getRoomColor())){ return true; }

        return false;
    }

}