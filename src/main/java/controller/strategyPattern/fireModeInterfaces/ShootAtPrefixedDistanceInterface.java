package controller.strategyPattern.fireModeInterfaces;

import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public interface ShootAtPrefixedDistanceInterface {

    Player shoot(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, int numberOfTargets, int fromStep, int toStep, boolean seeMode, int damage, int marks, String move, int distance);

}
