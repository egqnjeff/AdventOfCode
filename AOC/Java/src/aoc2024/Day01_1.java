package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day01/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day01/input.txt";
    public static void main(String[] args) throws Exception {
        ArrayList<Integer> leftCol = new ArrayList<Integer>();
        ArrayList<Integer> rightCol = new ArrayList<Integer>();
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
                rightCol.add(numList.get(1));
            }
            Collections.sort(leftCol);
            Collections.sort(rightCol);
            int sumDiff = 0;
            for(int i = 0; i < leftCol.size(); i++) {
                sumDiff += Math.abs(leftCol.get(i) - rightCol.get(i));
            }
            System.out.println("SumDiff = " + sumDiff);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
