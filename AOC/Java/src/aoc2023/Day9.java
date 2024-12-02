package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Day9 {
    // private static String inputPath = "AOC/Java/resource/2023/Day9/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day9/input.txt";

    public static void main(String[] args) throws Exception {
        new Day9();
    }
    
    public Day9() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            int sum = 0;

            for (String string : lines) {
                ArrayList<Integer> numList = new ArrayList<Integer>();
                for(String numString : string.split("\\s+")) {
                    numList.add(Integer.parseInt(numString));
                }
                int nextNum = getNextNum(numList);
                // System.out.println(nextNum);
                sum += nextNum;
            }
            System.out.println(sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static int getNextNum(ArrayList<Integer> numList) { 
        ArrayList<Integer> nextNumList = new ArrayList<Integer>();
        // System.out.println(numList);
        int sum = 0;
        for (int i = 1; i < numList.size(); i++) {
            int diff = numList.get(i) - numList.get(i - 1);
            sum += diff;
            nextNumList.add(diff);
        }
        int nextNum = 0;
        if(sum != 0) {
            nextNum = getNextNum(nextNumList);
        }
        return (numList.get(numList.size() - 1) + nextNum);
    } 
}
