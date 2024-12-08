package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day07_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day07/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day07/input.txt";
    public static void main(String[] args) throws Exception {
        new Day07_2();
    }

    long sumValues = 0;

    public Day07_2() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
                for (String line : lines) {
                    sumValues += getValue(line);
                }
        } catch (IOException e) {
            e.printStackTrace();
		}

        System.out.println("Sum values = " + sumValues);
    }

    private long getValue(String line) {
        String[] strings = line.split(":");
        long result = Long.parseLong(strings[0]);
        ArrayList<Long> numbers = new ArrayList<Long>();
        for(String split : strings[1].trim().split("\\s+")) {
            numbers.add(Long.parseLong(split));
        }
        char[] operations = new char[numbers.size() - 1];
        for(int i = 0; i < operations.length; i++) {
            operations[i] = '+';
        }
        do {
            if(getResult(numbers, operations) == result) {
                return result;
            }
        } while(incrementOperations(operations));
        return 0;
    }

    private long getResult(ArrayList<Long> numbers, char[] operations) {
        long result = numbers.get(0);
        for(int i = 1; i < numbers.size(); i++) {
            switch (operations[i-1]) {
                case '+' : {
                    result = result + numbers.get(i);
                    break;
                }
                case '|' : {
                    result = Long.parseLong(result + "" + numbers.get(i));
                    break;
                }
                case '*' : {
                    result = result * numbers.get(i);
                    break;
                }
            }
        }
        return result;
    }

    private boolean incrementOperations(char[] operations) {
        int i = 0;
        boolean done = false;
        do {
            switch (operations[i]) {
                case '+' : {
                    operations[i] = '|';
                    done = true;
                    break;
                }
                case '|' : {
                    operations[i] = '*';
                    done = true;
                    break;
                }
                case '*' : {
                    operations[i] = '+';
                    i++;
                    if(i >= operations.length) {
                        return false;
                    }
                    break;
                }
            }
        } while (!done && i < operations.length);
        return true;
    }
}
