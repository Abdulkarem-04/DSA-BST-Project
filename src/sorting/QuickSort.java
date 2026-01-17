package sorting;

import model.StudentRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * QUICK SORT - Sort StudentRecords by CGPA (Ascending or Descending)
 *
 * Algorithm: Partition-Based Divide & Conquer
 *  1. PARTITION: Choose pivot, partition array
 *     - Elements < pivot go LEFT
 *     - Elements > pivot go RIGHT
 *  2. CONQUER: Recursively sort left and right partitions
 *  3. COMBINE: Concatenate results
 *
 * Time Complexity:
 *  - Best: O(n log n) - ideal pivot selection
 *  - Average: O(n log n) - random pivot
 *  - Worst: O(n²) - bad pivot (e.g., already sorted)
 *
 * Space Complexity: O(log n) - for recursion stack (in-place sorting)
 *
 * Stability: UNSTABLE - doesn't preserve order of equal elements
 *
 * Use Case: Fast average-case sorting, in-place sorting
 *
 * Example (Descending by CGPA):
 *  Input:  [3.45, 3.75, 3.80, 3.20, 3.65]
 *  Output: [3.80, 3.75, 3.65, 3.45, 3.20]
 */
public class QuickSort {

    /**
     * Sort StudentRecords by CGPA
     *
     * @param records List of StudentRecords to sort
     * @param descending true for descending (high to low), false for ascending
     * @return New sorted List (original list unchanged)
     */
    public static List<StudentRecord> sortByCGPA(List<StudentRecord> records, boolean descending) {
        // Handle empty or single-element list
        if (records == null || records.size() <= 1) {
            return new ArrayList<>(records != null ? records : new ArrayList<>());
        }

        // Create working copy to avoid modifying original
        List<StudentRecord> copy = new ArrayList<>(records);

        // Start quick sort
        quickSortRecursive(copy, 0, copy.size() - 1, descending);

        return copy;
    }

    /**
     * Recursive quick sort helper
     *
     * @param records The list to sort
     * @param low Starting index
     * @param high Ending index
     * @param descending true for descending order
     */
    private static void quickSortRecursive(List<StudentRecord> records, int low, int high,
                                           boolean descending) {
        // Base case: single element or empty range is already sorted
        if (low >= high) {
            return;
        }

        // PARTITION: Get pivot position and partition array
        int pivotIndex = partition(records, low, high, descending);

        // CONQUER: Recursively sort left partition
        quickSortRecursive(records, low, pivotIndex - 1, descending);

        // CONQUER: Recursively sort right partition
        quickSortRecursive(records, pivotIndex + 1, high, descending);
    }

    /**
     * Partition array around pivot element
     *
     * Uses "Lomuto Partition Scheme"
     *  - Choose last element as pivot
     *  - Partition so elements < pivot are on left, > pivot on right
     *  - Return final pivot position
     *
     * @param records The list to partition
     * @param low Start index
     * @param high End index (pivot position)
     * @param descending true for descending order
     * @return Final position of pivot
     */
    private static int partition(List<StudentRecord> records, int low, int high, boolean descending) {
        // Choose rightmost element as pivot
        double pivotCGPA = records.get(high).getCgpa();

        // Index of smaller element - indicates the right position
        // of pivot found so far
        int i = low - 1;

        // Traverse through all elements
        // Compare each element with pivot
        for (int j = low; j < high; j++) {
            double currentCGPA = records.get(j).getCgpa();

            boolean shouldSwap;
            if (descending) {
                // For descending: swap if current > pivot (high values first)
                shouldSwap = currentCGPA > pivotCGPA;
            } else {
                // For ascending: swap if current < pivot (low values first)
                shouldSwap = currentCGPA < pivotCGPA;
            }

            if (shouldSwap) {
                i++;
                // Swap records[i] and records[j]
                swap(records, i, j);
            }
        }

        // Place pivot in its correct position
        swap(records, i + 1, high);
        return i + 1;
    }

    /**
     * Swap two elements in the list
     *
     * @param records The list
     * @param i Index of first element
     * @param j Index of second element
     */
    private static void swap(List<StudentRecord> records, int i, int j) {
        StudentRecord temp = records.get(i);
        records.set(i, records.get(j));
        records.set(j, temp);
    }

    /**
     * Verify that list is sorted by CGPA
     * Useful for testing and validation
     *
     * @param records List to check
     * @param descending true if should be descending, false if ascending
     * @return true if sorted correctly, false otherwise
     */
    public static boolean isSortedByCGPA(List<StudentRecord> records, boolean descending) {
        if (records == null || records.size() <= 1) {
            return true;
        }

        for (int i = 1; i < records.size(); i++) {
            double prev = records.get(i - 1).getCgpa();
            double curr = records.get(i).getCgpa();

            if (descending) {
                // For descending: previous should be >= current
                if (prev < curr) {
                    return false;
                }
            } else {
                // For ascending: previous should be <= current
                if (prev > curr) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Print sorted results in formatted table
     */
    public static void printSortedResults(List<StudentRecord> records, boolean descending) {
        System.out.println("\n" + "=".repeat(70));
        String order = descending ? "Descending" : "Ascending";
        System.out.println("=== Quick Sort (by CGPA, " + order + ") ===");
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
