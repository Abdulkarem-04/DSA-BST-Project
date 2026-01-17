package search;

import model.StudentRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * LINEAR SEARCH - Filter StudentRecords by CGPA threshold
 *
 * Algorithm: Sequential Search
 *  1. Iterate through entire list
 *  2. Check if student.cgpa >= threshold
 *  3. Add matching records to result
 *
 * Time Complexity: O(n) - must check every element
 * Space Complexity: O(k) - where k = number of matches
 *
 * Note: This is different from BST search!
 *  - BST search: O(log n) but only by matric number
 *  - Linear search: O(n) but can search by ANY property (CGPA, name, etc.)
 *
 * Use Case: When you need to filter by criteria that can't use BST ordering
 *
 * Example:
 *  Input: All students, threshold = 3.50
 *  Output: [
 *    AIU103 (Farah, 3.65),
 *    AIU108 (Hafiz, 3.50),
 *    AIU110 (Daniel, 3.80)
 *  ]
 *
 * MANDATORY FOR PROJECT - Must match PDF sample output exactly!
 */
public class LinearSearch {

    /**
     * Find all students with CGPA >= threshold
     *
     * @param records List of all StudentRecords
     * @param threshold Minimum CGPA (inclusive)
     * @return List of matching StudentRecords (in order found)
     */
    public static List<StudentRecord> findByMinimumCGPA(List<StudentRecord> records,
                                                        double threshold) {
        List<StudentRecord> results = new ArrayList<>();

        // Handle null input
        if (records == null) {
            return results;
        }

        // Validate threshold
        if (threshold < 0.0 || threshold > 4.0) {
            System.err.println("⚠️  Warning: CGPA threshold " + threshold + " is outside valid range [0.0, 4.0]");
        }

        // Linear search: iterate through all records
        for (StudentRecord record : records) {
            // Check if CGPA meets threshold (inclusive: >=)
            if (record.getCgpa() >= threshold) {
                results.add(record);
            }
        }

        return results;
    }

    /**
     * Find all students with CGPA <= maximum
     *
     * @param records List of all StudentRecords
     * @param maxCGPA Maximum CGPA (inclusive)
     * @return List of matching StudentRecords
     */
    public static List<StudentRecord> findByMaximumCGPA(List<StudentRecord> records,
                                                        double maxCGPA) {
        List<StudentRecord> results = new ArrayList<>();

        if (records == null) {
            return results;
        }

        for (StudentRecord record : records) {
            if (record.getCgpa() <= maxCGPA) {
                results.add(record);
            }
        }

        return results;
    }

    /**
     * Find all students with CGPA in range [min, max]
     *
     * @param records List of all StudentRecords
     * @param minCGPA Minimum CGPA (inclusive)
     * @param maxCGPA Maximum CGPA (inclusive)
     * @return List of matching StudentRecords
     */
    public static List<StudentRecord> findByRangeCGPA(List<StudentRecord> records,
                                                      double minCGPA, double maxCGPA) {
        List<StudentRecord> results = new ArrayList<>();

        if (records == null) {
            return results;
        }

        for (StudentRecord record : records) {
            if (record.getCgpa() >= minCGPA && record.getCgpa() <= maxCGPA) {
                results.add(record);
            }
        }

        return results;
    }

    /**
     * Find all students with name containing substring (case-insensitive)
     *
     * @param records List of all StudentRecords
     * @param namePattern Substring to search for
     * @return List of matching StudentRecords
     */
    public static List<StudentRecord> findByName(List<StudentRecord> records, String namePattern) {
        List<StudentRecord> results = new ArrayList<>();

        if (records == null || namePattern == null) {
            return results;
        }

        String pattern = namePattern.toLowerCase();
        for (StudentRecord record : records) {
            if (record.getName().toLowerCase().contains(pattern)) {
                results.add(record);
            }
        }

        return results;
    }

    /**
     * Get statistics about records matching criteria
     *
     * @param records List of matching StudentRecords
     * @return Statistics string
     */
    public static String getStatistics(List<StudentRecord> records) {
        if (records == null || records.isEmpty()) {
            return "No records found";
        }

        double avgCGPA = 0;
        double minCGPA = Double.MAX_VALUE;
        double maxCGPA = Double.MIN_VALUE;

        for (StudentRecord record : records) {
            double cgpa = record.getCgpa();
            avgCGPA += cgpa;
            minCGPA = Math.min(minCGPA, cgpa);
            maxCGPA = Math.max(maxCGPA, cgpa);
        }

        avgCGPA /= records.size();

        return String.format(
                "Count: %d | Min CGPA: %.2f | Max CGPA: %.2f | Avg CGPA: %.2f",
                records.size(), minCGPA, maxCGPA, avgCGPA
        );
    }

    /**
     * Print search results in formatted table
     * MANDATORY FORMAT - Must match PDF sample output!
     */
    public static void printResults(List<StudentRecord> records, double threshold) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== Linear Search (CGPA >= " + threshold + ") ===");
        System.out.println("=".repeat(70));
        System.out.printf("%-10s %-25s %-8s\n", "MATRIC", "NAME", "CGPA");
        System.out.println("-".repeat(70));

        if (records.isEmpty()) {
            System.out.println("No students found with CGPA >= " + threshold);
        } else {
            for (StudentRecord record : records) {
                System.out.printf("%-10s %-25s %.2f\n",
                        record.getMatricNumber(),
                        record.getName(),
                        record.getCgpa());
            }
        }

        System.out.println("-".repeat(70));
        System.out.println("Statistics: " + getStatistics(records));
        System.out.println("=".repeat(70) + "\n");
    }
}
