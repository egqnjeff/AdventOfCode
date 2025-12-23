package aoc2025.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day05_1 {
    // private static String inputPath = "AOC/Java/resource/2025/Day05/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day05/input.txt";
    public static void main(String[] args) throws Exception {
        new Day05_1();
    }

    public Day05_1() {
        int sum = 0;
        List<Range> ranges = new ArrayList<Range>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                if(line.contains("-")) {
                    ranges.add(new Range(line));
                } else if(line.length() > 0) {
                    long value = Long.parseLong(line);
                    for(Range range : ranges) {
                        if(range.containsValue(value)) {
                            sum++;
                            break;
                        }
                    }
                }
            }
            System.out.println("Final sum = " + sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public class Range {
        long lower = 0;
        long upper = 0;

        public Range(String input) {
            String[] split = input.split("-");
            lower = Long.parseLong(split[0]);
            upper = Long.parseLong(split[1]);
        }

        public boolean containsValue(long value) {
            return (value >= lower && value <= upper);
        }
    }
 }
