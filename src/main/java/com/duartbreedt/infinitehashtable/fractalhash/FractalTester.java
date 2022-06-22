package com.duartbreedt.infinitehashtable.fractalhash;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FractalTester extends FractalHash {

    private final NumberFormat formatter = new DecimalFormat("#0.00");
    private final NumberFormat millisecondFormatter = new DecimalFormat("#0.0000");

    public void findLongestLookup(String[] allInsertedStrings) {
        double totalTime = 0;
        int longestTimeIndex = -1;
        long longestTime = 0;

        for (int i = 0; i < allInsertedStrings.length; i++) {

            long startTime = System.nanoTime();
            find(allInsertedStrings[i]);
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;

            totalTime += (double) timeTaken / 1000000;

            if (timeTaken > longestTime) {
                longestTime = timeTaken;
                longestTimeIndex = i;
            }
        }

        System.out.println("Longest Lookup: " + allInsertedStrings[longestTimeIndex] + " " + millisecondFormatter.format((double) longestTime / 1000000) + " Miliseconds");
        System.out.println("Total Lookup Time: " + millisecondFormatter.format(totalTime) + " Miliseconds");
    }

    public void insertIntoInfiniteHashTable(String[] allInsertedStrings) {
        for (String allInsertedString : allInsertedStrings) {
            insert(allInsertedString);
        }
    }

    public void generateSpaceEfficiencyResults() {
        int slots = getNumberOfSlots();
        int inserted = getNumberOfInsertedNodes();

        double efficiency = ((double) inserted / slots) * 100.0;

        System.out.println(formatter.format(efficiency) + " full");
        System.out.println(inserted + "/" + slots);
    }
}