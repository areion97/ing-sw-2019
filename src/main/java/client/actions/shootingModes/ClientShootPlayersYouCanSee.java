package client.actions.shootingModes;

import client.messageManager.ClientInputController;
import client.ClientView;
import client.actions.ClientShoot;
import client.actions.ClientPlay;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientShootPlayersYouCanSee {
    /**
     * Waits answer from server to know if weapon chosen can shoot at least one player with the implemented interface ShootVisiblePlayer,
     * if yes asks to input the target and if move parameter is not "" asks if to move the target as well
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     * @param numberOfTargets number of targets that particular weapon can shoot
     * @param move move tag if the weapon can also move, "" otherwise
     */
    public static void shoot(PrintWriter out, Scanner in, Scanner stdin,int numberOfTargets,String move) {

        String canOrCannotUseWeapon = in.nextLine();

        if (canOrCannotUseWeapon.equals("cannotSeeAnyone")) {
            System.out.println("You can't use this weapon because you cannot see anyone.");
            ClientPlay.doAction(out, in, stdin);

        }
        else if (canOrCannotUseWeapon.equals("canSeeEveryone")) {
            System.out.println("You can't use this weapon because you see everyone.");
            ClientPlay.doAction(out, in, stdin);

        }
        else if(canOrCannotUseWeapon.equals("playerInSquare")) {
            System.out.println("You can't use this weapon because you can't see anyone or players are in your square.");
            ClientPlay.doAction(out, in, stdin);

        }

         else if(canOrCannotUseWeapon.equals("canUseWeapon")){
            int shootAgain = 0;

            for (int i = 0; i < numberOfTargets; i++) {

                System.out.println("Who do you want to shoot?");

                ClientView.showMessage(out,in,"viewPlayersAlive");

                ClientShoot.inputAndCheckPlayer(out, in, stdin, "shootedPlayer");

                if (numberOfTargets > 1&&i!=numberOfTargets-1){
                    String canSeePlayersNotShooted = in.nextLine();
                    if (canSeePlayersNotShooted.equals("noPlayersRemainedToShoot")) {
                        System.out.println("You cannot shoot any more players, because you can't see anyone after shooting this player.");
                        return;
                    }
                }


                if (numberOfTargets > 1 && i != numberOfTargets - 1) {
                    System.out.println("Do you want to shoot another target? 0 no, 1 yes");
                    shootAgain = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "shootAgain"));
                    if (shootAgain == 0)
                        return;
                }

                if (i == numberOfTargets - 1) {
                if (move.equals("AskAndMoveTarget")) {
                        ClientMoveAfterShooting.move(out, in, stdin, move);
                        return;
                    }
                }

            }

        }

    }

}
