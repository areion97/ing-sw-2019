package controller.statePattern;

import controller.ServerInputController;
import controller.modelView.ModelViewColors;
import controller.modelView.ModelViewPlayerPowerups;
import controller.modelView.ModelViewSquare;
import controller.modelView.Notify;
import model.Game;
import model.GameManager;
import model.maps.Map;
import model.players.Player;
import serverSocket.LaunchServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LoggedOut implements PlayerState {
    private static boolean timerElapsed;

    @Override
    public void nextAction(Scanner in, PrintWriter out, Player player) {

        int numberInput;

        LocalTime end = null;
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } while (Game.getPlayerArrayList().size() < 3);


        if (player.equals(Game.getPlayerArrayList().get(2))) {
            LocalTime now = LocalTime.now();
            end = now.plusSeconds(LaunchServer.getTimer());
        }
        startTimer(end, player);
        ServerInputController.inputAndCheckUsername(out, in, player, "username");
        numberInput = ServerInputController.inputAndCheckInteger(1, 4, out, in, player, "map");
        Game.addVote(numberInput);
        ModelViewColors.showColors(in, out);

        boolean reinsert;

        ServerInputController.inputAndCheckColor(0, Game.getColorsAvailable().size(), out, in, player, "color");
        player.setState(new Spawning());
        player.nextAction(in, out, player);


        int i, j = 0;

        do {
            j = 0;
            for (i = 0; i < Game.getPlayerArrayList().size(); i++) {
                Player p = Game.getPlayerArrayList().get(i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (p.getColor() == null)
                    i = Game.getPlayerArrayList().size();
                else
                    j++;
            }
        } while (j != Game.getPlayerArrayList().size());
        // waits all map votes


        if (Game.getPlayerArrayList().get(0).equals(player)) {
            try {
                setWinningMap();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //wait creating map
        do {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } while (Game.getMap() == null);

        player.setSquare(Game.getMap().getSquareSpawn(player.getSpawningColor()));

        ModelViewSquare.showSpawnSquare(out, in, player);
        ModelViewPlayerPowerups.showPlayerPowerups(in, out, player);



        if (player.equals(Game.getPlayerArrayList().get(0))) {
            do {
                j = 0;
                for (i = 0; i < Game.getPlayerArrayList().size(); i++) {
                    Player p = Game.getPlayerArrayList().get(i);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (p.getSquare() == null)
                        i = Game.getPlayerArrayList().size();
                    else
                        j++;

                }

            } while (j != Game.getPlayerArrayList().size());
        }
        //waits all players to spawn
    }

    private static void setWinningMap() throws IOException {
        ArrayList<Integer> sortedVotes = new ArrayList<>(Game.getMapVotes().values());

        Collections.sort(sortedVotes, Collections.reverseOrder());

        for (int i = 0; i < Game.getMapVotes().size(); i++) {

            if (Game.getMapVotes().get(i).equals(sortedVotes.get(0))) {
                Game.setMap(i + 1);
                break;
            }
        }


    }

    private static void startTimer(LocalTime end, Player player) {
        if (Game.getPlayerArrayList().get(2).equals(player)) {
            timerElapsed = false;

            do {
                LocalTime now;
                if (Game.getPlayerArrayList().size() >= 3) {

                    do {

                        now = LocalTime.now();

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    while (Game.getPlayerArrayList().size() != 5 && (now.getSecond() != end.getSecond() || now.getMinute() != end.getMinute()));

                    timerElapsed = true;

                    PrintWriter out = GameManager.getOut(player.getPlayerID());
                    out.println("allConnected");
                    out.flush();

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } while (!timerElapsed);
        }
        else {
            do {
                if (timerElapsed) {
                    PrintWriter out = GameManager.getOut(player.getPlayerID());
                    out.println("allConnected");
                    out.flush();
                    return;
                } else {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            } while (true);


        }
    }
}