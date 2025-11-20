package com.code.java17features;

/**
 * STRING TEMPLATES - Java 21 Feature (Preview)
 * 
 * Purpose: Enable safe and convenient interpolation of values into strings
 * Introduced: Java 21 (preview, under --enable-preview flag)
 * 
 * Note: String templates are a preview feature and may change.
 * Compile with: javac --enable-preview StringTemplatesDemo.java
 * Run with: java --enable-preview StringTemplatesDemo
 * 
 * Features:
 * • String templates using \{...} syntax
 * • Built-in template processors: STR, FMT, RAW
 * • Safe interpolation (no SQL injection, etc.)
 * • Multi-line template support
 */

public class StringTemplatesDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("        STRING TEMPLATES - Java 21 Feature");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateBasicStringTemplates();
        System.out.println("\n");
        demonstrateStringConcatenationComparison();
        System.out.println("\n");
        demonstrateComplexExpressions();
        System.out.println("\n");
        demonstrateMultiLineTemplates();
        System.out.println("\n");
        demonstrateFormattingWithFMT();
    }
    
    private static void demonstrateBasicStringTemplates() {
        System.out.println("EXAMPLE 1: Basic String Templates (Java 21+)");
        System.out.println("────────────────────────────────────────────\n");
        
        String name = "Alice";
        int age = 30;
        double salary = 75000.50;
        
        System.out.println("Using traditional string concatenation:");
        System.out.println("  \"Employee: \" + name + \", Age: \" + age + \", Salary: $\" + salary");
        
        System.out.println("\nUsing STR template (equivalent):");
        // Note: In actual Java 21, this would use string templates
        // String template = STR."Employee: \{name}, Age: \{age}, Salary: $\{salary}";
        // For demonstration without --enable-preview flag, we show the concept:
        String result = String.format("Employee: %s, Age: %d, Salary: $%.2f", name, age, salary);
        System.out.println("  " + result);
        
        System.out.println("\n✓ String templates: Cleaner syntax for interpolation");
    }
    
    private static void demonstrateStringConcatenationComparison() {
        System.out.println("EXAMPLE 2: Comparison with Traditional Methods (Java 21+)");
        System.out.println("──────────────────────────────────────────────────────────\n");
        
        String productName = "Laptop";
        int quantity = 5;
        double unitPrice = 999.99;
        double total = quantity * unitPrice;
        
        System.out.println("Traditional concatenation:");
        String old1 = "Product: " + productName + ", Qty: " + quantity + ", Unit Price: $" + unitPrice + ", Total: $" + total;
        System.out.println("  " + old1);
        
        System.out.println("\nUsing String.format():");
        String old2 = String.format("Product: %s, Qty: %d, Unit Price: $%.2f, Total: $%.2f", productName, quantity, unitPrice, total);
        System.out.println("  " + old2);
        
        System.out.println("\nString Template (Java 21 equivalent):");
        System.out.println("  STR.\"Product: \\{productName}, Qty: \\{quantity}, Unit Price: $\\{unitPrice}, Total: $\\{total}\"");
        
        System.out.println("\n✓ String templates are more readable and intuitive");
    }
    
    private static void demonstrateComplexExpressions() {
        System.out.println("EXAMPLE 3: Complex Expressions in Templates (Java 21+)");
        System.out.println("──────────────────────────────────────────────────────\n");
        
        int[] scores = {85, 92, 78, 95, 88};
        
        System.out.println("Expressions in template placeholders:");
        System.out.println("  Scores: " + scores.length + " tests taken");
        System.out.println("  Average: " + calculateAverage(scores));
        System.out.println("  Max score: " + findMax(scores));
        System.out.println("  Min score: " + findMin(scores));
        
        System.out.println("\nWith string templates (Java 21 equivalent):");
        System.out.println("  STR.\"Test Results:");
        System.out.println("      Scores: \\{scores.length} tests taken");
        System.out.println("      Average: \\{calculateAverage(scores)}");
        System.out.println("      Max: \\{findMax(scores)}, Min: \\{findMin(scores)}\"");
        
        System.out.println("\n✓ String templates can contain any Java expression");
    }
    
    private static void demonstrateMultiLineTemplates() {
        System.out.println("EXAMPLE 4: Multi-line Templates (Java 21+)");
        System.out.println("──────────────────────────────────────────\n");
        
        String userName = "John Doe";
        String email = "john@example.com";
        String phone = "+1-555-1234";
        String address = "123 Main St, New York, NY 10001";
        
        System.out.println("Traditional multi-line string (with escapes):");
        String oldFormat = "USER PROFILE\n"
                         + "─────────────────────────────────\n"
                         + "Name: " + userName + "\n"
                         + "Email: " + email + "\n"
                         + "Phone: " + phone + "\n"
                         + "Address: " + address + "\n";
        System.out.println(oldFormat);
        
        System.out.println("\nString template (Java 21+ equivalent with text blocks):");
        System.out.println("""
                           USER PROFILE
                           ─────────────────────────────────
                           Name: """ + userName + "\n" +
                           "Email: " + email + "\n" +
                           "Phone: " + phone + "\n" +
                           "Address: " + address);
        
        System.out.println("\n✓ String templates work well with text blocks");
    }
    
    private static void demonstrateFormattingWithFMT() {
        System.out.println("EXAMPLE 5: Formatting with FMT Template (Java 21+)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        double pi = 3.14159265359;
        double price = 19.999;
        int percentage = 67;
        
        System.out.println("Using String.format for precise formatting:");
        System.out.println("  Pi (4 decimals): " + String.format("%.4f", pi));
        System.out.println("  Price: " + String.format("$%.2f", price));
        System.out.println("  Percentage: " + String.format("%3d%%", percentage));
        
        System.out.println("\nWith FMT template (Java 21+ equivalent):");
        System.out.println("  FMT.\"Pi (4 decimals): \\{pi}%4.2f\"");
        System.out.println("  FMT.\"Price: $\\{price}%.2f\"");
        System.out.println("  FMT.\"Percentage: \\{percentage}%3d%%\"");
        
        System.out.println("\n✓ FMT template processor: sprintf-style formatting");
    }
    
    // Helper methods
    private static double calculateAverage(int[] scores) {
        if (scores.length == 0) return 0;
        int sum = 0;
        for (int score : scores) {
            sum += score;
        }
        return (double) sum / scores.length;
    }
    
    private static int findMax(int[] scores) {
        int max = scores[0];
        for (int score : scores) {
            if (score > max) max = score;
        }
        return max;
    }
    
    private static int findMin(int[] scores) {
        int min = scores[0];
        for (int score : scores) {
            if (score < min) min = score;
        }
        return min;
    }
}

/*
 * ═════════════════════════════════════════════════════════════════════════════
 * STRING TEMPLATES - COMPREHENSIVE Q&A (Java 21)
 * ═════════════════════════════════════════════════════════════════════════════
 * 
 * Q1: What are string templates?
 * A: String templates are a new way to interpolate values into strings in Java 21.
 *    They use a cleaner syntax with backslash-curly-brace: STR."Hello \{name}"
 *    They are safer than traditional concatenation and more readable than format().
 * 
 * Q2: What is the STR template processor?
 * A: STR is the basic string template processor in Java 21.
 *    Syntax: STR."text \\{expression} more text"
 *    Expression: Can be any Java expression that returns a value
 * 
 * Q3: What is the FMT template processor?
 * A: FMT is the formatting template processor (like sprintf).
 *    Syntax: FMT."Price: \\{price}%.2f"
 *    Format specifiers: Follow printf format rules (%.2f, %d, %s, etc.)
 * 
 * Q4: What is the RAW template processor?
 * A: RAW is used when you need the raw string without interpolation.
 *    Useful for regex patterns, SQL queries, or when backslashes are significant.
 *    Syntax: RAW."Pattern: \\d+\\.\\d+"
 * 
 * Q5: How do string templates prevent SQL injection?
 * A: String templates can use security-aware processors that validate input.
 *    For example, a database template processor could escape special characters.
 *    This prevents malicious SQL from being injected through interpolated values.
 * 
 * Q6: What is the difference between STR and String.format()?
 * A: STR: STR."Hello \\{name}, you are \\{age} years old"
 *    format: String.format("Hello %s, you are %d years old", name, age)
 *    STR is more readable, expressions can be complex
 *    format() requires format specifiers and type matching
 * 
 * Q7: Can you use method calls in string templates?
 * A: Yes, string templates can contain any Java expression:
 *    STR."List has \\{list.size()} items"
 *    STR."Square root of \\{num} is \\{Math.sqrt(num)}"
 *    STR."Date: \\{Calendar.getInstance().getTime()}"
 * 
 * Q8: Are string templates already available?
 * A: String templates are a PREVIEW feature in Java 21.
 *    Compile with: javac --enable-preview StringTemplatesDemo.java
 *    Run with: java --enable-preview StringTemplatesDemo
 *    They may change in future Java versions.
 * 
 * Q9: How do string templates work with multi-line strings?
 * A: They work great with text blocks (introduced in Java 15).
 *    Combine text blocks with template expressions:
 *    STR.\"\"\"
 *        Name: \\{name}
 *        Age: \\{age}
 *        \"\"\"
 * 
 * Q10: What are the benefits of string templates over concatenation?
 * A: • Readability: Clear what values are being interpolated
 *    • Safety: Validation processors can prevent injection attacks
 *    • Conciseness: Fewer operators than + concatenation
 *    • Expression support: Full Java expressions, not just variables
 *    • Formatting: Built-in support for printf-style formatting (FMT)
 * 
 * ═════════════════════════════════════════════════════════════════════════════
 */
