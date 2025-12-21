package aoc2025.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day02_1 {
    // private static String inputPath = "AOC/Java/resource/2025/Day02/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day02/input.txt";
    public static void main(String[] args) throws Exception {
        long sumIds = 0;
        List<Long> invalidIds = new ArrayList<Long>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                for (String range : line.split(",")) {
                    System.out.println("Range = " + range);
                    invalidIds.addAll(getInvalidIds(range));
                }
            }
            for (Long invalidId : invalidIds) {
                // System.out.println("InvalidId = " + invalidId);
                sumIds += invalidId;
            }
            System.out.println("Final sum = " + sumIds);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static List<Long> getInvalidIds(String range) {
        List<Long> invalidIds = new ArrayList<Long>();
        String[] split = range.split("-");
        String lowerStr = split[0];
        long lowerLong = Long.parseLong(lowerStr);
        String upperStr = split[1];
        long upperLong = Long.parseLong(upperStr);
        int len = lowerStr.length() / 2;
        long seed = 0;
        if(len > 0 ) {
            seed = Long.parseLong(lowerStr.substring(0, len));
        }
        long guess = 0;
        while(guess < upperLong) {
            guess = Long.parseLong(Long.toString(seed) + Long.toString(seed));
            if(guess >= lowerLong && guess <= upperLong) {
                invalidIds.add(guess);
                // System.out.println("InvalidId " + guess);
            }
            seed++;
        }
        return invalidIds;
    }
 }
