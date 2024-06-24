package RockPaperScissors;

import core.Game;
import core.InformedPlayer;
import core.Player;

import java.util.Arrays;

public class RPSGame implements Game {
    public static final int ROCK = 0;
    public static final int PAPER = 1;
    public static final int SCISSORS = 2;

    private Player[] players;

    private int[] moves;
    private int nextPlayer;

    private int winner;

    private boolean gameOver;
    private RoundInfo lastRoundInfo;
    private String[] playerNames;

    public RPSGame(Player[] players) {
        this.players = players;
        this.playerNames = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            playerNames[i] = players[i].name();
        }
        initialize();
    }

    @Override
    public void initialize() {
        moves = new int[players.length];
        winner = -1;
        gameOver = false;
        nextPlayer = 0;
        lastRoundInfo = null;       // because no prev round yet...
    }

    @Override
    public boolean makeMove(Player player) {
        if (isGameOver()) {
            System.out.println("Game already over; can't make a move");
            return false;
        }

        if (!player.equals(players[nextPlayer])) {
            System.err.println("Players moving out of order");  // TODO: more detailed message
            return false;
        }

        int move = (int) player.makeMove(null);

        if (!isValid(move)) {
            System.err.println("Player move invalid");      // TODO: more detailed message
            return false;
        }

        moves[nextPlayer] = move;
        nextPlayer++;

        if (nextPlayer == players.length) { // all players registered moves
            determineWinner();
            updateLastRoundInfo();
            informAllInformedPlayers(players);
            nextPlayer = 0;
            gameOver = true;
        }

        return true;
    }

    private void informAllInformedPlayers(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];

            if (p instanceof InformedPlayer) {
                ((RPSRoundInfo)lastRoundInfo).yourPlayerIndex = i;
                ((InformedPlayer)p).saveLastRoundInfo(lastRoundInfo);
            }
        }
    }

    private void updateLastRoundInfo() {
        RPSRoundInfo roundInfo = new RPSRoundInfo();
        roundInfo.playerMoves = Arrays.copyOf(moves, moves.length);
        roundInfo.playerNames = Arrays.copyOf(playerNames, playerNames.length);
        roundInfo.winnerIndex = getWinner();
        lastRoundInfo = roundInfo;
    }

    private boolean isValid(int move) {
        return 0 <= move && move <= 2;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getWinner() {
        return winner;
    }

    @Override
    public Player getWinningPlayer() {
        if (winner >= 0) {
            return players[getWinner()];
        }
        return null;
    }

    @Override
    public Game.RoundInfo getLastRoundInfo() {
        return lastRoundInfo;
    }

    private void determineWinner() {
        int player1Move = moves[0];
        int player2Move = moves[1];

        if (player1Move == player2Move) {
            winner = -1; // It's a draw
        } else if ((player1Move == ROCK && player2Move == SCISSORS) ||
                (player1Move == PAPER && player2Move == ROCK) ||
                (player1Move == SCISSORS && player2Move == PAPER)) {
            winner = 0;
        } else {
            winner = 1;
        }
    }

    public class RPSRoundInfo extends RoundInfo {
        public String[] playerNames;
        public int[] playerMoves;
        public int yourPlayerIndex;
        public int winnerIndex;
    }
}
