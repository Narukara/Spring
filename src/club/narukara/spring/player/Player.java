package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

public interface Player {
	public int[] decide(Chessboard chessboard, int side);
}
