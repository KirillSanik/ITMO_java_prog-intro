package game;

import java.util.Arrays;
import java.util.Map;

public class BoardOfMNK implements Board, Position{
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0",
            Cell.D, "-",
            Cell.P, "|"
    );

    private static final Map<Integer, Cell> NUM_TO_CELL = Map.of(
            0, Cell.E,
            1, Cell.X,
            2, Cell.O,
            3, Cell.D,
            4, Cell.P
    );

    private final Cell[][] field;
    private Cell turn;
    private final SizeBoard mn;
    private final int k;
    private int cntMotion = 0;
    private int currentPlayer = 1;

    public BoardOfMNK(SizeBoard mn, int k) {
        this.mn = mn;
        this.k = k;
        field = new Cell[mn.getM()][mn.getN()];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin(move)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        return GameResult.UNKNOWN;
    }

    private boolean checkDraw() {
        cntMotion++;
        if (cntMotion >= mn.getM() * mn.getN())
            return true;
        return false;
    }

    private boolean checkWin(Move move) {
        int cntLine = 1;
        for (int i = move.getRow() - 1; i >= 0; i--) {
            if (field[i][move.getCol()] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        for (int i = move.getRow() + 1; i < mn.getM(); i++) {
            if (field[i][move.getCol()] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        if (cntLine >= k) {
            return true;
        }

        cntLine = 1;
        for (int j = move.getCol() - 1; j >= 0; j--) {
            if (field[move.getRow()][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        for (int j = move.getCol() + 1; j < mn.getN(); j++) {
            if (field[move.getRow()][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        if (cntLine >= k) {
            return true;
        }

        cntLine = 1;
        for (int i = move.getRow() - 1, j = move.getCol() - 1; i >= 0 && j >= 0; i--, j--) {
            if (field[i][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        for (int i = move.getRow() + 1, j = move.getCol() + 1; i < mn.getM() && j < mn.getN(); i++, j++) {
            if (field[i][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        if (cntLine >= k) {
            return true;
        }

        cntLine = 1;
        for (int i = move.getRow() - 1, j = move.getCol() + 1; i >= 0 && j < mn.getN(); i--, j++) {
            if (field[i][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        for (int i = move.getRow() + 1, j = move.getCol() - 1; i < mn.getM() && j >= 0; i++, j--) {
            if (field[i][j] == turn) {
                cntLine++;
            }
            else {
                break;
            }
        }
        if (cntLine >= k) {
            return true;
        }
        return  false;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public boolean isValid(Move move) {
        return 0 <= move.getRow() && move.getRow() < mn.getM()
                && 0 <= move.getCol() && move.getCol() < mn.getN()
                && field[move.getRow()][move.getCol()] == Cell.E
                && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public SizeBoard getSizeBoard() {
        return mn;
    }

    @Override
    public void enterCurrentPlayer(int no) {
        currentPlayer = no;
        turn = NUM_TO_CELL.get(no);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        for (int c = 0; c < mn.getN(); c++) {
            sb.append(String.format("\t%d", c + 1));
        }
        sb.append(System.lineSeparator());
        for (int r = 0; r < mn.getM(); r++) {
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(String.format("\t %s", CELL_TO_STRING.get(cell)));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
