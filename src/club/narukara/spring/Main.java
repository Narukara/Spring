package club.narukara.spring;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.match.MatchManager;
import club.narukara.spring.player.*;

public class Main {

    public static void main(String[] args) {
        MatchManager matchManager = new MatchManager(new HumanPlayer(), new SearchTreePlayer(), new Chessboard(), false);
        matchManager.startMatch();
        matchManager.join();
        System.out.println(matchManager.getResult());
    }

}
