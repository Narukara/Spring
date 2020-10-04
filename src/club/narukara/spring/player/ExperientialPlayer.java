package club.narukara.spring.player;

import club.narukara.spring.chessboard.Chessboard;

import java.util.Random;

public class ExperientialPlayer implements InheritablePlayer {
    private double[][] matrix;

    public ExperientialPlayer() {
        matrix = new double[][]{{10, 10, -5}, {-5, 0, 10}, {5, 5, -5}, {0, 5, -5}, {0, 0, 10}, {0, 0, -5}, {0, 5, -5}, {0, 0, 10}};
    }

    @Override
    public int[] decide(Chessboard chessboard, int side) {
        int[] size = chessboard.getSize();
        int[][][] map = chessboard.getMap(chessboard.getNext() - 1);

        int[][] choices = new int[size[0] * size[1]][2];
        int tot1 = 0;
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[x][y][1] != -side) {
                    choices[tot1][0] = x;
                    choices[tot1++][1] = y;
                }
            }
        }

        int[][] goodChoices = new int[size[0] * size[1]][2];
        int tot2 = 0;
        double[][] score = evaluate(map, size, chessboard.getLimit(), side);
        double best = score[choices[0][0]][choices[0][1]];
        for (int i = 0; i < tot1; i++) {
            double iScore = score[choices[i][0]][choices[i][1]];
            if (iScore < best - 0.001) {
                continue;
            }
            if (iScore > best + 0.001) {
                best = iScore;
                tot2 = 0;
            }
            goodChoices[tot2++] = choices[i];
        }
        return goodChoices[new Random().nextInt(tot2)];
    }

    @Override
    public void announce(Chessboard chessboard, int side) {

    }

    public double[][] evaluate(int[][][] map, int[] size, int[][] limit, int side) {
        double[][] score = new double[size[0]][size[1]];
        int[][][] description = transform(map, size, limit, side);
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[x][y][1] != -side) {
                    int self = description[x][y][0];
                    if (x > 0) {
                        score[x][y] += matrix[self][description[x - 1][y][1]];
                    }
                    if (y > 0) {
                        score[x][y] += matrix[self][description[x][y - 1][1]];
                    }
                    if (x < size[0] - 1) {
                        score[x][y] += matrix[self][description[x + 1][y][1]];
                    }
                    if (y < size[1] - 1) {
                        score[x][y] += matrix[self][description[x][y + 1][1]];
                    }
                    score[x][y] /= limit[x][y] + 1; // important
                }
            }
        }
        return score;
    }

    private int[][][] transform(int[][][] map, int[] size, int[][] limit, int side) {
        int[][][] result = new int[size[0]][size[1]][2];
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[x][y][1] == -side) {
                    result[x][y][0] = -1;
                    result[x][y][1] = (map[x][y][0] == limit[x][y]) ? 2 : 1;
                } else {
                    result[x][y][0] = (limit[x][y] - 1) * 3 + map[x][y][0];
                    if (result[x][y][0] > 1) {
                        result[x][y][0]--;
                    }
                    if (result[x][y][0] > 6) {
                        result[x][y][0]--;
                    }
                    result[x][y][1] = 0;
                }
            }
        }
        return result;
    }

    @Override
    public void mutate(double step) {

    }

    @Override
    public void rebuild() {

    }
}
