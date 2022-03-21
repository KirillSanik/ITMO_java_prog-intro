package game;

public interface Position {
    Cell getTurn();

    boolean isValid(Move move);

    Cell getCell(int row, int column);

    SizeBoard getSizeBoard();
}
