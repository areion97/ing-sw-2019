package controller.statePattern;

import controller.actionController.GrabWeaponController;
import controller.modelView.ModelViewAmmo;
import controller.modelView.ModelViewPlayerPowerups;
import controller.modelView.ModelViewSpawnWeapons;
import controller.modelView.Notify;
import model.Game;
import model.decks.ammoDeck.ammoTiles.AmmoTile;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public class Grab implements PlayerState {


    @Override
    public void nextAction(Scanner in, PrintWriter out, Player player) {

        if (player.getSquare().getPlayerSpawn() == false) {

            AmmoTile ammo=player.grabAmmo();
            if (ammo.getPowerup() && player.getPowerups().size() < 3) {
                player.getPowerups().add(Game.getPowerupDeck().drawPowerup());
            }
            Game.getAmmoDeck().discardAmmoTile(ammo);

            ModelViewAmmo.showPlayerAmmo(in,out,player);

            Notify.notifyAll(player,"NotifyAmmoGrabbed",null);

            if(player.getSquare().getAmmoTile().getPowerup()) {
                out.println("showPowerups");
                out.flush();
                ModelViewPlayerPowerups.showPlayerPowerups(in, out, player);
            }
            else {
                out.println("doNotShow");
                out.flush();
            }

            player.getSquare().setAmmo(null);

            return;

        } else

            ModelViewSpawnWeapons.showSpawnWeapons(in, player, out);

            GrabWeaponController.controlWeaponGrab(out,in,player,"chosenWeapon");

    }

}


