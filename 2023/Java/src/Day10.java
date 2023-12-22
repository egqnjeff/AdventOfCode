import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Day10 {
    // private static String inputPath = "2023/Java/resource/Day10/test.txt";
    private static String inputPath = "2023/Java/resource/Day10/input.txt";

    public static void main(String[] args) throws Exception {
        new Day10();
    }
    
    List<String> lines;
    int w;
    int h;
    Point start;

    public Day10() {
        try {
            lines = Files.readAllLines(Paths.get(inputPath));
            w = lines.get(0).length();
            h = lines.size();
            start = getStartPoint();
            System.out.println(start);
            int maxLen = 0;
            for (Point point : getConnectedPoints(start)) {
                maxLen = Math.max(maxLen, calculateLoopLength(point));
            }
            System.out.println(maxLen / 2);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private int calculateLoopLength(Point next) {
        int length = 1;
        if(getConnectedPoints(next).contains(start)) {
            Point current = start;
            while(!next.equals(start)) {
                length++;
                List<Point> following = getConnectedPoints(next);
                following.remove(current);
                current = next;
                // System.out.println(current);
                next = following.get(0);
            }
        }
        return length;
    }

    public List<Point> getConnectedPoints(Point source) {
        List<Point> result = new ArrayList<Point>();
        char c = lines.get(source.y).charAt(source.x);
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
        for (int i = 0; i < lines.size(); i++) {
            String string = lines.get(i);
            for(int j = 0; j < string.length(); j++) {
                if(string.charAt(j) == 'S') {
                    return new Point(j,i);
                }
            }
        }
        return null;
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
