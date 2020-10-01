package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

import java.util.Random;

public class RandomPlayer implements Player {
    private static final Random random;

    static {
        random = new Random();
    }

    @Override
    public int[] decide(Chessboard chessboard, int side) {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new int[]{random.nextInt(4), random.nextInt(5)};
    }
}
