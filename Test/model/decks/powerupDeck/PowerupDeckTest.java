package model.decks.powerupDeck;

import model.Game;
import model.decks.powerupDeck.powerups.Powerup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerupDeckTest {

    @Test
    void drawPowerup() {
        Game game = new Game();
        int deckSize=Game.getPowerupDeck().getDeck().size();

        Powerup powerup =Game.getPowerupDeck().getDeck().get(0);
        assertEquals(powerup,Game.getPowerupDeck().drawPowerup());


        assertEquals(deckSize-1,Game.getPowerupDeck().getDeck().size());
        assertFalse(Game.getPowerupDeck().getDeck().contains(powerup));

    }
}