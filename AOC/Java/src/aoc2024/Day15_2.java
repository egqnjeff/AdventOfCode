package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Day15_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day15/test1.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day15/test2.txt";
    // private static String inputPath = "AOC/Java/resource/2024/Day15/input.txt";
    public static void main(String[] args) throws Exception {
        new Day15_2();
    }

    Pattern pattern = Pattern.compile("-?\\d+");

    ArrayList<Character> operations = new ArrayList<Character>();
    Point robot = new Point();
    char[][] grid;
    int w;
    int h;
    
    public Day15_2() {
        parseInput();
        // findRobot();
        // for(char dir : operations) {
        //     moveRobot(dir);
        // }
        System.out.println(toString());
        System.out.println("GPS = " + calculateGPS());
    }

    private void initGrid(ArrayList<String> lines) {
        w = lines.get(0).length() * 2;
        h = lines.size();
        grid = new char[w][h];
        for(int y = 0; y < h; y++) {
            String line = lines.get(y);
            for(int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                switch (c) {
                    case '#' : {
                        grid[x * 2][y] = '#';
                        grid[x * 2 + 1][y] = '#';
                        break;
                    }
                    case 'O' : {
                        grid[x * 2][y] = '[';
                        grid[x * 2 + 1][y] = ']';
                        break;
                    }
                    case '.' : {
                        grid[x * 2][y] = '.';
                        grid[x * 2 + 1][y] = '.';
                        break;
                    }
                    case '@' : {
                        grid[x * 2][y] = '@';
                        grid[x * 2 + 1][y] = '.';
                    }
                }
            }
        }
    }

    private void parseInput() {
        ArrayList<String> gridLines = new ArrayList<String>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            boolean gridMode = true;
            for (String line : lines) {
                if(gridMode) {
                    if(line.length() > 0) {
                        gridLines.add(line);
                    } else {
                        gridMode = false;
                        initGrid(gridLines);
                    }
                } else {
                    for (Character c : line.toCharArray()) {
                        if(c == '<' || c == '>' || c == '^' || c == 'v') {
                            operations.add(c);
                        }
                    }
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void findRobot() {
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                if(grid[i][j] == '@') {
                    robot.x = i;
                    robot.y = j;
                    return;
                }
            }
        }
    }

    public void moveRobot(char dir) {
        // System.out.println("Move robot - " + robot + " Dir - " + dir);
        int deltaX = 0;
        int deltaY = 0;
        switch (dir) {
            case '^' : {
                deltaY = -1;
                break;
            }
            case '>' : {
                deltaX = 1;
                break;
            }
            case 'v' : {
                deltaY = 1;
                break;
            }
            case '<' : {
                deltaX = -1;
                break;
            }
        }
        boolean done = false;
        boolean canPush = false;
        Point point = new Point(robot);
        while(!done) {
            point.x = point.x + deltaX;
            point.y = point.y + deltaY;
            if(point.x < 0 || point.x >= w || point.y < 0 || point.y >= h) {
        // System.out.println("OB");
                done = true;
            } else if(grid[point.x][point.y] == '.') {
        // System.out.println("Can Move");
                canPush = true;
                done = true;
            } else if(grid[point.x][point.y] == '#') {
        // System.out.println("Can't Move");
                canPush = false;
                done = true;
            }
        }
        if(canPush) {
            grid[robot.x][robot.y] = '.';
            robot.x = robot.x + deltaX;
            robot.y = robot.y + deltaY;
            grid[robot.x][robot.y] = '@';
            if(robot.x != point.x || robot.y != point.y) {
                grid[point.x][point.y] = 'O';
            }
        }
    }

    public long calculateGPS() {
        long result = 0;
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                if(grid[i][j] == '[') {
                    long gps = (100 * j) + i;
                    // System.out.println(gps);
                    result += gps;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        sb.append("\n");
        for(char c : operations) {
            sb.append(c);
        }
        sb.append("\n");
        return sb.toString();
    }

}
