package client.actions.shootingModes;

import client.messageManager.ClientInputController;
import client.ClientView;
import client.actions.ClientShoot;
import client.actions.ClientPlay;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientShootAtPrefixedDistance {
    /**
     * Waits answer from server to know if weapon chosen can shoot at least one player with the implemented interface ShootAtPrefixedDistance,
     *  if yes asks to input the target and if move parameter is not "" asks if to move the target as well
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     * @param weaponName name of the weapon chosen to shoot
     * @param numberOfTargets number of targets that particular weapon can shoot
     * @param move move tag if the weapon can also move, "" otherwise
     */
    public static void shoot(PrintWriter out, Scanner in, Scanner stdin, String weaponName, int numberOfTargets, String move) {
        String canOrCannotUseWeapon = in.nextLine();

        if (canOrCannotUseWeapon.equals("cannotSeeAnyone")) {
            System.out.println("You can't use this weapon because you cannot see anyone.");
            ClientPlay.doAction(out, in, stdin);

        } else if (canOrCannotUseWeapon.equals("noPlayersReachable")) {
            System.out.println("You can't use this weapon because you cannot reach any player.");
            ClientPlay.doAction(out, in, stdin);

        } else if (canOrCannotUseWeapon.equals("canUseWeapon")) {
            int shootAgain = 0;

            boolean reinsert;

            for (int i = 0; i < numberOfTargets; i++) {

                if (i != 0 && weaponName.equals("Shockwave")) {
                    String keepOnShooting = in.nextLine();
                    if (keepOnShooting.equals("noPlayersRemainedToShoot")) {
                        System.out.println("You cannot shoot more targets");
                        return;
                    }
                }

                do {
                    System.out.println("Who do you want to shoot?");
                    reinsert = false;
                    ClientView.showMessage(out, in, "viewPlayersAlive");
                    ClientShoot.inputAndCheckPlayer(out, in, stdin, "shootedPlayer");

                    if (weaponName.equals("Shockwave")) {
                        String canShootShockwave = in.nextLine();
                        if (canShootShockwave.equals("sameSquare")) {
                            System.out.println("Players shot must be in different squares");
                            reinsert = true;


                        } else
                            System.out.println("You shot!");
                    }


                } while (reinsert);


                if (numberOfTargets > 1 && i != numberOfTargets - 1) {
                    System.out.println("Do you want to shoot another target? 0 no, 1 yes");
                    shootAgain = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "shootAgain?"));
                    if (shootAgain == 0) {
                        return;
                    }
                }

                if (i == numberOfTargets - 1) {
                    if (move.equals("AskAndMoveTarget"))
                        ClientMoveAfterShooting.move(out, in, stdin, move);
                    else if (move.equals("MoveShootingPlayer")) {
                        in.nextLine();
                        System.out.println("You moved to shot player's square");
                    }
                }
            }
        }
    }
}