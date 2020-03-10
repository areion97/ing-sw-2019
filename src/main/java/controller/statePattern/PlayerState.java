package controller.statePattern;

import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public interface PlayerState {
     void nextAction(Scanner in, PrintWriter out, Player player);
}
