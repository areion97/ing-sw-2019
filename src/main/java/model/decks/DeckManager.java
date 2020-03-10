package model.decks;

/**
 * Manages deck shuffling
 */
public class DeckManager {
    private int countToShuffle;
    private int deckSize;

    /**
     * Tells if deck has to be shuffled or not
     * @return true if all cards from deck have been taken and needs to be shuffled, false otherwise
     */
       public boolean canShuffle() {
        if (countToShuffle ==deckSize ) {
            countToShuffle = 0;
            return true;
        }

        return false;
    }

    public int incrementCountToShuffle() {
        return countToShuffle++;
    }


    public void setDeckSize(int deckSize) {
        this.deckSize = deckSize;
    }

}


