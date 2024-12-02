package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3_2 {
    // private static String inputPath = "AOC/Java/resource/2023/Day3/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day3/input.txt";

    List<String> allLines = null;
    Object[][] matrix = null;

    public static void main(String[] args) throws Exception {
        new Day3_2();
    }
    
    public Day3_2() {
        StringBuilder sb = new StringBuilder();
        try {
			allLines = Files.readAllLines(Paths.get(inputPath));
            int sum = 0;

            matrix = getNumberMatrix();

            printMatrix();
            for(int row = 0; row < allLines.size(); row++) {
                for(int column = 0; column < allLines.get(0).length(); column++) {
                    char c = allLines.get(row).charAt(column);
                    if(c == '*') {
                        Set<Integer> intSet = getSurrondingIntegers(row, column);
                        sb.append("Found * at [" + row + "," + column + "] " + intSet.size() + "\n");
                        if(intSet.size() == 2) {
                            int product = 1;
                            for (Integer integer : intSet) {
                                product = product * integer;
                            }
                            sum += product; 
                        }
                    }
                }
            }
            // System.out.println(sb.toString());
            System.out.println(sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private Object[][] getNumberMatrix() {
        Object[][] matrix = new Object[allLines.size()][allLines.get(0).length()];
        int row = 0;
        int column = 0;
        while(row < allLines.size()) {
            String numStr = "";
            boolean parsingNumber = false;
            int colStart = 0;
            column = 0;
            while(column < allLines.get(row).length()) {
                char c = allLines.get(row).charAt(column);
                if(parsingNumber) {
                    if(Character.isDigit(c)) {
                        numStr = numStr + c;
                    } else {
                        Integer number = Integer.parseInt(numStr);
                        for(int i = 0; i < numStr.length(); i++) {
                            matrix[row][colStart + i] = number;
                        }
                        parsingNumber = false;
                    }
                }
                else {
                    if(Character.isDigit(c)) {
                        parsingNumber = true;
                        colStart = column;
                        numStr = "" + c;
                    }
                }
                column++;
            }
            if(parsingNumber) {
                Integer number = Integer.parseInt(numStr);
                for(int i = 0; i < numStr.length(); i++) {
                    matrix[row][colStart + i] = number;
                }
                parsingNumber = false;
            }
            row++;
        }
        return matrix;
    }

    private boolean isSpecialChar(int row, int column) {
        char c = allLines.get(row).charAt(column);
        return !(c == '.' || Character.isDigit(c));
    }

    private Set<Integer> getSurrondingIntegers(int row, int column) {
        Set<Integer> result = new HashSet<Integer>();
        int minRow = Math.max(row -1, 0);
        int maxRow = Math.min(row + 1, allLines.size());
        int minCol = Math.max(column -1, 0);
        int maxCol = Math.min(column + 1, allLines.get(row).length() - 1);
        // System.out.println("Testing [" + minRow + "," + minCol + "] [" + maxRow + "," + maxCol + "]");
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j <= maxCol; j++) {
                // System.out.println(matrix[i][j]);
                if(matrix[i][j] instanceof Integer) {
                    result.add((Integer) matrix[i][j]);
                }
            }
        }
        return result;
    }

    private void printMatrix() {
        StringBuilder sb = new StringBuilder();
        for(int row = 0; row < matrix.length; row++) {
            for(int column = 0; column < matrix[0].length; column++) {
                if(matrix[row][column] instanceof Integer) {
                    sb.append("I");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
 }
