package model;

/**
 * StudentRecord represents a single student's academic record.
 * Used as the data element in each Binary Search Tree node.
 *
 * Attributes:
 *  - name: Student's full name
 *  - matricNumber: Unique identifier (BST key) - lexicographically ordered
 *  - cgpa: Cumulative Grade Point Average (0.0 - 4.0)
 */
public class StudentRecord {

    // ============ ATTRIBUTES ============
    private String name;
    private String matricNumber;
    private double cgpa;

    // ============ CONSTRUCTORS ============

    /**
     * Default constructor - creates empty StudentRecord
     */
    public StudentRecord() {
        this("", "", 0.0);
    }

    /**
     * Full constructor - creates StudentRecord with all fields
     * @param name Student's full name
     * @param matricNumber Unique matric ID (e.g., "AIU105")
     * @param cgpa Cumulative GPA
     */
    public StudentRecord(String name, String matricNumber, double cgpa) {
        this.name = name;
        this.matricNumber = matricNumber;
        this.cgpa = cgpa;
    }

    // ============ GETTERS ============

    /**
     * @return Student's full name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Matric number (BST key)
     */
    public String getMatricNumber() {
        return matricNumber;
    }

    /**
     * @return CGPA value
     */
    public double getCgpa() {
        return cgpa;
    }

    // ============ SETTERS ============

    /**
     * Set the student's name
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the student's matric number
     * @param matricNumber New matric number
     */
    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    /**
     * Set the student's CGPA
     * @param cgpa New CGPA
     */
    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    // ============ UTILITY METHODS ============

    /**
     * Compare this StudentRecord with another based on matric number
     * Uses lexicographic (alphabetical) string ordering
     *
     * @param other The StudentRecord to compare with
     * @return negative if this < other, 0 if equal, positive if this > other
     *
     * Example:
     *  "AIU101".compareTo("AIU105") → negative (AIU101 < AIU105)
     *  "AIU110".compareTo("AIU105") → positive (AIU110 > AIU105)
     */
    public int compareTo(StudentRecord other) {
        if (other == null) {
            return 1; // This record comes after null
        }
        return this.matricNumber.compareTo(other.matricNumber);
    }

    /**
     * String representation of StudentRecord
     * Format: "Name (MatricNumber, CGPA: X.XX)"
     *
     * Example: "Ali Hassan (AIU105, CGPA: 3.45)"
     */
    @Override
    public String toString() {
        return String.format("%s (%s, CGPA: %.2f)",
                name, matricNumber, cgpa);
    }

    /**
     * Validate StudentRecord data integrity
     * Checks:
     *  - Name is not empty
     *  - Matric number is not empty
     *  - CGPA is within valid range (0.0 - 4.0)
     *
     * @return true if record is valid, false otherwise
     */
    public boolean isValid() {
        return (name != null && !name.trim().isEmpty()) &&
                (matricNumber != null && !matricNumber.trim().isEmpty()) &&
                (cgpa >= 0.0 && cgpa <= 4.0);
    }

    /**
     * Get a formatted string for CSV output
     * Format: "matric,name,cgpa"
     */
    public String toCSVString() {
        return String.format("%s,%s,%.2f", matricNumber, name, cgpa);
    }
}
