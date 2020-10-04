package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

import java.util.Random;

public class SearchTreePlayer implements Player {
    private final ExperientialPlayer experientialPlayer = new ExperientialPlayer();

    @Override
    public int[] decide(Chessboard chessboard, int side) {
        int[] size = chessboard.getSize();
        int[][][] map = chessboard.getMap(chessboard.getNext() - 1);
        int[][] limit = chessboard.getLimit();

        // get all possible choices
        int[][] choices = new int[size[0] * size[1]][3];
        int tot1 = 0;
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[x][y][1] != -side) {
                    if (map[x][y][0] == limit[x][y]) {
                        chessboard.add(new int[]{x, y}, side);
                        if (chessboard.getWinner() == side) { // be sure of victory
                            chessboard.undo();
                            return new int[]{x, y};
                        } else {
                            chessboard.undo();
                        }
                    }
                    choices[tot1][0] = x;
                    choices[tot1++][1] = y;
                }
            }
        }

        // make (worst) prediction
        for (int i = 0; i < tot1; i++) {
            chessboard.add(choices[i], side);
            choices[i][2] = chessboard.sum(side); // possible best situation
            int[][][] tempMap = chessboard.getMap(chessboard.getNext() - 1);
            cal:
            for (int x = 0; x < size[0]; x++) {
                for (int y = 0; y < size[1]; y++) {
                    if (tempMap[x][y][1] == -side && tempMap[x][y][0] == limit[x][y]) {
                        chessboard.add(new int[]{x, y}, -side);
                        if (chessboard.getWinner() == -side) {
                            choices[i][2] = 0;
                            chessboard.undo();
                            break cal;
                        }
                        choices[i][2] = Math.min(chessboard.sum(side), choices[i][2]);
                        chessboard.undo();
                    }
                }
            }
            chessboard.undo();
        }

        // select by (worst) prediction
        int[][] goodChoices = new int[size[0] * size[1]][2];
        int tot2 = 0;
        for (int i = 0, best = 0; i < tot1; i++) {
            if (choices[i][2] < best) {
                continue;
            }
            if (choices[i][2] > best) {
                best = choices[i][2];
                tot2 = 0;
            }
            goodChoices[tot2][0] = choices[i][0];
            goodChoices[tot2++][1] = choices[i][1];
        }
        if (tot2 == 1) {
            return goodChoices[0];
        }

        // select by experience
        int[][] betterChoices = new int[size[0] * size[1]][2];
        int tot3 = 0;
        double[][] score = experientialPlayer.evaluate(map, size, limit, side);
        double best = score[goodChoices[0][0]][goodChoices[0][1]];
        for (int i = 0; i < tot2; i++) {
            double iScore = score[goodChoices[i][0]][goodChoices[i][1]];
            if (iScore < best - 0.001) {
                continue;
            }
            if (iScore > best + 0.001) {
                best = iScore;
                tot3 = 0;
            }
            betterChoices[tot3++] = goodChoices[i];
        }
        return betterChoices[new Random().nextInt(tot3)];
    }

    @Override
    public void announce(Chessboard chessboard, int side) {

    }

}
