package club.narukara.spring;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.match.MatchManager;
import club.narukara.spring.player.*;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("--------------------------");
        System.out.println("| Spring Chess Game V1.0 |");
        System.out.println("--------------------------");
        System.out.println("1.PVE");
        System.out.println("2.PVP online");
        System.out.println("3.EVE");
        System.out.print(">>");
        Scanner scanner = new Scanner(System.in);
        Player player1, player2;
        boolean display = false;
        switch (scanner.nextInt()) {
            case 1:
                player1 = new HumanPlayer();
                System.out.println("1.easy (Random Player)");
                System.out.println("2.normal (Experiential Player)");
                System.out.println("3.hard (Search Tree Player)");
                System.out.print(">>");
                int s1 = scanner.nextInt();
                player2 = (s1 == 1) ? new RandomPlayer() : ((s1 == 2) ? new ExperientialPlayer() : new SearchTreePlayer());
                break;
            case 2:
                System.out.println("1.play as host");
                System.out.println("2.play as client");
                System.out.print(">>");
                if (scanner.nextInt() == 1) {
                    player1 = new HumanPlayer();
                    System.out.print("select a port (10000 - 65535) :");
                    int port = scanner.nextInt();
                    try {
                        System.out.println("please wait...");
                        player2 = new OnlinePlayer(port);
                        System.out.println("connect success");
                    } catch (IOException e) {
                        System.out.println("network error");
                        return;
                    }
                } else {
                    System.out.print("input host ip:");
                    scanner.nextLine();
                    String ip = scanner.nextLine();
                    System.out.print("input host port:");
                    int port = scanner.nextInt();
                    System.out.println("connecting...");
                    try {
                        LocalPlayer localPlayer = new LocalPlayer(ip, port, new HumanPlayer());
                        System.out.println("connect success");
                        localPlayer.start();
                        return;
                    } catch (IOException e) {
                        System.out.println("network error");
                        return;
                    }
                }
                break;
            default:
                display = true;
                System.out.println("1.easy (Random Player)");
                System.out.println("2.normal (Experiential Player)");
                System.out.println("3.hard (Search Tree Player)");
                System.out.print(">>");
                int s2 = scanner.nextInt();
                player1 = (s2 == 1) ? new RandomPlayer() : ((s2 == 2) ? new ExperientialPlayer() : new SearchTreePlayer());
                System.out.println("1.easy (Random Player)");
                System.out.println("2.normal (Experiential Player)");
                System.out.println("3.hard (Search Tree Player)");
                System.out.print(">>");
                s2 = scanner.nextInt();
                player2 = (s2 == 1) ? new RandomPlayer() : ((s2 == 2) ? new ExperientialPlayer() : new SearchTreePlayer());

        }
        MatchManager matchManager = new MatchManager(player1, player2, new Chessboard(), display);
        matchManager.startMatch();
        matchManager.join();
//        System.out.println("save this match?(y/n)");
//        System.out.println(">>");
//        String s = scanner.nextLine();
//        if (s.equals("y") || s.equals("Y")) {
//
//        }
    }

}
