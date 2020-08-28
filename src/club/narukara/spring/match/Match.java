package club.narukara.spring.match;

import club.narukara.spring.chessboard.Chessboard;
import club.narukara.spring.player.Player;

abstract class Match {
	protected Player[] players;
	protected Chessboard chessboard;
	protected Boolean isWon;

	public Match(Player[] players) {
		this.players = players;
	}

	abstract public void startNewMatch();

	abstract public void startNewMatch(Boolean first);

	abstract public void continueMatch(Chessboard chessboard);

	abstract public Boolean isFinished();

	abstract public Boolean isWon();

	abstract public void end();
}
