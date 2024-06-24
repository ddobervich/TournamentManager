package core;

public interface Game {
    /**
     * Initialize the game.
     */
    void initialize();

    /**
     * Make a move in the game.
     * @param player the player making the move
     * @return true if the move was successful, false otherwise
     */
    boolean makeMove(Player player);

    /**
     * Check if the game is over.
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Get the index of the winning player or -1 if no winner
     * @return the index of the winning player, or -1 if there is no winner
     */
    int getWinner();

    /**
     * Get the winning player or null if no winner
     * @return the winning player or null if no winner
     */
    Player getWinningPlayer();

    /**
     * Get the information from the last round
     * @return a RoundInfo object representing the current state of the game
     */
    RoundInfo getLastRoundInfo();

    /*
    Info about the previous round used to update all the InformedPlayers
     */
    public class RoundInfo {
    }

    /*
    Current state of the game, passed to Players making a move
     */
    public class GameState {
    }
}
