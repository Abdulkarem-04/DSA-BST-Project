package tree;

import model.StudentRecord;
import java.util.*;
import utilities.TreeVisualizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Binary Search Tree (BST) Implementation for Student Records
 *
 * BST Properties:
 *  - Left subtree contains keys < parent key
 *  - Right subtree contains keys > parent key
 *  - All keys are UNIQUE (duplicates rejected)
 *  - Keys are StudentRecord matric numbers (lexicographic ordering)
 *
 * Time Complexity:
 *  - Best/Average: O(log n)
 *  - Worst case: O(n) when tree becomes skewed
 *
 * Operations:
 *  1. INSERT: Add new student (reject duplicates)
 *  2. SEARCH: Find student by matric number
 *  3. DELETE: Remove student (3 cases: leaf, 1-child, 2-children)
 */
public class BST {

    // ============ ATTRIBUTES ============
    private Node root;

    // ============ CONSTRUCTORS ============

    /**
     * Create an empty Binary Search Tree
     */
    public BST() {
        this.root = null;
    }

    // ============ CORE OPERATIONS ============

    /**
     * INSERT: Add a new student record to the BST
     *
     * Algorithm:
     *  1. If tree is empty, create root
     *  2. Navigate tree: left if matric < current, right if matric > current
     *  3. Reject if matric == current (duplicate)
     *  4. Insert at leaf position
     *
     * @param record The StudentRecord to insert
     * @return true if inserted successfully, false if duplicate
     */
    public boolean insert(StudentRecord record) {
        // Validate input
        if (record == null || !record.isValid()) {
            System.err.println("❌ Cannot insert: Invalid student record");
            return false;
        }

        // If tree is empty, create root
        if (root == null) {
            root = new Node(record);
            System.out.println("✓ Insert: " + record.getMatricNumber() + " (" +
                    record.getName() + ", CGPA " + record.getCgpa() + ")");
            return true;
        }

        // Recursively insert
        return insertRecursive(root, record);
    }

    /**
     * Recursive helper for insert operation
     *
     * @param current Current node being examined
     * @param record StudentRecord to insert
     * @return true if inserted, false if duplicate
     */
    private boolean insertRecursive(Node current, StudentRecord record) {
        String newMatric = record.getMatricNumber();
        String currentMatric = current.getData().getMatricNumber();

        // Compare matric numbers using lexicographic ordering
        int comparison = newMatric.compareTo(currentMatric);

        if (comparison < 0) {
            // New matric is LESS than current → go LEFT
            if (current.getLeft() == null) {
                // Found insertion point
                current.setLeft(new Node(record));
                System.out.println("✓ Insert: " + record.getMatricNumber() + " (" +
                        record.getName() + ", CGPA " + record.getCgpa() + ")");
                return true;
            } else {
                // Continue left
                return insertRecursive(current.getLeft(), record);
            }

        } else if (comparison > 0) {
            // New matric is GREATER than current → go RIGHT
            if (current.getRight() == null) {
                // Found insertion point
                current.setRight(new Node(record));
                System.out.println("✓ Insert: " + record.getMatricNumber() + " (" +
                        record.getName() + ", CGPA " + record.getCgpa() + ")");
                return true;
            } else {
                // Continue right
                return insertRecursive(current.getRight(), record);
            }

        } else {
            // comparison == 0 → DUPLICATE MATRIC
            System.out.println("✗ Insert REJECTED: Duplicate matric number " + newMatric);
            return false;
        }
    }

    /**
     * SEARCH: Find a student by matric number
     *
     * Algorithm:
     *  1. Start from root
     *  2. If current is null, student not found
     *  3. If matric == current, return the record
     *  4. If matric < current, search left
     *  5. If matric > current, search right
     *
     * @param matricNumber The matric number to search for
     * @return StudentRecord if found, null if not found
     */
    public StudentRecord search(String matricNumber) {
        // Validate input
        if (matricNumber == null || matricNumber.isEmpty()) {
            System.err.println("❌ Cannot search: Invalid matric number");
            return null;
        }

        return searchRecursive(root, matricNumber);
    }

    /**
     * Recursive helper for search operation
     *
     * @param current Current node being examined
     * @param matricNumber Matric number to find
     * @return StudentRecord if found, null otherwise
     */
    private StudentRecord searchRecursive(Node current, String matricNumber) {
        // Base case: reached leaf or empty subtree
        if (current == null) {
            return null;
        }

        String currentMatric = current.getData().getMatricNumber();
        int comparison = matricNumber.compareTo(currentMatric);

        if (comparison == 0) {
            // Found the student!
            return current.getData();

        } else if (comparison < 0) {
            // Search in left subtree
            return searchRecursive(current.getLeft(), matricNumber);

        } else {
            // Search in right subtree
            return searchRecursive(current.getRight(), matricNumber);
        }
    }

    /**
     * DELETE: Remove a student from the BST
     *
     * Must handle THREE cases:
     *
     *  CASE 1 - LEAF NODE (no children):
     *    Simply remove it. Parent's pointer becomes null.
     *
     *  CASE 2 - ONE CHILD:
     *    Replace the node with its child. Bypass the node.
     *
     *  CASE 3 - TWO CHILDREN (most complex):
     *    Use In-Order Successor strategy:
     *    a) Find in-order successor (smallest node in right subtree)
     *    b) Copy successor's data to current node
     *    c) Delete the successor node (which has ≤1 child)
     *
     * @param matricNumber The matric number to delete
     * @return true if deleted successfully, false if not found
     */
    public boolean delete(String matricNumber) {
        // Validate input
        if (matricNumber == null || matricNumber.isEmpty()) {
            System.err.println("❌ Cannot delete: Invalid matric number");
            return false;
        }

        // Check if student exists before attempting deletion
        if (search(matricNumber) == null) {
            System.out.println("✗ Delete FAILED: Student " + matricNumber + " not found");
            return false;
        }

        Node[] result = new Node[1];
        result[0] = root;
        boolean deleted = deleteRecursive(result, 0, matricNumber);
        root = result[0];

        if (deleted) {
            System.out.println("✓ Deleted: " + matricNumber);
        }
        return deleted;
    }

    /**
     * Recursive helper for delete operation
     *
     * This uses an array wrapper to handle root changes
     * (Java passes Node by reference, but we need to change root itself)
     *
     * @param nodeRef Array containing the current node [0]
     * @param index Always 0 (used for array reference)
     * @param matricNumber Matric number to delete
     * @return true if deleted, false if not found
     */
    private boolean deleteRecursive(Node[] nodeRef, int index, String matricNumber) {
        Node current = nodeRef[index];

        // Base case: empty subtree
        if (current == null) {
            return false;
        }

        String currentMatric = current.getData().getMatricNumber();
        int comparison = matricNumber.compareTo(currentMatric);

        if (comparison < 0) {
            // Student is in left subtree
            if (current.getLeft() == null) {
                return false;
            }
            Node[] leftRef = new Node[1];
            leftRef[0] = current.getLeft();
            boolean result = deleteRecursive(leftRef, 0, matricNumber);
            current.setLeft(leftRef[0]);
            return result;

        } else if (comparison > 0) {
            // Student is in right subtree
            if (current.getRight() == null) {
                return false;
            }
            Node[] rightRef = new Node[1];
            rightRef[0] = current.getRight();
            boolean result = deleteRecursive(rightRef, 0, matricNumber);
            current.setRight(rightRef[0]);
            return result;

        } else {
            // Found the node to delete!
            // Handle THREE cases:

            // ===== CASE 1: LEAF NODE (no children) =====
            if (current.getLeft() == null && current.getRight() == null) {
                nodeRef[index] = null;  // Remove the node
                return true;
            }

            // ===== CASE 2: ONE CHILD =====
            if (current.getLeft() == null) {
                // Has only right child
                nodeRef[index] = current.getRight();
                return true;
            }
            if (current.getRight() == null) {
                // Has only left child
                nodeRef[index] = current.getLeft();
                return true;
            }

            // ===== CASE 3: TWO CHILDREN (In-Order Successor) =====
            // This is the most complex case
            // Strategy:
            //  1. Find in-order successor (smallest in right subtree)
            //  2. Copy successor's data to current node
            //  3. Delete the successor node (which has ≤1 child)

            Node successor = findMinNode(current.getRight());
            StudentRecord successorData = successor.getData();

            // Copy successor's data to current node
            current.setData(successorData);

            // Delete the successor (which is guaranteed to have 0 or 1 child)
            Node[] rightRef = new Node[1];
            rightRef[0] = current.getRight();
            deleteRecursive(rightRef, 0, successorData.getMatricNumber());
            current.setRight(rightRef[0]);

            return true;
        }
    }

    /**
     * Find the node with minimum matric number in a subtree
     * (Used for finding in-order successor in deletion)
     *
     * The minimum is always the leftmost node
     *
     * @param node Root of subtree to search
     * @return Node with minimum matric number
     */
    private Node findMinNode(Node node) {
        if (node == null) {
            return null;
        }

        // Keep going left until we find the leftmost node
        while (node.getLeft() != null) {
            node = node.getLeft();
        }

        return node;
    }

    // ============ UTILITY METHODS ============

    /**
     * Check if the tree is empty
     * @return true if root is null
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get the root node
     * @return root Node, or null if tree is empty
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Count total number of nodes in the tree
     * @return number of nodes
     */
    public int countNodes() {
        return countNodesRecursive(root);
    }

    /**
     * Recursive helper for counting nodes
     */
    private int countNodesRecursive(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodesRecursive(node.getLeft()) +
                countNodesRecursive(node.getRight());
    }

    /**
     * Calculate the height of the tree
     * Height = longest path from root to leaf
     * Single node = height 0
     * Empty tree = height -1
     *
     * @return height of tree
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }

    /**
     * Recursive helper for calculating height
     */
    private int getHeightRecursive(Node node) {
        if (node == null) {
            return -1;  // Empty subtree
        }

        // Height = 1 + max(left height, right height)
        int leftHeight = getHeightRecursive(node.getLeft());
        int rightHeight = getHeightRecursive(node.getRight());

        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Find the student with minimum matric number
     * @return StudentRecord with smallest matric, or null if tree empty
     */
    public StudentRecord findMin() {
        Node minNode = findMinNode(root);
        return (minNode == null) ? null : minNode.getData();
    }

    /**
     * Find the student with maximum matric number
     * @return StudentRecord with largest matric, or null if tree empty
     */
    public StudentRecord findMax() {
        Node maxNode = findMaxNode(root);
        return (maxNode == null) ? null : maxNode.getData();
    }

    /**
     * Find the node with maximum matric number in a subtree
     * (The rightmost node)
     */
    private Node findMaxNode(Node node) {
        if (node == null) {
            return null;
        }

        // Keep going right until we find the rightmost node
        while (node.getRight() != null) {
            node = node.getRight();
        }

        return node;
    }

    /**
     * Verify that the tree maintains BST property
     * For each node: all left children < node < all right children
     *
     * @return true if valid BST, false otherwise
     */
    public boolean isValidBST() {
        return isValidBSTRecursive(root, null, null);
    }

    /**
     * Recursive helper for BST validation
     * Uses min/max bounds to verify each node is in valid range
     */
    private boolean isValidBSTRecursive(Node node, String min, String max) {
        if (node == null) {
            return true;
        }

        String currentMatric = node.getData().getMatricNumber();

        // Check if current node is within bounds
        if (min != null && currentMatric.compareTo(min) <= 0) {
            return false;
        }
        if (max != null && currentMatric.compareTo(max) >= 0) {
            return false;
        }

        // Recursively check subtrees with updated bounds
        return isValidBSTRecursive(node.getLeft(), min, currentMatric) &&
                isValidBSTRecursive(node.getRight(), currentMatric, max);
    }

    /**
     * Get the size (number of nodes) in the tree
     * Alias for countNodes()
     */
    public int size() {
        return countNodes();
    }

    // ============ TREE TRAVERSALS ============

    /**
     * IN-ORDER TRAVERSAL: LEFT → NODE → RIGHT
     *
     * Produces output SORTED by matric number (ascending)
     * This is the most commonly used traversal.
     *
     * Example tree:        AIU105
     *                      /      \
     *                   AIU101   AIU110
     *                     /       /
     *                  AIU103  AIU108
     *
     * In-Order result: AIU101, AIU103, AIU105, AIU108, AIU110
     *                  (ascending order by matric ✓)
     *
     * Time Complexity: O(n) - visits each node once
     * Space Complexity: O(h) - recursion stack, h = height
     *
     * @return List of StudentRecords in sorted order by matric
     */
    public List<StudentRecord> inOrderTraversal() {
        List<StudentRecord> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for in-order traversal
     */
    private void inOrderRecursive(Node node, List<StudentRecord> result) {
        if (node == null) {
            return;
        }

        // LEFT
        inOrderRecursive(node.getLeft(), result);

        // NODE
        result.add(node.getData());

        // RIGHT
        inOrderRecursive(node.getRight(), result);
    }

    /**
     * PRE-ORDER TRAVERSAL: NODE → LEFT → RIGHT
     *
     * Visits parent BEFORE children.
     * Useful for copying/cloning the tree structure.
     * Produces prefix notation for expressions.
     *
     * Example tree:        AIU105
     *                      /      \
     *                   AIU101   AIU110
     *                     /
     *                  AIU103
     *
     * Pre-Order result: AIU105, AIU101, AIU103, AIU110
     *                   (root first, then subtrees)
     *
     * Time Complexity: O(n)
     * Space Complexity: O(h)
     *
     * @return List of StudentRecords in pre-order sequence
     */
    public List<StudentRecord> preOrderTraversal() {
        List<StudentRecord> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for pre-order traversal
     */
    private void preOrderRecursive(Node node, List<StudentRecord> result) {
        if (node == null) {
            return;
        }

        // NODE
        result.add(node.getData());

        // LEFT
        preOrderRecursive(node.getLeft(), result);

        // RIGHT
        preOrderRecursive(node.getRight(), result);
    }

    /**
     * POST-ORDER TRAVERSAL: LEFT → RIGHT → NODE
     *
     * Visits children BEFORE parent.
     * Useful for deleting the tree (delete children before parent).
     * Produces postfix notation for expressions.
     *
     * Example tree:        AIU105
     *                      /      \
     *                   AIU101   AIU110
     *                     /       /
     *                  AIU103  AIU108
     *
     * Post-Order result: AIU103, AIU101, AIU108, AIU110, AIU105
     *                    (leaves first, then parents)
     *
     * Time Complexity: O(n)
     * Space Complexity: O(h)
     *
     * @return List of StudentRecords in post-order sequence
     */
    public List<StudentRecord> postOrderTraversal() {
        List<StudentRecord> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    /**
     * Recursive helper for post-order traversal
     */
    private void postOrderRecursive(Node node, List<StudentRecord> result) {
        if (node == null) {
            return;
        }

        // LEFT
        postOrderRecursive(node.getLeft(), result);

        // RIGHT
        postOrderRecursive(node.getRight(), result);

        // NODE
        result.add(node.getData());
    }

    /**
     * LEVEL-ORDER TRAVERSAL: Breadth-First Search (BFS)
     *
     * Visits nodes level by level from top to bottom.
     * Uses Queue data structure (FIFO).
     * Useful for tree visualization.
     *
     * Example tree:        AIU105  (Level 0)
     *                      /      \
     *                   AIU101   AIU110 (Level 1)
     *                     /       /
     *                  AIU103  AIU108 (Level 2)
     *
     * Level-Order result: AIU105, AIU101, AIU110, AIU103, AIU108
     *                     (top to bottom, left to right)
     *
     * Time Complexity: O(n)
     * Space Complexity: O(w) where w = max width
     *
     * @return List of StudentRecords in level-order sequence
     */
    public List<StudentRecord> levelOrderTraversal() {
        List<StudentRecord> result = new ArrayList<>();

        // Empty tree
        if (root == null) {
            return result;
        }

        // Use a Queue for BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            // Dequeue the front node
            Node current = queue.poll();

            // Add its data to result
            result.add(current.getData());

            // Enqueue children (left first, then right)
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
        }

        return result;
    }

    /**
     * PRINT TRAVERSALS - Display all 4 traversals in formatted output
     * Useful for debugging and verification
     */
    public void printAllTraversals() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("🌳 TREE TRAVERSALS");
        System.out.println("=".repeat(70));

        // In-Order
        System.out.println("\n📍 IN-ORDER (LEFT → NODE → RIGHT) - SORTED:");
        List<StudentRecord> inOrder = inOrderTraversal();
        if (inOrder.isEmpty()) {
            System.out.println("  (empty tree)");
        } else {
            for (int i = 0; i < inOrder.size(); i++) {
                StudentRecord record = inOrder.get(i);
                System.out.printf("  %2d. %s (%s, CGPA: %.2f)\n",
                        i + 1, record.getName(), record.getMatricNumber(), record.getCgpa());
            }
        }

        // Pre-Order
        System.out.println("\n📍 PRE-ORDER (NODE → LEFT → RIGHT):");
        List<StudentRecord> preOrder = preOrderTraversal();
        if (preOrder.isEmpty()) {
            System.out.println("  (empty tree)");
        } else {
            for (int i = 0; i < preOrder.size(); i++) {
                System.out.print(preOrder.get(i).getMatricNumber());
                if (i < preOrder.size() - 1) System.out.print(" → ");
            }
            System.out.println();
        }

        // Post-Order
        System.out.println("\n📍 POST-ORDER (LEFT → RIGHT → NODE):");
        List<StudentRecord> postOrder = postOrderTraversal();
        if (postOrder.isEmpty()) {
            System.out.println("  (empty tree)");
        } else {
            for (int i = 0; i < postOrder.size(); i++) {
                System.out.print(postOrder.get(i).getMatricNumber());
                if (i < postOrder.size() - 1) System.out.print(" → ");
            }
            System.out.println();
        }

        // Level-Order
        System.out.println("\n📍 LEVEL-ORDER (BFS - Top to Bottom):");
        List<StudentRecord> levelOrder = levelOrderTraversal();
        if (levelOrder.isEmpty()) {
            System.out.println("  (empty tree)");
        } else {
            for (int i = 0; i < levelOrder.size(); i++) {
                System.out.print(levelOrder.get(i).getMatricNumber());
                if (i < levelOrder.size() - 1) System.out.print(" → ");
            }
            System.out.println();
        }

        System.out.println("\n" + "=".repeat(70));
    }

    // ============ TREE VISUALIZATION & ANALYSIS ============

    /**
     * TREE VISUALIZER - Print ASCII representation of the tree
     *
     * Creates a visual representation showing:
     *  - Node positions and relationships
     *  - Parent-child connections
     *  - Tree structure at a glance
     *
     * Example output:
     *        AIU105
     *       /      \
     *    AIU101   AIU110
     *      /       /
     *   AIU103   AIU108
     *
     * This is helpful for debugging and understanding tree balance.
     */

    /**
     * Print tree structure using TreeVisualizer
     * Shows ASCII representation of tree
     */
    public void printTree() {
        TreeVisualizer.printTree(this);
    }

    /**
     * Print detailed tree view with statistics
     */
    public void printTreeDetailed() {
        TreeVisualizer.printTreeDetailed(this);
    }

    /**
     * Print tree statistics
     */
    public void printTreeStats() {
        TreeVisualizer.printTreeStats(this);
    }

    /**
     * Print compact tree representation
     */
    public void printTreeCompact() {
        TreeVisualizer.printTreeCompact(this);
    }

    /**
     * Recursive helper for tree visualization
     * Uses box-drawing characters for better display
     */
    private void printTreeRecursive(Node node, String indent, boolean last) {
        if (node == null) {
            return;
        }

        // Print node data
        System.out.print(indent);
        if (last) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "│   ";
        }

        StudentRecord data = node.getData();
        System.out.println(String.format("%s (%s, CGPA: %.2f)",
                data.getMatricNumber(), data.getName(), data.getCgpa()));

        // Print children
        if (node.getLeft() != null || node.getRight() != null) {
            if (node.getRight() != null) {
                printTreeRecursive(node.getRight(), indent, node.getLeft() == null);
            }
            if (node.getLeft() != null) {
                printTreeRecursive(node.getLeft(), indent, true);
            }
        }
    }

    /**
     * DETAILED TREE ANALYSIS - Print comprehensive tree information
     *
     * Displays:
     *  - Node count and height
     *  - Balance factor
     *  - Tree properties (min, max, is valid BST)
     *  - Approximate height vs optimal height
     */
    public void printTreeAnalysis() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("📊 TREE ANALYSIS REPORT");
        System.out.println("=".repeat(70));

        if (root == null) {
            System.out.println("\nTree is EMPTY");
            System.out.println("=".repeat(70) + "\n");
            return;
        }

        int nodeCount = countNodes();
        int height = getHeight();
        int optimalHeight = (int) Math.ceil(Math.log(nodeCount + 1) / Math.log(2)) - 1;
        double balanceFactor = (double) height / optimalHeight;

        System.out.println("\n📈 TREE METRICS:");
        System.out.println("  • Total Nodes: " + nodeCount);
        System.out.println("  • Tree Height: " + height);
        System.out.println("  • Optimal Height: " + optimalHeight);
        System.out.println("  • Balance Factor: " + String.format("%.2f", balanceFactor));

        if (balanceFactor < 1.5) {
            System.out.println("    → Status: WELL-BALANCED ✓");
        } else if (balanceFactor < 2.0) {
            System.out.println("    → Status: REASONABLY BALANCED");
        } else {
            System.out.println("    → Status: SKEWED (degenerate)");
        }

        System.out.println("\n🔍 TREE PROPERTIES:");
        System.out.println("  • Min Node: " + (findMin() != null ? findMin().getMatricNumber() : "NULL"));
        System.out.println("  • Max Node: " + (findMax() != null ? findMax().getMatricNumber() : "NULL"));
        System.out.println("  • Is Valid BST: " + (isValidBST() ? "YES ✓" : "NO ❌"));

        System.out.println("\n⚖️  ROOT NODE:");
        if (root != null) {
            StudentRecord rootData = root.getData();
            System.out.println("  • Matric: " + rootData.getMatricNumber());
            System.out.println("  • Name: " + rootData.getName());
            System.out.println("  • CGPA: " + rootData.getCgpa());
            System.out.println("  • Left Child: " +
                    (root.getLeft() != null ? root.getLeft().getData().getMatricNumber() : "null"));
            System.out.println("  • Right Child: " +
                    (root.getRight() != null ? root.getRight().getData().getMatricNumber() : "null"));
        }

        System.out.println("\n" + "=".repeat(70) + "\n");
    }

    /**
     * Get depth of a specific node in the tree
     * Root depth = 0
     *
     * @param matricNumber The matric number to find depth for
     * @return Depth level, or -1 if not found
     */
    public int getNodeDepth(String matricNumber) {
        return getNodeDepthRecursive(root, matricNumber, 0);
    }

    /**
     * Recursive helper for finding node depth
     */
    private int getNodeDepthRecursive(Node node, String matricNumber, int depth) {
        if (node == null) {
            return -1;
        }

        String currentMatric = node.getData().getMatricNumber();

        if (currentMatric.equals(matricNumber)) {
            return depth;
        }

        int comparison = matricNumber.compareTo(currentMatric);

        if (comparison < 0) {
            return getNodeDepthRecursive(node.getLeft(), matricNumber, depth + 1);
        } else {
            return getNodeDepthRecursive(node.getRight(), matricNumber, depth + 1);
        }
    }

    /**
     * Check if the tree is BALANCED
     * A balanced tree has height ≈ log₂(n)
     *
     * @return true if tree is reasonably balanced
     */
    public boolean isBalanced() {
        int nodeCount = countNodes();
        if (nodeCount == 0) return true;

        int height = getHeight();
        int minHeight = (int) Math.floor(Math.log(nodeCount + 1) / Math.log(2)) - 1;
        int maxHeight = (int) Math.ceil(Math.log(nodeCount + 1) / Math.log(2)) - 1;

        return height >= minHeight && height <= maxHeight;
    }

    /**
     * Calculate the balance factor of the tree
     *
     * Balance Factor = Actual Height / Optimal Height
     *
     * Interpretation:
     *  - 1.0 = Perfect balance (ideal)
     *  - 1.0 - 1.5 = Reasonably balanced
     *  - > 1.5 = Skewed (degraded performance)
     *  - > 3.0 = Severely skewed (like linked list)
     *
     * Optimal Height = log₂(n+1) - 1
     * For n=1000: optimal ≈ 9.97, so 1.0 = perfect
     *
     * @return Balance factor as double
     */
    public double getBalanceFactor() {
        if (isEmpty()) {
            return 0.0;
        }

        int nodeCount = countNodes();
        int actualHeight = getHeight();

        // Calculate optimal height for perfectly balanced tree
        // Formula: log₂(n+1) - 1
        double optimalHeightDouble = Math.ceil(Math.log(nodeCount + 1) / Math.log(2)) - 1;
        int optimalHeight = (int) optimalHeightDouble;

        // Handle edge case of single node
        if (optimalHeight <= 0) {
            optimalHeight = 1;
        }

        // Balance factor = actual / optimal
        double balanceFactor = (double) actualHeight / optimalHeight;

        return balanceFactor;
    }



    /**
     * Get all nodes at a specific depth level
     *
     * @param depth The depth level (0 = root level)
     * @return List of StudentRecords at that depth
     */
    public List<StudentRecord> getNodesAtDepth(int depth) {
        List<StudentRecord> result = new ArrayList<>();
        getNodesAtDepthRecursive(root, depth, 0, result);
        return result;
    }

    /**
     * Recursive helper for getting nodes at specific depth
     */
    private void getNodesAtDepthRecursive(Node node, int targetDepth, int currentDepth,
                                          List<StudentRecord> result) {
        if (node == null) {
            return;
        }

        if (currentDepth == targetDepth) {
            result.add(node.getData());
            return;
        }

        if (currentDepth < targetDepth) {
            getNodesAtDepthRecursive(node.getLeft(), targetDepth, currentDepth + 1, result);
            getNodesAtDepthRecursive(node.getRight(), targetDepth, currentDepth + 1, result);
        }
    }

    /**
     * Get tree structure information for reporting
     * Returns a formatted string with tree statistics
     *
     * @return String containing tree info
     */
    public String getTreeInfo() {
        int nodeCount = countNodes();
        int height = getHeight();

        if (nodeCount == 0) {
            return "Empty BST";
        }

        return String.format(
                "BST Statistics:\n" +
                        "  Nodes: %d\n" +
                        "  Height: %d\n" +
                        "  Balance Factor: %.2f\n" +
                        "  Valid BST: %s\n" +
                        "  Min: %s\n" +
                        "  Max: %s",
                nodeCount,
                height,
                getBalanceFactor(),
                isValidBST() ? "Yes" : "No",
                findMin() != null ? findMin().getMatricNumber() : "N/A",
                findMax() != null ? findMax().getMatricNumber() : "N/A"
        );
    }

    /**
     * Find path from root to a specific node
     * Returns the sequence of matric numbers from root to target
     *
     * @param matricNumber Target matric number
     * @return List of matric numbers forming the path, empty if not found
     */
    public List<String> getPathToNode(String matricNumber) {
        List<String> path = new ArrayList<>();
        getPathRecursive(root, matricNumber, path);
        return path;
    }

    /**
     * Recursive helper for finding path
     */
    private boolean getPathRecursive(Node node, String matricNumber, List<String> path) {
        if (node == null) {
            return false;
        }

        String currentMatric = node.getData().getMatricNumber();
        path.add(currentMatric);

        if (currentMatric.equals(matricNumber)) {
            return true;  // Found!
        }

        int comparison = matricNumber.compareTo(currentMatric);

        if (comparison < 0) {
            if (getPathRecursive(node.getLeft(), matricNumber, path)) {
                return true;
            }
        } else {
            if (getPathRecursive(node.getRight(), matricNumber, path)) {
                return true;
            }
        }

        // Not found in this path, remove current node
        path.remove(path.size() - 1);
        return false;
    }

    /**
     * Calculate total search cost for all nodes
     * (Sum of depth of all nodes)
     * Used for performance analysis
     *
     * @return Total search cost
     */
    public long getTotalSearchCost() {
        return getTotalSearchCostRecursive(root, 0);
    }

    /**
     * Recursive helper for calculating search cost
     */
    private long getTotalSearchCostRecursive(Node node, int depth) {
        if (node == null) {
            return 0;
        }

        return (depth + 1) +
                getTotalSearchCostRecursive(node.getLeft(), depth + 1) +
                getTotalSearchCostRecursive(node.getRight(), depth + 1);
    }

    /**
     * Calculate average search cost
     * Lower is better (indicates good balance)
     *
     * @return Average search cost per node
     */
    public double getAverageSearchCost() {
        int nodeCount = countNodes();
        if (nodeCount == 0) return 0.0;

        return (double) getTotalSearchCost() / nodeCount;
    }

    /**
     * Get comparison results for performance analysis
     * Shows statistics about tree structure
     */
    public void printPerformanceAnalysis() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("⚡ PERFORMANCE ANALYSIS");
        System.out.println("=".repeat(70));

        int nodeCount = countNodes();

        if (nodeCount == 0) {
            System.out.println("\nTree is EMPTY");
            System.out.println("=".repeat(70) + "\n");
            return;
        }

        int height = getHeight();
        double balanceFactor = getBalanceFactor();
        long totalSearchCost = getTotalSearchCost();
        double avgSearchCost = getAverageSearchCost();

        System.out.println("\n📊 CURRENT TREE:");
        System.out.println("  • Nodes: " + nodeCount);
        System.out.println("  • Height: " + height);
        System.out.println("  • Total Search Cost: " + totalSearchCost);
        System.out.println("  • Average Search Cost: " + String.format("%.2f", avgSearchCost));
        System.out.println("  • Balance Factor: " + String.format("%.2f", balanceFactor));

        System.out.println("\n🎯 THEORETICAL OPTIMAL (Balanced BST):");
        int optimalHeight = (int) Math.ceil(Math.log(nodeCount + 1) / Math.log(2)) - 1;
        long optimalSearchCost = (long) (nodeCount * Math.log(nodeCount) / Math.log(2));
        double optimalAvgCost = Math.log(nodeCount) / Math.log(2);

        System.out.println("  • Optimal Height: " + optimalHeight);
        System.out.println("  • Optimal Search Cost: ≈" + optimalSearchCost);
        System.out.println("  • Optimal Avg Cost: " + String.format("%.2f", optimalAvgCost));

        System.out.println("\n📈 COMPARISON:");
        double heightRatio = (double) height / optimalHeight;
        double costRatio = avgSearchCost / optimalAvgCost;

        System.out.println("  • Height Ratio: " + String.format("%.2f", heightRatio) + "x optimal");
        System.out.println("  • Cost Ratio: " + String.format("%.2f", costRatio) + "x optimal");

        if (costRatio < 1.2) {
            System.out.println("  • Status: EXCELLENT ✓✓✓");
        } else if (costRatio < 1.5) {
            System.out.println("  • Status: GOOD ✓✓");
        } else if (costRatio < 2.0) {
            System.out.println("  • Status: ACCEPTABLE ✓");
        } else {
            System.out.println("  • Status: POOR - Consider rebalancing ⚠️");
        }

        System.out.println("\n" + "=".repeat(70) + "\n");
    }

    /**
     * Get leaf node count
     * @return Number of leaf nodes (nodes with no children)
     */
    public int getLeafCount() {
        return getLeafCountRecursive(root);
    }

    /**
     * Recursive helper for counting leaf nodes
     */
    private int getLeafCountRecursive(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.isLeaf()) {
            return 1;
        }

        return getLeafCountRecursive(node.getLeft()) +
                getLeafCountRecursive(node.getRight());
    }

    /**
     * Get internal node count (non-leaf nodes)
     * @return Number of internal nodes
     */
    public int getInternalNodeCount() {
        return countNodes() - getLeafCount();
    }

    public List<StudentRecord> linearSearchByCGPA(double minCGPA) {
        List<StudentRecord> results = new ArrayList<>();
        linearSearchHelper(root, minCGPA, results);
        return results;
    }

    private void linearSearchHelper(Node node, double minCGPA,
                                    List<StudentRecord> results) {
        if (node == null) return;
        linearSearchHelper(node.getLeft(), minCGPA, results);
        if (node.getData().getCgpa() >= minCGPA) {
            results.add(node.getData());
        }
        linearSearchHelper(node.getRight(), minCGPA, results);
    }


}
