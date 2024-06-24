package RockPaperScissors.SamplePlayers;

import RockPaperScissors.RPSGame;
import core.Game;
import core.Player;

public class RockPlayer implements Player {
    @Override
    public Object makeMove(Game.GameState gameState) {
        return RPSGame.ROCK;
    }

    @Override
    public String name() {
        return "rock player";
    }
}
