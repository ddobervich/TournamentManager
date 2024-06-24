package RockPaperScissors;

import core.TournamentManager;

public class RockPaperScissorsTournament {
    public static void main(String[] args) {
        String gameJarPath = "C:\\Users\\david_dobervich\\IdeaProjects\\TicTacToeTournament\\out\\artifacts\\RPSGame\\RPSGame.jar";
        String gameClassName = "RockPaperScissors.RPSGame";
        String playerDirectory = "C:\\Users\\david_dobervich\\IdeaProjects\\TicTacToeTournament\\out\\production\\TicTacToeTournament\\RockPaperScissors\\SamplePlayers\\";

        TournamentManager tournamentManager = new TournamentManager();
        tournamentManager.loadGame(gameJarPath, gameClassName);
        tournamentManager.loadPlayersFromDirectory(playerDirectory);
        tournamentManager.runTournament();
    }
}
