package RockPaperScissors.SamplePlayers;

import RockPaperScissors.RPSGame;
import core.Game;
import core.Player;

import java.util.Random;

public class RPSRandomPlayer implements Player {
    private static final Random random = new Random();

    @Override
    public Object makeMove(Game.GameState gameState) {
        int num = (int)(Math.random()*3);
        if (num == 0) return RPSGame.ROCK;
        if (num == 1) return RPSGame.PAPER;
        return RPSGame.SCISSORS;
    }

    @Override
    public String name() {
        return "Dr. Rando";
    }
}
