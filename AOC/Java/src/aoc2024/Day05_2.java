package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day05_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day05/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day05/input.txt";
    public static void main(String[] args) throws Exception {
        new Day05_2();
    }

    ArrayList<Integer[]> orderingRules = new ArrayList<Integer[]>();
    ArrayList<Integer[]> updatedPages = new ArrayList<Integer[]>();

    public Day05_2() {
        int sumMidPages = 0;
        parseInput();
        for (Integer[] pages : updatedPages) {
            if(testPages(pages) == 0) {
                sumMidPages += fixPages(pages);
            }
        }
        System.out.println("Sum middle pages = " + sumMidPages);
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            boolean rulesPhase = true;
            for (String line : lines) {
                if(line.length() == 0) {
                    rulesPhase = false;
                } else if(rulesPhase) {
                    orderingRules.add(parseRulesLine(line));
                } else {
                    updatedPages.add(parsePagesLine(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
		}
    }
    
    private Integer[] parseRulesLine(String line) {
        // System.out.print("Rule ");
        Integer[] rule = new Integer[2];
        String[] values = line.split("\\|");
        rule[0] = Integer.parseInt(values[0]);
        rule[1] = Integer.parseInt(values[1]);
        // System.out.println(rule[0] + "," + rule[1]);
        return rule;
    }
    
    private Integer[] parsePagesLine(String line) {
        // System.out.print("Pages ");
        String[] values = line.split(",");
        Integer[] pages = new Integer[values.length];
        for(int i = 0; i < values.length; i++) {
            pages[i] = Integer.parseInt(values[i]);
            // System.out.print(pages[i] + ",");
        }
        // System.out.println();
        return pages;
    }

    private int testPages(Integer[] pages) {
        for (Integer[] rule : orderingRules) {
            if(!testRule(rule, pages)) {
                return 0;
            }
        }
        return (pages[(pages.length -1) /2]);
    }

    private int fixPages(Integer[] pages) {
        int result = testPages(pages);
        while(result == 0) {
            for (Integer[] rule : orderingRules) {
                fixRule(rule, pages);
            }
            result = testPages(pages);
        }
        return (pages[(pages.length -1) /2]);
    }

    private boolean testRule(Integer[] rule, Integer[] pages) {
        int first = -1;
        int second = -1;
        for (int i = 0; i < pages.length; i++) {
            if(rule[0] == pages[i]) {
                first = i;
            }
            if(rule[1] == pages[i]) {
                second = i;
            }
        }
        if(second != -1 && first > second) {
            return false;
        }
        return true;
    }

    private void fixRule(Integer[] rule, Integer[] pages) {
        int first = -1;
        int second = -1;
        for (int i = 0; i < pages.length; i++) {
            if(rule[0] == pages[i]) {
                first = i;
            }
            if(rule[1] == pages[i]) {
                second = i;
            }
        }
        if(second != -1 && first > second) {
            int temp = pages[first];
            pages[first] = pages[second];
            pages[second] = temp;
        }
    }
}
