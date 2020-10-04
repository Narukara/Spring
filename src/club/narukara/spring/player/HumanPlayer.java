package club.narukara.spring.player;

import java.util.Scanner;

import club.narukara.spring.chessboard.Chessboard;

//Human player can only join ONE match
//only human player use 1~4/5
public class HumanPlayer implements Player {
    private final Scanner scanner;

    {
        scanner = new Scanner(System.in);
    }

    @Override
    public int[] decide(Chessboard chessboard, int side) {
        System.out.println(chessboard.display(1, side));
        int[] step = new int[2];
        do {
            System.out.print("x>>");
            step[0] = scanner.nextInt() - 1;
            System.out.print("y>>");
            step[1] = scanner.nextInt() - 1;
        } while (chessboard.add(step, side));
        System.out.println(chessboard.display(1, side));
        chessboard.undo();
        return step;
    }

    @Override
    public void announce(Chessboard chessboard, int side) {
        System.out.println("Result:\n" + chessboard.display(1, side));
//        scanner.close();
    }

}
