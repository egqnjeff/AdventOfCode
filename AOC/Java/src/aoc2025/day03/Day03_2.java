package aoc2025.day03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day03_2 {
    // private static String inputPath = "AOC/Java/resource/2025/Day03/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day03/input.txt";
    public static void main(String[] args) throws Exception {
        new Day03_2();
    }

    public Day03_2() {
        long sum = 0;
        List<Long> joltages = new ArrayList<Long>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                joltages.add(getJoltage(line));
            }
            for (Long joltage : joltages) {
                System.out.println("joltage = " + joltage);
                sum += joltage;
            }
            System.out.println("Final sum = " + sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public Long getJoltage(String line) {
        String result = "";
        char[] chars = line.toCharArray();
        int start = 0;
        int digits = 12;
        while(digits > 0) {
            int maxPos = chars.length - digits;
            char maxChar = '0';
            int charPos = -1;
            for(int i = start; i <= maxPos; i++) {
                if(chars[i] > maxChar) {
                    charPos = i;
                    maxChar = chars[i];
                }
            }
            result = result + maxChar;
            start = charPos + 1;
            digits--;            
        }
        return Long.parseLong(result);
    }
 }
