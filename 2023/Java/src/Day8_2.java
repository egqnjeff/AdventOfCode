import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Day8_2 {
    // private static String inputPath = "2023/Java/resource/Day8/test.txt";
    // private static String inputPath = "2023/Java/resource/Day8/test2.txt";
    // private static String inputPath = "2023/Java/resource/Day8/test3.txt";
    private static String inputPath = "2023/Java/resource/Day8/input.txt";

    public static void main(String[] args) throws Exception {
        new Day8_2();
    }
    
    public Day8_2() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            String instructions = lines.get(0).trim();
            List<String> start = new ArrayList<String>();
            Map<String, String> leftMap = new HashMap<String, String>();
            Map<String, String> rightMap = new HashMap<String, String>();
            List<Long> loopSize = new ArrayList<Long>();
    
            for(int i = 2; i < lines.size(); i++) {
                String[] split = lines.get(i).split("[ =(,)]+");
                leftMap.put(split[0], split[1]);
                rightMap.put(split[0], split[2]);
                if(split[0].endsWith("A")) {
                    start.add(split[0]);
                }
            }
            System.out.println("Start " + start);

            for (String current : start) {
                long steps = 0;
                int i = 0;
                while(!current.endsWith("Z")) {
                    // System.out.println("Mapping " + current);
                    // System.out.println("i " + i + " " + instructions.charAt(i));
                    // System.out.println("step " + steps);
                    steps++;
                    if(instructions.charAt(i) == 'L') {
                        current = leftMap.get(current);
                    } else {
                        current = rightMap.get(current);
                    }
                    i = (i + 1) % instructions.length();
                }
                loopSize.add(steps);
            }

            System.out.println(loopSize);
            long lcm = 1;
            for (Long size : loopSize) {
                lcm = findLCM(lcm, size);
            }
            System.out.println(lcm);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static long findLCM(long a, long b) { 
        long greater = Math.max(a, b); 
        long smallest = Math.min(a, b); 
        for (long i = greater;; i += greater) { 
            if (i % smallest == 0) 
                return i; 
        } 
    } 
}
