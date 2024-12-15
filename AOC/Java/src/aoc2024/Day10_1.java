package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day10_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day10/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day10/input.txt";
    public static void main(String[] args) throws Exception {
        new Day10_1();
    }

    int[][] grid;
    int w;
    int h;
    ArrayList<Trail> trails = new ArrayList<Trail>();
    int totalScore = 0;

    public Day10_1() {
        initGrid();
        for(Point point : getStartPoints()) {
            Trail trail = new Trail(point);
            totalScore += trail.calculateScore();
            // System.out.println(trail);
            trails.add(trail);
        }
        System.out.println("Result = " + totalScore);
    }

    private void initGrid() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            w = lines.get(0).length();
            h = lines.size();
            grid = new int[w][h];
            for(int y = 0; y < h; y++) {
                String line = lines.get(y);
                for(int x = 0; x < w; x++) {
                    grid[x][y] = Integer.parseInt("" + line.charAt(x));
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private ArrayList<Point> getStartPoints() {
        ArrayList<Point> result = new ArrayList<Point>();
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                if(grid[x][y] == 0) {
                    result.add(new Point(x,y));
                }
            }
        }
        return result;
    }

    private class Trail {

        Point _start = null;
        int score = -1;
        ArrayList<ArrayList<Point>> completePaths = new ArrayList<ArrayList<Point>>();

        public Trail( Point start) {
            _start = start;
        }
        public int calculateScore() {
            if(score >= 0) {
                return score;
            }
            ArrayList<ArrayList<Point>> activePaths = new ArrayList<ArrayList<Point>>();
            ArrayList<Point> path = new ArrayList<Point>();
            path.add(_start);
            activePaths.add(path);
            while(activePaths.size() > 0) {
                ArrayList<Point> current = activePaths.remove(0);
                int tier = current.size();
                for(Point point : getSurroundingPoints(current.get(tier - 1))) {
                    if(grid[point.x][point.y] == tier) {
                        ArrayList<Point> next = new ArrayList<Point>();
                        next.addAll(current);
                        next.add(point);
                        if(tier == 9) {
                            completePaths.add(next);
                        } else {
                            activePaths.add(next);
                        }
                    }
                }
            }
            HashSet<Point> uniqueEnds = new HashSet<Point>();
            for (ArrayList<Point> completedPath : completePaths) {
                uniqueEnds.add(completedPath.get(completedPath.size() -1));
            }
            score = uniqueEnds.size();
            return score;
        }

        private ArrayList<Point> getSurroundingPoints(Point point) {
            ArrayList<Point> points = new ArrayList<Point>();
            if(point.y > 0) {
                points.add(new Point(point.x, point.y - 1));
            }
            if(point.x > 0) {
                points.add(new Point(point.x - 1, point.y));
            }
            if(point.y < h - 1) {
                points.add(new Point(point.x, point.y + 1));
            }
            if(point.x < w - 1) {
                points.add(new Point(point.x + 1, point.y));
            }
            return points;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nStart " + _start);
            sb.append(" Score " + score);
            for (ArrayList<Point> path : completePaths) {
                sb.append("\n");
                sb.append(" Path ");
                for (Point point : path) {
                    sb.append("[x=" + point.x);
                    sb.append(",y=" + point.y);
                    sb.append("]");
                }
            }
            return sb.toString();
        }
    }
    
}
