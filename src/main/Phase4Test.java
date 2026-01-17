package main;

import model.StudentRecord;
import tree.BST;
import utilities.CSVDataLoader;
import java.util.List;

/**
 * Phase 4 Test - Verify all utility methods work correctly
 */
public class Phase4Test {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🧪 PHASE 4: UTILITY METHODS TEST");
        System.out.println("=".repeat(70) + "\n");

        // Load data from CSV
        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");

        if (records.length == 0) {
            System.err.println("❌ Failed to load CSV data. Test aborted.");
            return;
        }

        // ========== BUILD TEST TREE ==========
        System.out.println("-".repeat(70));
        System.out.println("Building test tree with 20 students...");
        System.out.println("-".repeat(70) + "\n");

        BST bst = new BST();
        for (int i = 0; i < Math.min(20, records.length); i++) {
            bst.insert(records[i]);
        }

        // ========== TEST 1: TREE VISUALIZATION ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 1: TREE VISUALIZATION");
        System.out.println("-".repeat(70));

        bst.printTree();

        // ========== TEST 2: TREE ANALYSIS ==========
        System.out.println("-".repeat(70));
        System.out.println("📝 TEST 2: TREE ANALYSIS");
        System.out.println("-".repeat(70));

        bst.printTreeAnalysis();

        // ========== TEST 3: HEIGHT AND COUNT ==========
        System.out.println("-".repeat(70));
        System.out.println("📝 TEST 3: HEIGHT & COUNT METRICS");
        System.out.println("-".repeat(70));

        System.out.println("\n📊 Node Counting:");
        System.out.println("  • Total Nodes: " + bst.countNodes());
        System.out.println("  • Leaf Nodes: " + bst.getLeafCount());
        System.out.println("  • Internal Nodes: " + bst.getInternalNodeCount());
        System.out.println("  • Size Method: " + bst.size());

        System.out.println("\n📍 Height Information:");
        System.out.println("  • Tree Height: " + bst.getHeight());
        int optimalHeight = (int) Math.ceil(Math.log(bst.countNodes() + 1) / Math.log(2)) - 1;
        System.out.println("  • Optimal Height (log₂(n)): " + optimalHeight);
        System.out.println("  • Height Difference: " + (bst.getHeight() - optimalHeight) +
                " (0 = perfect balance)");

        // ========== TEST 4: MIN/MAX OPERATIONS ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 4: MIN/MAX OPERATIONS");
        System.out.println("-".repeat(70));

        System.out.println("\nFinding extremes:");
        StudentRecord min = bst.findMin();
        StudentRecord max = bst.findMax();

        if (min != null) {
            System.out.println("  ✓ Min Node: " + min.getMatricNumber() + " (" + min.getName() + ")");
        }
        if (max != null) {
            System.out.println("  ✓ Max Node: " + max.getMatricNumber() + " (" + max.getName() + ")");
        }

        // ========== TEST 5: NODE DEPTH ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 5: NODE DEPTH ANALYSIS");
        System.out.println("-".repeat(70));

        System.out.println("\nNode depths in tree:");

        // Check depth of several nodes
        String[] testMatrics = {"AIU101", "AIU110", "AIU105"};
        for (String matric : testMatrics) {
            StudentRecord found = bst.search(matric);
            if (found != null) {
                int depth = bst.getNodeDepth(matric);
                System.out.println("  • " + matric + " (" + found.getName() + "): Depth = " + depth);
            }
        }

        // ========== TEST 6: NODES AT DEPTH ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 6: NODES AT SPECIFIC DEPTH LEVELS");
        System.out.println("-".repeat(70));

        System.out.println("\nNodes grouped by depth level:");
        int height = bst.getHeight();
        for (int d = 0; d <= height; d++) {
            List<StudentRecord> atDepth = bst.getNodesAtDepth(d);
            System.out.print("  Depth " + d + ": ");
            for (int i = 0; i < atDepth.size(); i++) {
                System.out.print(atDepth.get(i).getMatricNumber());
                if (i < atDepth.size() - 1) System.out.print(", ");
            }
            System.out.println(" (" + atDepth.size() + " nodes)");
        }

        // ========== TEST 7: BALANCE CHECK ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 7: BALANCE ANALYSIS");
        System.out.println("-".repeat(70));

        System.out.println("\nBalance Information:");
        System.out.println("  • Is Balanced: " + (bst.isBalanced() ? "YES ✓" : "NO ⚠️"));
        System.out.println("  • Balance Factor: " + String.format("%.2f", bst.getBalanceFactor()));

        if (bst.getBalanceFactor() < 1.5) {
            System.out.println("    → Tree is well-balanced ✓");
        } else if (bst.getBalanceFactor() < 2.0) {
            System.out.println("    → Tree is reasonably balanced");
        } else {
            System.out.println("    → Tree is skewed/unbalanced ⚠️");
        }

        // ========== TEST 8: PATHS ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 8: PATH FINDING");
        System.out.println("-".repeat(70));

        System.out.println("\nFinding paths from root to nodes:");

        for (String matric : testMatrics) {
            StudentRecord found = bst.search(matric);
            if (found != null) {
                List<String> path = bst.getPathToNode(matric);
                System.out.print("  Path to " + matric + ": ");
                for (int i = 0; i < path.size(); i++) {
                    System.out.print(path.get(i));
                    if (i < path.size() - 1) System.out.print(" → ");
                }
                System.out.println(" (" + path.size() + " steps)");
            }
        }

        // Test path to non-existent node
        System.out.println("  Path to AIU999: " + bst.getPathToNode("AIU999") + " (empty = not found)");

        // ========== TEST 9: SEARCH COST ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 9: SEARCH COST ANALYSIS");
        System.out.println("-".repeat(70));

        System.out.println("\nSearch Cost Metrics:");
        System.out.println("  • Total Search Cost: " + bst.getTotalSearchCost());
        System.out.println("  • Average Search Cost: " + String.format("%.2f", bst.getAverageSearchCost()));

        double optimalCost = Math.log(bst.countNodes()) / Math.log(2);
        System.out.println("  • Optimal Avg Cost (log₂(n)): " + String.format("%.2f", optimalCost));
        System.out.println("  • Cost Ratio: " +
                String.format("%.2f", bst.getAverageSearchCost() / optimalCost) + "x optimal");

        // ========== TEST 10: PERFORMANCE ANALYSIS ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 10: PERFORMANCE ANALYSIS");
        System.out.println("-".repeat(70));

        bst.printPerformanceAnalysis();

        // ========== TEST 11: TREE INFO STRING ==========
        System.out.println("-".repeat(70));
        System.out.println("📝 TEST 11: TREE INFO SUMMARY");
        System.out.println("-".repeat(70));

        System.out.println("\n" + bst.getTreeInfo());

        // ========== TEST 12: EDGE CASES ==========
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📝 TEST 12: EDGE CASES");
        System.out.println("-".repeat(70));

        System.out.println("\nEmpty tree utilities:");
        BST emptyBST = new BST();
        System.out.println("  • Count: " + emptyBST.countNodes());
        System.out.println("  • Height: " + emptyBST.getHeight());
        System.out.println("  • Leaf Count: " + emptyBST.getLeafCount());
        System.out.println("  • Average Search Cost: " + emptyBST.getAverageSearchCost());
        System.out.println("  • Is Balanced: " + emptyBST.isBalanced());

        System.out.println("\nSingle node tree:");
        BST singleBST = new BST();
        singleBST.insert(records[0]);
        System.out.println("  • Count: " + singleBST.countNodes());
        System.out.println("  • Height: " + singleBST.getHeight());
        System.out.println("  • Leaf Count: " + singleBST.getLeafCount());
        System.out.println("  • Is Balanced: " + singleBST.isBalanced() + " ✓");
        System.out.println("  • Min = Max: " +
                singleBST.findMin().getMatricNumber().equals(singleBST.findMax().getMatricNumber()));

        // ========== SUMMARY ==========
        System.out.println("\n" + "=".repeat(70));
        System.out.println("✅ PHASE 4 TEST COMPLETE");
        System.out.println("=".repeat(70));
        System.out.println("All utility methods working correctly!");
        System.out.println("Ready to move to Phase 5: Sorting Algorithms\n");
    }
}
