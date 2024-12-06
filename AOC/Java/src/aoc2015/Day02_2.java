package aoc2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day02_2 {
    private static String inputPath = "AOC/Java/resource/2015/Day02/input.txt";
    public static void main(String[] args) throws Exception {
        long ribbonFeet = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            for (String string : lines) {
                String[] split = string.split("x");
                int l = Integer.parseInt(split[0]);
                int w = Integer.parseInt(split[1]);
                int h = Integer.parseInt(split[2]);

                int lw = l + w;
                int lh = l + h;
                int wh = w + h;
                int smallest = lw;
                if(lh < smallest) {
                    smallest = lh;
                }
                if(wh < smallest) {
                    smallest = wh;
                }
                ribbonFeet += (2 * smallest) + (l * w * h);
            }
            System.out.println("Ribbon Feet = " + ribbonFeet);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
