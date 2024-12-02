package aoc2023;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
//    private static String inputPath = "AOC/Java/resource/2023/Day13/test.txt";
    private static String inputPath = "AOC/Java/resource/2023/Day13/input.txt";

    public static void main(String[] args) throws Exception {
        new Day13();
    }

    List<Record> records = new ArrayList<Record>();

    public Day13() {
        initRecords();
//        printRecords();

        int total = 0;
        for (Record record : records) {
//            System.out.println();
//            System.out.println(record.toString());
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
            return (getRow() * 100) + getColumn();
        }

        private int getRow() {
//            System.out.println("getRow");
            return getMirrorPosition(lines);
        }

        private int getColumn() {
//            System.out.println("getColumn");
            return getMirrorPosition(getInvertedLines());
        }

        private List<String> getInvertedLines() {
            List<String> strings = new ArrayList<String>();
            for(int i = 0; i < lines.get(0).length(); i++) {
                strings.add(getInvertedLine(i));
            }
            return strings;
        }

        private String getInvertedLine(int position) {
            String line = "";
            for(int i = 0; i < lines.size(); i++) {
                line = line + lines.get(i).charAt(position);
            }
            return line;
        }

        private int getMirrorPosition(List<String> strings) {
            for(int position = 1; position < strings.size(); position++) {
                boolean mirror = true;
                int min = Math.min(position, strings.size() - position);
//                System.out.println("Testing position " + position + " with min " + min);
                for(int i = 0; i < min; i++) {
                    int first = position - i - 1;
                    int second = position + i;
//                    System.out.println("Comparing:");
//                    System.out.println("first: " + first + " - " + strings.get(first));
//                    System.out.println("second: " + second + " - " + strings.get(second));
                    if(!strings.get(first).equals(strings.get(second))) {
                        mirror = false;
                        break;
                    }
                }
                if(mirror) {
//                    System.out.println("Mirrored at " + position);
                    return position;
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
