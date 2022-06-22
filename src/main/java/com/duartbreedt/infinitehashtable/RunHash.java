package com.duartbreedt.infinitehashtable;

import com.duartbreedt.infinitehashtable.fractalhash.FractalTester;
import com.duartbreedt.infinitehashtable.linkedlist.LinkedListTester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RunHash {

    private static final String WORDS_FILE_NAME = "src/main/resources/words.txt";
    private static String[] allInsertedStrings = null;

    public static void main(String[] args) {

        populateArrayOfWordsFromFile(300000);

        System.out.println("========== InfiniteHash Result ============");

        FractalTester tester = new FractalTester();

        tester.insertIntoInfiniteHashTable(allInsertedStrings);
        tester.generateSpaceEfficiencyResults();

        tester.findLongestLookup(allInsertedStrings);

        System.out.println("=========== LinkedList Hash Result ===========");

        LinkedListTester llTester = new LinkedListTester();

        llTester.insertIntoInfiniteHashTable(allInsertedStrings);
        llTester.generateSpaceEfficiencyResults();

        llTester.findLongestLookup(allInsertedStrings);
    }

    public static void populateArrayOfWordsFromFile(int lines) {
        allInsertedStrings = new String[lines];

        try (BufferedReader reader = new BufferedReader(new FileReader(WORDS_FILE_NAME))) {
            String line;

            for (int i = 0; (line = reader.readLine()) != null && i < lines; i++) {
                allInsertedStrings[i] = line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}