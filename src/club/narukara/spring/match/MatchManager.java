package club.narukara.spring.match;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.player.Player;

import java.util.Random;

public class MatchManager {
    private final Player player1, player2;
    private final Chessboard chessboard;
    private MatchThread matchThread;
    private final Boolean display;

    public MatchManager(Player player1, Player player2, Chessboard chessboard, Boolean display) {
        this.player1 = player1;
        this.player2 = player2;
        this.chessboard = chessboard;
        this.display = display;
    }

    public void startMatch() {
        startMatch(new Random().nextBoolean());
    }

    public void startMatch(Boolean first) {
        matchThread = new MatchThread(player1, player2, chessboard, first, display);
        matchThread.start();
    }

    public int getResult() {
        return matchThread.getResult();
    }

    public void join() {
        try {
            matchThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
