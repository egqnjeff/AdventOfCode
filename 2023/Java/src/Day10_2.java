import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day10_2 {
    // private static String inputPath = "2023/Java/resource/Day10/test.txt";
    // private static String inputPath = "2023/Java/resource/Day10/test2.txt";
    // private static String inputPath = "2023/Java/resource/Day10/test3.txt";
    private static String inputPath = "2023/Java/resource/Day10/input.txt";

    public static void main(String[] args) throws Exception {
        new Day10_2();
    }
    
    char[][] grid;
    int w;
    int h;
    Point start;
    List<List<Point>> allAreas = new ArrayList<List<Point>>();
    List<List<Point>> internalAreas = new ArrayList<List<Point>>();

    public Day10_2() {
        initGrid();

        start = getStartPoint();

        List<Point> completeLoop = getCompleteLoop();
        // System.out.println(completeLoop);

        updateGrid(completeLoop);
        printGrid();

        allAreas.addAll(findAreas());
        scanLoop(completeLoop);
        int sumInternal = 0;
        // System.out.println("Areas " + areas.size());
        for (List<Point> area : internalAreas) {
            System.out.println("Internal " + area.size());
            sumInternal += area.size();
        }
        System.out.println(sumInternal);
    }

    private void scanLoop(List<Point> loop) {
        Point start = null;
        for(int i = 0; i < w * h; i++) {
            Point p = new Point(i % w, i / w);
            if(loop.contains(p)) {
                start = p;
                break;
            }
        }
        int index = loop.indexOf(start);
        String dir = "";
        String in = "";
        Point current = loop.get((index + 1) % loop.size());
        if(current.x == start.x + 1) {
            dir = "E";
            in = "S";
        } else if(current.y == start.y + 1) {
            dir = "S";
            in = "E";
        }
        while(!current.equals(start)) {
            // System.out.println(index + " " + current + " " + dir + " " + in + " " + getChar(current));
            checkInternal(in, current);
            if(dir.equals("N")) {
                if(getChar(current) == 'F') {
                    dir = "E";
                    in = clockwise(in);
                } else if(getChar(current) == '7') {
                    dir = "W";
                    in = counterClockwise(in);
                }   
            } else if(dir.equals("E")) {
                if(getChar(current) == '7') {
                    dir = "S";
                    in = clockwise(in);
                } else if(getChar(current) == 'J') {
                    dir = "N";
                    in = counterClockwise(in);
                }
            } else if(dir.equals("S")) {
                if(getChar(current) == 'J') {
                    dir = "W";
                    in = clockwise(in);
                } else if(getChar(current) == 'L') {
                    dir = "E";
                    in = counterClockwise(in);
                }
            } else if(dir.equals("W")) {
                if(getChar(current) == 'F') {
                    dir = "S";
                    in = counterClockwise(in);
                } else if(getChar(current) == 'L') {
                    dir = "N";
                    in = clockwise(in);
                }
            }
            checkInternal(in, current);
            index = (index + 1) % loop.size();
            current = loop.get(index);
        }

    }

    private void checkInternal(String in, Point current) {
        Point p = null;
        if(in == "N") {
            p = new Point(current.x, current.y - 1);
        } else if(in == "E") {
            p = new Point(current.x + 1, current.y);
        } else if(in == "S") {
            p = new Point(current.x, current.y + 1);
        } else if(in == "W") {
            p = new Point(current.x - 1, current.y);
        }
        if(getChar(p) == 'O') {
            List<Point> internal = null;
            for (List<Point> area : allAreas) {
                if(area.contains(p)) {
                    internal = area;
                    break;
                }
            }
            if(internal != null) {
                System.out.println("Internal Area - " + internal);
                internalAreas.add(internal);
                allAreas.remove(internal);
            }
        }
    }

    private String clockwise(String str) {
        if(str == "N") {
            return "E";
        }
        if(str == "E") {
            return "S";
        }
        if(str == "S") {
            return "W";
        }
        return "N";
    }

    private String counterClockwise(String str) {
        if(str == "N") {
            return "W";
        }
        if(str == "W") {
            return "S";
        }
        if(str == "S") {
            return "E";
        }
        return "N";
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

    private List<List<Point>> findAreas() {
        List<List<Point>> areas = new ArrayList<List<Point>>();
        List<Point> area = findArea();
        while(area != null) {
            areas.add(area);
            area = findArea();
            // System.out.println(area);
        }
        return areas;
    }

    private List<Point> findArea() {
        List<Point> area = new ArrayList<Point>();
        List<Point> stack = new ArrayList<Point>();
        Point nextSpace = findNextSpace();
        // System.out.println(nextSpace);
        if(nextSpace == null) {
            return null;
        }
        stack.add(nextSpace);
        while(stack.size() > 0) {
            Point current = stack.remove(0);
            area.add(current);
            setChar(current, 'O');
            if(current.x > 0) {
                Point point = new Point(current.x - 1, current.y);
                if(!stack.contains(point) && getChar(point) == '.') {
                    // System.out.println("stack L");
                    stack.add(point);
                }
            }
            if(current.y > 0) {
                Point point = new Point(current.x, current.y - 1);
                if(!stack.contains(point) && getChar(point) == '.') {
                    // System.out.println("stack U");
                    stack.add(point);
                }
            }
            if(current.x < w - 1) {
                Point point = new Point(current.x + 1, current.y);
                if(!stack.contains(point) && getChar(point) == '.') {
                    // System.out.println("stack R");
                    stack.add(point);
                }
            }
            if(current.y < h - 1) {
                Point point = new Point(current.x, current.y + 1);
                if(!stack.contains(point) && getChar(point) == '.') {
                    // System.out.println("stack D");
                    stack.add(point);
                }
            }
        }
        // System.out.println(area.size());
        return area;
    }

    private Point findNextSpace() {
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if(getChar(i, j) == '.') {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private void updateGrid(List<Point> loop) {
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if(getChar(i, j) != '.' && !loop.contains(new Point(i, j))) {
                    setChar(i, j, '.');
                }
            }
        }
        Point s = loop.get(0);
        Point n = loop.get(1);
        Point l = loop.get(loop.size() - 1);
        boolean nextEast = n.x == s.x + 1;
        boolean nextWest = n.x == s.x - 1;
        boolean nextNorth = n.y == s.y - 1;
        boolean nextSouth = n.y == s.y + 1;
        boolean lastEast = l.x == s.x + 1;
        boolean lastWest = l.x == s.x - 1;
        boolean lastNorth = l.y == s.y + 1;
        boolean lastSouth = l.y == s.y - 1;
        if( (nextNorth && lastSouth) || (nextSouth && lastNorth)) {
            setChar(s, '|');
        }
        if( (nextEast && lastWest) || (nextWest && lastEast)) {
            setChar(s, '-');
        }
        if( (nextNorth && lastEast) || (nextEast && lastNorth)) {
            setChar(s, 'L');
        }
        if( (nextNorth && lastWest) || (nextWest && lastNorth)) {
            setChar(s, 'J');
        }
        if( (nextSouth && lastWest) || (nextWest && lastSouth)) {
            setChar(s, '7');
        }
        if( (nextSouth && lastEast) || (nextEast && lastSouth)) {
            setChar(s, 'F');
        }
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

    private List<Point> getCompleteLoop() {
        for (Point point : getConnectedPoints(start)) {
            List<Point> loop = calculateLoop(point);
            if(loop.size() > 1) {
                return loop;
            }
        }
        return null;
    }

    private List<Point> calculateLoop(Point next) {
        List<Point> loop = new ArrayList<Point>();
        loop.add(start);
        if(getConnectedPoints(next).contains(start)) {
            Point current = start;
            while(!next.equals(start)) {
                loop.add(next);
                List<Point> following = getConnectedPoints(next);
                following.remove(current);
                current = next;
                // System.out.println(current);
                next = following.get(0);
            }
        }
        return loop;
    }

    public List<Point> getConnectedPoints(Point source) {
        List<Point> result = new ArrayList<Point>();
        char c = grid[source.x][source.y];
        List<Point> north = new ArrayList<Point>();
        List<Point> south = new ArrayList<Point>();
        List<Point> east = new ArrayList<Point>();
        List<Point> west = new ArrayList<Point>();
        if(source.y > 0) {
            north.add(new Point(source.x, source.y - 1));
        }
        if(source.y < h) {
            south.add(new Point(source.x, source.y + 1));
        }
        if(source.x < w) {
            east.add(new Point(source.x + 1, source.y));
        }
        if(source.x > 0) {
            west.add(new Point(source.x - 1, source.y));
        }
        if(c == '|') {
            result.addAll(north);
            result.addAll(south);
            return result;
        }
        if(c == '-') {
            result.addAll(east);
            result.addAll(west);
            return result;
        }
        if(c == 'L') {
            result.addAll(north);
            result.addAll(east);
            return result;
        }
        if(c == 'J') {
            result.addAll(north);
            result.addAll(west);
            return result;
        }
        if(c == '7') {
            result.addAll(south);
            result.addAll(west);
            return result;
        }
        if(c == 'F') {
            result.addAll(south);
            result.addAll(east);
            return result;
        }
        if(c == 'S') {
            result.addAll(north);
            result.addAll(south);
            result.addAll(east);
            result.addAll(west);
            return result;
        }
        return result;
    }

    public Point getStartPoint() {
        for (int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if(grid[i][j] == 'S') {
                    return new Point(i,j);
                }
            }
        }
        return null;
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
