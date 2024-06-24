package core;

public class TournamentEntry {
    private Player player;
    private int points;
    private int gamesPlayed;
    private int gamesWon;

    public TournamentEntry(Player p) {
        this.player = p;
        points = 0;
        gamesPlayed = 0;
        gamesWon = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void incrementWins() {
        gamesWon++;
    }

    public void addPoints(int num) {
        points += num;
    }
}
