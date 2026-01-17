package tree;

import model.StudentRecord;

/**
 * Node represents a single node in the Binary Search Tree.
 * Each node contains:
 *  - data: A StudentRecord object
 *  - left: Reference to left child node (smaller matric numbers)
 *  - right: Reference to right child node (larger matric numbers)
 *
 * BST Property:
 *  - If student.matric < node.matric → goes to LEFT subtree
 *  - If student.matric > node.matric → goes to RIGHT subtree
 *  - Duplicates are REJECTED
 */
public class Node {

    // ============ ATTRIBUTES ============
    private StudentRecord data;
    private Node left;
    private Node right;

    // ============ CONSTRUCTORS ============

    /**
     * Create a new Node with the given StudentRecord
     * Initializes left and right pointers to null
     *
     * @param data The StudentRecord to store in this node
     */
    public Node(StudentRecord data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    // ============ GETTERS ============

    /**
     * @return The StudentRecord stored in this node
     */
    public StudentRecord getData() {
        return data;
    }

    /**
     * @return The left child node (or null if no left child)
     */
    public Node getLeft() {
        return left;
    }

    /**
     * @return The right child node (or null if no right child)
     */
    public Node getRight() {
        return right;
    }

    // ============ SETTERS ============

    /**
     * Set the StudentRecord data for this node
     * @param data The new StudentRecord
     */
    public void setData(StudentRecord data) {
        this.data = data;
    }

    /**
     * Set the left child node
     * @param left The new left child node
     */
    public void setLeft(Node left) {
        this.left = left;
    }

    /**
     * Set the right child node
     * @param right The new right child node
     */
    public void setRight(Node right) {
        this.right = right;
    }

    // ============ UTILITY METHODS ============

    /**
     * Check if this node is a leaf node (has no children)
     * @return true if both left and right are null
     */
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    /**
     * Check if this node has a left child
     * @return true if left is not null
     */
    public boolean hasLeftChild() {
        return left != null;
    }

    /**
     * Check if this node has a right child
     * @return true if right is not null
     */
    public boolean hasRightChild() {
        return right != null;
    }

    /**
     * Check if this node has both children
     * @return true if both left and right are not null
     */
    public boolean hasBothChildren() {
        return this.left != null && this.right != null;
    }

    /**
     * Check if node has exactly one child
     *
     * @return true if exactly one of left or right is not null
     */
    public boolean hasOneChild() {
        return (this.left != null && this.right == null) ||
                (this.left == null && this.right != null);
    }

    /**
     * Get the one child (used when node has exactly one child)
     *
     * @return the non-null child, or null if no children
     */
    public Node getChild() {
        return (this.left != null) ? this.left : this.right;
    }

    /**
     * Get the number of children this node has
     * @return 0 (leaf), 1 (one child), or 2 (both children)
     */
    public int getChildCount() {
        int count = 0;
        if (left != null) count++;
        if (right != null) count++;
        return count;
    }

    /**
     * String representation of this node
     * Shows the student record stored in this node
     */
//    @Override
//    public String toString() {
//        return "Node(" + data.toString() + ")";
//    }

    /**
     * String representation of node
     */
    @Override
    public String toString() {
        return data.getMatricNumber() + " (" + data.getName() +
                ", CGPA " + data.getCgpa() + ")";
    }
}
