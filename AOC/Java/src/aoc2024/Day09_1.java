package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09_1 {
    // private static String inputPath = "AOC/Java/resource/2024/Day09/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day09/input.txt";
    public static void main(String[] args) throws Exception {
        new Day09_1();
    }

    public Day09_1() {
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
        int firstFree = getNextFree(processedBlocks, 0);
        int lastUsed = getNextUsed(processedBlocks, processedBlocks.length -1);
        while(lastUsed > firstFree) {
            processedBlocks[firstFree] = processedBlocks[lastUsed];
            processedBlocks[lastUsed] = -1;
            firstFree = getNextFree(processedBlocks, firstFree + 1);
            lastUsed = getNextUsed(processedBlocks, lastUsed -1);
        }
        return processedBlocks;
    }

    private int getNextFree(Integer[] blocks, int index) {
        if(index < blocks.length) {
            if(blocks[index] == -1) {
                return index;
            }
            return getNextFree(blocks, index + 1);
        }
        return index;
    }

    private int getNextUsed(Integer[] blocks, int index) {
        if(index > 0) {
            if(blocks[index] != -1) {
                return index;
            }
            return getNextUsed(blocks, index - 1);
        }
        return index;
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

}
