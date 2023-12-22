import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    // private static String inputPath = "2023/Java/resource/Day6/test.txt";
    private static String inputPath = "2023/Java/resource/Day6/input.txt";

    public static void main(String[] args) throws Exception {
        new Day6();
    }
    
    public Day6() {
        List<Integer> times = new ArrayList<Integer>();
        List<Integer> distances = new ArrayList<Integer>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            String[] timeSplit = lines.get(0).trim().split("\\s+");
            for (int i = 1; i < timeSplit.length; i++) {
                times.add(Integer.parseInt(timeSplit[i]));
            }
            System.out.println(times);
            String[] distanceSplit = lines.get(1).trim().split("\\s+");
            for (int i = 1; i < distanceSplit.length; i++) {
                distances.add(Integer.parseInt(distanceSplit[i]));
            }
            System.out.println(distances);

            int product = 1;
            for(int i = 0; i < times.size(); i++) {
                int time = times.get(i);
                int distance = distances.get(i);
                int count = 0;
                for(int t = 1; t < time; t++) {
                    if(t * (time - t) > distance) {
                        count++;
                    }
                }
                System.out.println(count);
                product = product * count;
            }
            System.out.println(product);

        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
