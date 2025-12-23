package aoc2025.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day05_2 {
    // private static String inputPath = "AOC/Java/resource/2025/Day05/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day05/input.txt";
    public static void main(String[] args) throws Exception {
        new Day05_2();
    }

    public Day05_2() {
        long sum = 0;
        List<Range> ranges = new ArrayList<Range>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                if(line.contains("-")) {
                    ranges.add(new Range(line));
                }
            }

            List<Range> collapsed = collapseRanges(ranges);
            while(ranges.size() > collapsed.size()) {
                ranges = collapsed;
                collapsed = collapseRanges(ranges);
            }

            for(Range range : ranges) {
                // System.out.println(range);
                sum += range.length();
            }
            System.out.println("Final sum = " + sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public List<Range> collapseRanges(List<Range> ranges) {
        List<Range> newRanges = new ArrayList<Range>();
        for(Range range : ranges) {
            boolean overlapped = false;
            for(Range newRange : newRanges) {
                if(newRange.overlaps(range)) {
                    overlapped = true;
                    newRange.extend(range);
                }
            }
            if(!overlapped) {
                newRanges.add(range);
            }
        }
        return newRanges;
    }

    public class Range {
        public long lower = 0;
        public long upper = 0;

        public Range(String input) {
            String[] split = input.split("-");
            lower = Long.parseLong(split[0]);
            upper = Long.parseLong(split[1]);
        }

        public boolean containsValue(long value) {
            return (value >= lower && value <= upper);
        }

        public boolean overlaps(Range range) {
            return (range.lower <= upper && range.upper >= lower);
        }

        public void extend(Range range) {
            lower = Math.min(lower, range.lower);
            upper = Math.max(upper, range.upper);
        }

        public long length() {
            return upper - lower + 1;
        }

        public String toString() {
            return lower + "-" + upper;
        }
    }
 }
