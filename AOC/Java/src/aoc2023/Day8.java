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

public class Day8 {
    // private static String inputPath = "AOC/Java/resource/2023/Day8/test.txt";
    // private static String inputPath = "AOC/Java/resource/2023/Day8/test2.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day8/input.txt";

    public static void main(String[] args) throws Exception {
        new Day8();
    }
    
    public Day8() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            String instructions = lines.get(0).trim();
            String current = "AAA";
            String end = "ZZZ";
            Map<String, String> leftMap = new HashMap<String, String>();
            Map<String, String> rightMap = new HashMap<String, String>();
    
            for(int i = 2; i < lines.size(); i++) {
                String[] split = lines.get(i).split("[ =(,)]+");
                leftMap.put(split[0], split[1]);
                rightMap.put(split[0], split[2]);
            }

            int steps = 0;
            int i = 0;
            while(!current.equals(end)) {
                // System.out.println("Mapping " + current);
                // System.out.println("i " + i);
                // System.out.println("step " + steps);
                steps++;
                if(instructions.charAt(i) == 'L') {
                    current = leftMap.get(current);
                } else {
                    current = rightMap.get(current);
                }
                i = (i + 1) % instructions.length();
            }

            System.out.println(steps);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
}
