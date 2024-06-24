package RockPaperScissors.SamplePlayers;

import RockPaperScissors.RPSGame;
import core.Game;
import core.InformedPlayer;

public class BeatLastMovePlayer implements InformedPlayer {
    int opponentLastMove;

    public BeatLastMovePlayer() {
        opponentLastMove = RPSGame.ROCK;		// we'll start by trying to beat rock
    }

    private int getMoveToBeat(int prev) {
        if (prev == RPSGame.ROCK) return RPSGame.PAPER;
        if (prev == RPSGame.SCISSORS) return RPSGame.ROCK;
        return RPSGame.SCISSORS;
    }

    public void updateLastRoundInfo(int yourMove, int opponentMove, int outcome) {
        opponentLastMove = opponentMove;
    }

    @Override
    public void saveLastRoundInfo(Game.RoundInfo info) {
        RPSGame.RPSRoundInfo lastRound = (RPSGame.RPSRoundInfo)info;

        int oppIndex = 1 - lastRound.yourPlayerIndex;
        opponentLastMove = lastRound.playerMoves[oppIndex];
    }

    @Override
    public Object makeMove(Game.GameState gameState) {
        return getMoveToBeat(opponentLastMove);
    }

    @Override
    public String name() {
        return "beat last opp move";
    }
}