package serverSocket;

import controller.ServerInputController;
import controller.TurnController;
import controller.modelView.ModelViewMap;
import controller.modelView.Notify;
import controller.powerupController.PowerupController;
import model.Game;
import model.GameManager;
import model.decks.powerupDeck.powerups.Powerup;
import model.decks.powerupDeck.powerups.Teleporter;
import model.players.Player;
import controller.statePattern.LoggedOut;
import controller.statePattern.Playing;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Runs thread of player connected,initializes output and input stream and manages player turn
 */
public class EchoServerClientHandler implements Runnable {
    private Socket socket;


    public EchoServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Waits all players to log in, then sets first player and when all are connected player turn starts.
     * Meanwhile it's send message "waiting" to the clients that are not playing
     */
    public void run() {

        try {
            Scanner in = null;
            in = new Scanner(socket.getInputStream());
            PrintWriter out = null;
            out = new PrintWriter(socket.getOutputStream());
            int playerId=Game.addPlayer();
            Player player= Game.getPlayerArrayList().get(playerId);

            GameManager.addOut(playerId,out);
            GameManager.addIn(playerId,in);

            player.setState(new LoggedOut());
            player.nextAction(in,out,player);

            Game.setCurrentPlayer(Game.getPlayerArrayList().get(0));

            boolean reinsert=true;

         do {

             if (player.equals(Game.getCurrentPlayer())) {

                 System.out.println("Current player is " + Game.getCurrentPlayer().getPlayerID());
                 out.println("canPlay");
                 out.flush();

                 Notify.notifyAll(player,"NotifyUserPlaying",null);
                 ModelViewMap.showMap(in,out);

                 PowerupController.checkAndUseTeleporter(out,in,player);
                 PowerupController.checkAndUseNewton(out,in,player);

                 player.setState(new Playing());
                 player.nextAction(in, out, player); // first action

                 player.setState(new Playing());
                 player.nextAction(in, out, player); // second action

                 TurnController.reloadWeapon(out,in,player);

                 PowerupController.checkAndUseTeleporter(out,in,player);
                 PowerupController.checkAndUseNewton(out,in,player);

                 Notify.notifyAll(player,"NotifyUpdateDamageAndMarks",null);

                 TurnController.replaceAmmoAndWeapons();

                 TurnController.checkDeadPlayers(in,out);

                 if(Game.getSkullsNumber()==0) {
                     out.println("ranking");
                     out.flush();
                     Player winningPlayer= Notify.sendRank(out);//sends ranking current player
                     Notify.notifyAll(player,"NotifyRanking",null);  //sends ranking to all

                     Notify.notifyAll(player,"endGame",null);

                     Notify.notifyAll(player,"NotifyWinningPlayer",null);
                     Notify.sendWinningPlayer(winningPlayer,out);
                     return;
                 }

                 out.println("gameNotFinished");
                 out.flush();

                 TurnController.pass(player.getPlayerID());

                 out.println("waiting");
                 out.flush();

             }

             else if(!player.equals(Game.getCurrentPlayer())&&player.getAlive()) {

                 Thread.sleep(1000);
                 out.println("waiting");
                 out.flush();

             }
             else if(!player.getAlive()) {
                 Thread.sleep(1000);
             }

         } while(reinsert);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}