package client.actions;

import client.messageManager.ClientInputController;
import client.ClientView;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientPlay {
    /**
     * Turn of the player: player chooses one of these 3 actions ( 0 and 2 asks you if you want to move as well)
     * 0 Move and Grab or only grab
     * 1 Only move
     * 2 Move and Shoot or only shoot
     *
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     */
    public static void doAction(PrintWriter out, Scanner in, Scanner stdin) {
        String line;
        ClientView.showMessage(out,in,"viewSquare");

        System.out.println("What action would you like to perform?\n0)Grab ammo or weapon\n1)Move\n2)Shoot");
        int action = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "action"));
        int move;
        if (action == 0) {

            System.out.println("Do you also want to move? 0 no, 1 yes");
            move = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "move?"));
            if (move == 1) {
                ClientInputController.sendAndCheckMove(out, in, stdin, "coordinates");
            }

            line = in.nextLine();
            if (line.equals("grabAmmo")) {
                line = in.nextLine();
                if (line.equals("ammoAlreadyTaken")) {
                    System.out.println("Ammo in this square has already been taken. ");
                    doAction(out, in, stdin);

                } else if (line.equals("ammoGrabbed")) {
                    System.out.println("Ammo grabbed!\n");
                    System.out.println("Your ammos are: ");
                    ClientView.showMessage(out, in, "viewAmmo");

                    line = in.nextLine();
                    if (line.equals("showPowerups")) {
                        System.out.println("Your powerups are: ");
                        ClientView.showMessage(out, in, "viewPowerups");
                    }

                }

            }
                else if (line.equals("grabWeapon")) {

                System.out.println("Choose weapon to grab");

                ClientView.showMessage(out, in, "viewSpawnWeapons");

                ClientInputController.sendAndCheckOneValue(out, in, stdin, "chosenWeapon");

                line = in.nextLine();

                if (line.equals("weaponFull")) {
                    System.out.println("You are full of weapons, choose weapon to discard");
                    ClientView.showMessage(out, in, "viewPlayerWeapons");
                    ClientInputController.sendAndCheckOneValue(out, in, stdin, "swapWeapon");

                } else if (line.equals("cannotPayCost")) {
                    System.out.println("You can't pay weapon cost");
                    doAction(out, in, stdin);
                    return;
                }

                System.out.println("Weapon grabbed!");
                System.out.println("Your weapons are:");

                ClientView.showMessage(out, in, "viewPlayerWeapons");
                System.out.println("After paying the cost your ammos are: ");
                ClientView.showMessage(out, in, "viewAmmo");

            }

            } else if (action == 1) {
                ClientInputController.sendAndCheckMove(out, in, stdin, "coordinates");
            }
            else {
                line=in.nextLine();
                if(line.equals("allPlayersDead")) {
                    System.out.println("All players are dead you cannot shoot");
                    doAction(out, in, stdin);
                    return;
                }

                else if (line.equals("noWeapons")) {
                    System.out.println("You have no weapons to shoot.");
                    doAction(out,in,stdin);
                    return;

                }

                System.out.println("Do you also want to move? 0 no, 1 yes");
                move = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "move?"));

                if (move == 1) {

                    line = in.nextLine();

                    if (line.equals("notShot6Times")) {
                        System.out.println("You cannot move. You can move 1 square only if you've been hit >= 6 times");
                        ClientPlay.doAction(out, in, stdin);
                        return;
                    } else
                        ClientInputController.sendAndCheckMove(out, in, stdin, "coordinates");

                }
                    ClientView.showMessage(out,in,"viewPlayerWeapons");
                    ClientInputController.sendAndCheckOneValue(out, in, stdin, "shootingWeapon");


                    String loaded= in.nextLine();

                    if(loaded.equals("weaponNotLoaded")) {
                        System.out.println("Weapon is not loaded, you cannot use it.");
                        doAction(out,in,stdin);
                        return;
                    }

                    String shootingMessage = in.nextLine(); //FireMode, name of the weapon, numberOfpeopleToShoot

                    ClientShoot.shoot(out, in, stdin, shootingMessage);

            }

    }

    /**
     * Tells the player if he wishes to reaload weapon, if it's already loaded, if he can reload any weapon or if he hasn't got enough ammos to pay for the cost
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     */
    public static void reloadWeapon(PrintWriter out, Scanner in, Scanner stdin) {

        boolean reinsert;
        boolean reloadAnotherOne;

        do {
            reloadAnotherOne = true;

            String line = in.nextLine();

            if (line.equals("canReload")) {
                System.out.println("Do you want to reload any weapon? 0 no, 1 yes");

                if (Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "reloadWeapon?")) == 0)
                    return;

                do {
                    reinsert = false;

                    ClientView.showMessage(out, in, "viewPlayerWeapons");

                    ClientInputController.sendAndCheckOneValue(out, in, stdin, "weaponToReload");

                    line = in.nextLine();

                    if (line.equals("weaponAlreadyLoaded")) {
                        reinsert = true;
                        System.out.println("Weapon is already loaded");
                    } else if (line.equals("cannotPayCost")) {
                        reinsert = true;
                        System.out.println("You don't have enough ammos to reload");
                    } else {
                        System.out.println("You've reloaded the weapon!\n" );
                        System.out.println("Your ammos after reloading are: ");
                        ClientView.showMessage(out, in, "viewAmmo");
                    }

                } while (reinsert);

            } else {
                System.out.println("\nYou have no more weapons to reload!");
                reloadAnotherOne=false;
            }

        } while (reloadAnotherOne);
    }
}