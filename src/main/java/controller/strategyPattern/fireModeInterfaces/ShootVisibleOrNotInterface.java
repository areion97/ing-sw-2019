package controller.strategyPattern.fireModeInterfaces;

import model.decks.weaponDeck.weapons.Weapon;
import model.players.Player;

import java.io.PrintWriter;
import java.util.Scanner;

public interface ShootVisibleOrNotInterface {
    Player shoot(PrintWriter out, Scanner in, Weapon weapon, boolean canSeeModality, Player shootingPlayer, int numberOfTargets, int damage, int marks, String move, int distance);
}
