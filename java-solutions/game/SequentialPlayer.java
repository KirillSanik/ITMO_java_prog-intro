package game;

public class SequentialPlayer implements Player {
    @Override
    public Move makeMove(Position position) {
        for (int r = 0; r < position.getSizeBoard().getM(); r++) {
            for (int c = 0; c < position.getSizeBoard().getN(); c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
    }
}
