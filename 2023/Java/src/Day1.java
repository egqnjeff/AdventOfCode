import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws Exception {
        // List<String> testLines = new ArrayList<String>();
        // testLines.add("two1nine");
        // testLines.add("eightwothree");
        // testLines.add("abcone2threexyz");
        // testLines.add("xtwone3four");
        // testLines.add("4nineeightseven2");
        // testLines.add("zoneight234");
        // testLines.add("7pqrstsixteen");
        // int count = countLines(testLines);
        // System.out.println("Total Value: " + count);

        try {
			List<String> allLines = Files.readAllLines(Paths.get("2023/Java/resource/Day1/input.txt"));
			int count = countLines(allLines);
            System.out.println("Total Value: " + count);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private static int countLines(List<String> allLines) {
        int count = 0;
        for (String line : allLines) {
            System.out.println(line);
            String numberOnly = line.replaceAll("one", "one1one");
            numberOnly = numberOnly.replaceAll("two", "two2two");
            numberOnly = numberOnly.replaceAll("three", "three3three");
            numberOnly = numberOnly.replaceAll("four", "four4four");
            numberOnly = numberOnly.replaceAll("five", "five5five");
            numberOnly = numberOnly.replaceAll("six", "six6six");
            numberOnly = numberOnly.replaceAll("seven", "seven7seven");
            numberOnly = numberOnly.replaceAll("eight", "eight8eight");
            numberOnly = numberOnly.replaceAll("nine", "nine9nine");
            numberOnly = numberOnly.replaceAll("[^0-9]", "");
            System.out.println(numberOnly);
            String s = "" + numberOnly.charAt(0) + numberOnly.charAt(numberOnly.length() - 1);
            int i = Integer.parseInt(s);
            System.out.println(i);
            count += i;
        }
        return count;
    }
}
