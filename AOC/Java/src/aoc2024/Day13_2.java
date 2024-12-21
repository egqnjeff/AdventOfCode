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

public class Day13_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day13/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day13/input.txt";
    public static void main(String[] args) throws Exception {
        new Day13_2();
    }

    Pattern pattern = Pattern.compile("\\d+");
    ArrayList<Configuration> allConfigurations = new ArrayList<Configuration>();

    public Day13_2() {
        parseInput();
        long totalTokens = 0;
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
                    prize.x = prize.x;
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
        long prizeX;
        long prizeY;

        public Configuration(Point buttonA, Point buttonB, Point prize) {
            this.buttonA = buttonA;
            this.buttonB = buttonB;
            prizeX = prize.x + 10000000000000l;
            prizeY = prize.y + 10000000000000l;
        }

        public long getLowestCost() {
            long cost = 0;
            long numerator = (prizeY * buttonA.x) - (prizeX * buttonA.y);
            long denominator = (buttonB.y * buttonA.x) - (buttonB.x * buttonA.y);
            if(numerator % denominator == 0) {
                long pressB = numerator / denominator;
                long pressA = (prizeX - (pressB * buttonB.x)) / buttonA.x;
                long myPrizeX = (pressA * buttonA.x) + (pressB * buttonB.x);
                long myPrizeY = (pressA * buttonA.y) + (pressB * buttonB.y);
                if(myPrizeX == prizeX && myPrizeY == prizeY) {
                    cost = (3 * pressA) + pressB;
                }
            }
            return cost;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Button A=" + buttonA);
            sb.append(" Button B=" + buttonB);
            sb.append(" Prize=[" + prizeX + "," + prizeY + "]");
            return sb.toString();
        }
    }

}
