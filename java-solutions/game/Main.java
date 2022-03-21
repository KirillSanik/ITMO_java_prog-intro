package game;

import java.util.Scanner;

public class Main {
    public static int readNum(Scanner in) {
        try {
            return Integer.parseInt(in.next());
        } catch (NumberFormatException e) {
            System.out.println("Это не число, повторите попытку");
            return readNum(in);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final int m, n, k;
        System.out.print("Введите m: ");
        m = readNum(in);
        System.out.print("Введите n: ");
        n = readNum(in);
        System.out.print("Введите k: ");
        k = readNum(in);
        final int result = new MultiplayerGame(
                new BoardOfMNK(new SizeBoard(m, n), k),
                new RandomPlayer(),
                //new SequentialPlayer()
                new HumanPlayer(in),
                new RandomPlayer()
        ).play(true);
        switch (result) {
            case -2:
                System.out.println("Sombody loose");
                break;
            case 1:
                System.out.println("First player won");
                break;
            case 2:
                System.out.println("Second player won");
                break;
            case 3:
                System.out.println("Third player won");
                break;
            case 4:
                System.out.println("Four player won");
                break;
            case 0:
                System.out.println("Draw");
                break;
            default:
                throw new AssertionError("Unknown result " + result);
        }
    }
}
