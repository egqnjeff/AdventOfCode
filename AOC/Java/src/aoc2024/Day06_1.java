package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day06_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day06/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day06/input.txt";
    public static void main(String[] args) throws Exception {
        new Day06_1();
    }

    char[][] grid;
    int w = 0;
    int h = 0;
    Point guard = null;

    public Day06_1() {
        initGrid();
        // printGrid();
        findGuard();
        // System.out.println("Guard Position " + guard);
        while(moveGuard()){};
        printGrid();
        System.out.println("Visited = " + countVisited());
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

    private void findGuard() {
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                switch (grid[x][y]) { 
                    case '^':
                    case '>':
                    case 'v':
                    case '<': {
                        guard = new Point(x,y);
                        return;
                    }
                }
            }
        }
    }

    private boolean moveGuard() {
        char direction = grid[guard.x][guard.y];
        Point next = getNextPosition(direction);
        // System.out.println("Next Position " + next);

        if(next.x < 0 || next.x >= w || next.y < 0 || next.y >= h) {
            grid[guard.x][guard.y] = 'X';
            return false;
        }
        if(grid[next.x][next.y] == '#') {
            switch (direction) {
                case '^' : {
                    grid[guard.x][guard.y] = '>';
                    break;
                }
                case '>' : {
                    grid[guard.x][guard.y] = 'v';
                    break;
                }
                case 'v' : {
                    grid[guard.x][guard.y] = '<';
                    break;
                }
                case '<' : {
                    grid[guard.x][guard.y] = '^';
                    break;
                }
            }
            return true;
        }
        grid[next.x][next.y] = direction;
        grid[guard.x][guard.y] = 'X';
        guard = next;
        return true;
    }

    private Point getNextPosition(char direction) {
        Point next = null;
        switch (direction) {
            case '^' : {
                next = new Point(guard.x, guard.y - 1);
                break;
            }
            case '>' : {
                next = new Point(guard.x + 1, guard.y);
                break;
            }
            case 'v' : {
                next = new Point(guard.x, guard.y + 1);
                break;
            }
            case '<' : {
                next = new Point(guard.x - 1, guard.y);
                break;
            }
        }
        return next;
    }

    private int countVisited() {
        int visited = 0;
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                if(grid[x][y] == 'X') { 
                    visited++;
                }
            }
        }
        return visited;
    }

    private void printGrid() {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

}
