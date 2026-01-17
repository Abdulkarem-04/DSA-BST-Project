package utilities;

import tree.BST;
import tree.Node;
import model.StudentRecord;
import java.util.*;

/**
 * TREE VISUALIZER - Print ASCII tree diagrams
 *
 * Functionality:
 *  ✓ Print tree structure in ASCII format
 *  ✓ Show parent-child relationships
 *  ✓ Handle trees of any size
 *  ✓ Format output for readability
 *
 * Example output:
 *          AIU105
 *         /      \
 *      AIU101   AIU110
 *        /        /
 *     AIU103   AIU108
 */
public class TreeVisualizer {

    /**
     * Print tree structure using in-order traversal with indentation
     * Simple, readable format for all tree sizes
     */
    public static void printTree(BST bst) {
        if (bst.isEmpty()) {
            System.out.println("(empty tree)");
            return;
        }

        System.out.println();
        printTreeRecursive(bst.getRoot(), "", true);
        System.out.println();
    }

    /**
     * Recursive helper to print tree with indentation
     *
     * @param node Current node
     * @param indent Current indentation string
     * @param isLast Whether this is the last child of parent
     */
    private static void printTreeRecursive(Node node, String indent, boolean isLast) {
        if (node == null) {
            return;
        }

        // Print current node
        System.out.print(indent);
        if (isLast) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "│   ";
        }

        StudentRecord data = node.getData();
        System.out.println(data.getMatricNumber() + " (" + data.getName() +
                ", CGPA " + String.format("%.2f", data.getCgpa()) + ")");

        // Get children
        Node left = node.getLeft();
        Node right = node.getRight();

        // Print children (only process non-null children)
        if (left != null || right != null) {
            // If only left child exists
            if (left != null && right == null) {
                printTreeRecursive(left, indent, true);
            }
            // If only right child exists
            else if (left == null && right != null) {
                printTreeRecursive(right, indent, true);
            }
            // If both children exist
            else {
                printTreeRecursive(left, indent, false);
                printTreeRecursive(right, indent, true);
            }
        }
    }

    /**
     * Print tree in level-order format (breadth-first)
     * Shows each level on separate line
     */
    public static void printTreeLevelOrder(BST bst) {
        if (bst.isEmpty()) {
            System.out.println("(empty tree)");
            return;
        }

        System.out.println();
        Queue<Node> queue = new LinkedList<>();
        queue.add(bst.getRoot());
        int level = 0;
        int nodesInLevel = 1;
        int nodesInNextLevel = 0;

        while (!queue.isEmpty()) {
            System.out.print("Level " + level + ": ");

            for (int i = 0; i < nodesInLevel; i++) {
                Node node = queue.poll();

                if (node != null) {
                    StudentRecord data = node.getData();
                    System.out.print(data.getMatricNumber() + " ");

                    // Add children to queue
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                        nodesInNextLevel++;
                    }
                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                        nodesInNextLevel++;
                    }
                }
            }

            System.out.println();
            nodesInLevel = nodesInNextLevel;
            nodesInNextLevel = 0;
            level++;
        }
        System.out.println();
    }

    /**
     * Print compact tree representation (single line)
     * Format: (left_subtree) node (right_subtree)
     */
    public static void printTreeCompact(BST bst) {
        if (bst.isEmpty()) {
            System.out.println("(empty tree)");
            return;
        }

        System.out.print("Tree: ");
        printCompactRecursive(bst.getRoot());
        System.out.println();
    }

    /**
     * Recursive helper for compact tree printing
     */
    private static void printCompactRecursive(Node node) {
        if (node == null) {
            return;
        }

        if (node.getLeft() != null || node.getRight() != null) {
            System.out.print("(");

            if (node.getLeft() != null) {
                printCompactRecursive(node.getLeft());
            }

            System.out.print(" " + node.getData().getMatricNumber() + " ");

            if (node.getRight() != null) {
                printCompactRecursive(node.getRight());
            }

            System.out.print(")");
        } else {
            System.out.print(node.getData().getMatricNumber());
        }
    }

    /**
     * Print tree statistics
     */
    public static void printTreeStats(BST bst) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("TREE STATISTICS");
        System.out.println("=".repeat(50));

        if (bst.isEmpty()) {
            System.out.println("Tree is empty");
        } else {
            System.out.println("Nodes: " + bst.countNodes());
            System.out.println("Height: " + bst.getHeight());
            System.out.println("Min CGPA: " + bst.findMin().getCgpa());
            System.out.println("Max CGPA: " + bst.findMax().getCgpa());
            System.out.println("Valid BST: " + bst.isValidBST());
            System.out.println("Balance Factor: " +
                    String.format("%.2f", bst.getBalanceFactor()));
        }

        System.out.println("=".repeat(50) + "\n");
    }

    /**
     * Print tree with detailed node information
     */
    public static void printTreeDetailed(BST bst) {
        if (bst.isEmpty()) {
            System.out.println("(empty tree)");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("DETAILED TREE VIEW");
        System.out.println("=".repeat(70) + "\n");

        printDetailedRecursive(bst.getRoot(), "", true);

        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Recursive helper for detailed tree printing
     */
    private static void printDetailedRecursive(Node node, String indent, boolean isLast) {
        if (node == null) {
            return;
        }

        StudentRecord data = node.getData();

        System.out.print(indent);
        if (isLast) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "│   ";
        }

        // Print node details
        System.out.printf("%s | %-25s | CGPA: %.2f | Leaf: %s\n",
                data.getMatricNumber(),
                data.getName(),
                data.getCgpa(),
                (node.getLeft() == null && node.getRight() == null) ? "YES" : "NO");

        // Print children
        Node left = node.getLeft();
        Node right = node.getRight();

        if (left != null || right != null) {
            if (left != null && right == null) {
                printDetailedRecursive(left, indent, true);
            } else if (left == null && right != null) {
                printDetailedRecursive(right, indent, true);
            } else {
                printDetailedRecursive(left, indent, false);
                printDetailedRecursive(right, indent, true);
            }
        }
    }

    /**
     * Generate ASCII art box around text
     */
    public static void printBox(String title) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("=== " + title + " ===");
        System.out.println("=".repeat(70) + "\n");
    }

    /**
     * Print section separator
     */
    public static void printSeparator(String title) {
        System.out.println("\n" + "-".repeat(70));
        System.out.println(title);
        System.out.println("-".repeat(70) + "\n");
    }
}
