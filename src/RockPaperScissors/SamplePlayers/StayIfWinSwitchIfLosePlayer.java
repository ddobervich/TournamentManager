package RockPaperScissors.SamplePlayers;

import RockPaperScissors.InformedRPSPlayer;
import RockPaperScissors.RPSGame;
import core.Game;

public class StayIfWinSwitchIfLosePlayer extends InformedRPSPlayer {
    private int nextMove;

    public StayIfWinSwitchIfLosePlayer() {
        super("StayIfIWin");
        nextMove = randomMove();
    }


    private int moveToBeat(int prev) {
        if (prev == RPSGame.ROCK) return RPSGame.PAPER;
        if (prev == RPSGame.SCISSORS) return RPSGame.ROCK;
        return RPSGame.SCISSORS;
    }

    private int randomMove() {
        return (int)(Math.random()*3);
    }

    @Override
    public void saveLastRoundInfo(int yourMove, int oppMove, boolean tie, boolean youWon) {
        if (tie){     // tie
            nextMove = randomMove();
        } else if (!youWon) {    // someone else won
            int winningMove = oppMove;
            nextMove = moveToBeat(winningMove);
        }
    }

    @Override
    public Object makeMove(Game.GameState gameState) {
        return nextMove;
    }
}
