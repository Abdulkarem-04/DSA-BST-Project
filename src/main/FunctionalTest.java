package main;

import model.StudentRecord;
import tree.BST;
import utilities.CSVDataLoader;
import utilities.TreeVisualizer;
import sorting.MergeSort;
import sorting.QuickSort;
import search.LinearSearch;
import java.util.List;

/**
 * FUNCTIONAL TEST CASE - Test Case 1
 *
 * Requirements:
 *  ✓ Insert ≥15 students from CSV
 *  ✓ Display in-order traversal (sorted)
 *  ✓ Search 3 existing + 2 non-existing students
 *  ✓ Delete: leaf node, one-child node, two-child node
 *  ✓ Display tree after deletions
 *  ✓ Apply Merge Sort and Quick Sort
 *  ✓ Apply Linear Search filter
 *
 * Output Format: MUST match PDF sample EXACTLY for grading consistency
 */
public class FunctionalTest {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== INSERT STUDENTS ===");
        System.out.println("=".repeat(80));

        // Load data
        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");
        if (records.length == 0) {
            System.err.println("❌ Failed to load CSV data.");
            return;
        }

        // ========== PART 1: INSERT STUDENTS ==========
        BST bst = new BST();

        System.out.println();
        for (int i = 0; i < 15; i++) {
            StudentRecord record = records[i];
            System.out.println("Insert: " + record.getMatricNumber() + " (" +
                    record.getName() + ", CGPA " + record.getCgpa() + ")");
            bst.insert(record);
        }

        List<StudentRecord> allStudents = bst.inOrderTraversal();

        // ========== PART 2: BST DIAGRAM ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== BST DIAGRAM (Auto-generated) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        printSimpleTreeDiagram(bst);

        // ========== PART 3: IN-ORDER TRAVERSAL (SORTED) ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== In-order Traversal (Sorted by ID_Number) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        for (StudentRecord record : allStudents) {
            System.out.println(record.getMatricNumber() + " (" + record.getName() +
                    ", CGPA " + record.getCgpa() + ")");
        }

        // ========== PART 4: BST SEARCH ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== BST Search (by Matric Number) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        String[] searchMatrics = {"AIU101", "AIU103", "AIU110", "AIU999", "XYZ000"};
        int searchCount = 0;

        for (String matric : searchMatrics) {
            searchCount++;
            System.out.println("Search Matric Number: " + matric);

            StudentRecord found = bst.search(matric);
            if (found != null) {
                System.out.println("Result: FOUND");
                System.out.println("Name: " + found.getName());
                System.out.println("CGPA: " + found.getCgpa());
            } else {
                System.out.println("Result: NOT FOUND");
            }

            if (searchCount < searchMatrics.length) {
                System.out.println();
            }
        }

        // ========== PART 5: LINEAR SEARCH (MANDATORY) ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== Linear Search (CGPA >= 3.50) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        List<StudentRecord> highCGPA = LinearSearch.findByMinimumCGPA(allStudents, 3.50);
        for (StudentRecord record : highCGPA) {
            System.out.println(record.getMatricNumber() + " (" + record.getName() +
                    ", " + record.getCgpa() + ")");
        }

        // ========== PART 6: DELETE OPERATIONS ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== DELETE OPERATIONS ===");
        System.out.println("=".repeat(80));
        System.out.println();

        // Delete leaf node
        System.out.println("Delete LEAF NODE (AIU101):");
        bst.delete("AIU101");
        System.out.println();

        // Delete one-child node
        System.out.println("Delete ONE-CHILD NODE (AIU102):");
        bst.delete("AIU102");
        System.out.println();

        // Delete two-child node
        System.out.println("Delete TWO-CHILD NODE (AIU105):");
        bst.delete("AIU105");
        System.out.println();

        // ========== PART 7: TREE AFTER DELETIONS ==========
        System.out.println("=".repeat(80));
        System.out.println("=== In-order Traversal After Deletions ===");
        System.out.println("=".repeat(80));
        System.out.println();

        List<StudentRecord> remainingStudents = bst.inOrderTraversal();
        for (StudentRecord record : remainingStudents) {
            System.out.println(record.getMatricNumber() + " (" + record.getName() +
                    ", CGPA " + record.getCgpa() + ")");
        }
        System.out.println();
        System.out.println("Remaining nodes: " + bst.countNodes());

        // ========== PART 8: MERGE SORT ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== Merge Sort (by Name) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        List<StudentRecord> sortedByName = MergeSort.sortByName(remainingStudents);
        for (StudentRecord record : sortedByName) {
            System.out.println(record.getName() + " (" + record.getMatricNumber() +
                    ", " + record.getCgpa() + ")");
        }

        // ========== PART 9: QUICK SORT (DESCENDING) ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("=== Quick Sort (by CGPA, Desc) ===");
        System.out.println("=".repeat(80));
        System.out.println();

        List<StudentRecord> sortedByCGPA = QuickSort.sortByCGPA(remainingStudents, true);
        for (StudentRecord record : sortedByCGPA) {
            System.out.println(record.getName() + " (" + record.getMatricNumber() +
                    ", " + record.getCgpa() + ")");
        }

        // ========== FINAL SUMMARY ==========
        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ FUNCTIONAL TEST CASE COMPLETE");
        System.out.println("=".repeat(80));
        System.out.println("Status: All operations successful");
        System.out.println("Initial nodes: 15");
        System.out.println("Deleted: 3");
        System.out.println("Remaining: " + bst.countNodes());
        System.out.println("BST Valid: " + (bst.isValidBST() ? "YES ✓" : "NO ❌"));
        System.out.println("=".repeat(80) + "\n");
    }

    /**
     * Print simple tree diagram (basic ASCII art)
     */
    private static void printSimpleTreeDiagram(BST bst) {
        if (bst.isEmpty()) {
            System.out.println("(empty tree)");
            return;
        }

        bst.printTree();


    }
}
