package client.actions.shootingModes;

import client.messageManager.ClientInputController;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientMoveAfterShooting {
    /**
     * Asks current player if he wishes to move after shooting and then asks to insert square
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     * @param move move tag
     */
    public static void move(PrintWriter out, Scanner in, Scanner stdin,String move) {
        if (move.equals("AskAndMoveTarget")) {
            System.out.println("Do you want to move the shooted player? 0 no, 1 yes");

            int moveTarget = Integer.parseInt(ClientInputController.sendAndCheckOneValue(out, in, stdin, "moveTarget?"));

            if (moveTarget == 0) {
                return;
            }
        }

        if (!move.equals("")) {
            System.out.println("Where do you want to move it?");
            ClientInputController.sendAndCheckMove(out, in, stdin, "coordinates");
            System.out.println("Player moved!");
        }
    }
}
