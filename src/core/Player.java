package core;

public interface Player {
    /**
     * Make a move in the game.
     *
     * @param gameState the current game
     * @return an object representing the move
     */
    Object makeMove(Game.GameState gameState);

    String name();
}