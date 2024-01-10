import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day15 {
    // private static String inputPath = "2023/Java/resource/Day15/test.txt";
    private static String inputPath = "2023/Java/resource/Day15/input.txt";

    public static void main(String[] args) throws Exception {
        new Day15();
    }

    public Day15() {
        String[] steps = initSteps();

        int total = 0;
        for(int i = 0; i < steps.length; i++) {
            total += calculateHash(steps[i]);
        }
        System.out.println("Total " + total);
    }

    private int calculateHash(String step) {
        // System.out.println("calculateHash with " + step);
        int current = 0;
        for(int i = 0; i < step.length(); i++) {
            current += (int) step.charAt(i);
            current = current * 17;
            current = current % 256;
            // System.out.println("Char " + step.charAt(i) + " current " + current);
        }
        return current;
    }

    private String[] initSteps() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            return lines.get(0).split(",");
        } catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }

}
