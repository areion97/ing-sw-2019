/**
 * @author Andrea
 */
package model.players;

import controller.modelView.Notify;
import model.Game;

import java.util.ArrayList;

/**
 * Creates all Player Board Model elements and methods to set and get them
 */
public class PlayerBoard {
    private int [] killRewardPoints= {8,6,4,2,1};


    private ArrayList <Player> damageTaken;
    private ArrayList <Player> marks;

    public PlayerBoard() {

        damageTaken=new ArrayList<Player>();
        marks =  new ArrayList<Player>();
    }

    public ArrayList<Player> getMarks() {
        return marks;
    }

    public ArrayList <Player> getDamageTaken() {
        return damageTaken;
    }

    public int [] getRewardPoints() {
        return killRewardPoints;
    }

    public void diminishRewards(int index) {
            killRewardPoints[index]=0;
    }

}