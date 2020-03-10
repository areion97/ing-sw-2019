package client.messageManager;

import client.ClientView;
import client.messageManager.FormatMessage;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientInputController {
    public static String sendAndCheckOneValue(PrintWriter out, Scanner in, Scanner stdin,String object) {

            String inputLine = stdin.nextLine();
            String stringModified = FormatMessage.format(inputLine, object);
            out.println(stringModified);
            out.flush();
            String socketLine = in.nextLine();

            if (socketLine.equals("reinsertUsername")) {
                System.out.println("Username already taken. Reinsert username");

            } else if (socketLine.equals("reinsert")) {
                System.out.println("Wrong input.");
            }
            else if (socketLine.equals("squareNotExisting")) {
                System.out.println("Square does not exists.");
            }
            else if (socketLine.equals("outOfRange")) {
                System.out.println("Input out of range.");
            }
            else if (socketLine.equals("noPlayersCardinalDirection")) {
                System.out.println("There are no players in this cardinal direction");
            }
            else if(socketLine.equals("colorAlreadyTaken")) {
                System.out.println("Color already taken.");
                ClientView.showMessage(out,in,"viewColor");
            }
            else if(socketLine.equals("okInput")) {
                return inputLine;
            }
        return sendAndCheckOneValue(out,in,stdin,object);

    }

    public static boolean sendAndCheckMove(PrintWriter out, Scanner in, Scanner stdin, String object) {

            System.out.println("X:");
            String x = stdin.nextLine();
            System.out.println("Y:");
            String y = stdin.nextLine();
            String stringModified = FormatMessage.formatMoveAction(x,y,object);
            out.println(stringModified);
            out.flush();
            String socketLine = in.nextLine();

            if(socketLine.equals("squareNotExisting")) {
                System.out.println("Square does not exists.");
            }
            else if(socketLine.equals("tooFar"))
                System.out.println("You cannot move this distance.");
            else if(socketLine.equals("okMove")) {
                if(object.equals("moveTractorBeam"))
                    System.out.println("Target moved in square x: "+x+", y: "+y);
                else
                    System.out.println("Moved in square x: "+x+", y: "+y);

                return true;
            }

            else if(socketLine.equals("cannotSeeToSquare"))
                System.out.println("You cannot see square choosen, please reinsert square");

            else if(socketLine.equals("reinsert"))
                System.out.println("Square does not exists. Reinsert coordinates.");
            else
                return false;

            return sendAndCheckMove(out,in,stdin,object);
    }

}
