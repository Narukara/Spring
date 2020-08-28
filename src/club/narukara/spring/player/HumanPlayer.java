package club.narukara.spring.player;

import java.util.Scanner;

import club.narukara.spring.chessboard.Chessboard;

//Human player can only join ONE match 
public class HumanPlayer implements Player {
	private int next, side;
	private int[] size;
	private static char[] num = { '①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧' };
	private Scanner scanner;

	{
		scanner = new Scanner(System.in);
	}

	@Override
	public int[] decide(Chessboard chessboard, int side) {
		this.side = side;
		this.size = chessboard.getSize();
		for (int i = chessboard.getNext(); next < i; next++) {
			show(chessboard, next);
		}
		System.out.println(">>");
		int[] step = new int[2];
		step[0] = scanner.nextInt();
		step[1] = scanner.nextInt();
		return step;
	}

	private void show(Chessboard chessboard, int next) {
		int[] steps = chessboard.getSteps(next);
		System.out.println(((steps[0] == side) ? "self" : "enemy") + ": " + steps[1] + "," + steps[2]);
		int[][][] map = chessboard.getMap(next);
		StringBuilder stringBuilder = new StringBuilder("x 1 2 3 4 5\n");
		for (int x = 0; x < size[0]; x++) {
			stringBuilder.append(x + 1 + " ");
			for (int y = 0; y < size[1]; y++) {
				if (map[x][y][1] == 0) {
					stringBuilder.append("  ");
				} else if (map[x][y][1] == side) {
					stringBuilder.append(map[x][y][0] + " ");
				} else {
					stringBuilder.append(num[map[x][y][0] - 1] + " ");
				}
			}
			stringBuilder.append("\n");
		}
	}

}
