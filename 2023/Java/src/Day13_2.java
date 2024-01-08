import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13_2 {
//    private static String inputPath = "2023/Java/resource/Day13/test.txt";
    private static String inputPath = "2023/Java/resource/Day13/input.txt";

    public static void main(String[] args) throws Exception {
        new Day13_2();
    }

    List<Record> records = new ArrayList<Record>();

    public Day13_2() {
        initRecords();
//        printRecords();

        int total = 0;
        for (Record record : records) {
//            System.out.println();
            System.out.println(record.toString());
            total += record.getSummary();
        }
        System.out.println("Total " + total);
    }

    private void initRecords() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            Record record = new Record();
            records.add(record);
            for (String line : lines) {
                if(line.length() == 0) {
                    record = new Record();
                    records.add(record);
                } else {
                    record.addLine(line);
                }
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void printRecords() {
        StringBuilder sb = new StringBuilder();
        for (Record record : records) {
            sb.append(record);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    class Record {
        List<String> lines = new ArrayList<String>();

        public Record() {
        }

        public void addLine(String line) {
            lines.add(line);
        }

        public int getSummary() {
            char[][] grid = new char[lines.get(0).length()][lines.size()];
            for(int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j] = lines.get(j).charAt(i);
                }
            }

            // get unmodified mirror positions
            int unmodifiedRow = getRow(grid, 0);
            int unmodifiedCol = getColumn(grid, 0);

            // get modified mirror position
            for(int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    invertChar(grid, i, j);
                    int summary = (getRow(grid, unmodifiedRow) * 100) + getColumn(grid, unmodifiedCol);
                    if(summary > 0) {
                        return summary;
                    }
                    invertChar(grid, i, j);
                }
            }
            return 0;
        }

        private int getRow(char[][] grid, int skip) {
//            System.out.println("getRow");
            return getMirrorPosition(getInvertedGrid(grid), skip);
        }

        private int getColumn(char[][] grid, int skip) {
//            System.out.println("getColumn");
            return getMirrorPosition(grid, skip);
        }
        private void invertChar(char[][] grid, int row, int column) {
            if(grid[row][column] == '.') {
                grid[row][column] = '#';
            } else {
                grid[row][column] = '.';
            }
        }

        private char[][] getInvertedGrid(char[][] grid) {
            char[][] inverted = new char[grid[0].length][grid.length];
            for(int i = 0; i < grid.length; i++) {
                for(int j = 0; j < grid[i].length; j++) {
                    inverted[j][i] = grid[i][j];
                }
            }
            return inverted;
        }

        // checks by columns
        private int getMirrorPosition(char[][] grid, int skip) {
            for(int position = 1; position < grid.length; position++) {
                if(position != skip) {
                    boolean mirror = true;
                    int min = Math.min(position, grid.length - position);
                    //                System.out.println("Testing position " + position + " with min " + min);
                    int i = 0;
                    while (i < min && mirror) {
                        int first = position - i - 1;
                        int second = position + i;
                        //                    System.out.println("Comparing:");
                        //                    System.out.println("first: " + first + " - " + Arrays.toString(grid[first]));
                        //                    System.out.println("second: " + second + " - " + Arrays.toString(grid[second]));
                        for (int j = 0; j < grid[first].length; j++) {
                            if (grid[first][j] != (grid[second][j])) {
                                mirror = false;
                                break;
                            }
                        }
                        i++;
                    }
                    if (mirror) {
                        System.out.println("Mirrored at " + position);
                        return position;
                    }
                }
            }
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Record)) {
                return false;
            }
            Record that = (Record) obj;
            if(this.lines.size() != that.lines.size()) {
                return false;
            }
            for(int i = 0; i < this.lines.size(); i++) {
                if(this.lines.get(i) != that.lines.get(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        }
    }
}
