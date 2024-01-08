import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
    // private static String inputPath = "2023/Java/resource/Day12/test.txt";
    private static String inputPath = "2023/Java/resource/Day12/input.txt";

    public static void main(String[] args) throws Exception {
        new Day12();
    }
    
    List<Record> records = new ArrayList<Record>();

    public Day12() {
        initRecords();
        // printRecords();
        int total = 0;
        for (Record record : records) {
            System.out.println(record.toString());
            int possibleSprings = record.getPossibleSprings();
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
        String springs;
        List<Integer> groups = new ArrayList<Integer>();
        int[] gaps;
        int extra;

        public Record(String input) {
            String[] split = input.split("\\s+");
            springs = split[0];
            for (String numStr : split[1].split(",")) {
                groups.add(Integer.parseInt(numStr));
            }
            gaps = new int[groups.size()];
        }

        private int getPossibleSprings() {
            // List<String> possible = new ArrayList<String>();
            for (int i = 0; i < gaps.length; i++) {
                gaps[i] = 0;
            }
            int min = groups.size() - 1;
            for (Integer group : groups) {
                min += group;
            }
            extra = springs.length() - min;
            System.out.println("Extra = " + extra);

            int sumPossible = 0;
            do {
                if(isPossible(gaps)) {
                    sumPossible++;
                }
                // int sum = 0;
                // for (int gap : gaps) {
                //     sum += gap;
                // }
                // int last = extra - sum;
    
                // StringBuilder sb = new StringBuilder();
                // sb.append("Len - ");
                // for (int i : gaps) {
                //     sb.append(i);
                //     sb.append(", ");
                // }
                // sb.append(last);
                // System.out.println(sb.toString());
            } 
            while(incrementGaps());
            return sumPossible;
        }

        private boolean isPossible(int[] gaps) {
            String guess = "";
            for(int i = 0; i < groups.size(); i++) {
                if(i > 0) {
                    guess = guess + ".";
                }
                for(int j = 0; j < gaps[i]; j++) {
                    guess = guess + ".";
                }
                for(int j = 0; j < groups.get(i); j++) {
                    guess = guess + "#";
                }
            }
            for(int j = guess.length(); j < springs.length(); j++) {
                guess = guess + ".";
            }
            // System.out.println("Compare");
            // System.out.println(">" + springs + "<");
            // System.out.println(">" + guess + "<");
            for (int i = 0; i < springs.length(); i++) {
                if(springs.charAt(i) == '?' || springs.charAt(i) == guess.charAt(i)) {
                } else {
                    // System.out.println(" False");
                    return false;
                }
            }
            // System.out.println(" True");
            return true;
        }

        private boolean incrementGaps() {
            if(gaps[gaps.length - 1] == extra) {
                return false;
            }
            int sum = 0;
            for (int gap : gaps) {
                sum += gap;
            }
            int index = 0;
            boolean rollover = true;
            while(rollover && index < gaps.length) {
                if(sum >= extra) {
                    sum -= gaps[index];
                    gaps[index] = 0;
                } else {
                    gaps[index]++;
                    sum++;
                    rollover = false;
                }
                index++;
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
