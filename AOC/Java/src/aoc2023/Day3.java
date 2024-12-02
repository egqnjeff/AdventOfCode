package aoc2023;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3 {
    // private static String inputPath = "AOC/Java/resource/2023/Day3/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day3/input.txt";

    private int row = 0;
    private int column = 0;
    List<String> allLines = null;

    public static void main(String[] args) throws Exception {
        new Day3();
    }
    
    public Day3() {
        StringBuilder sb = new StringBuilder();
        try {
            row = 0;
            column = 0;
			allLines = Files.readAllLines(Paths.get(inputPath));
            int sum = 0;

            while(row < allLines.size()) {
                String number = "";
                boolean parsingNumber = false;
                int colStart = 0;
                column = 0;
                while(column < allLines.get(row).length()) {
                    char c = allLines.get(row).charAt(column);
                    if(parsingNumber) {
                        if(Character.isDigit(c)) {
                            number = number + c;
                        } else {
                            if(isAdjacentToSpecial(row, colStart, number.length())) {
                                sum += Integer.parseInt(number);
                                sb.append(" Valid ");
                            } 
                            parsingNumber = false;
                            sb.append("[" + number + "]");
                        }
                    }
                    else {
                        if(Character.isDigit(c)) {
                            parsingNumber = true;
                            colStart = column;
                            number = "" + c;
                        }
                    }
                    column++;
                }
                if(parsingNumber) {
                    if(isAdjacentToSpecial(row, colStart, number.length())) {
                        sum += Integer.parseInt(number);
                        sb.append(" Valid ");
                    }
                    parsingNumber = true;
                }
                row++;
            }
            System.out.println(sb.toString());
            System.out.println(sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private boolean isSpecialChar(int row, int column) {
        char c = allLines.get(row).charAt(column);
        return !(c == '.' || Character.isDigit(c));
    }

    private boolean isAdjacentToSpecial(int startRow, int startCol, int length) {
        int minRow = Math.max(startRow -1, 0);
        int maxRow = Math.min(startRow + 1, allLines.size());
        int minCol = Math.max(startCol -1, 0);
        int maxCol = Math.min(startCol + length + 1, allLines.get(startRow).length() - 1);
        System.out.println("Testing [" + minRow + "," + minCol + "] [" + maxRow + "," + maxCol + "]");
        for (int i = minRow; i <= maxRow; i++) {
            for (int j = minCol; j < maxCol; j++) {
                System.out.println(allLines.get(i).charAt(j));
                if(isSpecialChar(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }
 }
