package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day02_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day02/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day02/input.txt";
    public static void main(String[] args) throws Exception {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            int countSafeReports = 0;

            for (String string : lines) {
                int previous = 0;
                float sign = 0f;
                boolean safe = true;
                for(String numString : string.split("\\s+")) {
                    int next = Integer.parseInt(numString);
                    if(previous != 0) {
                        int levelDiff = previous - next;
                        if(sign == 0) {
                            sign = Math.signum(levelDiff);
                        }
                        if(Math.signum(levelDiff) != sign) {
                            safe = false;
                            break;
                        }
                        if(levelDiff == 0 || Math.abs(levelDiff) > 3) {
                            safe = false;
                            break;
                        }
                    }
                    previous = next;
                }
                if(safe) {
                    countSafeReports++;
                }
            }
            System.out.println("countSafeReports = " + countSafeReports);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
