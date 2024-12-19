package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day11/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day11/input.txt";
    public static void main(String[] args) throws Exception {
        new Day11_1();
    }

    int blinks = 25;

    public Day11_1() {
        ArrayList<String> stones = parseInput();
        System.out.println("Start - " + stonesString(stones));
        for(int i = 1; i <= blinks; i++) {
            stones = processStones(stones);
        // System.out.println("Pass [" + i + "] - " + stonesString(stones));
        }
        System.out.println("Num Stones = " + stones.size());
    }

    private ArrayList<String> parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            String line = lines.get(0);
            ArrayList<String> input = new ArrayList<String>();
            for(String numString : line.split("\\s+")) {
                input.add(numString);
            }
            return input;
        } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    private ArrayList<String> processStones(ArrayList<String> input) {
        ArrayList<String> output = new ArrayList<String>();
        for(String stone : input) {
            output.addAll(applyRules(stone));
        }
        return output;
    }

    private ArrayList<String> applyRules(String stone) {
        ArrayList<String> output = new ArrayList<String>();
        // rule 1
        // If the stone is engraved with the number 0, 
        // it is replaced by a stone engraved with the number 1.
        if(stone.equals("0")) {
            output.add("1");
        }
        
        // rule 2
        // If the stone is engraved with a number that has an even number of digits, 
        // it is replaced by two stones. 
        // The left half of the digits are engraved on the new left stone, 
        // and the right half of the digits are engraved on the new right stone. 
        // (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
        else if(stone.length() % 2 == 0) {
            output.add(stone.substring(0, stone.length() / 2));
            long right = Long.parseLong(stone.substring(stone.length() / 2, stone.length()));
            output.add("" + right);
        }
        // rule 3
        // If none of the other rules apply, 
        // the stone is replaced by a new stone; 
        // the old stone's number multiplied by 2024 is engraved on the new stone.
        else {
            long val = Long.parseLong(stone);
            val = val * 2024;
            output.add("" + val);
        }
        // rule 4
        // No matter how the stones change, 
        // their order is preserved, and they stay on their perfectly straight line.
        return output;
    }

    private String stonesString(ArrayList<String> stones) {
        StringBuilder sb = new StringBuilder();
        sb.append("Length=" + stones.size());
        sb.append("[");
        for (String stone : stones) {
            sb.append(stone);
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

}
