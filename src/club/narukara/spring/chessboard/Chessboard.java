package club.narukara.spring.chessboard;

import java.io.Serializable;

public class Chessboard implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8382256685260739233L;
    private final int[][][][] map;
    private final int[][] limit;
    private final int[][] steps;
    private final int[] size;
    private int next = 0, winner = 0;
    private static final char[] num = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public Chessboard() {
        size = new int[]{4, 5};
        limit = new int[4][5];
        steps = new int[42][3];
        map = new int[42][4][5][2];
        setLimit();
    }

    public Chessboard(int[] size) {
        this.size = size;
        limit = new int[size[0]][size[1]];
        int max = getMax(size[0], size[1]);
        steps = new int[max][3];
        map = new int[max][size[0]][size[1]][2];
        setLimit();
    }

    public int[][][] getMap(int which) {
        return map[which];
    }

    public int[] getSteps(int which) {
        return steps[which];
    }

    public int[] getSize() {
        return size;
    }

    public int getNext() {
        return next;
    }

    public int getWinner() {
        return winner;
    }

    private static int getMax(int x, int y) {
        return 3 * x * y - 2 * (x + y);
    }

    private void setLimit() {
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (x == 0 || x == size[0] - 1) {
                    if (y == 0 || y == size[1] - 1) {
                        limit[x][y] = 1;
                    } else {
                        limit[x][y] = 2;
                    }
                } else {
                    if (y == 0 || y == size[1] - 1) {
                        limit[x][y] = 2;
                    } else {
                        limit[x][y] = 3;
                    }
                }
            }
        }
    }

    private boolean isLegal(int x, int y) {
        return x >= 0 && x < size[0] && y >= 0 && y < size[1];
    }

    private void copy() {
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                map[next][x][y][0] = map[next - 1][x][y][0];
                map[next][x][y][1] = map[next - 1][x][y][1];
            }
        }

    }

    public boolean add(int[] step, int side) {
        int x = step[0], y = step[1];
        if (!isLegal(x, y) || (next > 0 && map[next - 1][x][y][1] == -side)) {
            return true;
        }
        steps[next][0] = side;
        steps[next][1] = x;
        steps[next][2] = y;
        if (next != 0) {
            copy();
        } else {
            for (int i = 0; i < size[0]; i++) {
                for (int j = 0; j < size[1]; j++) {
                    map[0][i][j][0] = 0;
                    map[0][i][j][1] = 0;
                }
            }
        }
        map[next][x][y][0]++;
        map[next][x][y][1] = side;
        spring(x, y);
        next++;
        return false;
    }

    private void spring(int x, int y) {
        if (map[next][x][y][0] > limit[x][y]) {
            map[next][x][y][0] -= limit[x][y] + 1;
            int side = map[next][x][y][1];
            if (map[next][x][y][0] == 0) {
                map[next][x][y][1] = 0;
            }
            if (x != 0) {
                springHelper(x - 1, y, side);
            }
            if (x != size[0] - 1 && winner == 0) {
                springHelper(x + 1, y, side);
            }
            if (y != 0 && winner == 0) {
                springHelper(x, y - 1, side);
            }
            if (y != size[1] - 1 && winner == 0) {
                springHelper(x, y + 1, side);
            }
        }
    }

    private void springHelper(int x, int y, int side) {
        map[next][x][y][0]++;
        map[next][x][y][1] = side;
        judge();
        if (winner != 0) {
            return;
        }
        spring(x, y);
    }

    private void judge() {
        int win = 0;
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[next][x][y][1] != 0) {
                    if (win == 0) {
                        win = map[next][x][y][1];
                    } else if (win != map[next][x][y][1]) {
                        return;
                    }
                }
            }
        }
        winner = win;
    }

    public int sum(int side) {
        int sum = 0;
        for (int x = 0; x < size[0]; x++) {
            for (int y = 0; y < size[1]; y++) {
                if (map[next - 1][x][y][1] == side) {
                    sum += map[next - 1][x][y][0];
                }
            }
        }
        return sum;
    }

    public void undo() {
        next--;
        winner = 0;
    }

    public StringBuilder display(int lastStep, int side) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = Math.max(next - lastStep, 0); i < next; i++) {
            stringBuilder.append((steps[i][0] == side) ? "self" : "enemy").append(": ").append(steps[i][1] + 1).append(",").append(steps[i][2] + 1).append('\n');
            stringBuilder.append("x 1 2 3 4 5\n");
            for (int x = 0; x < size[0]; x++) {
                stringBuilder.append(x + 1).append(" ");
                for (int y = 0; y < size[1]; y++) {
                    if (map[i][x][y][1] == 0) {
                        stringBuilder.append("  ");
                    } else if (map[i][x][y][1] == side) {
                        stringBuilder.append(map[i][x][y][0]).append(" ");
                    } else {
                        stringBuilder.append(num[map[i][x][y][0] - 1]).append(" ");
                    }
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder;
    }

}
