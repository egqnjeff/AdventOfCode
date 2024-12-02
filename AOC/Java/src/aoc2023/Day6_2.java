package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6_2 {
    // private static String inputPath = "AOC/Java/resource/2023/Day6/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day6/input.txt";

    public static void main(String[] args) throws Exception {
        new Day6_2();
    }
    
    public Day6_2() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            String[] timeSplit = lines.get(0).split(":");
            long time = Long.parseLong(timeSplit[1].replaceAll("\\s", ""));
            System.out.println(time);

            String[] distanceSplit = lines.get(1).split(":");
            long distance = Long.parseLong(distanceSplit[1].replaceAll("\\s", ""));
            System.out.println(distance);

            long count = 0;
            for(long t = 1; t < time; t++) {
                if(t * (time - t) > distance) {
                    count++;
                }
            }
            System.out.println(count);

        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
