package model.decks.weaponDeck;

import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponDeckTest {

    @Test
    void drawWeapon() {
        Game game = new Game();
        int deckSize=Game.getWeaponDeck().getDeck().size();
        Weapon weapon =Game.getWeaponDeck().getDeck().get(0);
        assertEquals(weapon,Game.getWeaponDeck().drawWeapon());

        assertEquals(deckSize-1,Game.getWeaponDeck().getDeck().size());
        assertFalse(Game.getWeaponDeck().getDeck().contains(weapon));
    }
}