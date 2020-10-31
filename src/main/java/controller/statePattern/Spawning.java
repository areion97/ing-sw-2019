package controller.statePattern;

import controller.ServerInputController;
import controller.modelView.ModelViewPlayerPowerups;
import controller.modelView.ModelViewSquare;
import controller.modelView.ModelViewTwoPowerups;
import model.Game;
import model.decks.powerupDeck.powerups.Powerup;
import model.players.Player;
import controller.statePattern.PlayerState;

import java.io.PrintWriter;
import java.util.Scanner;

public class Spawning implements PlayerState {
    @Override
    public void nextAction(Scanner in, PrintWriter out, Player player) {
        Powerup powerup1 = Game.getPowerupDeck().drawPowerup();
        Powerup powerup2 = Game.getPowerupDeck().drawPowerup();

        ModelViewTwoPowerups.showPowerups(in, out, powerup1, powerup2);

        int input = ServerInputController.inputAndCheckInteger(0, 1, out, in, player, "chooseSpawningPowerup");

        if(player.getPowerups().size()==3) {
            out.println("fullOfPowerups");
            out.flush();

            ModelViewPlayerPowerups.showPlayerPowerups(in,out,player);
            int powerupIndex= ServerInputController.inputAndCheckInteger(0,2,out,in,player,"discardPowerup");

            Game.getPowerupDeck().discardPowerup(player.getPowerups().get(powerupIndex));
            player.getPowerups().remove(powerupIndex);
        }

        else if(!player.getAlive()) {
            out.println("canTakePowerup");
            out.flush();
        }

        if (input == 0) {
            player.getPowerups().add(powerup1);
            Game.getPowerupDeck().discardPowerup(powerup2);
            player.setSpawningColor(powerup1.getAmmoColor());

        } else if (input == 1) {
            player.getPowerups().add(powerup2);
            Game.getPowerupDeck().discardPowerup(powerup1);
            player.setSpawningColor(powerup2.getAmmoColor());
        }

    }
}
