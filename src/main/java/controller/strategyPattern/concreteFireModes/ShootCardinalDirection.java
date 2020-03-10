package controller.strategyPattern.concreteFireModes;

import controller.GiveDmgOrMarks;
import controller.ServerInputController;
import controller.enumerations.CardinalDirections;
import controller.modelView.ModelViewPlayersAlive;
import controller.modelView.Notify;
import controller.statePattern.Move;
import controller.statePattern.Playing;
import model.Game;
import model.decks.weaponDeck.weapons.Weapon;
import model.maps.Square;
import model.players.Player;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ShootCardinalDirection {
    public static void shoot(PrintWriter out, Scanner in, Weapon weapon, Player shootingPlayer, int damage, int marks) {

        boolean reinsert;
        boolean canShoot = false;
        for (int i = 0; i < 4; i++) {
            if (checkPlayerExistenceCardinalDirection(CardinalDirections.values()[i], shootingPlayer,null)) {
                System.out.println("can shhoot true");
                canShoot = true;
            }

        }

        if (canShoot) {
            out.println("canShoot");
            out.flush();
            int cardinalDirection;
            do {
                reinsert = false;
                cardinalDirection = ServerInputController.inputAndCheckInteger(0, 3, out, in, shootingPlayer, "cardinal");
                System.out.println("vabbene input");
                if (checkPlayerExistenceCardinalDirection(CardinalDirections.values()[cardinalDirection], shootingPlayer,null)) {
                    out.println("existenceOfPlayer");
                    out.flush();
                } else {
                    out.println("noPlayersInDirection");
                    out.flush();
                    reinsert = true;
                }
            } while (reinsert);


            ModelViewPlayersAlive.showAlivePlayers(out, in, shootingPlayer);
            Player shootedPlayer = inputAndCheckPlayer(out, in, weapon,shootingPlayer,CardinalDirections.values()[cardinalDirection]);

            GiveDmgOrMarks.addDmg(shootedPlayer,shootingPlayer, damage);
            GiveDmgOrMarks.addMark(shootedPlayer,shootingPlayer, marks);
            weapon.setLoaded(false);

            Notify.notifyShoot(shootingPlayer,"NotifyShoot",shootedPlayer,weapon,damage,marks);

            for (Player p : Game.getPlayerArrayList()) {
                System.out.println(p.getPlayerID() + " damage " + p.getPlayerBoard().getDamageTaken());
                System.out.println(p.getPlayerID() + " marks " + p.getPlayerBoard().getMarks());
            }
        }

        else {
            out.println("noPlayersToShoot");
            out.flush();
            Move.setPreviousSquare(shootingPlayer);
            shootingPlayer.setState(new Playing());
            shootingPlayer.nextAction(in, out, shootingPlayer);
        }
    }


    public static Player inputAndCheckPlayer(PrintWriter out, Scanner in, Weapon weapon,Player shootingPlayer, CardinalDirections cardinalDirection) {

        int shootedPlayerId = 0;
        Player shootedPlayer = null;
        String line;
        ArrayList<Player> playersAlive = Game.getPlayersAlive();

        line = in.nextLine();

        shootingPlayer.getServerMessage().deserialize(line);

        if (shootingPlayer.getServerMessage().getObject().equals("shootedPlayer")) {
            try {
                shootedPlayerId = Integer.parseInt(shootingPlayer.getServerMessage().getValue());
                if (shootedPlayerId < Game.getPlayerArrayList().size() && shootedPlayerId >= 0 && shootedPlayerId != shootingPlayer.getPlayerID()) {
                    shootedPlayer = Game.getPlayerArrayList().get(shootedPlayerId);

                    System.out.println(!playersAlive.contains(shootedPlayer));

                    if (!playersAlive.contains(shootedPlayer)) {
                        System.out.println("player dead");
                        out.println("playerDeadOrShot");
                        out.flush();
                    } else if (!checkPlayerExistenceCardinalDirection(cardinalDirection, shootingPlayer, shootedPlayer)) {
                        out.println("playerNotInDirection");
                        out.flush();
                    } else {
                        System.out.println("ok input");
                        out.println("okInput");
                        out.flush();
                        return shootedPlayer;
                    }
                } else {
                    out.println("reinsert");
                    out.flush();
                }
            } catch (NumberFormatException e) {
                out.println("reinsert");
                out.flush();
            }

        } else {
            out.println("reinsert");
            out.flush();
        }

        return inputAndCheckPlayer(out, in,weapon, shootingPlayer,cardinalDirection);

}
    public static boolean checkPlayerExistenceCardinalDirection(CardinalDirections cardinalDirection,Player shootingPlayer,Player shootedPlayer) {
        Square s = shootingPlayer.getSquare();
        int a = 0;
        int b = 0;
        int x = s.getX();
        int y = s.getY();
        String orientation= "Vertical";
        if (cardinalDirection.equals(CardinalDirections.NORTH)) {
            a = 0;
            b = x;
        } else if (cardinalDirection.equals(CardinalDirections.SOUTH)) {
            a = x;
            b = 2;
        } else if (cardinalDirection.equals(CardinalDirections.WEST)) {
            a = 0;
            b = y;
            orientation="Horizontal";
        } else if (cardinalDirection.equals(CardinalDirections.EAST)) {
            a = y;
            b = 3;
            orientation="Horizontal";
        }

        for (int i = a; i <= b; i++) {
            if(orientation.equals("Horizontal"))
                s=Game.getMap().getSquare(x,i);
            else
                s = Game.getMap().getSquare(i, y);

            if (s != null) {
                if (shootedPlayer != null) {
                    if (shootedPlayer.getSquare().equals(s))
                        return true;
                }

                else {
                    if (existencePlayerInSquare(s, shootingPlayer))
                        return true;
                }

            }

        }
        return false;

    }




    public static boolean existencePlayerInSquare(Square s,Player shootingPlayer) {
        for (Player p:Game.getPlayersAlive()) {
            if (!p.equals(shootingPlayer)) {
                if (p.getSquare().equals(s))
                    return true;
            }
        }
        return false;
    }

}