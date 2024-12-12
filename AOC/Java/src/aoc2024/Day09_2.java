package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day09/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day09/input.txt";
    public static void main(String[] args) throws Exception {
        new Day09_2();
    }

    public Day09_2() {
        Integer[] input = parseInput();
        Integer[] blocks = processDiskMap(input);
        printBlocks(blocks);
        Integer[] processedBlocks = processBlocks(blocks);
        printBlocks(processedBlocks);
        long result = checksum(processedBlocks);
        System.out.println("Checksum = " + result);
    }

    private Integer[] parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            String line = lines.get(0);
            Integer[] input = new Integer[line.length()];
            for (int i =0; i < line.length(); i++) {
                input[i] = Integer.parseInt("" + line.charAt(i));
            }
            return input;
        } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    private Integer[] processDiskMap(Integer[] diskMap) {
        ArrayList<Integer> blocks = new ArrayList<Integer>();
        int fileId = 0;
        for(int i = 0; i < diskMap.length; i+=2) {
            for (int x = 0; x < diskMap[i]; x++) {
              blocks.add(fileId);
            }
            fileId++;
            if(i < diskMap.length - 2) {
                for(int x = 0; x < diskMap[i + 1]; x++) {
                    blocks.add(-1);
                }
            }
        }
        return blocks.toArray(new Integer[0]);
    }

    private Integer[] processBlocks(Integer[] blocks) {
        Integer[] processedBlocks = Arrays.copyOf(blocks, blocks.length);
        MyFile nextFile = getNextFile(processedBlocks, processedBlocks.length -1);
        while(nextFile != null) {
            int firstFree = getFirstFree(processedBlocks, nextFile.length);
            if(firstFree < nextFile.index) {
                for(int i = 0; i < nextFile.length; i++) {
                    //move file
                    processedBlocks[firstFree + i] = processedBlocks[nextFile.index + i];
                    processedBlocks[nextFile.index + i] = -1;
                }
            }
            nextFile = getNextFile(processedBlocks, nextFile.index -1);  
        }
        return processedBlocks;
    }

    private int getFirstFree(Integer[] blocks, int size) {
        int index = 0;
        while(index < blocks.length) {
            if(blocks[index] == -1) {
                int len = 1;
                while(len < size && 
                    index + len < blocks.length && 
                    blocks[index + len] == -1) {
                    len++;
                }
                if(len == size) {
                    return index;
                }
            }
            index++;
        }
        return index;
    }

    private MyFile getNextFile(Integer[] blocks, int index) {
        if(index > 0) {
            if(blocks[index] != -1) {
                MyFile result = new MyFile();
                result.id = blocks[index];
                result.index = index;
                result.length = 1;
                while(index-- > 0 && blocks[index] == result.id) {
                    result.index = index;
                    result.length = result.length + 1;
                }
                return result;
            }
            return getNextFile(blocks, index - 1);
        }
        return null;
    }

    private long checksum(Integer[] blocks) {
        long result = 0;
        for(int i = 0; i < blocks.length; i++) {
            int value = blocks[i] == -1 ? 0 : blocks[i];
            result += i * value;
        }
        return result;
    }

    private void printBlocks(Integer[] blocks) {
        StringBuilder sb = new StringBuilder();
        for (Integer block : blocks) {
            if(block >= 0) {
                sb.append(block);
            } else {
                sb.append(".");
            }
        }
        System.out.println(sb);
    }

    private class MyFile {
        int index;
        int id;
        int length;
    }
}
