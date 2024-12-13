package aoc2024;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day06/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day06/input.txt";
    public static void main(String[] args) throws Exception {
        new Day06_2();
    }

    char[][] grid;
    int w = 0;
    int h = 0;
    Position guard = null;

    int counter = 0;

    Set<Point> addedObstacles = new HashSet<Point>();

    public Day06_2() {
        initGrid();

        guard = findGuard();
        // System.out.println("Guard Position " + guard);
        
        ArrayList<Position> guardPath = createPath(guard);

        // updateGrid(guardPath);

        // printGrid();
        System.out.println("Path size = " + guardPath.size() + " counter = " + counter);
        System.out.println("Added Obstacles = " + addedObstacles.size());
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

    private Position findGuard() {
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                switch (grid[x][y]) { 
                    case '^':
                    case '>':
                    case 'v':
                    case '<': {
                        Position position = new Position();
                        position.direction = grid[x][y];
                        position.location = new Point(x,y);
                        return position;
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<Position> createPath(Position start) {
        ArrayList<Position> path = new ArrayList<Position>();
        Position current = start;
        while(!isOffGrid(current.location) && !path.contains(current)) {
            // System.out.println("Main Path " + current.location);
            path.add(current);
            if(addingObstacleCreatesLoop(current)) {
                addedObstacles.add(nextLocation(current));
            }
            current = nextPosition(current);
        }
        return path;
    }

    private boolean addingObstacleCreatesLoop(Position start) {
        // System.out.println("Check Obstacle at " + start);
        counter++;
        Point obstacle = nextLocation(start);
        if(isOffGrid(obstacle) || isAtObstacle(obstacle)) {
            return false;
        }

        // Add obstacle to grid
        char old = grid[obstacle.x][obstacle.y];
        grid[obstacle.x][obstacle.y] = '#';

        ArrayList<Position> path = new ArrayList<Position>();
        Position current = guard;
        while(!isOffGrid(current.location) && !path.contains(current)) {
            // System.out.println("Obstacle Path " + current);
            path.add(current);
            current = nextPosition(current);
        }

        // remove obstacle from grid
        grid[obstacle.x][obstacle.y] = old;

        if(isOffGrid(current.location)) {
            return false;
        }
        System.out.println("Counter = " + counter + " Path size = " + path.size() + " Last Pos = " + current);
        return true;
    }

    private int countVisited() {
        int visited = 0;
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                switch (grid[x][y]) {
                    case 'O':
                    case 'X':
                    case '+':
                    case '-':
                    case '|': {
                        visited++;
                    }
                }
            }
        }
        return visited;
    }

    private void updateGrid(ArrayList<Position> path) {
        for (Position position : path) {
            grid[position.location.x][position.location.y] = 'X';
        }
        for (Point obstacle : addedObstacles) {
            grid[obstacle.x][obstacle.y] = 'O';            
        }
    }

    private void printGrid() {
        for(int y = 0; y < h; y++) {
            for(int x = 0; x < w; x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println();
        }
    }

    private Point nextLocation(Position previous) {
        Point next = null;
        int x = previous.location.x;
        int y = previous.location.y;
        switch (previous.direction) {
            case '^' : {
                next = new Point(x, y - 1);
                break;
            }
            case '>' : {
                next = new Point(x + 1, y);
                break;
            }
            case 'v' : {
                next = new Point(x, y + 1);
                break;
            }
            case '<' : {
                next = new Point(x - 1, y);
                break;
            }
        }
        return next;
    }

    private boolean isOffGrid(Point point) {
        if(point.x < 0 || point.x >= w || point.y < 0 || point.y >= h) {
            return true;
        }
        return false;
    }

    private boolean isAtObstacle(Point point) {
        if(isOffGrid(point)) {
            return false;
        }
        switch (grid[point.x][point.y]) {
            case '#' : {
                return true;
            }
        }
        return false;
    }

    private char rotateDirection(char direction) {
        switch (direction) {
            case '^' : {
                return '>';
            }
            case '>' : {
                return 'v';
            }
            case 'v' : {
                return '<';
            }
            case '<' : {
                return '^';
            }
        }
        return direction;
    }
        
    private Position nextPosition(Position last) {
        Point nextLocation = nextLocation(last);
        Position nextPosition = new Position(last);
        if(isAtObstacle(nextLocation)) {
            nextPosition.direction = rotateDirection(last.direction);
        } else {
            nextPosition.location = nextLocation;
            return nextPosition;
        }
        return nextPosition;
    }

    private class Position {
        char direction;
        Point location;

        public Position() {
        }

        public Position(Position other) {
            this.direction = other.direction;
            this.location = other.location;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("direction[" + direction + "] location[" + location + "]");
            return sb.toString();
        }

        @Override
        public boolean equals(Object other) {
            if(other == null || getClass() != other.getClass()) {
                return false;
            }
            Position that = (Position) other;
            return this.direction == that.direction &&
                this.location.x == that.location.x &&
                this.location.y == that.location.y;
        }

        @Override
        public int hashCode() {
            int result = 1;
            final int prime = 31;

            result = prime * result + Character.hashCode(direction);
            result = prime * result + (location == null ? 0 : location.hashCode());
            return result;
        }
    }

}
