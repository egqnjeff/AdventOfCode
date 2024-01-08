import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12_2 {
    // private static String inputPath = "2023/Java/resource/Day12/test.txt";
    private static String inputPath = "2023/Java/resource/Day12/input.txt";

    public static void main(String[] args) throws Exception {
        new Day12_2();
    }
    
    List<Record> records = new ArrayList<Record>();

    public Day12_2() {
        initRecords();
        // printRecords();
        long total = 0;
        for (Record record : records) {
            System.out.println(record.toString());
            long possibleSprings = record.countPossibleSprings();
            System.out.println("Possible " + possibleSprings);
            total += possibleSprings;
        }
        System.out.println("Total " + total);
    }

    private void initRecords() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for (String line : lines) {
                records.add(new Record(line));
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
        String springs = "";
        List<Integer> groups = new ArrayList<Integer>();
        int[] maxPosition;
        int multiplier = 5;

        public Record(String input) {
            String[] split = input.split("\\s+");
            for(int i = 0; i < multiplier; i++) {
                if(i > 0) {
                    springs = springs + "?";
                }
                springs = springs + split[0];
                for (String numStr : split[1].split(",")) {
                    groups.add(Integer.parseInt(numStr));
                }
            }
            maxPosition = new int[groups.size()];
            int sum = 0;
            for(int i = 0; i < groups.size(); i++) {
                sum += groups.get(i);
            }
            sum += groups.size() - 2;
            for(int i = 0; i < groups.size(); i++) {
                maxPosition[i] = springs.length() - sum;
                sum -= groups.get(i) + 1;
            }
        }

        Map<String, Long> cache = new HashMap<>();

        private long countPossibleSprings() {
            Long count = nextPosition(0, 0);
            return count;
        }

        private long nextPosition(int index, int startPosition) {
            String key = index + "_" + startPosition;
            if(cache.containsKey(key)) {
                return cache.get(key);
            }
            Long count = 0l;
//            System.out.println("NextPosition - " + index + ", " + startPosition + ", " + count);
            // find next non-space
            int position = startPosition;
            while(position < springs.length() && springs.charAt(position) == '.') {
                position++;
            }

            boolean done = false;
            // if group[index] is valid in current position
            while(!done) {
                while(!done && isValidPosition(index, position)) {
                    if (index == groups.size() - 1) {
//                        System.out.println("At Last Index - " + index + ", " + startPosition + ", " + count);
                        //   if last index and remainder is not #
                        //     then increment count and position until exhausted
                        boolean increment = true;
                        for (int i = position + groups.get(index); i < springs.length(); i++) {
                            if (springs.charAt(i) == '#') {
                                increment = false;
                                break;
                            }
                        }
                        if (increment) {
                            count++;
                        }
                        position++;
                        if(position >= maxPosition[index] || springs.charAt(position - 1) == '#') {
                            done = true;
                        }
                    } else {
//                        System.out.println("Recurse - " + index + ", " + startPosition + ", " + count);
                        //   else recurse with the next index and start position
                        count += nextPosition(index + 1, position + groups.get(index) + 1);

                        position++;
                        // if we go past a # (or exceed a max position) then return
                        if(position >= maxPosition[index] || springs.charAt(position - 1) == '#') {
                            done = true;
                        }
                    }
                }
                // increment position and try again
                position++;
                // if we go past a # (or exceed a max position) then return
                if(position >= maxPosition[index] || springs.charAt(position - 1) == '#') {
                    done = true;
                }
            }
            cache.put(key, count);
            return count;
        }

        private boolean isValidPosition(int index, int position) {
            for(int i = 0; i < groups.get(index); i++) {
                if(position + i > springs.length() - 1 || springs.charAt(position + i) == '.') {
                    return false;
                }
            }
            if(position + groups.get(index) < springs.length() && springs.charAt(position + groups.get(index)) == '#') {
                return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Record)) {
                return false;
            }
            Record that = (Record) obj;
            if(!this.springs.equals(that.springs)) {
                return false;
            }
            if(this.groups.size() != that.groups.size()) {
                return false;
            }
            for(int i = 0; i < this.groups.size(); i++) {
                if(this.groups.get(i) != that.groups.get(i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(springs);
            sb.append(" ");
            sb.append(groups);
            return sb.toString();
        }
    }
}
