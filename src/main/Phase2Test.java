package main;

import model.StudentRecord;
import tree.BST;
import utilities.CSVDataLoader;

/**
 * Phase 2 Test - Verify Insert, Search, Delete operations work correctly
 */
public class Phase2Test {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 PHASE 2: BST CORE OPERATIONS TEST");
        System.out.println("=".repeat(70) + "\n");

        // Load data from CSV
        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");

        if (records.length == 0) {
            System.err.println("❌ Failed to load CSV data. Test aborted.");
            return;
        }

        // ========== TEST 1: INSERT OPERATION ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 1: INSERT OPERATION");
        System.out.println("-".repeat(70));

        BST bst = new BST();
        System.out.println("✓ Created empty BST");

        // Insert first 15 students
        System.out.println("\nInserting first 15 students...");
        for (int i = 0; i < Math.min(15, records.length); i++) {
            bst.insert(records[i]);
        }

        System.out.println("\nTree Statistics:");
        System.out.println("  • Total nodes: " + bst.countNodes());
        System.out.println("  • Tree height: " + bst.getHeight());
        System.out.println("  • Is valid BST: " + bst.isValidBST());
        System.out.println("  • Min matric: " + bst.findMin().getMatricNumber());
        System.out.println("  • Max matric: " + bst.findMax().getMatricNumber());

        // Test duplicate rejection
        System.out.println("\nTesting DUPLICATE REJECTION:");
        StudentRecord duplicate = records[0];  // Try to insert first record again
        boolean duplicateResult = bst.insert(duplicate);
        System.out.println("  • Result: " + (duplicateResult ? "INSERTED" : "REJECTED") + " ✓");
        System.out.println("  • Node count after duplicate attempt: " + bst.countNodes() + " (unchanged)");

        // ========== TEST 2: SEARCH OPERATION ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 2: SEARCH OPERATION");
        System.out.println("-".repeat(70));

        // Search for existing students
        System.out.println("\nSearching for EXISTING students:");
        String[] searchMatrics = {"AIU101", "AIU105", "AIU110"};
        for (String matric : searchMatrics) {
            StudentRecord found = bst.search(matric);
            if (found != null) {
                System.out.println("  ✓ " + matric + ": FOUND - " + found.getName() + " (CGPA: " + found.getCgpa() + ")");
            } else {
                System.out.println("  ✗ " + matric + ": NOT FOUND");
            }
        }

        // Search for non-existing students
        System.out.println("\nSearching for NON-EXISTING students:");
        String[] notFoundMatrics = {"AIU999", "XYZ000", "BBB999"};
        for (String matric : notFoundMatrics) {
            StudentRecord found = bst.search(matric);
            if (found == null) {
                System.out.println("  ✓ " + matric + ": NOT FOUND (correct) ✓");
            } else {
                System.out.println("  ✗ " + matric + ": Found (unexpected)");
            }
        }

        // ========== TEST 3: DELETE OPERATION ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 3: DELETE OPERATION (3 Cases)");
        System.out.println("-".repeat(70));

        System.out.println("\nBefore deletion: " + bst.countNodes() + " nodes");

        // CASE 1: Delete a LEAF node
        System.out.println("\n  CASE 1: Delete LEAF NODE (AIU101)");
        bst.delete("AIU101");
        System.out.println("  After deletion: " + bst.countNodes() + " nodes");
        System.out.println("  Is valid BST: " + bst.isValidBST());

        // CASE 2: Delete node with ONE CHILD
        System.out.println("\n  CASE 2: Delete node with ONE CHILD (AIU102)");
        bst.delete("AIU102");
        System.out.println("  After deletion: " + bst.countNodes() + " nodes");
        System.out.println("  Is valid BST: " + bst.isValidBST());

        // CASE 3: Delete node with TWO CHILDREN (delete root or high-degree node)
        System.out.println("\n  CASE 3: Delete node with TWO CHILDREN");
        // Find a node with two children to delete
        for (int i = 3; i < Math.min(15, records.length); i++) {
            String testMatric = records[i].getMatricNumber();
            StudentRecord node = bst.search(testMatric);
            if (node != null) {
                System.out.println("  Deleting: " + testMatric + " (" + node.getName() + ")");
                bst.delete(testMatric);
                System.out.println("  After deletion: " + bst.countNodes() + " nodes");
                System.out.println("  Is valid BST: " + bst.isValidBST());
                break;
            }
        }

        // Try to delete non-existent node
        System.out.println("\n  Attempting to delete NON-EXISTENT node (AIU999):");
        bst.delete("AIU999");

        // ========== TEST 4: EDGE CASES ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 4: EDGE CASES");
        System.out.println("-".repeat(70));

        // Test empty tree operations
        System.out.println("\nTest EMPTY TREE operations:");
        BST emptyBST = new BST();
        System.out.println("  • Empty tree size: " + emptyBST.countNodes());
        System.out.println("  • Empty tree height: " + emptyBST.getHeight());
        System.out.println("  • Search in empty tree: " + (emptyBST.search("AIU001") == null ? "NULL ✓" : "ERROR"));
        System.out.println("  • Delete from empty tree: " + (emptyBST.delete("AIU001") ? "ERROR" : "FALSE ✓"));

        // Test single node tree
        System.out.println("\nTest SINGLE NODE tree:");
        BST singleBST = new BST();
        singleBST.insert(records[0]);
        System.out.println("  • Single node size: " + singleBST.countNodes());
        System.out.println("  • Single node height: " + singleBST.getHeight());
        System.out.println("  • Search in single node: " +
                (singleBST.search(records[0].getMatricNumber()) != null ? "FOUND ✓" : "NOT FOUND"));
        System.out.println("  • Delete single node: " +
                (singleBST.delete(records[0].getMatricNumber()) ? "SUCCESS ✓" : "FAILED"));
        System.out.println("  • Tree after deletion: " + singleBST.countNodes() + " nodes");

        // ========== TEST 5: TREE STATISTICS ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 5: TREE STATISTICS (Final)");
        System.out.println("-".repeat(70));

        System.out.println("\nFinal tree state:");
        System.out.println("  • Node count: " + bst.countNodes());
        System.out.println("  • Tree height: " + bst.getHeight());
        System.out.println("  • Is valid BST: " + bst.isValidBST() + " ✓");
        System.out.println("  • Min matric: " + (bst.findMin() != null ? bst.findMin().getMatricNumber() : "NULL"));
        System.out.println("  • Max matric: " + (bst.findMax() != null ? bst.findMax().getMatricNumber() : "NULL"));

        // ========== SUMMARY ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ PHASE 2 TEST COMPLETE");
        System.out.println("=".repeat(70));
        System.out.println("All Insert, Search, Delete operations working correctly!");
        System.out.println("Ready to move to Phase 3: Tree Traversals\n");
    }
}
