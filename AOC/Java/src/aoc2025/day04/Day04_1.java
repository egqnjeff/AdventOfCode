package aoc2025.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day04_1 {
    // private static String inputPath = "AOC/Java/resource/2025/Day04/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day04/input.txt";
    public static void main(String[] args) throws Exception {
        new Day04_1();
    }

    char[][] grid;
    int w;
    int h;

    public Day04_1() {
        initGrid();
        // printGrid();
        int sum = 0;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                if(grid[x][y] == '@' && getRolls(x, y) < 4){
                    sum++;
                }
            }
        }
        System.out.println("Sum = " + sum);
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

    private void printGrid() {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

    private int getRolls(int x, int y) {
        int count = 0;
        int minX = Math.max(0, x-1);
        int minY = Math.max(0, y-1);
        int maxX = Math.min(w-1, x+1);
        int maxY = Math.min(h-1, y+1);
        // System.out.print("Testing [" + x + "][" + y + "]");
        for(int i = minX; i <= maxX; i++) {
            for(int j = minY; j <= maxY; j++) {
                if(i != x || j != y) {
                    if(grid[i][j] == '@') {
                        count++;
                    }
                }
            }
        }
        // System.out.println("  count = " + count);
        return count;
    }

}
