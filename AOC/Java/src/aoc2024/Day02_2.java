package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day02_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day02/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day02/input.txt";
    public static void main(String[] args) throws Exception {
        new Day02_2();
    }

    public Day02_2() {
        int countSafeReports = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String string : lines) {
                ArrayList<Integer> report = new ArrayList<Integer>();
                for(String numString : string.split("\\s+")) {
                    report.add(Integer.parseInt(numString));
                }
                if(isSafeReport(report)) {
                    countSafeReports++;
                }
            }
            System.out.println("countSafeReports = " + countSafeReports);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public boolean isSafeReport(ArrayList<Integer> report) {
        boolean safe = testReport(report);
        int i = 0;
        while(!safe && i < report.size()) {
            ArrayList<Integer> reportCopy = new ArrayList<Integer>();
            reportCopy.addAll(report);
            reportCopy.remove(i);
            safe = testReport(reportCopy);
            i++;
        }
        return safe;
    }

    public boolean testReport(ArrayList<Integer> report) {
        int previous = 0;
        float sign = 0f;
        boolean safe = true;
        for(int next : report) {
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
        return safe;
    }
 }
