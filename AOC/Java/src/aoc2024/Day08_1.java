package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day08_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day08/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day08/input.txt";
    public static void main(String[] args) throws Exception {
        new Day08_1();
    }

    Map<Character, ArrayList<Point>> antennaLocations = new HashMap<Character, ArrayList<Point>>();
    Set<Point> antinodes = new HashSet<Point>();

    int width;
    int height;

    public Day08_1() {
        parseInput();
        calculateAntinodes();
        System.out.println("Antinodes = " + antinodes.size());
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            width = lines.get(0).length();
            height = lines.size();
            for(int y = 0; y < height; y++) {
                String line = lines.get(y);
                for(int x = 0; x < width; x++) {
                    char c = line.charAt(x);
                    if(c != '.') {
                        addAntenna(c, new Point(x,y));
                    }
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void addAntenna(char c, Point p) {
        ArrayList<Point> locations = antennaLocations.get(c);
        if(locations == null) {
            locations = new ArrayList<Point>();
            antennaLocations.put(c, locations);
        }
        locations.add(p);
    }

    private void calculateAntinodes() {
        for (char antenna : antennaLocations.keySet()) {
            processList(antennaLocations.get(antenna));
        }
    }

    private void processList(ArrayList<Point> list) {
        if(list.size() >= 2) {
            ArrayList<Point> tempList = new ArrayList<Point>();
            tempList.addAll(list);
            Point first = tempList.remove(0);
            for (Point point : tempList) {
                processPair(first, point);
            }
            processList(tempList);
        }
    }

    private void processPair(Point first, Point second) {
        addAntinode(new Point(2 * first.x - second.x, 2 * first.y - second.y));
        addAntinode(new Point(2 * second.x - first.x, 2 * second.y - first.y));
    }

    private void addAntinode(Point antinode) {
        if(antinode.x >=0 && antinode.x < width && antinode.y >= 0 && antinode.y < height) {
            antinodes.add(antinode);
        }
    }
}
