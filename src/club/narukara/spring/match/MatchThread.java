package club.narukara.spring.match;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.player.Player;

public class MatchThread extends Thread {
    private Player player1, player2;
    private final Chessboard chessboard;
    private final Boolean first, display;

    public MatchThread(Player player1, Player player2, Chessboard chessboard, Boolean first, Boolean display) {
        this.player1 = player1;
        this.player2 = player2;
        this.chessboard = chessboard;
        this.first = first;
        this.display = display;
    }

    @Override
    public void run() {
        if (!first) {
            Player temp = player1;
            player1 = player2;
            player2 = temp;
        }
        while (true) {
            while (chessboard.add(player1.decide(chessboard, 1), 1)) ;
            if (display) System.out.println(chessboard.display(1, 1));
            if (chessboard.getWinner() != 0) break;
            while (chessboard.add(player2.decide(chessboard, -1), -1)) ;
            if (display) System.out.println(chessboard.display(1, 1));
            if (chessboard.getWinner() != 0) break;
        }
        player1.announce(chessboard, 1);
        player2.announce(chessboard, -1);
    }

    // 0 -> not over
    // 1 -> first player won
    // 2 -> second player won
    public int getResult() {
        if (getState() == State.TERMINATED) {
            if (first) {
                if (chessboard.getWinner() == 1) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (chessboard.getWinner() == 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        } else {
            return 0;
        }
    }
}
