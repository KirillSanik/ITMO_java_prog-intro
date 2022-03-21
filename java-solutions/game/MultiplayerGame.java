package game;

public class MultiplayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private final Player player3;
    private final Player player4;
    private int cntPlayers;
    private int cntLivePlayers;

    public MultiplayerGame(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = null;
        this.player4 = null;
        cntPlayers = 2;
        cntLivePlayers = cntPlayers;
    }

    public MultiplayerGame(Board board, Player player1, Player player2, Player player3) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = null;
        cntPlayers = 3;
        cntLivePlayers = cntPlayers;
    }

    public MultiplayerGame(Board board, Player player1, Player player2, Player player3, Player player4) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        cntPlayers = 4;
        cntLivePlayers = cntPlayers;
    }

    public int play(boolean log) {
        while (true) {
            if (player1 != null) {
                final int result1 = makeMove(player1, 1, log);
                if (result1 != -1) {
                    return result1;
                }
            }
            if (player2 != null) {
                final int result2 = makeMove(player2, 2, log);
                if (result2 != -1) {
                    return result2;
                }
            }
            if (player3 != null) {
                final int result3 = makeMove(player3, 3, log);
                if (result3 != -1) {
                    return result3;
                }
            }
            if (player4 != null) {
                final int result4 = makeMove(player4, 4, log);
                if (result4 != -1) {
                    return result4;
                }
            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        board.enterCurrentPlayer(no);
        final Move move = player.makeMove(board.getPosition());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return -2;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
