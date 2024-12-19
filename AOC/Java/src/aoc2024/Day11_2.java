package aoc2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11_2 {
    // private static String inputPath = "AOC/Java/resource/2024/Day11/test.txt";
    private static String inputPath = "AOC/Java/resource/2024/Day11/input.txt";
    public static void main(String[] args) throws Exception {
        new Day11_2();
    }

    int blinks = 75;
    long numStones = 0;

    public Day11_2() {
        for(String stone : parseInput()) {
            numStones += Cache.getStone(stone).getNumStones(blinks);
        }
        System.out.println("Num Stones = " + numStones);
    }

    private ArrayList<String> parseInput() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            String line = lines.get(0);
            ArrayList<String> input = new ArrayList<String>();
            for(String numString : line.split("\\s+")) {
                input.add(numString);
            }
            return input;
        } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

    private static ArrayList<String> applyRules(String stone) {
        ArrayList<String> output = new ArrayList<String>();
        // rule 1
        // If the stone is engraved with the number 0, 
        // it is replaced by a stone engraved with the number 1.
        if(stone.equals("0")) {
            output.add("1");
        }
        
        // rule 2
        // If the stone is engraved with a number that has an even number of digits, 
        // it is replaced by two stones. 
        // The left half of the digits are engraved on the new left stone, 
        // and the right half of the digits are engraved on the new right stone. 
        // (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
        else if(stone.length() % 2 == 0) {
            output.add(stone.substring(0, stone.length() / 2));
            long right = Long.parseLong(stone.substring(stone.length() / 2, stone.length()));
            output.add("" + right);
        }
        // rule 3
        // If none of the other rules apply, 
        // the stone is replaced by a new stone; 
        // the old stone's number multiplied by 2024 is engraved on the new stone.
        else {
            long val = Long.parseLong(stone);
            val = val * 2024;
            output.add("" + val);
        }
        // rule 4
        // No matter how the stones change, 
        // their order is preserved, and they stay on their perfectly straight line.
        return output;
    }

    public static class Cache {

        static Map<String, Cache> cacheMap = new HashMap<String, Cache>();

        public static Cache getStone(String stone) {
            if(!cacheMap.keySet().contains(stone)) {
                Cache current = new Cache(stone);
                cacheMap.put(stone, current);
            }
            return cacheMap.get(stone);
        }

        private String _stone;
        private ArrayList<Cache> connectedList = new ArrayList<Cache>();
        private Map<Integer, Long> cachedCount = new HashMap<Integer, Long>();

        public Cache(String stone) {
            _stone = stone;
            cachedCount.put(0,1l);
        }

        public long getNumStones(int iteration) {
            if(!cachedCount.keySet().contains(iteration)) {
                if(connectedList.size() == 0) {
                    for(String connected : applyRules(_stone)) {
                        connectedList.add(Cache.getStone(connected));
                    }
                }
                long sum = 0;
                for (Cache cache : connectedList) {
                    sum += cache.getNumStones(iteration - 1);
                }
                cachedCount.put(iteration, sum);
            }
            return cachedCount.get(iteration);
        }

    } 
}
