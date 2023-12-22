import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11_2 {
    // private static String inputPath = "2023/Java/resource/Day11/test.txt";
    private static String inputPath = "2023/Java/resource/Day11/input.txt";

    public static void main(String[] args) throws Exception {
        new Day11_2();
    }
    
    char[][] grid;
    int w;
    int h;

    List<Integer> rows;
    List<Integer> columns;
    long multiplier = 1000000;
    // long multiplier = 2;

    public Day11_2() {
        initGrid();
        // printGrid();

        List<Point> galaxies = getGalaxies();
        // System.out.println(galaxies);

        // updateGalaxies(galaxies);
        // System.out.println(galaxies);
        rows = getRows();
        columns = getColumns();

        List<Point[]> galaxyPairs = getGalaxyPairs(galaxies);
        // System.out.println(galaxyPairs.size());

        long sum = 0;
        for (Point[] pair : galaxyPairs) {
            sum += calculatePathLength(pair);
            // System.out.println(pair[0] + " " + pair[1] + " " + path.size());
        }
        System.out.println(sum);
    }

    private long calculatePathLength(Point[] pair) {
        long length = 0;
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
            length++;
        }
        long numCols = 0;
        for (int col : columns) {
            if( col > Math.min(pair[0].x, pair[1].x) && 
                col < Math.max(pair[0].x, pair[1].x)) {
                numCols++;
            }
        }
        long numRows = 0;
        for (int row : rows) {
            if( row > Math.min(pair[0].y, pair[1].y) &&
                row < Math.max(pair[0].y, pair[1].y)) {
                numRows++;
            }
        }
        length += (multiplier - 1) * (numCols + numRows);
        return length;
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

    private void updateGalaxies(List<Point> galaxies) {
        List<Integer> rows = getRows();
        List<Integer> columns = getColumns();
        for (Point galaxy : galaxies) {
            int x = galaxy.x;
            int y = galaxy.y;
            for (Integer i : columns) {
                if(i < galaxy.x) {
                    x += multiplier - 1;
                }
            }
            for(Integer i : rows) {
                if(i < galaxy.y) {
                    y += multiplier - 1;
                }
            } 
            galaxy.x = x;
            galaxy.y = y;     
        }
    }

    private List<Integer> getColumns() {
        List<Integer> columns = new ArrayList<Integer>();
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
        return columns;
    }

    private List<Integer> getRows() {
        List<Integer> rows = new ArrayList<Integer>();
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
        return rows;
    }

    private Character getChar(int x, int y) {
        return grid[x][y];
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
