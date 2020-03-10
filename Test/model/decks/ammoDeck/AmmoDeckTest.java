package model.decks.ammoDeck;

import model.Game;
import model.decks.ammoDeck.ammoTiles.AmmoTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmmoDeckTest {

    @Test
    void drawAmmoTile() {
        Game game = new Game();
        int deckSize=Game.getAmmoDeck().getDeck().size();
        AmmoTile ammo1 =Game.getAmmoDeck().getDeck().get(0);
        assertEquals(ammo1,Game.getAmmoDeck().drawAmmoTile());

        assertEquals(deckSize-1,Game.getAmmoDeck().getDeck().size());
        assertFalse(Game.getAmmoDeck().getDeck().contains(ammo1));

    }
}