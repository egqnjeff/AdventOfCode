import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day14 {
    // private static String inputPath = "2023/Java/resource/Day14/test.txt";
    private static String inputPath = "2023/Java/resource/Day14/input.txt";

    public static void main(String[] args) throws Exception {
        new Day14();
    }

    public Day14() {
        char[][] grid = initGrid();
        // printRecords();

        shiftNorth(grid);
        // count rocks
        int total = countRocks(grid);
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

}
