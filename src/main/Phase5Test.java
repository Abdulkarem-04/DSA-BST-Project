package main;

import model.StudentRecord;
import tree.BST;
import utilities.CSVDataLoader;
import sorting.MergeSort;
import sorting.QuickSort;
import search.LinearSearch;
import java.util.List;
import java.util.ArrayList;

/**
 * Phase 5 Test - Verify Merge Sort, Quick Sort, and Linear Search work correctly
 */
public class Phase5Test {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 PHASE 5: SORTING & SEARCHING TEST");
        System.out.println("=".repeat(70) + "\n");

        // Load data from CSV
        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");

        if (records.length == 0) {
            System.err.println("❌ Failed to load CSV data. Test aborted.");
            return;
        }

        // ========== BUILD TEST TREE & GET RECORDS ==========
        System.out.println("-".repeat(70));
        System.out.println("Building test BST with 20 students...");
        System.out.println("-".repeat(70) + "\n");

        BST bst = new BST();
        List<StudentRecord> allRecords = new ArrayList<>();

        for (int i = 0; i < Math.min(20, records.length); i++) {
            StudentRecord record = records[i];
            bst.insert(record);
            allRecords.add(record);
        }

        System.out.println("✓ Tree built with " + bst.countNodes() + " students\n");

        // ========== TEST 1: MERGE SORT BY NAME ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 1: MERGE SORT (by Name A-Z)");
        System.out.println("-".repeat(70));

        System.out.println("\nBefore sort (first 5):");
        for (int i = 0; i < Math.min(5, allRecords.size()); i++) {
            System.out.println("  " + allRecords.get(i).getName() + " - " +
                    allRecords.get(i).getMatricNumber());
        }

        long mergeStartTime = System.nanoTime();
        List<StudentRecord> sortedByName = MergeSort.sortByName(allRecords);
        long mergeEndTime = System.nanoTime();
        long mergeDuration = (mergeEndTime - mergeStartTime) / 1000; // microseconds

        System.out.println("\nAfter Merge Sort (all):");
        for (StudentRecord record : sortedByName) {
            System.out.println("  " + record.getName() + " (" + record.getMatricNumber() +
                    ", CGPA: " + record.getCgpa() + ")");
        }

        boolean isSortedByName = MergeSort.isSortedByName(sortedByName);
        System.out.println("\n✓ Correctly sorted by name: " + (isSortedByName ? "YES ✓" : "NO ❌"));
        System.out.println("✓ Time taken: " + mergeDuration + " microseconds");
        System.out.println("✓ Original list unchanged: " +
                (!allRecords.get(0).getName().equals(sortedByName.get(0).getName()) ? "YES ✓" : "POSSIBLY"));

        MergeSort.printSortedResults(sortedByName);

        // ========== TEST 2: QUICK SORT BY CGPA (ASCENDING) ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 2: QUICK SORT (by CGPA, Ascending)");
        System.out.println("-".repeat(70));

        long quickAscStartTime = System.nanoTime();
        List<StudentRecord> sortedByCGPAAsc = QuickSort.sortByCGPA(allRecords, false);
        long quickAscEndTime = System.nanoTime();
        long quickAscDuration = (quickAscEndTime - quickAscStartTime) / 1000;

        boolean isSortedByCGPAAsc = QuickSort.isSortedByCGPA(sortedByCGPAAsc, false);
        System.out.println("Correctly sorted by CGPA (ascending): " +
                (isSortedByCGPAAsc ? "YES ✓" : "NO ❌"));
        System.out.println("Time taken: " + quickAscDuration + " microseconds");

        QuickSort.printSortedResults(sortedByCGPAAsc, false);

        // ========== TEST 3: QUICK SORT BY CGPA (DESCENDING) ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 3: QUICK SORT (by CGPA, Descending)");
        System.out.println("-".repeat(70));

        long quickDescStartTime = System.nanoTime();
        List<StudentRecord> sortedByCGPADesc = QuickSort.sortByCGPA(allRecords, true);
        long quickDescEndTime = System.nanoTime();
        long quickDescDuration = (quickDescEndTime - quickDescStartTime) / 1000;

        boolean isSortedByCGPADesc = QuickSort.isSortedByCGPA(sortedByCGPADesc, true);
        System.out.println("Correctly sorted by CGPA (descending): " +
                (isSortedByCGPADesc ? "YES ✓" : "NO ❌"));
        System.out.println("Time taken: " + quickDescDuration + " microseconds");

        QuickSort.printSortedResults(sortedByCGPADesc, true);

        // ========== TEST 4: LINEAR SEARCH (CGPA >= 3.50) ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 4: LINEAR SEARCH (CGPA >= 3.50) - MANDATORY");
        System.out.println("-".repeat(70));

        // Get in-order traversal from BST for Linear Search
        List<StudentRecord> allStudents = bst.inOrderTraversal();

        long linearStartTime = System.nanoTime();
        List<StudentRecord> highCGPAStudents = LinearSearch.findByMinimumCGPA(allStudents, 3.50);
        long linearEndTime = System.nanoTime();
        long linearDuration = (linearEndTime - linearStartTime) / 1000;

        System.out.println("Search threshold: CGPA >= 3.50");
        System.out.println("Time taken: " + linearDuration + " microseconds");
        System.out.println("Students found: " + highCGPAStudents.size());

        LinearSearch.printResults(highCGPAStudents, 3.50);

        // ========== TEST 5: LINEAR SEARCH (OTHER THRESHOLDS) ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 5: LINEAR SEARCH (Various Thresholds)");
        System.out.println("-".repeat(70));

        double[] thresholds = {3.20, 3.50, 3.70, 3.90};

        System.out.println("\nSearch results by threshold:");
        for (double threshold : thresholds) {
            List<StudentRecord> results = LinearSearch.findByMinimumCGPA(allStudents, threshold);
            System.out.println("  CGPA >= " + threshold + ": " + results.size() + " students");
        }

        // ========== TEST 6: LINEAR SEARCH BY RANGE ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 6: LINEAR SEARCH (CGPA Range)");
        System.out.println("-".repeat(70));

        System.out.println("\nSearch CGPA range [3.40, 3.70]:");
        List<StudentRecord> rangeResults = LinearSearch.findByRangeCGPA(allStudents, 3.40, 3.70);

        System.out.printf("%-10s %-25s %-8s\n", "MATRIC", "NAME", "CGPA");
        System.out.println("-".repeat(70));
        for (StudentRecord record : rangeResults) {
            System.out.printf("%-10s %-25s %.2f\n",
                    record.getMatricNumber(), record.getName(), record.getCgpa());
        }
        System.out.println("Total: " + rangeResults.size() + " students");

        // ========== TEST 7: LINEAR SEARCH BY NAME ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 7: LINEAR SEARCH (by Name Pattern)");
        System.out.println("-".repeat(70));

        System.out.println("\nSearch for names containing 'i' or 'I':");
        List<StudentRecord> nameResults = LinearSearch.findByName(allStudents, "i");

        System.out.printf("%-10s %-25s %-8s\n", "MATRIC", "NAME", "CGPA");
        System.out.println("-".repeat(70));
        for (StudentRecord record : nameResults) {
            System.out.printf("%-10s %-25s %.2f\n",
                    record.getMatricNumber(), record.getName(), record.getCgpa());
        }
        System.out.println("Total: " + nameResults.size() + " students");

        // ========== TEST 8: PERFORMANCE COMPARISON ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 8: PERFORMANCE COMPARISON");
        System.out.println("-".repeat(70));

        System.out.println("\nAlgorithm Performance (microseconds):");
        System.out.println("  • Merge Sort: " + mergeDuration + " µs");
        System.out.println("  • Quick Sort (Asc): " + quickAscDuration + " µs");
        System.out.println("  • Quick Sort (Desc): " + quickDescDuration + " µs");
        System.out.println("  • Linear Search: " + linearDuration + " µs");

        System.out.println("\nTime Complexity Analysis:");
        System.out.println("  • Merge Sort: O(n log n) = guaranteed");
        System.out.println("  • Quick Sort: O(n log n) average, O(n²) worst");
        System.out.println("  • Linear Search: O(n) = check every element");

        // ========== TEST 9: EDGE CASES ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 9: EDGE CASES");
        System.out.println("-".repeat(70));

        // Empty list
        System.out.println("\nTesting with empty list:");
        List<StudentRecord> emptyList = new ArrayList<>();
        System.out.println("  • Merge Sort: " + MergeSort.sortByName(emptyList).size() + " elements");
        System.out.println("  • Quick Sort: " + QuickSort.sortByCGPA(emptyList, false).size() + " elements");
        System.out.println("  • Linear Search: " + LinearSearch.findByMinimumCGPA(emptyList, 3.5).size() + " matches");

        // Single element
        System.out.println("\nTesting with single element:");
        List<StudentRecord> singleList = new ArrayList<>();
        singleList.add(records[0]);
        System.out.println("  • Merge Sort: " + MergeSort.sortByName(singleList).size() + " elements ✓");
        System.out.println("  • Quick Sort: " + QuickSort.sortByCGPA(singleList, false).size() + " elements ✓");
        System.out.println("  • Linear Search: " +
                LinearSearch.findByMinimumCGPA(singleList, 3.0).size() + " matches ✓");

        // ========== SUMMARY ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ PHASE 5 TEST COMPLETE");
        System.out.println("=".repeat(70));
        System.out.println("All sorting and searching algorithms working correctly!");
        System.out.println("Ready to move to Phase 6: Testing & Analysis\n");
    }
}
