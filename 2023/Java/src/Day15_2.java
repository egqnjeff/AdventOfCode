import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15_2 {
    // private static String inputPath = "2023/Java/resource/Day15/test.txt";
    private static String inputPath = "2023/Java/resource/Day15/input.txt";

    public static void main(String[] args) throws Exception {
        new Day15_2();
    }

    public Day15_2() {
        List<Step> steps = initSteps();

        Map<Integer, List<Step>> boxes = initBoxes(steps);

        int power = calculatePower(boxes);
        System.out.println("Power " + power);
    }

    private int calculatePower(Map<Integer, List<Step>> boxes) {
        int power = 0;
        // One plus the box number of the lens in question.
        // The slot number of the lens within the box: 1 for the first lens, 2 for the second lens, and so on.
        // The focal length of the lens.
        for(Integer i : boxes.keySet()) {
            for(int j = 0; j < boxes.get(i).size(); j++) {
                int value = (i+1) * (j+1) * boxes.get(i).get(j).focalLength;
                power += value;
            }
        }
        return power;
    }

    private Map<Integer, List<Step>> initBoxes(List<Step> steps) {
        Map<Integer, List<Step>> boxes = new HashMap<>();
        for(int i = 0; i < 256; i++) {
            boxes.put(i, new ArrayList<>());
        }

        for(Step step : steps) {
            List<Step> box = boxes.get(step.getLabelHash());
            if(step.operation.equals("=")) {
                // add or update
                boolean updated = false;
                for(int i = 0; i < box.size(); i++) {
                    if(box.get(i).label.equals(step.label)) {
                        box.set(i, step);
                        updated = true;
                        break;
                    }
                }
                if(!updated) {
                    box.add(step);
                }
            } else {
                // remove
                for(int i = 0; i < box.size(); i++) {
                    if(box.get(i).label.equals(step.label)) {
                        box.remove(i);
                        break;
                    }
                }
            }
        }
        return boxes;
    }

    private List<Step> initSteps() {
        List<Step> steps = new ArrayList<Step>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            for(String data : lines.get(0).split(",")) {
                steps.add(new Step(data));
            }
        } catch (IOException e) {
			e.printStackTrace();
		}
        return steps;
    }

    public class Step {

        String label;
        String operation;
        int focalLength = 0;

        public Step(String input) {
            int labelLength = input.indexOf("-") + input.indexOf("=") + 1;
            label = input.substring(0, labelLength);
            operation = input.substring(labelLength, labelLength + 1);
            if(input.length() > labelLength + 1) {
                focalLength = Integer.parseInt(input.substring(labelLength + 1));
            }
        }

        private int getLabelHash() {
            // System.out.println("calculateHash with " + step);
            int current = 0;
            for(int i = 0; i < label.length(); i++) {
                current += (int) label.charAt(i);
                current = current * 17;
                current = current % 256;
                // System.out.println("Char " + step.charAt(i) + " current " + current);
            }
            return current;
        }
    
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Step [");
            sb.append("label[");
            sb.append(label);
            sb.append("],[");
            sb.append("operation[");
            sb.append(operation);
            sb.append("],[");
            sb.append("focalLength[");
            sb.append(focalLength);
            sb.append("],[");
            sb.append("hash[");
            sb.append(getLabelHash());
            sb.append("]]");
            return sb.toString();
        }
    }

}
