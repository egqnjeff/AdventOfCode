package aoc2025.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day01_2 {
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
                countZero += rotate / 100;
                rotate = rotate % 100;
                if(direction == 'L') {
                    rotate = -rotate;
                }
                int _dial = dial + rotate;
                if(dial != 0 && (_dial < 1 || _dial > 99)) {
                    countZero++;
                    System.out.println("Direction = " + direction +
                        " amount = " + rotate + 
                        " original = " + dial +
                        " new = " + _dial);
                }
                dial = (_dial + 100) % 100;
            }
            System.out.println("Final count = " + countZero);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
