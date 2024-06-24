package RockPaperScissors;

import RockPaperScissors.RPSGame;
import core.Game;
import core.InformedPlayer;

public abstract class InformedRPSPlayer implements InformedPlayer {
    protected String name;

    public InformedRPSPlayer(String name) {
        this.name = name;
    }

    @Override
    public void saveLastRoundInfo(Game.RoundInfo roundInfo) {
        RPSGame.RPSRoundInfo info = (RPSGame.RPSRoundInfo)roundInfo;

        int yourMove = info.playerMoves[info.yourPlayerIndex];
        int oppIndex = 1-info.yourPlayerIndex;
        int oppMove = info.playerMoves[oppIndex];
        boolean tie = (info.winnerIndex == -1);
        boolean won = (info.winnerIndex == info.yourPlayerIndex);

        saveLastRoundInfo(yourMove, oppMove, tie, won);
    }

    public abstract void saveLastRoundInfo(int yourMove, int oppMove, boolean tie, boolean youWon);

    @Override
    public abstract Object makeMove(Game.GameState gameState);

    @Override
    public String name() {
        return name;
    }
}
