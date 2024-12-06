package aoc2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;

public class Day03_1 {
    private static String inputPath = "AOC/Java/resource/2015/Day03/input.txt";
    public static void main(String[] args) throws Exception {
        new Day03_1();
    }

    HashMap<Point, Integer> houses = new HashMap<Point, Integer> ();
    public Day03_1() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            int x = 0;
            int y = 0;
            addPresent(x, y);
            for (String string : lines) {
                for(int i = 0; i < string.length(); i++) {
                    if(string.charAt(i) == '^') {
                        y++;
                    }
                    if(string.charAt(i) == '>') {
                        x++;
                    }
                    if(string.charAt(i) == 'v') {
                        y--;
                    }
                    if(string.charAt(i) == '<') {
                        x--;
                    }
                    addPresent(x, y);
                }
            }
            System.out.println("Houses = " + houses.size());
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void addPresent(int x, int y) {
        Point p = new Point(x,y);
        Integer count = houses.get(p);
        if(count == null) {
            houses.put(p, 1);
        } else {
            houses.put(p, count + 1);
        }
    }

 }
