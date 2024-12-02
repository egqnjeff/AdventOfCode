package aoc2023;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day2 {
    public static void main(String[] args) throws Exception {
        new Day2();
    }
    
    public Day2() {
        // List<String> testLines = new ArrayList<String>();
        // testLines.add("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green");
        // testLines.add("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue");
        // testLines.add("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red");
        // testLines.add("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red");
        // testLines.add("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green");
        // Map<Integer, List<CubeSet>> guesses = parseGuess(testLines);

        // // CubeSet testSet = new CubeSet(13,12,14);

        // // int sum = sumPossibleGames(testSet, guesses);
        // int sum = sumGamePower(guesses);
        // System.out.println("Game Total = " + sum);
        
        try {
			List<String> allLines = Files.readAllLines(Paths.get("AOC/Java/resource/2023/Day2/input.txt"));
			Map<Integer, List<CubeSet>> guesses = parseGuess(allLines);
            CubeSet testSet = new CubeSet(13,12,14);
            // int sum = sumPossibleGames(testSet, guesses);
            int sum = sumGamePower(guesses);
            System.out.println("Game Total = " + sum);
        } catch (IOException e) {
			e.printStackTrace();
		}

    }

    private int sumPossibleGames(CubeSet testSet, Map<Integer, List<CubeSet>> guesses) {
        int sum = 0;
        for (int gameNumber : guesses.keySet()) {
            boolean isPossible = true;
            for (CubeSet guess : guesses.get(gameNumber)) {
                isPossible = isPossible && testSet.isPossible(guess);
            }
            if(isPossible) {
                sum += gameNumber;
                System.out.println(gameNumber);
            }
        }
        return sum;
    }

    private int sumGamePower(Map<Integer, List<CubeSet>> guesses) {
        int sum = 0;
        for (int gameNumber : guesses.keySet()) {
            int red = 0;
            int green = 0;
            int blue = 0;
            for (CubeSet guess : guesses.get(gameNumber)) {
                red = Math.max(red, guess.red);
                green = Math.max(green, guess.green);
                blue = Math.max(blue, guess.blue);
            }
            int power = red * green * blue;
            System.out.println(power);
            sum += power;
        }
        return sum;
    }

    private Map<Integer, List<CubeSet>> parseGuess(List<String> allLines) {
        Map<Integer, List<CubeSet>> returnMap = new HashMap<Integer, List<CubeSet>>();
        for (String line : allLines) {
            // System.out.println(line);
            String[] gameSplit = line.split(":");
            int gameNumber = Integer.parseInt(gameSplit[0].replaceAll("Game ", ""));
            // System.out.println(gameNumber);
            List<CubeSet> guessList = new ArrayList<CubeSet>();
            String[] guessSplit = gameSplit[1].split(";");
            for (String guess : guessSplit) {
                guessList.add(new CubeSet(guess));
            }
            returnMap.put(gameNumber, guessList);
            // String s = "" + numberOnly.charAt(0) + numberOnly.charAt(numberOnly.length() - 1);
            // int i = Integer.parseInt(s);
            // System.out.println(i);
            // count += i;
        }
        return returnMap;
    }

    class CubeSet {

        int red = 0;
        int blue = 0;
        int green = 0;

        /*
         * Input in the form of 
         * 1 green, 3 red, 6 blue
         * with each being optional
         */
        public CubeSet(String input) {
            String[] split = input.split(",");
            for (String string : split) {
                if(string.contains("green")) {
                    green = Integer.parseInt(string.replaceAll("[^0-9]", ""));
                } else if(string.contains("red")) {
                    red = Integer.parseInt(string.replaceAll("[^0-9]", ""));
                } else if(string.contains("blue")) {
                    blue = Integer.parseInt(string.replaceAll("[^0-9]", ""));
                }
            }
        }

        public CubeSet(int green, int red, int blue) {
            this.green = green;
            this.red = red;
            this.blue = blue;
        }

        public boolean isPossible(CubeSet other) {
            return (red >= other.red && green >= other.green && blue >= other.blue); 
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(green);
            sb.append(" green, ");
            sb.append(red);
            sb.append(" red, ");
            sb.append(blue);
            sb.append(" blue");
            return sb.toString();
        }
    }
}
