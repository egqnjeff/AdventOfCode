package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day04_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day04/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day04/input.txt";
    public static void main(String[] args) throws Exception {
        new Day04_1();
    }

    char[][] grid;
    int w;
    int h;

    public Day04_1() {
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
        if(grid[x][y] != 'X') {
            return 0;
        }
        count += matchUp(x, y);
        count += matchUpLeft(x, y);
        count += matchUpRight(x, y);
        count += matchDown(x, y);
        count += matchDownLeft(x, y);
        count += matchDownRight(x, y);
        count += matchLeft(x, y);
        count += matchRight(x, y);
        return count;
    }

    private int matchUp(int x, int y) {
        if(y >= 3) {
            if(grid[x][y-1] == 'M' && grid[x][y-2] == 'A' && grid[x][y-3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchUpLeft(int x, int y) {
        if(x >= 3 && y >= 3) {
            if(grid[x-1][y-1] == 'M' && grid[x-2][y-2] == 'A' && grid[x-3][y-3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchUpRight(int x, int y) {
        if(x <= w - 4 && y >= 3) {
            if(grid[x+1][y-1] == 'M' && grid[x+2][y-2] == 'A' && grid[x+3][y-3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchDown(int x, int y) {
        if(y <= h - 4) {
            if(grid[x][y+1] == 'M' && grid[x][y+2] == 'A' && grid[x][y+3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchDownLeft(int x, int y) {
        if(x >= 3 && y <= h - 4) {
            if(grid[x-1][y+1] == 'M' && grid[x-2][y+2] == 'A' && grid[x-3][y+3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchDownRight(int x, int y) {
        if(x <= w - 4 && y <= h - 4) {
            if(grid[x+1][y+1] == 'M' && grid[x+2][y+2] == 'A' && grid[x+3][y+3] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchLeft(int x, int y) {
        if(x >= 3) {
            if(grid[x-1][y] == 'M' && grid[x-2][y] == 'A' && grid[x-3][y] == 'S') {
                return 1;
            }
        }
        return 0;
    }

    private int matchRight(int x, int y) {
        if(x <= w - 4) {
            if(grid[x+1][y] == 'M' && grid[x+2][y] == 'A' && grid[x+3][y] == 'S') {
                return 1;
            }
        }
        return 0;
    }


}
