package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day03/test2.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day03/input.txt";
    public static void main(String[] args) throws Exception {
        new Day03_2();
    }

    public Day03_2() {
        long result = 0;
        boolean skip = false;
        ArrayList<String> matches = new ArrayList<String>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String string : lines) {
                matches.addAll(parseMatches(string));
            }
            for (String operation : matches) {
                // System.out.println(operation);
                if(isDo(operation)) {
                    skip = false;
                }
                if(isDont(operation)) {
                    skip = true;
                }
                if(!skip && isMul(operation)) {
                    // System.out.println("Perform " + operation);
                    result += performOperation(operation);
                }
            }
            System.out.println("Result = " + result);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public ArrayList<String> parseMatches(String string) {
        // System.out.println(string);
        ArrayList<String> matches = new ArrayList<String>();
        Pattern pattern = Pattern.compile("(do\\(\\))|(don\\'t\\(\\))|(mul\\(\\d{1,3},\\d{1,3}\\))");
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()) {
            matches.add(matcher.group(0));
        }
        // System.out.println(matches);
        return matches;
    }
    public int performOperation(String string) {
        Pattern pattern = Pattern.compile("\\d{1,3}");
        Matcher matcher = pattern.matcher(string);
        matcher.find();
        int term1 = Integer.parseInt(matcher.group(0));
        matcher.find();
        int term2 = Integer.parseInt(matcher.group(0));
        return term1 * term2;
    }

    public boolean isDo(String string) {
        return string.startsWith("do(");
    }

    public boolean isDont(String string) {
        return string.startsWith("don");
    }

    public boolean isMul(String string) {
        return string.startsWith("mul");
    }
}
