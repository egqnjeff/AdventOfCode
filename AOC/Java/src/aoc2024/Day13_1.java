package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day13/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day13/input.txt";
    public static void main(String[] args) throws Exception {
        new Day13_1();
    }

    Pattern pattern = Pattern.compile("\\d+");
    ArrayList<Configuration> allConfigurations = new ArrayList<Configuration>();

    public Day13_1() {
        parseInput();
        int totalTokens = 0;
        for(Configuration configuration : allConfigurations) {
            totalTokens += configuration.getLowestCost();
        }
        System.out.println("Total tokens = " + totalTokens);
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            Iterator<String> lineIter = lines.iterator();
            while(lineIter.hasNext()) {
                String line = lineIter.next();
                if(line.startsWith("Button A")) {
                    Point buttonA = getPoint(line);
                    line = lineIter.next();
                    Point buttonB = getPoint(line);
                    line = lineIter.next();
                    Point prize = getPoint(line);
                    allConfigurations.add(new Configuration(buttonA, buttonB, prize));
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public Point getPoint(String line) {
        Matcher matcher = pattern.matcher(line);
        Point point = new Point();
        matcher.find();
        point.x = Integer.parseInt(matcher.group(0));
        matcher.find();
        point.y = Integer.parseInt(matcher.group(0));
        return point;
    }

    public class Configuration {

        Point buttonA;
        Point buttonB;
        Point prize;

        public Configuration(Point buttonA, Point buttonB, Point prize) {
            this.buttonA = buttonA;
            this.buttonB = buttonB;
            this.prize = prize;
        }

        public int getLowestCost() {
            int lowestCost = 0;
            int x = 0;
            int y = 0;
            int pressA = 0;
            while(x < prize.x && y < prize.y) {
                pressA++;
                x+= buttonA.x;
                y+= buttonA.y;
                int innerX = x;
                int innerY = y;
                int pressB = 0;
                while(innerX < prize.x && innerY < prize.y) {
                    pressB++;
                    innerX+= buttonB.x;
                    innerY+= buttonB.y;
                }
                if(innerX == prize.x && innerY == prize.y) {
                    int cost = (3 * pressA) + pressB;
                    if(lowestCost == 0 || cost < lowestCost) {
                        lowestCost = cost;
                    }
                }
            }
            return lowestCost;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Button A=" + buttonA);
            sb.append(" Button B=" + buttonB);
            sb.append(" Prize=" + prize);
            return sb.toString();
        }
    }

}
