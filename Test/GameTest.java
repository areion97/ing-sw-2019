import model.Game;
import model.players.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void addPlayer() throws IOException {
        Game game = new Game();
        int id=0;
        int playersConnected=0;
        int playerArraySize=Game.getPlayerArrayList().size();
        assertEquals(id,Game.addPlayer());

        Player p=Game.getPlayerArrayList().get(id);

        assertEquals(playerArraySize+1,Game.getPlayerArrayList().size());
        assertTrue(Game.getPlayersAlive().contains(p));
        assertEquals(Game.getPlayerArrayList().size(),Game.getPlayersConnected());
        assertEquals(id,Game.getPlayerArrayList().get(id).getPlayerID());
    }

    @Test
    void addVote(){
        Game game = new Game();
        int map = 1;
        int previousVotes = 0;
        int incrementedVote=previousVotes+1;
        Game.addVote(map);
        incrementedVote++;
        assertEquals(incrementedVote,Game.getMapVotes().get(map-1));

        Game.addVote(map);
        assertEquals(incrementedVote,Game.getMapVotes().get(map-1));

    }

}