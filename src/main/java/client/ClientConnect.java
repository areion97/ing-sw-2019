package client;

import client.actions.ClientPlay;
import client.messageManager.ClientInputController;
import controller.ServerInputController;
import model.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * Manages players connections and players' turn. Player can perform 2 actions and before or after player can use Powerups Teleport or Newton.
 * Prints players' board and ranking at the end of the turn
 * Tells the winner
 * Manages messages of who are still waiting for their turn
 */
public class ClientConnect {
    private String ip;
    private int port;


    public ClientConnect(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
//192.168.43.25
    public static void main(String[] args) {
        ClientConnect client = new ClientConnect("192.168.43.25", 8079);
        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void startClient() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out= new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {

                String line;
                System.out.println("Waiting players to connect...");

                line=in.nextLine(); //all connected

                System.out.println("Insert username:");
                ClientInputController.sendAndCheckOneValue(out,in,stdin,"username");
                System.out.println("Vote map: (1-4)");
                ClientInputController.sendAndCheckOneValue(out,in,stdin,"map");
                System.out.println("Choose your character color");
                ClientView.showMessage(out,in,"viewColor");
                ClientInputController.sendAndCheckOneValue(out,in,stdin,"color");

                System.out.println("Choose a powerup card, its color will be your spawn point");
                ClientView.showMessage(out,in,"viewTwoPowerups");

                ClientInputController.sendAndCheckOneValue(out,in,stdin,"chooseSpawningPowerup");

                System.out.println("Waiting other players to vote map...");
                ClientView.showMessage(out,in,"viewSpawnSquare");
                System.out.println("Your powerups are: ");
                ClientView.showMessage(out,in,"viewPowerups");
                System.out.println();
                System.out.println("Waiting players to spawn...");
                System.out.println();

                boolean reinsert;

            do {
                reinsert=true;
                line = in.nextLine();


                if (line.equals("canPlay")) {

                    System.out.println("Map:\n----------------------------------------------------------------------- ");
                    ClientView.showMessage(out,in,"viewMap");
                    System.out.println("--------------------------------------------------------------------------");
                    checkAndUseTeleporter(out,in,stdin);
                    checkAndUseNewton(out,in,stdin);

                    ClientPlay.doAction(out, in, stdin);

                    System.out.println("--------------------------------------------------------------------------");


                    ClientPlay.doAction(out, in, stdin);



                    ClientPlay.reloadWeapon(out,in,stdin);
                    System.out.println();

                    checkAndUseTeleporter(out,in,stdin);
                    checkAndUseNewton(out,in,stdin);

                    line=in.nextLine();
                    if(line.equals("deadPlayer")) {
                        System.out.print ("You killed ");
                        ClientView.getNotified(in); //You've killed..
                        System.out.println("Waiting player to respawn...");

                        ClientView.getNotified(in); //player boards
                        System.out.println("Score: ");
                        ClientView.getNotified(in); //score
                    }
                    else {
                        System.out.println("Player boards: ");
                        System.out.println("--------------------------------------------------------------------------");
                        ClientView.getNotified(in);  //Send player boards
                        System.out.println("--------------------------------------------------------------------------");

                        System.out.println("You passed turn.");
                    }


                    line=in.nextLine();


                    if(line.equals("ranking")) {
                        System.out.println("Final ranking is: ");
                        ClientView.getNotified(in);
                        System.out.println("The winner is: ");
                        ClientView.getNotified(in);
                        return;
                    }


                } else if (line.equals("endGame")) {
                    System.out.println("The winner is : ");
                    line=in.nextLine();
                    ClientView.getNotified(in);
                    reinsert=false;

                }
                else if (line.equals("waiting")) {
                    Thread.sleep(1000);
                    reinsert = true;

                }


                else if(line.equals("respawnDeadPlayer")) {

                    System.out.println("Choose a powerup card, its color will be your spawn point");
                    ClientView.showMessage(out,in,"viewTwoPowerups");

                    ClientInputController.sendAndCheckOneValue(out,in,stdin,"chooseSpawningPowerup");
                    line=in.nextLine();
                    if(line.equals("fullOfPowerups")) {
                        System.out.println("You are full. Choose one powerup to discard");
                        ClientView.showMessage(out,in,"viewPowerups");
                        ClientInputController.sendAndCheckOneValue(out,in,stdin,"discardPowerup");
                    }

                    ClientView.showMessage(out,in,"viewSpawnSquare");
                    System.out.println("Your powerups are: ");
                    ClientView.showMessage(out,in,"viewPowerups");


                }

           /*     else if (line.equals("youHaveTagbackGrenade")) {
                    System.out.println("You have TagbackGrenade, do you want to use it to give one mark to the player who shot you? 0 no, 1 yes");

                    int useTagBack=Integer.parseInt(ClientInputController.sendAndCheckOneValue(out,in,stdin,"useTagbackGrenade?"));

                    if(useTagBack==1) {
                        ClientView.showMessage(out,in,"NotifyTagbackGrenade");
                     //   ClientView.getNotified(in);
                        System.out.println("Waiting player to end his turn...");
                    }

                }*/

                else
                    ClientView.getNotified(in);   //players who are not playing print messages sent from server


            } while(reinsert);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stdin.close();
            in.close();
            out.close();
            socket.close();

        }
    }

    /**
     * Tells player if he wants to use Teleporter Powerup, if yes insert the player to move and in which square to move it
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     */
    public static void checkAndUseTeleporter(PrintWriter out,Scanner in,Scanner stdin) {
        String line=in.nextLine();
        if(line.equals("youHaveTeleporter")) {
            System.out.println("You have a Teleporter, do you want to use it? 0 no, 1 yes");
            int input=Integer.parseInt(ClientInputController.sendAndCheckOneValue(out,in,stdin,"useTeleporter?"));
            if(input==1) {
                System.out.println("Choose a square where you want to move...");
                ClientInputController.sendAndCheckMove(out,in,stdin,"moveTeleporter");
            }
        }
    }

    /**
     * Tells player if he wants to use Newton Powerup, if yes insert the player to move and in which square to move it
     * @param out socket outStream
     * @param in socket inStream
     * @param stdin standard input
     */
    public static void checkAndUseNewton(PrintWriter out,Scanner in,Scanner stdin) {
        String line=in.nextLine();
        if(line.equals("youHaveNewton")) {
            System.out.println("You have a Newton, do you want to use it? 0 no, 1 yes");
            int input=Integer.parseInt(ClientInputController.sendAndCheckOneValue(out,in,stdin,"useNewton?"));
            if(input==1) {
                System.out.println("Choose a player you want to move...");
                ClientView.showMessage(out,in,"viewPlayersAlive");
                ClientInputController.sendAndCheckOneValue(out,in,stdin,"playerToMove");

                System.out.println("Where do you want to move it?");

                ClientInputController.sendAndCheckMove(out,in,stdin,"moveNewton");
            }
        }
    }
}