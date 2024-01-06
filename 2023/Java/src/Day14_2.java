import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14_2 {
    // private static String inputPath = "2023/Java/resource/Day14/test.txt";
    private static String inputPath = "2023/Java/resource/Day14/input.txt";

    public static void main(String[] args) throws Exception {
        new Day14_2();
    }

    int cycles = 1000000000;
    // int cycles = 30;

    public Day14_2() {
        Map<String, Integer> uniqueCycles = new HashMap<>();
        List<Integer> countList = new ArrayList<>();

        char[][] grid = initGrid();

        int first = 0;
        int second = 0;

        for(int i = 0; i < cycles; i++) {
            shiftNorth(grid);
            shiftWest(grid);
            shiftSouth(grid);
            shiftEast(grid);
            countList.add(countRocks(grid));
            String gridString = gridAsString(grid);
            System.out.println(uniqueCycles.size() + " " + countList.get(i)+ " containsKey " + uniqueCycles.containsKey(gridString) + gridString);
            if(uniqueCycles.containsKey(gridString)) {
                first = uniqueCycles.get(gridString);
                second = i;
                break;
            }
            uniqueCycles.put(gridString, i);
        }
        int index = (cycles - first) % (second - first) + first - 1;
        System.out.println("first " + first + "  second " + second + "  index " + index);
        int total = countList.get(index);
        System.out.println("Total " + total);
    }

    private int countRocks(char[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 'O') {
                    count += grid.length - j;
                }
            }
        }
        return count;
    }

    private void shiftNorth(char[][] grid) {
        boolean repeat = true;
        while(repeat) {
            repeat = false;
            for(int i = 0; i < grid.length; i++) {
                for(int j = 1; j < grid[0].length; j++) {
                    if(grid[i][j] == 'O' && grid[i][j-1] == '.') {
                        grid[i][j] = '.';
                        grid[i][j-1] = 'O';
                        repeat = true;
                    }
                }
            }
        }
    }

    private void shiftSouth(char[][] grid) {
        boolean repeat = true;
        while(repeat) {
            repeat = false;
            for(int i = 0; i < grid.length; i++) {
                for(int j = 0; j < grid[0].length - 1; j++) {
                    if(grid[i][j] == 'O' && grid[i][j+1] == '.') {
                        grid[i][j] = '.';
                        grid[i][j+1] = 'O';
                        repeat = true;
                    }
                }
            }
        }
    }

    private void shiftEast(char[][] grid) {
        boolean repeat = true;
        while(repeat) {
            repeat = false;
            for(int i = 0; i < grid.length - 1; i++) {
                for(int j = 0; j < grid[0].length; j++) {
                    if(grid[i][j] == 'O' && grid[i+1][j] == '.') {
                        grid[i][j] = '.';
                        grid[i+1][j] = 'O';
                        repeat = true;
                    }
                }
            }
        }
    }

    private void shiftWest(char[][] grid) {
        boolean repeat = true;
        while(repeat) {
            repeat = false;
            for(int i = 1; i < grid.length; i++) {
                for(int j = 0; j < grid[0].length; j++) {
                    if(grid[i][j] == 'O' && grid[i-1][j] == '.') {
                        grid[i][j] = '.';
                        grid[i-1][j] = 'O';
                        repeat = true;
                    }
                }
            }
        }
    }

    private char[][] initGrid() {
        char[][] grid = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            grid = new char[lines.get(0).length()][lines.size()];
            for (int i = 0; i < lines.get(0).length(); i++) {
                for(int j = 0; j < lines.size(); j++) {
                    grid[i][j] = lines.get(j).charAt(i);
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        return grid;
    }

    private String gridAsString(char[][] grid) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                sb.append(grid[i][j]);
            }
        }
        return sb.toString();
    }
}
