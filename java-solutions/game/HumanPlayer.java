package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final Scanner in;

    public HumanPlayer(Scanner in) {
        this.in = in;
    }

    @Override
    public Move makeMove(Position position) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        Move move = new Move(0,0, position.getTurn());
        boolean okInput;
        String n1, n2;
        do {
            okInput = true;
            try {
                n1 = in.next();
                n2 = in.next();
                move = new Move(Integer.parseInt(n1) - 1, Integer.parseInt(n2) - 1, position.getTurn());
            } catch (NumberFormatException e) {
                move = new Move(-1, -1, Cell.E);
            }
            if (!position.isValid(move)) {
                okInput = false;
                System.out.println("Неправильные введеные данные, повторите попытку");
            }
        } while(!okInput);
        return move;
    }
}
