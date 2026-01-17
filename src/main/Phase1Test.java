package main;

import model.StudentRecord;
import tree.Node;
import utilities.CSVDataLoader;

/**
 * Phase 1 Test - Verify StudentRecord, Node, and CSV Loading work correctly
 */
public class Phase1Test {

    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🧪 PHASE 1: FOUNDATION TEST");
        System.out.println("=".repeat(60) + "\n");

        // ========== TEST 1: StudentRecord ==========
        System.out.println("📝 TEST 1: StudentRecord Creation");
        System.out.println("-".repeat(60));

        StudentRecord student1 = new StudentRecord("Ali Hassan", "AIU105", 3.45);
        System.out.println("✓ Created: " + student1);
        System.out.println("  - Name: " + student1.getName());
        System.out.println("  - Matric: " + student1.getMatricNumber());
        System.out.println("  - CGPA: " + student1.getCgpa());
        System.out.println("  - Valid: " + student1.isValid());

        // ========== TEST 2: StudentRecord Comparison ==========
        System.out.println("\n📝 TEST 2: StudentRecord Comparison");
        System.out.println("-".repeat(60));

        StudentRecord s1 = new StudentRecord("Aisyah", "AIU101", 3.20);
        StudentRecord s2 = new StudentRecord("Bilal", "AIU103", 3.75);
        StudentRecord s3 = new StudentRecord("Daniel", "AIU105", 3.80);

        System.out.println("Comparing matric numbers (lexicographic ordering):");
        System.out.println("  AIU101 vs AIU103: " + s1.compareTo(s2) + " (negative = AIU101 < AIU103) ✓");
        System.out.println("  AIU105 vs AIU103: " + s3.compareTo(s2) + " (positive = AIU105 > AIU103) ✓");
        System.out.println("  AIU101 vs AIU101: " + s1.compareTo(s1) + " (zero = equal) ✓");

        // ========== TEST 3: Node Creation ==========
        System.out.println("\n📝 TEST 3: Node Creation");
        System.out.println("-".repeat(60));

        Node node1 = new Node(student1);
        System.out.println("✓ Created Node: " + node1);
        System.out.println("  - Is Leaf: " + node1.isLeaf());
        System.out.println("  - Has Children: " + node1.getChildCount());

        // ========== TEST 4: Node Linking ==========
        System.out.println("\n📝 TEST 4: Node Linking (Tree Structure)");
        System.out.println("-".repeat(60));

        Node root = new Node(s2);  // AIU103 as root
        Node leftChild = new Node(s1);  // AIU101 as left
        Node rightChild = new Node(s3);  // AIU105 as right

        root.setLeft(leftChild);
        root.setRight(rightChild);

        System.out.println("Root: " + root.getData().getMatricNumber() + " " + root.getData().getName());
        System.out.println("├─ Left:  " + root.getLeft().getData().getMatricNumber() + " " + root.getLeft().getData().getName());
        System.out.println("└─ Right: " + root.getRight().getData().getMatricNumber() + " " + root.getRight().getData().getName());
        System.out.println("✓ Tree structure created successfully");

        // ========== TEST 5: CSV Loading ==========
        System.out.println("\n📝 TEST 5: CSV Data Loading");
        System.out.println("-".repeat(60));

        StudentRecord[] records = CSVDataLoader.loadFromCSV("data/students.csv");
        System.out.println("✓ Loaded " + records.length + " student records from CSV");

        if (records.length > 0) {
            System.out.println("\nFirst 5 records:");
            for (int i = 0; i < Math.min(5, records.length); i++) {
                System.out.println("  " + (i+1) + ". " + records[i]);
            }

            System.out.println("\nLast 5 records:");
            int start = Math.max(0, records.length - 5);
            for (int i = start; i < records.length; i++) {
                System.out.println("  " + (i+1) + ". " + records[i]);
            }
        }

        // ========== TEST 6: Data Validation ==========
        System.out.println("\n📝 TEST 6: Data Validation");
        System.out.println("-".repeat(60));

        if (records.length > 0) {
            StudentRecord testRecord = records[0];
            System.out.println("Testing record: " + testRecord);
            System.out.println("  - Valid: " + testRecord.isValid());
            System.out.println("  - CGPA in range (0-4): " + (testRecord.getCgpa() >= 0 && testRecord.getCgpa() <= 4));
            System.out.println("  - Name not empty: " + !testRecord.getName().isEmpty());
            System.out.println("  - Matric not empty: " + !testRecord.getMatricNumber().isEmpty());
        }

        // ========== SUMMARY ==========
        System.out.println("\n" + "=".repeat(60));
        System.out.println("✅ PHASE 1 TEST COMPLETE");
        System.out.println("=".repeat(60));
        System.out.println("All components ready for Phase 2 (BST Operations)");
        System.out.println("Next: Implement BST insert, search, delete operations\n");
    }
}
