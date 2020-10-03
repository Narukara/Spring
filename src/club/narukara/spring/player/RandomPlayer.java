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
        int[] size = chessboard.getSize();
        return new int[]{random.nextInt(size[0]), random.nextInt(size[1])};
    }

    @Override
    public void announce(Chessboard chessboard, int side) {

    }
}
