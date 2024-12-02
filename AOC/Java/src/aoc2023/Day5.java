package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    // private static String inputPath = "AOC/Java/resource/2023/Day5/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day5/input.txt";

    public static void main(String[] args) throws Exception {
        new Day5();
    }
    
    public Day5() {
        StringBuilder sb = new StringBuilder();
        List<Long> seeds = new ArrayList<Long>();
        List<ConvertMap> convertMaps = new ArrayList<ConvertMap>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            ConvertMap convertMap = null;
            for (String line : lines) {
                if(line.startsWith("seeds:")) {
                    // create seed list
                    String[] split = line.trim().split("\\s+");
                    for (int i = 1; i < split.length; i++) {
                       seeds.add(Long.parseLong(split[i]));
                    }
                } else if(line.length() == 0) {
                    // Do nothing
                } else if(line.contains("map")) {
                    // create new map
                    convertMap = new ConvertMap();
                    convertMaps.add(convertMap);
                } else {
                    // add to the existing map
                    convertMap.addLine(line);
                }
            }

            long minLocation = Long.MAX_VALUE;
            for (int i = 0; i < seeds.size(); i += 2) {
                System.out.println(i);
                for(int j = 0; j < seeds.get(i + 1); j++) {
                    long current = seeds.get(i) + j;
                    for (ConvertMap map : convertMaps) {
                        current = map.convertSource(current);
                    }
                    minLocation = Math.min(minLocation, current);
                }
            }
            System.out.println(minLocation);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    class ConvertMap {

        List<Long[]> values = new ArrayList<Long[]>();

        public ConvertMap() {
        }

        public void addLine(String line) {
            String[] split = line.trim().split("\\s+");
            Long[] numbers = new Long[split.length];
            for (int i = 0; i < split.length; i++) {
                numbers[i] = Long.parseLong(split[i]);
            }
            values.add(numbers);
        }

        public long convertSource(long source) {
            // System.out.println("Checking " + source);
            for (Long[] numbers : values) {
                // System.out.println(integers[1] + " <= " + source + " && " + source + " <= " + (integers[1] + integers[2]));
                if(numbers[1] <= source && source <= numbers[1] + numbers[2]) {
                    // System.out.println("return " + (source + (integers[0] - integers[1])));
                    return source + (numbers[0] - numbers[1]);
                }
            }
            // System.out.println("return " + source);
            return source;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Long[] value : values) {
                for (Long number : value) {
                    sb.append(number);
                    sb.append(" ");                        
                }
                sb.append("\n");
            }
            return sb.toString();
        }
    }
 }
