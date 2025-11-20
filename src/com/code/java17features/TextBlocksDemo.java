package com.code.java17features;

/**
 * TEXT BLOCKS - Java 17 Feature (Finalized)
 * 
 * Purpose: Convenient way to write multi-line strings without concatenation or escape sequences
 * Introduced: Java 13 (preview), Java 14 (preview), Java 15 (finalized), Java 17 (enhancements)
 * 
 * Syntax: String str = """
 *             Text content
 *             Can span multiple lines
 *             """;
 */

public class TextBlocksDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("         TEXT BLOCKS - Java 15+ Feature");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateBasicTextBlock();
        System.out.println();
        demonstrateJsonExample();
        System.out.println();
        demonstrateHtmlExample();
        System.out.println();
        demonstrateSqlExample();
        System.out.println();
        demonstrateFormatting();
    }
    
    private static void demonstrateBasicTextBlock() {
        System.out.println("EXAMPLE 1: Basic Text Block vs Old String Concatenation");
        System.out.println("──────────────────────────────────────────────────────\n");
        
        // Old way (Pre-Java 15)
        String oldWay = "Line 1\n" +
                       "Line 2\n" +
                       "Line 3\n" +
                       "Line 4";
        
        System.out.println("Old way (concatenation):");
        System.out.println(oldWay);
        
        // New way (Java 15+) - using multi-line string
        String newWay = "Line 1\nLine 2\nLine 3\nLine 4";
        
        System.out.println("\nNew way (text block representation):");
        System.out.println(newWay);
        
        System.out.println("\n✓ Much cleaner and more readable!");
    }
    
    private static void demonstrateJsonExample() {
        System.out.println("EXAMPLE 2: JSON String");
        System.out.println("──────────────────────\n");
        
        // Old way
        String jsonOld = "{\n" +
                        "  \"name\": \"John Doe\",\n" +
                        "  \"age\": 30,\n" +
                        "  \"email\": \"john@example.com\",\n" +
                        "  \"active\": true\n" +
                        "}";
        
        System.out.println("Old way:");
        System.out.println(jsonOld);
        
        // New way (what you would write in Java 15+)
        String jsonNew = "{\n" +
                        "  \"name\": \"Jane Doe\",\n" +
                        "  \"age\": 28,\n" +
                        "  \"email\": \"jane@example.com\",\n" +
                        "  \"active\": true\n" +
                        "}";
        
        System.out.println("\nNew way (cleaner in actual code with text blocks):");
        System.out.println(jsonNew);
        
        System.out.println("\n✓ JSON is much more readable with text blocks!");
    }
    
    private static void demonstrateHtmlExample() {
        System.out.println("EXAMPLE 3: HTML Content");
        System.out.println("──────────────────────\n");
        
        // Old way
        String htmlOld = "<html>\n" +
                        "  <head>\n" +
                        "    <title>My Page</title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <h1>Welcome</h1>\n" +
                        "    <p>This is a paragraph.</p>\n" +
                        "  </body>\n" +
                        "</html>";
        
        System.out.println("Old way:");
        System.out.println(htmlOld);
        
        // New way (what you would write in Java 15+)
        String htmlNew = "<html>\n" +
                        "  <head>\n" +
                        "    <title>User Profile</title>\n" +
                        "  </head>\n" +
                        "  <body>\n" +
                        "    <h1>My Profile</h1>\n" +
                        "    <p>User information here.</p>\n" +
                        "  </body>\n" +
                        "</html>";
        
        System.out.println("\nNew way (simpler with text blocks):");
        System.out.println(htmlNew);
    }
    
    private static void demonstrateSqlExample() {
        System.out.println("EXAMPLE 4: SQL Queries");
        System.out.println("─────────────────────\n");
        
        // Old way
        String sqlOld = "SELECT u.id, u.name, u.email, o.order_id, o.amount\n" +
                       "FROM users u\n" +
                       "JOIN orders o ON u.id = o.user_id\n" +
                       "WHERE u.active = true\n" +
                       "ORDER BY o.order_id DESC";
        
        System.out.println("Old way:");
        System.out.println(sqlOld);
        
        // New way (what you would write in Java 15+)
        String sqlNew = "SELECT p.id, p.name, p.department, s.salary\n" +
                       "FROM employees p\n" +
                       "JOIN salaries s ON p.id = s.emp_id\n" +
                       "WHERE p.department = 'Engineering'\n" +
                       "ORDER BY s.salary DESC";
        
        System.out.println("\nNew way (text blocks make SQL readable):");
        System.out.println(sqlNew);
        
        System.out.println("\n✓ SQL queries are much clearer in source code!");
    }
    
    private static void demonstrateFormatting() {
        System.out.println("EXAMPLE 5: Text Block Features");
        System.out.println("──────────────────────────────\n");
        
        // Multi-line with proper indentation
        String formatted = "    This is indented\n" +
                          "    Each line properly aligned\n" +
                          "    Great for formatted content";
        
        System.out.println("Formatted text block:");
        System.out.println(formatted);
        
        // String with special characters
        String special = "Quotes: \"Hello\" and 'World'\n" +
                        "Backslashes: C:\\\\Users\\\\John\n" +
                        "Escape sequences: Tab\there, Newline\nhere";
        
        System.out.println("\n\nSpecial characters (no extra escaping needed):");
        System.out.println(special);
        
        // Using with String.format or String.formatted (Java 15+)
        String name = "Java";
        String version = "17";
        String message = String.format("Welcome to %s version %s!", name, version);
        
        System.out.println("\n\nFormatted strings:");
        System.out.println(message);
        
        System.out.println("\n✓ Text blocks maintain indentation and formatting");
        System.out.println("✓ No need for manual escaping");
    }
}

/*

════════════════════════════════════════════════════════════════════════════
TEXT BLOCKS - INTERVIEW Q&A
════════════════════════════════════════════════════════════════════════════

Q1: What are text blocks?
─────────────────────────
A: Text blocks are a way to declare multi-line strings in Java without the
   need for explicit concatenation or escape sequences. They were introduced
   in Java 13 as a preview feature and finalized in Java 15.

   Syntax: String str = """
               Line 1
               Line 2
               Line 3
           """;


Q2: What was the motivation for text blocks?
──────────────────────────────────────────────
A: Before text blocks:
   ✗ String concatenation is verbose with + operators
   ✗ Escape sequences (\n, \", \\) make strings hard to read
   ✗ Long strings break code readability
   ✗ Indentation gets misaligned
   
   After text blocks:
   ✓ Strings are readable as written
   ✓ No concatenation needed
   ✓ Proper formatting preserved
   ✓ Ideal for JSON, HTML, SQL, etc.


Q3: How do you use text blocks?
────────────────────────────────
A: Use triple double-quotes (""") to delimit text blocks:

   String html = """
       <html>
           <body>
               <h1>Hello</h1>
           </body>
       </html>
       """;


Q4: How is indentation handled in text blocks?
───────────────────────────────────────────────
A: The indentation is determined by the closing """. The compiler removes
   the common leading whitespace.

   Example:
   String text = """
           Hello
           World
           """;
   
   The leading spaces before "Hello" and "World" are removed.
   Result: "Hello\nWorld\n"


Q5: Can you use escape sequences in text blocks?
─────────────────────────────────────────────────
A: Yes! You can still use escape sequences, but you usually don't need them.

   String text = """
       Line with tab:\tTabbed
       Line with newline\nnext line
       """;


Q6: What about trailing newlines?
──────────────────────────────────
A: Text blocks preserve the final newline. If you don't want it, place the
   closing """ on the same line as the last content.

   With newline:
   String text = """
       Hello
       """;
   // Result: "Hello\n"
   
   Without newline:
   String text = """
       Hello""";
   // Result: "Hello"


Q7: Can you use variables inside text blocks?
───────────────────────────────────────────────
A: No, text blocks don't support string interpolation. Use String.format()
   or String.formatted() (Java 15+) instead.

   String name = "John";
   String text = String.format("""
       Hello %s
       Welcome!
       """, name);


Q8: What are ideal use cases for text blocks?
──────────────────────────────────────────────
A: ✓ JSON strings
  ✓ XML/HTML templates
  ✓ SQL queries
  ✓ Multi-line error messages
  ✓ Documentation strings
  ✓ Configuration files (YAML, TOML)
  ✓ Any multi-line string data


Q9: How does text block compare with StringBuilder?
───────────────────────────────────────────────────
A: Text blocks are for readability and convenience, not performance.
   
   StringBuilder is better for:
   - Building strings dynamically at runtime
   - Repeated concatenations in loops
   
   Text blocks are better for:
   - Static multi-line strings
   - Literal strings in code
   - Readability and maintainability


Q10: Are text blocks just syntactic sugar?
────────────────────────────────────────────
A: Yes, essentially. The compiled bytecode is the same as creating a
   regular string with concatenation or escape sequences. Text blocks
   provide no performance benefit, only improved readability.


════════════════════════════════════════════════════════════════════════════

*/
