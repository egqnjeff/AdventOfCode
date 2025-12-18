package aoc2025.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day01_1 {
    // private static String inputPath = "AOC/Java/resource/2025/Day01/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day01/input.txt";
    public static void main(String[] args) throws Exception {
        int countZero = 0;
        int dial = 50;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            for (String line : lines) {
                char direction = line.charAt(0);
                int rotate = Integer.parseInt(line.substring(1));
                if(direction == 'L') {
                    rotate = -rotate;
                }
                dial = dial + rotate;
                dial = (dial + 100) % 100;
                if(dial == 0) {
                    countZero++;
                }
                // System.out.println("Direction = " + direction + " amount = " + rotate + " dial = " + dial);
            }
            System.out.println("Final count = " + countZero);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
