package aoc2015;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.awt.Point;

public class Day03_2 {
    private static String inputPath = "AOC/Java/resource/2015/Day03/input.txt";
    public static void main(String[] args) throws Exception {
        new Day03_2();
    }

    HashMap<Point, Integer> houses = new HashMap<Point, Integer> ();
    public Day03_2() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            Point santa = new Point(0,0);
            Point robo = new Point(0,0);
            addPresent(santa);
            addPresent(robo);
            for (String string : lines) {
                for(int i = 0; i < string.length(); i++) {
                    char c = string.charAt(i);
                    if(i % 2 == 0) {
                        move(santa, c);
                    } else {
                        move(robo, c);
                    }
                }
            }
            System.out.println("Houses = " + houses.size());
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void move(Point p, char c) {
        if(c == '^') {
            p.y = p.y + 1;
        }
        if(c == '>') {
            p.x = p.x + 1;
        }
        if(c == 'v') {
            p.y = p.y - 1;
        }
        if(c == '<') {
            p.x = p.x - 1;
        }
        addPresent(p);
    }

    private void addPresent(Point _p) {
        Point p = new Point(_p.x, _p.y);
        Integer count = houses.get(p);
        if(count == null) {
            houses.put(p, 1);
        } else {
            houses.put(p, count + 1);
        }
    }

 }
