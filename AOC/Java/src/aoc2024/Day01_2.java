package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day01_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day01/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day01/input.txt";
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> leftCol = new ArrayList<Integer>();
        Map<Integer, Integer> rightCol = new HashMap<Integer, Integer>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            for (String string : lines) {
                ArrayList<Integer> numList = new ArrayList<Integer>();
                for(String numString : string.split("\\s+")) {
                    numList.add(Integer.parseInt(numString));
                }
                if(numList.size() != 2) {
                    System.out.println("problem with line " + string);
                }
                leftCol.add(numList.get(0));
                int rightNum = numList.get(1);
                int count = rightCol.containsKey(rightNum) ? rightCol.get(rightNum) : 0;
                rightCol.put(rightNum, count + 1);
            }
            int sumSimilarity = 0;
            for(int leftNum : leftCol) {
                int count = rightCol.containsKey(leftNum) ? rightCol.get(leftNum) : 0;
                sumSimilarity += leftNum * count;
            }
            System.out.println("sumSimilarity = " + sumSimilarity);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
