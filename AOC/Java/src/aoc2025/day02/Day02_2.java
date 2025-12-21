package aoc2025.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day02_2 {
    // private static String inputPath = "AOC/Java/resource/2025/Day02/test.txt";
    private static String inputPath = "AOC/Java/resource/2025/Day02/input.txt";
    public static void main(String[] args) throws Exception {
        long sumIds = 0;
        List<Long> invalidIds = new ArrayList<Long>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                for (String range : line.split(",")) {
                    // System.out.println("Range = " + range);
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
        for(Long value = lowerLong; value <= upperLong; value++) {
            // System.out.println("Value=" + value);
            if(isInvalidId(value)) {
                // System.out.println(" InvalidId " + value);
                invalidIds.add(value);
            }
        }
        return invalidIds;
    }

    public static boolean isInvalidId(Long id) {
        String idStr = Long.toString(id);
        int size = 1;
        while (size <= (idStr.length() / 2)) {
            if(idStr.length() % size == 0) {
                String base = idStr.substring(0, size);
                int last = 0;
                boolean invalid = true;
                for(int next = size; next <= idStr.length(); next += size) {
                    // System.out.print(" Size=" + size + " base=" + base);
                    // System.out.print(" last=" + last + " next=" + next);
                    // System.out.println(" subStr=" + idStr.substring(last, next));
                    if(!base.equals(idStr.substring(last, next))) {
                        invalid = false;
                        break;
                    }
                    last = next;
                }
                if(invalid) {
                    return true;
                }
            }
            size++;
        }
        return false;
    }

}
