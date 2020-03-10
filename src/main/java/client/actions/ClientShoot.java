package client.actions;

import client.messageManager.ClientInputController;
import client.ClientView;
import client.actions.shootingModes.ClientShootAtPrefixedDistance;
import client.actions.shootingModes.ClientShootPlayersYouCanSee;
import client.messageManager.FormatMessage;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientShoot {
    /**
     * Depending on which weapons player chose to shoot with, Classes of package shootingModes are called
     * @param out socket outputStream
     * @param in socket inputStream
     * @param stdin standard input
     * @param shootingMessage weapon name + interfaceName or fireMode + move tag
     */
    public static void shoot(PrintWriter out, Scanner in, Scanner stdin, String shootingMessage) {
        String weaponName="";
        String fireMode="";
        String numberOfTargets="";
        String move="";
        boolean reinsert;
        int firstComma=shootingMessage.indexOf(',');
        int secondComma=shootingMessage.indexOf(',',firstComma+1);
        int thirdComma=shootingMessage.indexOf(',',secondComma+1);

        if(firstComma!=-1) {
             weaponName=shootingMessage.substring(0,firstComma);
             fireMode = shootingMessage.substring(firstComma+1,secondComma);
             numberOfTargets = shootingMessage.substring(secondComma+1,thirdComma);
             move=shootingMessage.substring(thirdComma+1);
        }

        if(fireMode.equals("ShootVisibleOrNot")) {
            ClientShootPlayersYouCanSee.shoot(out, in, stdin,Integer.parseInt(numberOfTargets),move);
        }
        else if(fireMode.equals("ShootAtPrefixedDistance")) {
            ClientShootAtPrefixedDistance.shoot(out, in, stdin, weaponName, Integer.parseInt(numberOfTargets), move);
          
        }

        else if (shootingMessage.equals("ShootDealDmgInSquare")) {
             String line =in.nextLine();
            if(line.equals("noPlayersInSquare")) {
                System.out.println("There are no players in the square");
                ClientPlay.doAction(out,in,stdin);
            }

        }
        else if(shootingMessage.equals("ShootInVisibleRoom")) {

            do {
                reinsert=false;
                System.out.println("Insert room color: red|white|yellow|blue|green|violet");
                String room = stdin.nextLine();
                out.println(room);
                out.flush();


                String line = in.nextLine();
                if (line.equals("noPlayersInRoom")) {
                    System.out.println("There are no players in the room.");
                    ClientPlay.doAction(out, in, stdin);
                } else if (line.equals("wrongInput")) {
                    System.out.println("Wrong input.");
                    reinsert=true;
                }
                else if(line.equals("canUseWeapon")) {
                    System.out.println("Players shot!");
                    return;
                }

            } while(reinsert);

        }

        else if(shootingMessage.equals("ShootMoveToCanSeeSquare")) {

            System.out.println("Who do you want to shoot?");

            ClientView.showMessage(out, in, "viewPlayersAlive");

            inputAndCheckPlayer(out, in, stdin, "shootedPlayer");

            System.out.println("Insert square you want to move target to and shoot");

            ClientInputController.sendAndCheckMove(out, in, stdin, "moveTractorBeam");
        }

        else if(shootingMessage.equals("ShootCardinalDirection")) {
            String line = in.nextLine();
            if (line.equals("canShoot")) {
                do {
                    reinsert = true;

                    System.out.println("Choose cardinal direction:\n0)North 1) South 2) West 3) East");


                    ClientInputController.sendAndCheckOneValue(out, in, stdin, "cardinal");

                    line = in.nextLine();
                    if (line.equals("existenceOfPlayer"))
                        reinsert = false;
                    else
                        System.out.println("You can't shoot any player because they are not in direction");

                } while (reinsert);

                ClientView.showMessage(out, in, "viewPlayersAlive");

                inputAndCheckPlayer(out, in, stdin, "shootedPlayer");

            } else {
                System.out.println("You cannot shoot because there are no players in cardinal directions");
                ClientPlay.doAction(out, in, stdin);
            }

        }
    }

    public static String inputAndCheckPlayer (PrintWriter out, Scanner in, Scanner stdin, String object) {

        String inputLine = stdin.nextLine();
        String stringModified = FormatMessage.format(inputLine, object);
        out.println(stringModified);
        out.flush();

        String socketLine = in.nextLine();

        if (socketLine.equals("playerNotVisible")) {
            System.out.println("You can't see this player.");

        } else if (socketLine.equals("reinsert")) {
            System.out.println("Wrong input.");

        } else if (socketLine.equals("playerDeadOrShot")) {
            System.out.println("Player is dead or has been already shot, you cannot shoot him.");
        }
        else if (socketLine.equals("alreadyShot")) {
            System.out.println("You've already shot this player, please choose another one.");

        }
        else if (socketLine.equals("cannotSeeAnyone")) {
            System.out.println("You can't use this weapon because you cannot see anyone.");
            ClientPlay.doAction(out, in, stdin);
            return inputLine;

        } else if (socketLine.equals("noPlayersReachable")) {
            System.out.println("Weapon cannot reach player.");
        }
        else if (socketLine.equals("playerVisible")) {
            System.out.println("You can see this player. Choose a player you cannot see.");
        }
        else if (socketLine.equals("playerInSquare")) {
            System.out.println("Player in square. You cannot shoot");

        }
        else if (socketLine.equals("playerNotInDirection")) {
            System.out.println("Player is not in this direction. Please reinsert player");
        }

        else if(socketLine.equals("noPlayersInSquare")) {
            System.out.println("You can't use this weapon because there are no players in the square.");
            ClientPlay.doAction(out, in, stdin);
            return inputLine;
        }
        else if (socketLine.equals("okInput")) {
            System.out.println("Player shot!");
            return inputLine;
        }
        else if(socketLine.equals("playerAlreadyShot"))
            System.out.println("Player has been already shot, choose another one ");

        return inputAndCheckPlayer(out, in, stdin, object);
    }
}