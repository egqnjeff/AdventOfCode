package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day04_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day04/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day04/input.txt";
    public static void main(String[] args) throws Exception {
        new Day04_2();
    }

    char[][] grid;
    int w;
    int h;

    public Day04_2() {
        initGrid();
        int result = searchGrid();
        System.out.println("Result = " + result);
    }

    private void initGrid() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            w = lines.get(0).length();
            h = lines.size();
            grid = new char[w][h];
            for(int y = 0; y < h; y++) {
                String line = lines.get(y);
                for(int x = 0; x < w; x++) {
                    grid[x][y] = line.charAt(x);
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int searchGrid() {
        int count = 0;
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                count += matchAllDirections(x, y);
            }
        }
        return count;
    }

    private int matchAllDirections(int x, int y) {
        int count = 0;
        if(grid[x][y] != 'A') {
            return 0;
        }
        count += matchX(x, y);
        return count;
    }

    private int matchX(int x, int y) {
        if(x >= 1 && y >= 1 && x <= w - 2 && y <= h - 2) {
            if( (grid[x-1][y-1] == 'M' && grid[x+1][y+1] == 'S') ||
                (grid[x-1][y-1] == 'S' && grid[x+1][y+1] == 'M')) {
                if( (grid[x-1][y+1] == 'M' && grid[x+1][y-1] == 'S') ||
                    (grid[x-1][y+1] == 'S' && grid[x+1][y-1] == 'M')) {
                    return 1;
                }
            }
        }
        return 0;
    }

}
