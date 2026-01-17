package sorting;

import model.StudentRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * MERGE SORT - Sort StudentRecords by Name (Alphabetically A-Z)
 *
 * Algorithm: Divide & Conquer
 *  1. DIVIDE: Split array into two halves
 *  2. CONQUER: Recursively sort each half
 *  3. MERGE: Merge sorted halves back together
 *
 * Time Complexity:
 *  - Best: O(n log n)
 *  - Average: O(n log n)
 *  - Worst: O(n log n)
 *  → GUARANTEED O(n log n) - ALWAYS!
 *
 * Space Complexity: O(n) - requires temporary array for merging
 *
 * Stability: STABLE - preserves relative order of equal elements
 *
 * Use Case: When you need guaranteed O(n log n) performance
 *
 * Example:
 *  Input:  [Ali, Aisyah, Bilal, Daniel, Citra]
 *  Output: [Aisyah, Ali, Bilal, Citra, Daniel]
 */
public class MergeSort {

    /**
     * Sort StudentRecords by Name in ascending alphabetical order (A-Z)
     *
     * @param records List of StudentRecords to sort
     * @return New sorted List (original list unchanged)
     */
    public static List<StudentRecord> sortByName(List<StudentRecord> records) {
        // Handle empty or single-element list
        if (records == null || records.size() <= 1) {
            return new ArrayList<>(records != null ? records : new ArrayList<>());
        }

        // Create working copy to avoid modifying original
        List<StudentRecord> copy = new ArrayList<>(records);

        // Start merge sort
        mergeSortRecursive(copy, 0, copy.size() - 1);

        return copy;
    }

    /**
     * Recursive merge sort helper
     * Divides the array and recursively sorts each half
     *
     * @param records The list to sort
     * @param left Starting index
     * @param right Ending index
     */
    private static void mergeSortRecursive(List<StudentRecord> records, int left, int right) {
        // Base case: single element or empty range is already sorted
        if (left >= right) {
            return;
        }

        // Find middle point
        int mid = left + (right - left) / 2;

        // DIVIDE: Recursively sort left half
        mergeSortRecursive(records, left, mid);

        // DIVIDE: Recursively sort right half
        mergeSortRecursive(records, mid + 1, right);

        // CONQUER: Merge the sorted halves
        merge(records, left, mid, right);
    }

    /**
     * Merge two sorted subarrays
     *
     * Merges records[left...mid] and records[mid+1...right]
     * Both subarrays are already sorted by name
     *
     * @param records The list containing both subarrays
     * @param left Start of first subarray
     * @param mid End of first subarray
     * @param right End of second subarray
     */
    private static void merge(List<StudentRecord> records, int left, int mid, int right) {
        // Create temporary arrays for merging
        List<StudentRecord> leftArr = new ArrayList<>();
        List<StudentRecord> rightArr = new ArrayList<>();

        // Copy data to temporary arrays
        for (int i = left; i <= mid; i++) {
            leftArr.add(records.get(i));
        }
        for (int i = mid + 1; i <= right; i++) {
            rightArr.add(records.get(i));
        }

        // Merge the temporary arrays back into records
        int i = 0;      // Index for left array
        int j = 0;      // Index for right array
        int k = left;   // Index for main array

        while (i < leftArr.size() && j < rightArr.size()) {
            // Compare names alphabetically using compareTo()
            String leftName = leftArr.get(i).getName();
            String rightName = rightArr.get(j).getName();

            if (leftName.compareTo(rightName) <= 0) {
                // Left element is smaller or equal (STABLE)
                records.set(k++, leftArr.get(i++));
            } else {
                // Right element is smaller
                records.set(k++, rightArr.get(j++));
            }
        }

        // Copy remaining elements from left array
        while (i < leftArr.size()) {
            records.set(k++, leftArr.get(i++));
        }

        // Copy remaining elements from right array
        while (j < rightArr.size()) {
            records.set(k++, rightArr.get(j++));
        }
    }

    /**
     * Verify that list is sorted by name
     * Useful for testing and validation
     *
     * @param records List to check
     * @return true if sorted, false otherwise
     */
    public static boolean isSortedByName(List<StudentRecord> records) {
        if (records == null || records.size() <= 1) {
            return true;
        }

        for (int i = 1; i < records.size(); i++) {
            String prev = records.get(i - 1).getName();
            String curr = records.get(i).getName();

            if (prev.compareTo(curr) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Print sorted results in formatted table
     */
    public static void printSortedResults(List<StudentRecord> records) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== Merge Sort (by Name) ===");
        System.out.println("=".repeat(70));
        System.out.printf("%-25s %-10s %-8s\n", "NAME", "MATRIC", "CGPA");
        System.out.println("-".repeat(70));

        for (StudentRecord record : records) {
            System.out.printf("%-25s %-10s %.2f\n",
                    record.getName(),
                    record.getMatricNumber(),
                    record.getCgpa());
        }

        System.out.println("=".repeat(70) + "\n");
    }
}
