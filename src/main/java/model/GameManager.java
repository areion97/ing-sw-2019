package model;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *  Allows you to get all players' sockets, output streams and input streams
 */
public class GameManager {
    private static Map<Integer, PrintWriter> idOut= new HashMap<>();

    private static Map<Integer, Scanner> idIn= new HashMap<>();

    public static void addOut(int id, PrintWriter printWriter) {
        idOut.put(id,printWriter);
    }

    public static PrintWriter getOut (int id) {
        return idOut.get(id);
    }

    public static void addIn(int id, Scanner scanner) {
        idIn.put(id,scanner);
    }

    public static Scanner getIn (int id) {
        return idIn.get(id);
    }
}