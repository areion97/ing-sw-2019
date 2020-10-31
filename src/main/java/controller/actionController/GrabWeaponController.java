/**
 * @author Andrea
 */

package controller.actionController;
import controller.ServerInputController;
import controller.modelView.ModelViewAmmo;
import controller.modelView.ModelViewPlayerWeapons;

import controller.modelView.Notify;
import controller.statePattern.Move;
import controller.statePattern.Playing;
import model.players.Player;
import model.decks.weaponDeck.weapons.Weapon;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages grab ammo or weapon actions
 */
public class GrabWeaponController {
    /**
     * This function asks the player to input what weapon he would like to grab and if he is full (max 3 weapons),
     * he is asked to drop a weapon of his.
     * **/
    public static void controlWeaponGrab(PrintWriter out, Scanner in, Player player, String object) {
        boolean reinsert=false;
        Weapon weaponDiscarded = null;
        Weapon weaponGrabbed = null;
        int discardedWeaponIndex;
        int weaponIndex;
        do {
            weaponIndex = ServerInputController.inputAndCheckInteger(0,player.getSquare().getWeapons().size()-1,out,in,player,"chosenWeapon");
            if (player.getServerMessage().getObject().equals(object)) {

                weaponGrabbed = player.getSquare().getWeapons().get(weaponIndex);

                int red=player.getAmmoRYB()[0];
                int yellow=player.getAmmoRYB()[1];
                int blue=player.getAmmoRYB()[2];


                for (int i=0;i<weaponGrabbed.getAdditionalCost().size();i++) {
                    if(weaponGrabbed.getAdditionalCost().get(i).toString().equals("RED"))
                        red --;
                    else if(weaponGrabbed.getAdditionalCost().get(i).toString().equals("YELLOW"))
                        yellow--;
                    else
                        blue--;
                }


                if(red<0||yellow<0||blue<0) {

                    out.println("cannotPayCost");
                    out.flush();
                    player.setState(new Playing());
                    player.nextAction(in,out,player);
                    return;
                }

                else {
                    player.setAmmos(red,yellow,blue);
                }

                player.getSquare().getWeapons().remove(weaponIndex);

                if (player.getWeapons().size() == 3) {
                    out.println("weaponFull");
                    out.flush();

                    ModelViewPlayerWeapons.showPlayerWeapons(player,out,in);

                    discardedWeaponIndex =ServerInputController.inputAndCheckInteger(0,player.getWeapons().size(),out,in,player,"swapWeapon");

                    weaponDiscarded = player.getWeapons().get(discardedWeaponIndex);
                    player.getWeapons().remove(discardedWeaponIndex);
                    player.getSquare().getWeapons().add(weaponDiscarded);
                    weaponDiscarded.setLoaded(true);

                }

                player.getWeapons().add(weaponGrabbed);
                reinsert=false;


                out.println("weaponGrabbed");
                out.flush();

                ModelViewPlayerWeapons.showPlayerWeapons(player,out,in);
                ModelViewAmmo.showPlayerAmmo(in,out,player);

                Notify.notifyAll(player,"NotifyWeaponGrabbed",null);



            }
            else
                reinsert=true;

        } while (reinsert);
    }
}
