package com.code.java17features;

/**
 * Java 17 New Features - Interview Guide
 * 
 * Java 17 (released September 2021) is an LTS (Long Term Support) version with significant features.
 * This class provides an overview of the major features introduced in Java 17.
 * 
 * Key Features in Java 17:
 * 1. Sealed Classes
 * 2. Pattern Matching for switch (Preview)
 * 3. Records (Final)
 * 4. Text Blocks (Final)
 * 5. Enhanced Pseudo-Random Number Generators
 * 6. Foreign Function & Memory API (Preview)
 * 7. Deprecation of Applet API
 * 8. Strong Encapsulation of JDK Internals
 * 9. New macOS Rendering Pipeline
 * 10. Removing RMI Activation
 * 
 * This file serves as an index to all feature examples
 */

public class Java17Features {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        JAVA 17 NEW FEATURES - INTERVIEW PREPARATION       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("Key Java 17 Features:");
        System.out.println("─────────────────────\n");
        
        System.out.println("1. SEALED CLASSES");
        System.out.println("   └─ Control which classes can extend a class");
        System.out.println("   └─ See: SealedClassesDemo.java\n");
        
        System.out.println("2. PATTERN MATCHING for switch");
        System.out.println("   └─ Type patterns and guard clauses");
        System.out.println("   └─ See: PatternMatchingDemo.java\n");
        
        System.out.println("3. RECORDS (Final Feature)");
        System.out.println("   └─ Compact way to create immutable data classes");
        System.out.println("   └─ See: RecordsDemo.java\n");
        
        System.out.println("4. TEXT BLOCKS (Final Feature)");
        System.out.println("   └─ Multi-line strings with proper formatting");
        System.out.println("   └─ See: TextBlocksDemo.java\n");
        
        System.out.println("5. SEALED INTERFACES");
        System.out.println("   └─ Control which interfaces can extend an interface");
        System.out.println("   └─ See: SealedInterfacesDemo.java\n");
        
        System.out.println("6. INSTANCEOF PATTERN MATCHING");
        System.out.println("   └─ Pattern variables in instanceof");
        System.out.println("   └─ See: InstanceofPatternDemo.java\n");
        
        System.out.println("7. RECORDS WITH SEALED CLASSES");
        System.out.println("   └─ Combining records and sealed classes");
        System.out.println("   └─ See: RecordsWithSealedClassesDemo.java\n");
        
        System.out.println("════════════════════════════════════════════════════════════\n");
        System.out.println("Common Interview Questions About Java 17:");
        System.out.println("──────────────────────────────────────────\n");
        System.out.println("Q1: What are sealed classes and why are they useful?");
        System.out.println("Q2: What are records and how do they improve code?");
        System.out.println("Q3: What is pattern matching in switch?");
        System.out.println("Q4: How do text blocks improve string handling?");
        System.out.println("Q5: What is the difference between sealed classes and abstract classes?");
        System.out.println("Q6: Can records extend other classes?");
        System.out.println("Q7: What are the benefits of sealed interfaces?");
        System.out.println("Q8: How does instanceof pattern matching work?");
        System.out.println("Q9: Can sealed classes have abstract methods?");
        System.out.println("Q10: What is the relationship between records and value types?");
    }
}

/*

════════════════════════════════════════════════════════════════════════════
SUMMARY OF JAVA 17 FEATURES FOR INTERVIEW
════════════════════════════════════════════════════════════════════════════

1. SEALED CLASSES
──────────────────
Purpose: Control which classes can extend your class
Introduced: Java 15 (preview), Java 16 (preview), Java 17 (final)

Before Java 17:
    public class Shape { }
    // Anyone can extend Shape

After Java 17:
    public sealed class Shape permits Circle, Rectangle, Triangle { }
    // Only Circle, Rectangle, Triangle can extend Shape

Benefits:
    ✓ More control over inheritance hierarchy
    ✓ Better compiler optimizations
    ✓ Clearer code intent
    ✓ Safer type casting


2. PATTERN MATCHING IN SWITCH
──────────────────────────────
Purpose: Use patterns in switch statements
Introduced: Java 17 (preview)

Before Java 17:
    Object obj = "hello";
    String result;
    if (obj instanceof String) {
        String str = (String) obj;
        result = str.toUpperCase();
    } else if (obj instanceof Integer) {
        Integer num = (Integer) obj;
        result = String.valueOf(num * 2);
    }

After Java 17:
    Object obj = "hello";
    String result = switch(obj) {
        case String s -> s.toUpperCase();
        case Integer i -> String.valueOf(i * 2);
        default -> "unknown";
    };


3. RECORDS (FINAL)
──────────────────
Purpose: Compact way to create immutable data classes
Introduced: Java 14 (preview), Java 15 (preview), Java 16 (final), Java 17 (enhancements)

Before Java 17:
    public class Point {
        private final int x;
        private final int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int x() { return x; }
        public int y() { return y; }
        
        @Override
        public boolean equals(Object o) { ... }
        
        @Override
        public int hashCode() { ... }
        
        @Override
        public String toString() { ... }
    }

After Java 17:
    public record Point(int x, int y) { }
    
    // Automatically provides:
    // - Constructor
    // - Accessors (x(), y())
    // - equals(), hashCode(), toString()


4. TEXT BLOCKS (FINAL)
──────────────────────
Purpose: Multi-line strings with proper formatting
Introduced: Java 13 (preview), Java 14 (preview), Java 15 (final)

Before Java 17:
    String json = "{\n" +
                  "  \"name\": \"John\",\n" +
                  "  \"age\": 30\n" +
                  "}";

After Java 17:
    String json = """
        {
          "name": "John",
          "age": 30
        }""";


5. SEALED INTERFACES
────────────────────
Purpose: Control which types can implement an interface
Introduced: Java 17 (same as sealed classes)

Example:
    public sealed interface Animal permits Dog, Cat, Bird { }
    
    public class Dog implements Animal { }
    public class Cat implements Animal { }
    public class Bird implements Animal { }
    
    // Other classes cannot implement Animal


6. INSTANCEOF PATTERN MATCHING
───────────────────────────────
Purpose: Eliminate redundant casts

Before Java 17:
    if (obj instanceof String) {
        String str = (String) obj;
        System.out.println(str.length());
    }

After Java 17:
    if (obj instanceof String str) {
        System.out.println(str.length());
    }


════════════════════════════════════════════════════════════════════════════
COMPARISON TABLE: ABSTRACT CLASS vs SEALED CLASS
════════════════════════════════════════════════════════════════════════════

Aspect              │ Abstract Class           │ Sealed Class
────────────────────┼──────────────────────────┼──────────────────
Purpose             │ Partial implementation   │ Limit subclasses
Can have fields     │ YES                      │ YES
Can have methods    │ YES (abstract/concrete)  │ YES
Constructor         │ YES (protected)          │ YES
Multiple inherit    │ NO                       │ NO
Final subclasses    │ NOT automatic            │ Can require final
Control lineage     │ NO                       │ YES
Memory efficient    │ Normal                   │ Better (JVM can optimize)


════════════════════════════════════════════════════════════════════════════
JAVA 17 VERSION HIGHLIGHTS
════════════════════════════════════════════════════════════════════════════

Release Date:       September 2021
Type:               LTS (Long Term Support) - supported until September 2026
Features:           7 finalized + several preview features
Total Changes:      490 RFEs, 1,000+ bugs fixed


════════════════════════════════════════════════════════════════════════════

*/
