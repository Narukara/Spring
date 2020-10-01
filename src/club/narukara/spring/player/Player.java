package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

public interface Player {
	int[] decide(Chessboard chessboard, int side);
}
