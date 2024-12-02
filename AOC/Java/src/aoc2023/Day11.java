package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11 {
    // private static String inputPath = "AOC/Java/resource/2023/Day11/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day11/input.txt";

    public static void main(String[] args) throws Exception {
        new Day11();
    }
    
    char[][] grid;
    int w;
    int h;

    public Day11() {
        initGrid();
        printGrid();

        updateGrid();
        printGrid();

        List<Point> galaxies = getGalaxies();
        List<Point[]> galaxyPairs = getGalaxyPairs(galaxies);
        System.out.println(galaxyPairs.size());

        int sum = 0;
        for (Point[] pair : galaxyPairs) {
            List<Point> path = calculatePath(pair);
            System.out.println(pair[0] + " " + pair[1] + " " + path.size());
            sum += path.size();
        }
        System.out.println(sum);
    }

    private List<Point> calculatePath(Point[] pair) {
        List<Point> path = new ArrayList<Point>();
        Point current = pair[0];
        Point last = pair[1];
        while(!current.equals(last)) {
            int deltaX = last.x - current.x;
            int deltaY = last.y - current.y;
            if(Math.abs(deltaX) > Math.abs(deltaY)) {
                current = new Point(current.x + (int) Math.signum(deltaX), current.y);
            } else {
                current = new Point(current.x, current.y + (int) Math.signum(deltaY));
            }
            path.add(current);
        }
        return path;
    }

    private List<Point[]> getGalaxyPairs(List<Point> galaxies) {
        List<Point[]> pairs = new ArrayList<Point[]>();
        for(int x = 0; x < galaxies.size() - 1; x++) {
            for(int i = x + 1; i < galaxies.size(); i++) {
                Point[] pair = new Point[2];
                pair[0] = galaxies.get(x);
                pair[1] = galaxies.get(i);
                pairs.add(pair);
            }
        }
        return pairs;
    }

    private List<Point> getGalaxies() {
        List<Point> galaxies = new ArrayList<Point>();
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                if(getChar(x, y) == '#') {
                    galaxies.add(new Point(x, y));
                }
            }
        }
        return galaxies;
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

    private void updateGrid() {
        List<Integer> columns = new ArrayList<Integer>();
        List<Integer> rows = new ArrayList<Integer>();
        for(int x = 0; x < w; x++) {
            boolean galaxy = false;
            for(int y = 0; y < h; y++) {
                if(getChar(x, y) == '#') {
                    galaxy = true;
                    break;
                }
            }
            if(!galaxy) {
                columns.add(x);
            }
        }
        for(int y = 0; y < h; y++) {
            boolean galaxy = false;
            for(int x = 0; x < w; x++) {
                if(getChar(x, y) == '#') {
                    galaxy = true;
                    break;
                }
            }
            if(!galaxy) {
                rows.add(y);
            }
        }
        System.out.println("Rows " + rows);
        System.out.println("Columns " + columns);
        for(int i = 1; i <= rows.size(); i++) {
            insertRow(rows.get(rows.size() - i));
        }
        for(int i = 1; i <= columns.size(); i++) {
            insertColumn(columns.get(columns.size() - i));
        }
    }

    private void insertColumn(int column) {
        char[][] newGrid = new char[w + 1][h];
        int index = 0;
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                newGrid[index][y] = grid[x][y];
            }
            if(index == column) {
                x--;
            }
            index++;
        }
        w = w + 1;
        grid = newGrid;
    }

    private void insertRow(int row) {        
        char[][] newGrid = new char[w][h + 1];
        int index = 0;
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                newGrid[x][index] = grid[x][y];
            }
            if(index == row) {
                y--;
            }
            index++;
        }
        h = h + 1;
        grid = newGrid;
    }

    private Character getChar(Point point) {
        return getChar(point.x, point.y);
    }

    private Character getChar(int x, int y) {
        return grid[x][y];
    }

    private void setChar(Point p, char c) {
        setChar(p.x, p.y, c);
    }

    private void setChar(int x, int y, char c) {
        grid[x][y] = c;
    }

    public void printGrid() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    class Point {
        int x;
        int y;

        public Point() {
            x = 0;
            y = 0;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Point)) {
                return false;
            }
            Point that = (Point) obj;
            return ((this.x == that.x) && (this.y == that.y));
        }

        @Override
        public String toString() {
            return "[" + x + "," + y +"]";
        }
    }
}
