package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day12_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day12/test3.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day12/input.txt";
    public static void main(String[] args) throws Exception {
        new Day12_2();
    }

    char[][] grid;
    int w;
    int h;
    int total = 0;

    enum Direction {
        Up, Right, Down, Left
    }

    ArrayList<Region> allRegions = new ArrayList<Region>();
    ArrayList<Point> consumed = new ArrayList<Point>();

    public Day12_2() {
        initGrid();
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                Point point = new Point(i, j);
                if(!consumed.contains(point)) {
                    Region region = new Region(point);
                    consumed.addAll(region.plots);
                    allRegions.add(region);
                }
            }
        }
        int totalPrice = 0;
        for(Region region : allRegions) {
            // System.out.println(region);
            totalPrice += (region.getNumSides() * region.plots.size());
        }
        System.out.println("Total Price = " + totalPrice);
    }

    private void initGrid() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            w = lines.get(0).length();
            h = lines.size();
            grid = new char[w][h];
            for(int y = 0; y < h; y++) {
                String line = lines.get(y);
                for(int x = 0; x < w; x++) {
                    grid[x][y] = line.charAt(x);
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public class Boundary {
        Point point;
        Direction direction;

        public Boundary(Point p, Direction d) {
            point = p;
            direction = d;
        }
    }

    public class Region {

        ArrayList<Boundary> boundary = new ArrayList<Boundary>();
        HashSet<Point> plots = new HashSet<Point>();
        char plant;
        private ArrayList<Boundary> remaining = new ArrayList<Boundary>();

        public Region(Point start) {
            plant = grid[start.x][start.y];
            findConnected(start);
        }

        public void findConnected(Point point) {
            plots.add(point);
            for(Point surrounding : getSurrounding(point)) {
                findConnected(surrounding);
            }
        }

        public ArrayList<Point> getSurrounding(Point point) {
            ArrayList<Point> surrounding = new ArrayList<Point>();
            if(point.y > 0 && grid[point.x][point.y - 1] == plant) {
                Point up = new Point(point.x, point.y - 1);
                if(!plots.contains(up)) {
                    plots.add(up);
                    surrounding.add(up);
                }
            } else {
                boundary.add(new Boundary(point, Direction.Up));
            }
            if(point.y < h - 1 && grid[point.x][point.y + 1] == plant) {
                Point down = new Point(point.x, point.y + 1);
                if(!plots.contains(down)) {
                    plots.add(down);
                    surrounding.add(down);
                }
            } else {
                boundary.add(new Boundary(point, Direction.Down));
            }
            if(point.x > 0 && grid[point.x - 1][point.y] == plant) {
                Point left = new Point(point.x - 1, point.y);
                if(!plots.contains(left)) {
                    plots.add(left);
                    surrounding.add(left);
                }
            } else {
                boundary.add(new Boundary(point, Direction.Left));
            }
            if(point.x < w - 1 && grid[point.x + 1][point.y] == plant) {
                Point right = new Point(point.x + 1, point.y);
                if(!plots.contains(right)) {
                    plots.add(right);
                    surrounding.add(right);
                }
            } else {
                boundary.add(new Boundary(point, Direction.Right));
            }
            return surrounding;
        }
    
        public int getNumSides() {
            ArrayList<ArrayList<Boundary>> sides = new ArrayList<ArrayList<Boundary>>();
            remaining.addAll(boundary);
            while(remaining.size() > 0) {
                sides.add(expandSide(remaining.remove(0), remaining));
            }
            return sides.size();
        }
        
        private ArrayList<Boundary> expandSide(Boundary start, ArrayList<Boundary> remaining) {
            ArrayList<Boundary> side = new ArrayList<Boundary>();
            side.add(start);
            for(int i = -1; i <= 1; i += 2) {
                Boundary next = nextBoundary(start, i);
                while(next != null) {
                    side.add(next);
                    remaining.remove(next);
                    next = nextBoundary(next, i);
                }
            }
            return side;
        }

        private Boundary nextBoundary(Boundary last, int increment) {
            int x = last.point.x;
            int y = last.point.y;
            Direction dir = last.direction;
            if(dir.equals(Direction.Up) || dir.equals(Direction.Down)) {
                x += increment;
            }
            if(dir.equals(Direction.Right) || dir.equals(Direction.Left)) {
                y += increment;
            }
            for(Boundary boundary : remaining) {
                if( boundary.point.x == x && 
                    boundary.point.y == y &&
                    boundary.direction.equals(dir)) {
                        return boundary;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Plant[" + plant +"]");
            sb.append("Plot[" + plots.size() +"]");
            sb.append("Boundary[" + boundary.size() +"]");
            for(Boundary b : boundary) {
                sb.append("{");
                sb.append(b.point.x);
                sb.append(",");
                sb.append(b.point.y);
                sb.append(",");
                sb.append(b.direction);
                sb.append("}");
            }
            return sb.toString();
        }
    }

}
