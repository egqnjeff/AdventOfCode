import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day4_2 {
    // private static String inputPath = "2023/Java/resource/Day4/test.txt";
    private static String inputPath = "2023/Java/resource/Day4/input.txt";

    List<ScratchCard> cards = new ArrayList<ScratchCard>();

    public static void main(String[] args) throws Exception {
        new Day4_2();
    }
    
    public Day4_2() {
        StringBuilder sb = new StringBuilder();
        try {
            for (String string : Files.readAllLines(Paths.get(inputPath))) {
                cards.add(new ScratchCard(string));
            }

            int[] cardCount = new int[cards.size()];
            for (ScratchCard card : cards) {
                int currentCardIndex = card.cardNumber -1;
                incrementCardCount(cardCount, currentCardIndex, 1);
                int matches = card.getMatches();
                for(int x = 1; x <= matches; x++) {
                    incrementCardCount(cardCount, currentCardIndex + x, cardCount[currentCardIndex]);
                }
            }
            int sum = 0;
            for (int count : cardCount) {
                sum += count;
            }
            // System.out.println(sb.toString());
            System.out.println(sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    private void incrementCardCount(int[] cardCount, int index, int i) {
        cardCount[index] = cardCount[index] + i;
    }

    class ScratchCard {

        int cardNumber;
        List<Integer> winningNumbers;
        List<Integer> yourNumbers;

        public ScratchCard(String input) {
            winningNumbers = new ArrayList<Integer>();
            yourNumbers = new ArrayList<Integer>();
            String[] cardSplit = input.split(":");
            String[] cardNumSplit = cardSplit[0].split("\\s+");
            cardNumber = Integer.parseInt(cardNumSplit[1]);
            String[] numberSplit = cardSplit[1].split("\\|");
            for (String number : numberSplit[0].trim().split("\\s+")) {
                winningNumbers.add(Integer.parseInt(number));
            }
            for (String number : numberSplit[1].trim().split("\\s+")) {
                yourNumbers.add(Integer.parseInt(number));
            }
        }

        public int calculatePoints() {
            int point = 0;
            for (Integer winningNum : winningNumbers) {
                for (Integer yourNum : yourNumbers) {
                    if(winningNum == yourNum) {
                        if(point == 0) {
                            point = 1;
                        } else {
                            point = point * 2;
                        }
                    }
                }
            }
            return point;
        }

        public int getMatches() {
            int matches = 0;
            for (Integer winningNum : winningNumbers) {
                for (Integer yourNum : yourNumbers) {
                    if(winningNum == yourNum) {
                        matches++;
                    }
                }
            }
            return matches;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Card ");
            sb.append(cardNumber);
            sb.append(" :");
            for (Integer num : winningNumbers) {
                sb.append(" ");
                sb.append(num);
            }
            sb.append(" |");
            for (Integer num : yourNumbers) {
                sb.append(" ");
                sb.append(num);
            }
            return sb.toString();
        }
    }
 }
