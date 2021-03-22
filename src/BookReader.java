import java.util.*;
import java.io.*;
import java.text.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class BookReader {
    public static void main(String[] args) throws FileNotFoundException {
        DecimalFormat df = new DecimalFormat("#.##");
        HashMap<String, Integer> alice = new HashMap<>();
        ArrayList<String> commonWords = new ArrayList<>();
        int wordsCount = 0;

        File file = new File("alice.txt");
        File file2 = new File("commonwords.txt");
        Scanner sc = new Scanner(file);
        Scanner sc2 = new Scanner(file2);

        while (sc2.hasNext()) {
            String word = sc2.next();
            commonWords.add(word);
        }

        while (sc.hasNext()) {
            String word = sc.next();
            word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (!commonWords.contains(word)) {
                if (!alice.containsKey(word)) {
                    alice.put(word, 1);
                    wordsCount++;
                } else {
                    alice.put(word, alice.get(word) + 1);
                    wordsCount++;
                }
            }
        }

        //Sorting
        Map<String, Integer> sortedAlice = alice.entrySet().stream()
                .sorted(Collections.reverseOrder(comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (a1, a2) -> a2, LinkedHashMap::new));

        Iterator<Map.Entry<String, Integer>> it = sortedAlice.entrySet().iterator();
        int counter = 0;
        System.out.println("A 10 leggyakrabban hasznalt szó, SZÁZALÉKOSÍTVA, SORRENDBEN az " + file + "-ben...");
        while (it.hasNext() && counter != 10) {
            Map.Entry<String, Integer> aliceSortedTen = it.next();
            double percentage = (Double.valueOf(aliceSortedTen.getValue()) * 100) / (double) wordsCount;
            System.out.println(aliceSortedTen.getKey() + ": " + df.format(percentage) + "%");
            counter++;
        }
    }
}
