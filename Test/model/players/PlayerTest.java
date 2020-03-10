package model.players;

import model.Game;
import model.decks.ammoDeck.ammoTiles.AmmoTile;
import model.players.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void grabAmmo() throws IOException {
        Game g= new Game();
        Game.setMap(2);
        Player p = new Player();
        AmmoTile ammo= new AmmoTile(1,1,1,false);
        p.setAmmos(1,1,1);
        p.setSquareXY(1,1);
        p.getSquare().setAmmo(ammo);
        assertEquals(p.getSquare().getAmmoTile(),p.grabAmmo());

        assertEquals(2,p.getAmmoRYB()[0]);
        assertEquals(2,p.getAmmoRYB()[1]);
        assertEquals(2,p.getAmmoRYB()[2]);

        assertEquals(p.getSquare().getAmmoTile(),p.grabAmmo());
        assertEquals(3,p.getAmmoRYB()[0]);
        assertEquals(3,p.getAmmoRYB()[1]);
        assertEquals(3,p.getAmmoRYB()[2]);

    }





}