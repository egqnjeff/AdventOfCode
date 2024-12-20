package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day16_2 {
    // private static String inputPath = "AOC/Java/resource/2023/Day16/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day16/input.txt";

    public static void main(String[] args) throws Exception {
        new Day16_2();
    }

    char[][] grid;
    char[][] energized;
    List<String> cache;

    public Day16_2() {
        grid = initGrid();
        printGrid(grid);

        int maxEnergized = 0;

        for(int i = 0; i < grid.length; i++) {
            energized = new char[grid.length][grid[0].length];
            cache = new ArrayList<>();
            updateEnergized(i, 0, "S");
            maxEnergized = Math.max(maxEnergized, countEnergized(energized));
        }
        for(int i = 0; i < grid.length; i++) {
            energized = new char[grid.length][grid[0].length];
            cache = new ArrayList<>();
            updateEnergized(i, grid[0].length - 1, "N");
            maxEnergized = Math.max(maxEnergized, countEnergized(energized));
        }
        for(int j = 0; j < grid[0].length; j++) {
            energized = new char[grid.length][grid[0].length];
            cache = new ArrayList<>();
            updateEnergized(0, j, "E");
            maxEnergized = Math.max(maxEnergized, countEnergized(energized));
        }
        for(int j = 0; j < grid[0].length; j++) {
            energized = new char[grid.length][grid[0].length];
            cache = new ArrayList<>();
            updateEnergized(grid.length - 1, j, "W");
            maxEnergized = Math.max(maxEnergized, countEnergized(energized));
        }

        System.out.println("Max Energized = " + maxEnergized);
    }

    private void updateEnergized(int i, int j, String direction) {
        // check grid bounds
        if(i < 0 || j < 0 || i >= grid.length || j >= grid[0].length) {
            return;
        }
        // check cache
        String key = i + "," + j + "," + direction;
        if(cache.contains(key)) {
            return;
        }
        cache.add(key);
        energized[i][j] = '#';

        if(grid[i][j] == '.') {
            if(direction.equals("N")) {
                updateEnergized(i, j-1, "N");
            } else if(direction.equals("E")) {
                updateEnergized(i+1, j, "E");
            } else if(direction.equals("S")) {
                updateEnergized(i, j+1, "S");
            } else {
                updateEnergized(i-1, j, "W");
            }
        } else if(grid[i][j] == '\\') {
            if(direction.equals("N")) {
                updateEnergized(i-1, j, "W");
            } else if(direction.equals("E")) {
                updateEnergized(i, j+1, "S");
            } else if(direction.equals("S")) {
                updateEnergized(i+1, j, "E");
            } else {
                updateEnergized(i, j-1, "N");
            }
        } else if(grid[i][j] == '/') {
            if(direction.equals("N")) {
                updateEnergized(i+1, j, "E");
            } else if(direction.equals("E")) {
                updateEnergized(i, j-1, "N");
            } else if(direction.equals("S")) {
                updateEnergized(i-1, j, "W");
            } else {
                updateEnergized(i, j+1, "S");
            }
        } else if(grid[i][j] == '|') {
            if(direction.equals("N")) {
                updateEnergized(i, j-1, "N");
            } else if(direction.equals("E")) {
                updateEnergized(i, j-1, "N");
                updateEnergized(i, j+1, "S");
            } else if(direction.equals("S")) {
                updateEnergized(i, j+1, "S");
            } else {
                updateEnergized(i, j-1, "N");
                updateEnergized(i, j+1, "S");
            }
        } else if(grid[i][j] == '-') {
            if(direction.equals("N")) {
                updateEnergized(i-1, j, "W");
                updateEnergized(i+1, j, "E");
            } else if(direction.equals("E")) {
                updateEnergized(i+1, j, "E");
            } else if(direction.equals("S")) {
                updateEnergized(i-1, j, "W");
                updateEnergized(i+1, j, "E");
            } else {
                updateEnergized(i-1, j, "W");
            }
        }
    }

    private char[][] initGrid() {
        char[][] grid = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            grid = new char[lines.get(0).length()][lines.size()];
            for (int i = 0; i < lines.get(0).length(); i++) {
                for(int j = 0; j < lines.size(); j++) {
                    grid[i][j] = lines.get(j).charAt(i);
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        return grid;
    }

    private int countEnergized(char[][] energized) {
        int count = 0;
        for(int j = 0; j < energized[0].length; j++) {
            for (int i = 0; i < energized.length; i++) {
                if(energized[i][j] == '#') {
                    count++;
                }
            }
        }
        return count;
    }

    private void printGrid(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

}
