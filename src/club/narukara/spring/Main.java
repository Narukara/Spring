package club.narukara.spring;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.match.MatchManager;
import club.narukara.spring.player.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        MatchManager matchManager = new MatchManager(new HumanPlayer(), new OnlinePlayer(51234), new Chessboard(), false);
        matchManager.startMatch();
        matchManager.join();
        System.out.println(matchManager.getResult());
    }

}
