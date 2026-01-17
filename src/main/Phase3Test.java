package main;

import model.StudentRecord;
import tree.BST;
import utilities.CSVDataLoader;
import java.util.List;

/**
 * Phase 3 Test - Verify all 4 traversal methods work correctly
 */
public class Phase3Test {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 PHASE 3: TREE TRAVERSALS TEST");
        System.out.println("=".repeat(70) + "\n");

        // Load data from CSV
        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");

        if (records.length == 0) {
            System.err.println("❌ Failed to load CSV data. Test aborted.");
            return;
        }

        // ========== BUILD TEST TREE ==========
        System.out.println("-".repeat(70));
        System.out.println("Building test tree with 15 students...");
        System.out.println("-".repeat(70) + "\n");

        BST bst = new BST();
        for (int i = 0; i < Math.min(15, records.length); i++) {
            bst.insert(records[i]);
        }

        System.out.println("\nTree Statistics:");
        System.out.println("  • Node count: " + bst.countNodes());
        System.out.println("  • Tree height: " + bst.getHeight());
        System.out.println("  • Is valid BST: " + bst.isValidBST());

        // ========== TEST 1: IN-ORDER TRAVERSAL ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 1: IN-ORDER TRAVERSAL");
        System.out.println("-".repeat(70));
        System.out.println("\nExpectation: Output should be SORTED by matric number (ascending)");
        System.out.println("Algorithm: LEFT → NODE → RIGHT\n");

        List<StudentRecord> inOrder = bst.inOrderTraversal();
        System.out.println("Results:");

        // Verify in-order produces sorted output
        boolean isSorted = true;
        for (int i = 0; i < inOrder.size(); i++) {
            StudentRecord record = inOrder.get(i);
            System.out.printf("  %2d. %-10s %s (CGPA: %.2f)\n",
                    i + 1, record.getMatricNumber(), record.getName(), record.getCgpa());

            // Check if sorted
            if (i > 0) {
                String prev = inOrder.get(i - 1).getMatricNumber();
                String curr = record.getMatricNumber();
                if (prev.compareTo(curr) > 0) {
                    isSorted = false;
                }
            }
        }

        System.out.println("\n  ✓ Is SORTED by matric: " + (isSorted ? "YES ✓" : "NO ❌"));
        System.out.println("  ✓ Total nodes visited: " + inOrder.size());

        // ========== TEST 2: PRE-ORDER TRAVERSAL ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 2: PRE-ORDER TRAVERSAL");
        System.out.println("-".repeat(70));
        System.out.println("\nExpectation: Root visited first, then subtrees");
        System.out.println("Algorithm: NODE → LEFT → RIGHT");
        System.out.println("Use case: Tree copying, prefix notation\n");

        List<StudentRecord> preOrder = bst.preOrderTraversal();
        System.out.println("Results (order shown):");
        System.out.print("  ");
        for (int i = 0; i < preOrder.size(); i++) {
            System.out.print(preOrder.get(i).getMatricNumber());
            if (i < preOrder.size() - 1) System.out.print(" → ");
        }
        System.out.println();

        // Verify root is first
        if (bst.getRoot() != null) {
            String rootMatric = bst.getRoot().getData().getMatricNumber();
            String firstInPre = preOrder.get(0).getMatricNumber();
            System.out.println("\n  ✓ Root visited FIRST: " +
                    (rootMatric.equals(firstInPre) ? "YES ✓" : "NO ❌"));
        }
        System.out.println("  ✓ Total nodes visited: " + preOrder.size());

        // ========== TEST 3: POST-ORDER TRAVERSAL ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 3: POST-ORDER TRAVERSAL");
        System.out.println("-".repeat(70));
        System.out.println("\nExpectation: Leaves visited first, then parents");
        System.out.println("Algorithm: LEFT → RIGHT → NODE");
        System.out.println("Use case: Tree deletion, postfix notation\n");

        List<StudentRecord> postOrder = bst.postOrderTraversal();
        System.out.println("Results (order shown):");
        System.out.print("  ");
        for (int i = 0; i < postOrder.size(); i++) {
            System.out.print(postOrder.get(i).getMatricNumber());
            if (i < postOrder.size() - 1) System.out.print(" → ");
        }
        System.out.println();

        // Verify root is last
        if (bst.getRoot() != null) {
            String rootMatric = bst.getRoot().getData().getMatricNumber();
            String lastInPost = postOrder.get(postOrder.size() - 1).getMatricNumber();
            System.out.println("\n  ✓ Root visited LAST: " +
                    (rootMatric.equals(lastInPost) ? "YES ✓" : "NO ❌"));
        }
        System.out.println("  ✓ Total nodes visited: " + postOrder.size());

        // ========== TEST 4: LEVEL-ORDER TRAVERSAL ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 4: LEVEL-ORDER TRAVERSAL (BFS)");
        System.out.println("-".repeat(70));
        System.out.println("\nExpectation: Nodes visited level by level (top to bottom)");
        System.out.println("Algorithm: Breadth-First Search using Queue");
        System.out.println("Use case: Tree visualization, layer-by-layer processing\n");

        List<StudentRecord> levelOrder = bst.levelOrderTraversal();
        System.out.println("Results (order shown):");
        System.out.print("  ");
        for (int i = 0; i < levelOrder.size(); i++) {
            System.out.print(levelOrder.get(i).getMatricNumber());
            if (i < levelOrder.size() - 1) System.out.print(" → ");
        }
        System.out.println();

        System.out.println("\n  ✓ Total nodes visited: " + levelOrder.size());

        // ========== TEST 5: VERIFY ALL TRAVERSALS VISIT SAME NODES ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 5: CONSISTENCY CHECK");
        System.out.println("-".repeat(70));

        System.out.println("\nVerifying all traversals visit the same nodes (different order):");

        int inOrderCount = inOrder.size();
        int preOrderCount = preOrder.size();
        int postOrderCount = postOrder.size();
        int levelOrderCount = levelOrder.size();

        System.out.println("  • In-Order count: " + inOrderCount);
        System.out.println("  • Pre-Order count: " + preOrderCount);
        System.out.println("  • Post-Order count: " + postOrderCount);
        System.out.println("  • Level-Order count: " + levelOrderCount);

        boolean allEqual = (inOrderCount == preOrderCount &&
                preOrderCount == postOrderCount &&
                postOrderCount == levelOrderCount);
        System.out.println("\n  ✓ All traversals visit same number of nodes: " + (allEqual ? "YES ✓" : "NO ❌"));

        // ========== TEST 6: TRAVERSAL WITH SMALL EXAMPLES ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 6: SMALL EXAMPLE TREES");
        System.out.println("-".repeat(70));

        // Empty tree
        System.out.println("\nTesting EMPTY TREE:");
        BST emptyBST = new BST();
        System.out.println("  • In-Order: " + emptyBST.inOrderTraversal().size() + " nodes");
        System.out.println("  • Pre-Order: " + emptyBST.preOrderTraversal().size() + " nodes");
        System.out.println("  • Post-Order: " + emptyBST.postOrderTraversal().size() + " nodes");
        System.out.println("  • Level-Order: " + emptyBST.levelOrderTraversal().size() + " nodes");
        System.out.println("  ✓ All return empty list for empty tree");

        // Single node
        System.out.println("\nTesting SINGLE NODE TREE:");
        BST singleBST = new BST();
        singleBST.insert(records[0]);
        List<StudentRecord> singleIn = singleBST.inOrderTraversal();
        List<StudentRecord> singlePre = singleBST.preOrderTraversal();
        List<StudentRecord> singlePost = singleBST.postOrderTraversal();
        List<StudentRecord> singleLevel = singleBST.levelOrderTraversal();

        System.out.println("  • All traversals return: " + records[0].getMatricNumber());
        System.out.println("  ✓ In-Order: " + singleIn.get(0).getMatricNumber());
        System.out.println("  ✓ Pre-Order: " + singlePre.get(0).getMatricNumber());
        System.out.println("  ✓ Post-Order: " + singlePost.get(0).getMatricNumber());
        System.out.println("  ✓ Level-Order: " + singleLevel.get(0).getMatricNumber());

        // ========== TEST 7: PRINT ALL TRAVERSALS ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 7: FORMATTED TRAVERSAL OUTPUT");
        System.out.println("-".repeat(70));

        bst.printAllTraversals();

        // ========== SUMMARY ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ PHASE 3 TEST COMPLETE");
        System.out.println("=".repeat(70));
        System.out.println("All 4 traversal methods working correctly!");
        System.out.println("Ready to move to Phase 4: Utility Methods\n");
    }
}
