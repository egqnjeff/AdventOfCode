package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14_1 {
    // int width = 11;
    // int height = 7;
    // private static String inputPath = "AOC/Java/resource/2024/Day14/test.txt";
    int width = 101;
    int height = 103;
    private static String inputPath = "AOC/Java/resource/2024/Day14/input.txt";
    public static void main(String[] args) throws Exception {
        new Day14_1();
    }

    Pattern pattern = Pattern.compile("-?\\d+");

    ArrayList<Robot> allRobots = new ArrayList<Robot>();

    public Day14_1() {
        parseInput();
        int midX = width / 2;
        int midY = height / 2;
        int[] quadrants = new int[4];
        for(Robot robot : allRobots) {
            // System.out.println(robot);
            Point p = robot.move(100);
            // System.out.println(p);
            if(p.x < midX && p.y < midY) {
                quadrants[0] = quadrants[0] + 1;
            }
            if(p.x > midX && p.y < midY) {
                quadrants[1] = quadrants[1] + 1;
            }
            if(p.x < midX && p.y > midY) {
                quadrants[2] = quadrants[2] + 1;
            }
            if(p.x > midX && p.y > midY) {
                quadrants[3] = quadrants[3] + 1;
            }
        }
        int result = 1;
        for(int quadrant : quadrants) {
            result = result * quadrant;
        }
        System.out.println("Result = " + result);
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
