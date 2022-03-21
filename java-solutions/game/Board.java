package game;

public interface Board {
    Position getPosition();

    void enterCurrentPlayer(int no);

    GameResult makeMove(Move move);
}
