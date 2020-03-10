package client;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Displaying messages from server
 */
public class ClientView {
    /**
     * Sends to the server which object to be displayed and shows it
     * @param out socket outStream
     * @param in socket inStream
     * @param object string of the object to display
     */
    public static void showMessage(PrintWriter out, Scanner in, String object) {
        out.println(object);
        out.flush();
        String line;
        line = "";
        while (!line.equals("exit")) {
            line = in.nextLine();
            if (!line.equals("exit"))
                System.out.println(line);
        }

    }

    /**
     * Waits server to send notification and prints it
     * @param in socket inStream
     */
     public static void getNotified(Scanner in) {
        String line;
        line = "";
        while (!line.equals("exit")) {
            line = in.nextLine();
            if (!line.equals("exit"))
                System.out.println(line);
        }

    }
}
