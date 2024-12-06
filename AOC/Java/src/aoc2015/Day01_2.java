package aoc2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day01_2 {
    private static String inputPath = "AOC/Java/resource/2015/Day01/input.txt";
    public static void main(String[] args) throws Exception {
        int floor = 0;
        int position = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));

            for (String string : lines) {
                for(int i = 0; i < string.length(); i++) {
                    position++;
                    if(string.charAt(i) == '(') {
                        floor++;
                    }
                    if(string.charAt(i) == ')') {
                        floor--;
                    }
                    if(floor == -1) {
                        System.out.println("Basement at position = " + position);
                    }
                }
            }
            System.out.println("Floor = " + floor);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

 }
