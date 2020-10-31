package controller.statePattern;

import controller.ServerInputController;
import controller.modelView.ModelViewPlayerWeapons;
import model.decks.weaponDeck.weapons.Weapon;
import model.players.*;
import serverSocket.messageManager.FormatShootingMessage;

import java.io.PrintWriter;
import java.util.Scanner;

public class Shoot implements PlayerState {
    @Override
    public void nextAction(Scanner in, PrintWriter out, Player shootingPlayer) {

        ModelViewPlayerWeapons.showPlayerWeapons(shootingPlayer,out,in);
        int weaponIndex=ServerInputController.inputAndCheckInteger(0,shootingPlayer.getWeapons().size()-1,out,in,shootingPlayer,"shootingWeapon");
        Weapon weapon=shootingPlayer.getWeapons().get(weaponIndex);


        if(!weapon.getLoaded()) {
            out.println("weaponNotLoaded");
            out.flush();
            Move.setPreviousSquare(shootingPlayer);
            shootingPlayer.setState(new Playing());
            shootingPlayer.nextAction(in,out,shootingPlayer);
            return;
        }
        out.println("weaponLoaded");
        out.flush();


        String fireMode = weapon.getBasicAttackInterface();

        if(fireMode.equals("ShootVisibleOrNot")||fireMode.equals("ShootAtPrefixedDistance")) {

             String line= FormatShootingMessage.format(weapon.getWeaponName(),fireMode,weapon.getNumberOfTargets(),weapon.getMove());
             out.println(line);
             out.flush();

        }

        else if(fireMode.equals("ShootDealDmgInSquare")) {
            out.println("ShootDealDmgInSquare");
            out.flush();
        }
        else if(fireMode.equals("ShootInVisibleRoom")) {
            out.println("ShootInVisibleRoom");
            out.flush();
        }
        else if(fireMode.equals("ShootMoveToCanSeeSquare")) {
            out.println("ShootMoveToCanSeeSquare");
            out.flush();
        }
        else if(fireMode.equals("ShootCardinalDirection")) {
            out.println("ShootCardinalDirection");
            out.flush();
        }

        weapon.shootWithBasicAttack(out,in,shootingPlayer);

        }

    }





