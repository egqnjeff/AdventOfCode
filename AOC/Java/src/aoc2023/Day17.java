package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class Day17 {
    private static String inputPath = "AOC/Java/resource/2023/Day17/test.txt";
    // private static String inputPath = "AOC/Java/resource/2023/Day17/input.txt";

    public static void main(String[] args) throws Exception {
        new Day17();
    }

    int[][] grid;

    public Day17() {
        grid = initGrid();
        printGrid(grid);

        Node start = new Node(0, 0, "");
        Node end = new Node(grid.length - 1, grid[0].length - 1, "");
        Path shortestPath = getShortestPath(start, end);

        System.out.println("Shortest Path");
        System.out.println("    Nodes = " + shortestPath.nodeList.size());
        System.out.println("Heat Loss = " + shortestPath.getHeatLoss());
    }

    private int[][] initGrid() {
        int[][] grid = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            grid = new int[lines.get(0).length()][lines.size()];
            for (int i = 0; i < lines.get(0).length(); i++) {
                for(int j = 0; j < lines.size(); j++) {
                    grid[i][j] = Integer.parseInt("" + lines.get(j).charAt(i));
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        return grid;
    }

    private void printGrid(int[][] grid) {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                sb.append(grid[i][j]);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    TreeSet<Path> activePaths = new TreeSet<>();
    Map<Node, Integer> shortestMap = new HashMap<>();

    private void printActivePaths() {
        StringBuilder sb = new StringBuilder();
        sb.append(activePaths.size());
        for(Path path : activePaths) {
            sb.append(path);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private void addPath(Path path) {
        activePaths.add(path);
    }

    private Path getNextPath() {
        // System.out.println("getNextPath start - " + activePaths.size());
        Path first = activePaths.first();
        activePaths.remove(first);
        // System.out.println("getNextPath end - " + activePaths.size());
        return first;
    }
    
    private Path getShortestPath(Node start, Node end) {
        for (Node adjacent : getAdjacentNodes(start)) {
            addPath(new Path(null, adjacent));
        }
        while(activePaths.size() > 0) {
            Path path = getNextPath();
            Node pathEnd = path.getEndNode();
            if(!shortestMap.containsKey(pathEnd)) {
                shortestMap.put(pathEnd, path.getHeatLoss());
            }
            Integer shortest = shortestMap.get(pathEnd);
            if(path.getHeatLoss() > shortest) {
                continue;
            }
            shortestMap.put(pathEnd, path.getHeatLoss());
            // printActivePaths();
            // try {
            //     Thread.sleep(200);
            // } catch (InterruptedException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }
            System.out.println(path.getHeatLoss());
            for (Node adjacent : getAdjacentNodes(pathEnd)) {
                if(path.passesRules(adjacent)) {
                    Path newPath = new Path(path, adjacent);
                    if(adjacent.equals(end)) {
                        return newPath;
                    }
                    addPath(newPath);
                }
            }
        }
        return null;
    }

    private List<Node> getAdjacentNodes(Node source) {
        List<Node> adjacent = new ArrayList<Node>();
        if(source.j > 0) {
            adjacent.add(new Node(source.i, source.j - 1, "N"));
        }
        if(source.i < grid.length - 1) {
            adjacent.add(new Node(source.i + 1, source.j, "E"));
        }
        if(source.j < grid[0].length - 1) {
            adjacent.add(new Node(source.i, source.j + 1, "S"));
        }
        if(source.i > 0) {
            adjacent.add(new Node(source.i - 1, source.j, "W"));
        }
        return adjacent;
    }

    public class Path implements Comparable<Path> {
        List<Node> nodeList = new ArrayList<Node>();

        public Path(Path lastPath, Node node) {
            if(lastPath != null) {
                nodeList.addAll(lastPath.nodeList);
            }
            nodeList.add(node);
        }

        public int getHeatLoss() {
            int total = 0;
            for (Node node : nodeList) {
                total += node.getHeatLoss();
            }
            return total;
        }

        public Node getEndNode() {
            Node end = null;
            if(nodeList.size() > 0) {
                end = nodeList.get(nodeList.size() - 1);
            }
            return end;
        }

        public boolean passesRules(Node next) {
            // loops on itself
            if(nodeList.contains(next)) {
                return false;
            }
            if(nodeList.size() >= 3 && 
                next.direction.equals(nodeList.get(nodeList.size() - 1).direction) &&
                next.direction.equals(nodeList.get(nodeList.size() - 2).direction) &&
                next.direction.equals(nodeList.get(nodeList.size() - 3).direction)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Path) {
                Path other = (Path) obj;
                if(this.nodeList.containsAll(other.nodeList) &&
                    other.nodeList.containsAll(this.nodeList)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(Path other) {
            if(this.equals(other)) {
                return 0;
            }
            if(other.getHeatLoss() == this.getHeatLoss()) {
                if(other.nodeList.size() == this.nodeList.size()) {
                    // determine how to best compare similar paths
                    return this.nodeList.toString().compareTo(other.nodeList.toString());
                }
                return this.nodeList.size() - other.nodeList.size();
            }
            return this.getHeatLoss() - other.getHeatLoss();
        }

        @Override
        public String toString() {
            return nodeList + " - " + getHeatLoss();
        }
    }

    public class Node {
        int i;
        int j;
        String direction;

        public Node(int i, int j, String direction) {
            this.i = i;
            this.j = j;
            this.direction = direction;
        }

        public int getHeatLoss() {
            return grid[i][j];
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Node) {
                Node other = (Node) obj;
                if(this.i == other.i && this.j == other.j) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "[" + i + "," + j + "," + getHeatLoss() + "]";
        }
    }
}
