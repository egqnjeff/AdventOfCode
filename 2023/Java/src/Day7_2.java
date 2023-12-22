import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Day7_2 {
    // private static String inputPath = "2023/Java/resource/Day7/test.txt";
    private static String inputPath = "2023/Java/resource/Day7/input.txt";

    public static void main(String[] args) throws Exception {
        new Day7_2();
    }
    
    public Day7_2() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            Set<Hand> handSet = new TreeSet<Hand>();
            for (String line : lines) {
                handSet.add(new Hand(line));
            }
            long count = 1;
            long sum = 0;
            for (Hand hand : handSet) {
                sum += count * hand.bid;
                count++;
            }
            System.out.println(sum);
        } catch (IOException e) {
			e.printStackTrace();
		}
    }

    class Hand implements Comparable {
        String cards;
        int bid;
        int type;

        static Map<Character, Integer> cardValue = new HashMap<Character, Integer>();
        static {
            cardValue.put('2',2);
            cardValue.put('3',3);
            cardValue.put('4',4);
            cardValue.put('5',5);
            cardValue.put('6',6);
            cardValue.put('7',7);
            cardValue.put('8',8);
            cardValue.put('9',9);
            cardValue.put('T',10);
            cardValue.put('J',1);
            cardValue.put('Q',12);
            cardValue.put('K',13);
            cardValue.put('A',14);
        }

        public Hand(String line) {
            String[] split = line.split("\\s+");
            cards = split[0];
            bid = Integer.parseInt(split[1]);
            type = getType();
        }

        private int getType() {
            Map<Byte, Integer> matches = new HashMap<Byte, Integer>();
            int jokers = 0;
            for (Byte c : cards.getBytes()) {
                if(c == 'J') {
                    jokers++;
                } else {
                    Integer count = matches.get(c);
                    if(count == null) {
                        count = 0;
                    }
                    count++;
                    matches.put(c, count);
                }
            }
            if(jokers > 0) {
                byte maxC = ' ';
                int maxI = 0;
                for(Byte c : matches.keySet()) {
                    if(matches.get(c) > maxI) {
                        maxI = matches.get(c);
                        maxC = c;
                    }
                }
                matches.put(maxC, maxI + jokers);
            }
            if(matches.size() == 1) {
                // 5 of a kind
                return 7;
            } else if(matches.size() == 2) {
                for (Integer count : matches.values()) {
                    if(count == 4) {
                        // 4 of a king
                        return 6;
                    }
                }
                // full house
                return 5;
            } else if(matches.size() == 3) {
                for (Integer count : matches.values()) {
                    if(count == 3) {
                        // 3 of a king
                        return 4;
                    }
                }
                // 2 pair
                return 3;
            }
            else if(matches.size() == 4) {
                // 1 pair
                return 2;
            }
            // high card
            return 1;
        }

        @Override
        public int compareTo(Object other) {
            // a negative integer, zero, or a positive integer 
            //as this object is less than, equal to, or greater than the specified object.
            Hand that = (Hand) other;
            if(this.type != that.type) {
                return this.type - that.type;
            }
            for (int i = 0; i < this.cards.length(); i++) {
                int thisVal = Hand.cardValue.get(this.cards.charAt(i));
                int thatVal = Hand.cardValue.get(that.cards.charAt(i));
                if(thisVal != thatVal) {
                    return thisVal - thatVal;
                }
            }
            return 0;
        }
    }
 }
