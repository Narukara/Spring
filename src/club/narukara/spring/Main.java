package club.narukara.spring;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.match.MatchManager;
import club.narukara.spring.player.*;

public class Main {

    public static void main(String[] args) {
        MatchManager matchManager = new MatchManager(new HumanPlayer(), new RandomPlayer(), new Chessboard(), false);
        matchManager.startMatch();
        System.out.println(matchManager.getResult());
        matchManager.join();
        System.out.println(matchManager.getResult());

    }

}
