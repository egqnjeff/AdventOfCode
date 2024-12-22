package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_2 {
    // int width = 11;
    // int height = 7;
    // private static String inputPath = "AOC/Java/resource/2024/Day14/test.txt";
    int width = 101;
    int height = 103;
    private static String inputPath = "AOC/Java/resource/2024/Day14/input.txt";
    public static void main(String[] args) throws Exception {
        new Day14_2();
    }

    Pattern pattern = Pattern.compile("-?\\d+");

    ArrayList<Robot> allRobots = new ArrayList<Robot>();

    long min = 999999999999l;

    public Day14_2() {
        parseInput();
        ArrayList<Point> points = new ArrayList<Point>();
        for(int i = 0; i < 100000; i++) {
            points.clear();
            for(Robot robot : allRobots) {
                Point p = robot.move(i);
                points.add(p);
            }
            long sumSquared = checkForTree(points);
            if(sumSquared < min) {
                min = sumSquared;
                System.out.println("Pass:" + i + " check: " + sumSquared);
                System.out.println(toString(points));
            }
        }
    }

    public long checkForTree(ArrayList<Point> points) {
        int midX = width / 2;
        int midY = height / 2;
        long sumSquare = 0;
        for(Point point : points) {
            int deltaX = midX - point.x;
            int deltaY = midY - point.y;
            sumSquare += (deltaX*deltaX) + (deltaY*deltaY);
        }
        return sumSquare;
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                allRobots.add(new Robot(line));
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private String toString(ArrayList<Point> points) {
        char[][] grid = new char[width][height];
        for (Point point : points) {
            grid[point.x][point.y] = '*';
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                if(grid[i][j] == '*') {
                    sb.append("*");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public class Robot {
        
        Point start;
        Point vector;
        
        public Robot(String input) {
            parseInput(input);
        }
        
        public void parseInput(String input) {
            Matcher matcher = pattern.matcher(input);
            start = new Point();
            matcher.find();
            start.x = Integer.parseInt(matcher.group(0));
            matcher.find();
            start.y = Integer.parseInt(matcher.group(0));

            vector = new Point();
            matcher.find();
            vector.x = Integer.parseInt(matcher.group(0));
            matcher.find();
            vector.y = Integer.parseInt(matcher.group(0));
        }
    
        public Point move(int seconds) {
            Point location = new Point();
            location.x = (start.x + (seconds * vector.x)) % width;
            location.y = (start.y + (seconds * vector.y)) % height;
            if(location.x < 0) {
                location.x = location.x + width;
            }
            if(location.y < 0) {
                location.y = location.y + height;
            }
            return location;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Start=" + start);
            sb.append(" Vector=" + vector);
            return sb.toString();
        }
    }

}
