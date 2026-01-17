package utilities;

import model.StudentRecord;
import java.io.*;
import java.util.*;

/**
 * CSVDataLoader handles reading student records from a CSV file.
 *
 * Expected CSV Format:
 *  matric,name,cgpa
 *  AIU101,Aisyah Rahman,3.20
 *  AIU102,Bilal Ahmed,3.75
 *  ... (more records)
 *
 * Features:
 *  - Skips header row automatically
 *  - Validates each record before adding
 *  - Handles malformed data gracefully
 *  - Reports statistics (loaded, skipped, errors)
 */
public class CSVDataLoader {

    // ============ CONSTANTS ============
    private static final String CSV_DELIMITER = ",";
    private static final int EXPECTED_FIELDS = 3; // matric, name, cgpa

    // ============ STATIC METHODS ============

    /**
     * Load student records from a CSV file
     *
     * @param filename Path to the CSV file (e.g., "data/students.csv")
     * @return Array of StudentRecord objects, empty array if file not found
     */
    public static StudentRecord[] loadFromCSV(String filename) {
        List<StudentRecord> recordList = new ArrayList<>();
        int lineNumber = 0;
        int skippedCount = 0;
        int errorCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip empty lines
                if (line.trim().isEmpty()) {
                    skippedCount++;
                    continue;
                }

                // Skip header row (first non-empty line with "matric" in it)
                if (lineNumber == 1 && line.toLowerCase().contains("matric")) {
                    skippedCount++;
                    continue;
                }

                try {
                    StudentRecord record = parseCSVLine(line);

                    if (record != null && record.isValid()) {
                        recordList.add(record);
                    } else {
                        errorCount++;
                        System.err.println("⚠️  Line " + lineNumber + ": Invalid record data - " + line);
                    }
                } catch (Exception e) {
                    errorCount++;
                    System.err.println("⚠️  Line " + lineNumber + ": Parse error - " + e.getMessage());
                }
            }

            // Print loading statistics
            printLoadingStatistics(filename, recordList.size(), skippedCount, errorCount);

        } catch (FileNotFoundException e) {
            System.err.println("❌ ERROR: File not found - " + filename);
            System.err.println("   Please ensure the file exists at: " + new File(filename).getAbsolutePath());
            return new StudentRecord[0];
        } catch (IOException e) {
            System.err.println("❌ ERROR: Unable to read file - " + e.getMessage());
            return new StudentRecord[0];
        }

        // Convert list to array
        return recordList.toArray(new StudentRecord[0]);
    }

    /**
     * Parse a single CSV line into a StudentRecord
     *
     * Expected format: "matric,name,cgpa"
     * Example: "AIU105,Ali Hassan,3.45"
     *
     * @param line The CSV line to parse
     * @return StudentRecord object, or null if parsing fails
     * @throws NumberFormatException if CGPA cannot be parsed as double
     */
    private static StudentRecord parseCSVLine(String line) throws NumberFormatException {
        // Split by comma
        String[] parts = line.split(CSV_DELIMITER);

        // Validate field count
        if (parts.length != EXPECTED_FIELDS) {
            throw new IllegalArgumentException(
                    "Expected " + EXPECTED_FIELDS + " fields, got " + parts.length
            );
        }

        // Extract and trim fields
        String matric = parts[0].trim();
        String name = parts[1].trim();
        String cgpaStr = parts[2].trim();

        // Validate non-empty fields
        if (matric.isEmpty() || name.isEmpty() || cgpaStr.isEmpty()) {
            throw new IllegalArgumentException("One or more fields are empty");
        }

        // Parse CGPA as double
        double cgpa = Double.parseDouble(cgpaStr);

        // Create and return StudentRecord
        return new StudentRecord(name, matric, cgpa);
    }

    /**
     * Print loading statistics to console
     */
    private static void printLoadingStatistics(String filename, int loaded,
                                               int skipped, int errors) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📂 CSV LOADING REPORT");
        System.out.println("=".repeat(50));
        System.out.println("File: " + filename);
        System.out.println("✓ Loaded:  " + loaded + " records");
        if (skipped > 0) {
            System.out.println("⊘ Skipped: " + skipped + " lines (headers/empty)");
        }
        if (errors > 0) {
            System.out.println("✗ Errors:  " + errors + " records (invalid)");
        }
        System.out.println("=".repeat(50) + "\n");
    }

    /**
     * Print all loaded records to console (for verification)
     *
     * @param records Array of StudentRecord objects to display
     */
    public static void printAllRecords(StudentRecord[] records) {
        if (records == null || records.length == 0) {
            System.out.println("No records to display.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("📋 ALL LOADED STUDENT RECORDS");
        System.out.println("=".repeat(60));
        System.out.printf("%-10s %-25s %-8s\n", "MATRIC", "NAME", "CGPA");
        System.out.println("-".repeat(60));

        for (StudentRecord record : records) {
            if (record != null) {
                System.out.printf("%-10s %-25s %.2f\n",
                        record.getMatricNumber(),
                        record.getName(),
                        record.getCgpa());
            }
        }

        System.out.println("=".repeat(60) + "\n");
    }
}
