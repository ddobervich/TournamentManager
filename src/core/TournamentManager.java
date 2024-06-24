package core;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class TournamentManager {
    private static final int NUM_GAMES = 100;
    private List<TournamentEntry> allPlayers = new ArrayList<>();
    private Class<? extends Game> gameClass;

    public void loadGame(String gameJarPath, String gameClassName) {
        try {
            File file = new File(gameJarPath);
            URL[] urls = {file.toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class<?> loadedClass = urlClassLoader.loadClass(gameClassName);
            gameClass = (Class<? extends Game>) loadedClass;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayersFromDirectory(String directoryPath) {
        try {
            // Load the directory as a URL
            File directory = new File(directoryPath);
            URL[] urls = {directory.toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);

            // Filter for .class files in the directory
            File[] classFiles = directory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.isFile() && pathname.getName().endsWith(".class");
                }
            });

            // Iterate over each .class file
            for (File classFile : classFiles) {
                String className = "RockPaperScissors.SamplePlayers." + classFile.getName().replace(".class", "");
                try {
                    // Load the class
                    Class<?> clazz = urlClassLoader.loadClass(className);

                    // Check if it implements Player
                    if (Player.class.isAssignableFrom(clazz)) {
                        // Instantiate the class using a no-arg constructor
                        Constructor<?> constructor = clazz.getDeclaredConstructor();
                        constructor.setAccessible(true);
                        Player player = (Player) constructor.newInstance();
                        TournamentEntry entry = new TournamentEntry(player);
                        allPlayers.add(entry);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            urlClassLoader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadPlayersFromJars(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.isDirectory()) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        File[] jarFiles = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".jar"));
        if (jarFiles == null || jarFiles.length == 0) {
            System.out.println("No JAR files found in the directory.");
            return;
        }

        for (File jarFile : jarFiles) {
            try {
                URL[] urls = {jarFile.toURI().toURL()};
                URLClassLoader urlClassLoader = new URLClassLoader(urls);
                String className = getClassNameFromJar(jarFile.getName());
                Class<?> cls = urlClassLoader.loadClass(className);
                if (Player.class.isAssignableFrom(cls)) {
                    Player player = (Player) cls.getDeclaredConstructor().newInstance();
                    TournamentEntry entry = new TournamentEntry(player);
                    allPlayers.add(entry);
                    System.out.println("Loaded player: " + className);
                } else {
                    System.out.println("Class " + className + " does not implement core.Player interface.");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found in JAR file: " + jarFile.getName());
                e.printStackTrace();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | ClassCastException e) {
                System.out.println("Failed to instantiate class from JAR file: " + jarFile.getName());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getClassNameFromJar(String jarFileName) {
        // Assuming the class name is the same as the jar file name without the extension
        return jarFileName.substring(0, jarFileName.length() - 4); // Remove ".jar"
    }

    public void runTournament() {
        if (gameClass == null) {
            System.out.println("core.Game class not loaded.");
            return;
        }

        for (int i = 0; i < allPlayers.size(); i++) {
            for (int j = 0; j < allPlayers.size(); j++) {
                if (i != j) {
                    for (int n = 0; n < NUM_GAMES; n++) {
                        TournamentEntry[] players = {allPlayers.get(i), allPlayers.get(j)};
                        Player winner = playGame(players[0].getPlayer(), players[1].getPlayer());
                        updateEntrants(players, winner);
                    }
                }
            }
        }

        printResults();
    }

    private Player playGame(Player... players) {
        try {
            Game game = gameClass.getDeclaredConstructor(Player[].class).newInstance((Object)players);
            game.initialize();

            while (!game.isGameOver()) {
                for (Player player : players) {
                    game.makeMove(player);
                }
            }

            return game.getWinningPlayer();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateEntrants(TournamentEntry[] entrants, Player winner) {
        for (int i = 0; i < entrants.length; i++) {
            TournamentEntry entrant = entrants[i];

            entrant.incrementGamesPlayed();
            if (winner == null) {
                entrant.addPoints(1);   // 1 point for a tie
            } else if (entrant.getPlayer().equals(winner)) {
                entrant.incrementWins();
                entrant.addPoints(2);   // 3 points for a win
            } else {
                // 0 points for a loss
            }
        }
    }

    private void printResults() {
        Comparator<TournamentEntry> pointsCompare = new Comparator<TournamentEntry>() {
            @Override
            public int compare(TournamentEntry o1, TournamentEntry o2) {
                return o2.getPoints() - o1.getPoints();
            }
        };

        Collections.sort(allPlayers, pointsCompare);

        for (int i = 0; i < allPlayers.size(); i++) {
            TournamentEntry player = allPlayers.get(i);

            System.out.println((i+1) + " place with " + player.getPoints() + " points: " + player.getPlayer().name());
        }
    }
}