package com.code.java17features;

/**
 * PATTERN MATCHING - Java 17-21 Feature Evolution
 * 
 * Java 16: Pattern matching for instanceof (preview)
 * Java 17: Pattern matching for instanceof (finalized)
 * Java 18-20: Pattern matching for switch (preview)
 * Java 21: Pattern matching for switch (finalized)
 * 
 * This demo shows:
 * 1. Type Patterns with instanceof (Java 16+)
 * 2. Guard Clauses (Java 16+)
 * 3. Pattern Matching in Switch (Java 21+)
 * 4. Record Patterns (Java 21+)
 * 5. Sealed Class Integration
 */

// Sealed interface for shapes
sealed interface Shape permits Circle, Rectangle, Triangle {
    double getArea();
}

record Circle(double radius) implements Shape {
    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }
}

record Rectangle(double width, double height) implements Shape {
    @Override
    public double getArea() {
        return width * height;
    }
}

record Triangle(double a, double b, double c) implements Shape {
    @Override
    public double getArea() {
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}

// Sealed hierarchy for HTTP responses
sealed interface HttpResponse permits OkResponse, NotFoundResponse, MovedResponse {}

record OkResponse(int code, Object data) implements HttpResponse {}
record NotFoundResponse(int code, String errorCode, String message) implements HttpResponse {}
record MovedResponse(int code, String location) implements HttpResponse {}

// Animals for pattern matching
sealed interface Animal permits Dog, Cat, Bird {}

record Dog(String name, String breed) implements Animal {}
record Cat(String name, String color) implements Animal {}
record Bird(String name, double wingSpan) implements Animal {}

public class PatternMatchingDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   PATTERN MATCHING - Java 16+ to Java 21 Evolution");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateInstanceOfPatterns();
        System.out.println("\n");
        demonstrateGuardClauses();
        System.out.println("\n");
        demonstratePatternMatchingSwitch();
        System.out.println("\n");
        demonstrateRecordPatterns();
    }
    
    private static void demonstrateInstanceOfPatterns() {
        System.out.println("EXAMPLE 1: instanceof Type Patterns (Java 16+)");
        System.out.println("──────────────────────────────────────────────\n");
        
        Object[] objects = {
            "Hello String",
            42,
            3.14,
            true,
            new Dog("Buddy", "Golden Retriever")
        };
        
        System.out.println("Processing objects with type patterns:");
        for (Object obj : objects) {
            // OLD WAY (before Java 16):
            // if (obj instanceof String) {
            //     String str = (String) obj;
            //     System.out.println("String: " + str);
            // }
            
            // NEW WAY (Java 16+):
            if (obj instanceof String str) {
                System.out.println("  String: " + str + " (length: " + str.length() + ")");
            } else if (obj instanceof Integer num) {
                System.out.println("  Integer: " + num + " (even: " + (num % 2 == 0) + ")");
            } else if (obj instanceof Double d) {
                System.out.println("  Double: " + d + " (rounded: " + Math.round(d) + ")");
            } else if (obj instanceof Boolean bool) {
                System.out.println("  Boolean: " + bool);
            } else if (obj instanceof Dog dog) {
                System.out.println("  Dog: " + dog.name() + " (breed: " + dog.breed() + ")");
            }
        }
        
        System.out.println("\n✓ Type patterns: Type check + cast in one line");
    }
    
    private static void demonstrateGuardClauses() {
        System.out.println("EXAMPLE 2: Guard Clauses (Java 16+)");
        System.out.println("───────────────────────────────────\n");
        
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 4.0, 5.0),
            new Circle(2.0),
            new Rectangle(10.0, 5.0)
        };
        
        System.out.println("Filtering large shapes (area > 20):");
        for (Shape shape : shapes) {
            // Guard clause: additional condition after pattern
            if (shape instanceof Circle c && c.getArea() > 20) {
                System.out.println("  Large Circle: radius=" + c.radius() + ", area=" + String.format("%.2f", c.getArea()));
            } else if (shape instanceof Rectangle r && r.getArea() > 20) {
                System.out.println("  Large Rectangle: " + r.width() + "x" + r.height() + ", area=" + r.getArea());
            } else if (shape instanceof Triangle t && t.getArea() > 20) {
                System.out.println("  Large Triangle: sides=" + t.a() + "," + t.b() + "," + t.c() + ", area=" + String.format("%.2f", t.getArea()));
            }
        }
        
        System.out.println("\n✓ Guard clauses: Pattern + additional condition");
    }
    
    private static void demonstratePatternMatchingSwitch() {
        System.out.println("EXAMPLE 3: Pattern Matching in Switch (Java 21+)");
        System.out.println("─────────────────────────────────────────────────\n");
        
        Object[] responses = {
            new OkResponse(200, "{\n  \"id\": 123,\n  \"name\": \"John\"\n}"),
            new NotFoundResponse(404, "NOT_FOUND", "Resource not found"),
            new MovedResponse(301, "https://example.com/new-location"),
            new OkResponse(201, "Resource created"),
            new NotFoundResponse(500, "INTERNAL_ERROR", "Database connection failed"),
            "Unknown response type"
        };
        
        System.out.println("Handling HTTP responses with pattern matching:");
        for (Object response : responses) {
            // Java 21+: Pattern matching in switch statement
            String result = switch (response) {
                case OkResponse s when s.code() == 200 -> 
                    "✓ Success: " + s.data();
                case OkResponse s when s.code() == 201 -> 
                    "✓ Created: " + s.data();
                case NotFoundResponse e -> 
                    "✗ Error [" + e.code() + "]: " + e.errorCode() + " - " + e.message();
                case MovedResponse r -> 
                    "↻ Redirect: " + r.location();
                default -> 
                    "? Unknown: " + response;
            };
            
            System.out.println("  " + result);
        }
        
        System.out.println("\n✓ Pattern matching in switch: Exhaustive type checking");
    }
    
    private static void demonstrateRecordPatterns() {
        System.out.println("EXAMPLE 4: Record Patterns (Java 21+)");
        System.out.println("────────────────────────────────────\n");
        
        Animal[] animals = {
            new Dog("Buddy", "Golden Retriever"),
            new Cat("Whiskers", "Orange"),
            new Bird("Tweety", 0.5),
            new Dog("Rex", "Husky"),
            new Cat("Mittens", "Calico")
        };
        
        System.out.println("Processing animals with record patterns:");
        for (Animal animal : animals) {
            // Record patterns extract record components directly
            String info = switch (animal) {
                case Dog(String name, String breed) when breed.length() > 5 ->
                    "🐕 Large breed dog: " + name + " (" + breed + ")";
                case Dog(String name, String breed) ->
                    "🐕 Dog: " + name + " (" + breed + ")";
                case Cat(String name, String color) ->
                    "🐈 Cat: " + name + " (color: " + color + ")";
                case Bird(String name, double wingSpan) when wingSpan > 0.4 ->
                    "🦅 Large bird: " + name + " (wingSpan: " + wingSpan + "m)";
                case Bird(String name, double wingSpan) ->
                    "🐦 Bird: " + name + " (wingSpan: " + wingSpan + "m)";
            };
            
            System.out.println("  " + info);
        }
        
        System.out.println("\n✓ Record patterns: Extract and match record components");
    }
}

/*
 * ═════════════════════════════════════════════════════════════════════════════
 * PATTERN MATCHING - COMPREHENSIVE Q&A (Java 16-21)
 * ═════════════════════════════════════════════════════════════════════════════
 * 
 * Q1: What is pattern matching?
 * A: Pattern matching is a technique to check if a value has a certain structure
 *    and extract its parts if it does. It simplifies code by combining type 
 *    checking, casting, and extraction into a single operation.
 * 
 * Q2: When was pattern matching introduced?
 * A: Java 14: instanceof pattern (preview), Java 15: instanceof pattern (preview 2),
 *    Java 16: instanceof pattern (finalized), Java 17: finalized for production,
 *    Java 18-20: Pattern matching in switch (preview), Java 21: finalized
 * 
 * Q3: What are type patterns?
 * A: Type patterns check if a value matches a specific type and bind it to a
 *    variable. Before Java 16: if (obj instanceof String) { String s = (String) obj; }
 *    After Java 16+: if (obj instanceof String s) { ... }
 * 
 * Q4: What are guard clauses?
 * A: Guard clauses are conditions added to patterns using && operator.
 *    They allow additional filtering beyond type matching.
 *    Example: if (obj instanceof String str && str.length() > 5) { ... }
 * 
 * Q5: How does pattern matching in switch work (Java 21)?
 * A: Java 21 adds pattern matching to switch statements, allowing exhaustive
 *    type-based dispatch without explicit casting.
 *    switch (shape) {
 *        case Circle c -> ...
 *        case Rectangle r -> ...
 *    }
 * 
 * Q6: What are record patterns?
 * A: Record patterns (Java 21) allow extracting record components directly
 *    in patterns, without needing to call accessors.
 *    if (obj instanceof Point(int x, int y) && x > 0) { ... }
 * 
 * Q7: How do sealed classes improve pattern matching?
 * A: When using sealed classes with pattern matching, the compiler can verify
 *    that all cases are handled. This makes code safer and enables exhaustive
 *    checking.
 * 
 * Q8: Can you combine record patterns with sealed classes?
 * A: Yes! This is the most powerful combination for type-safe dispatch:
 *    sealed interface Result permits Ok, Err {}
 *    record Ok<T>(T value) implements Result {}
 *    switch (result) {
 *        case Ok(var value) -> System.out.println("Success: " + value);
 *        case Err(var ex) -> System.out.println("Error");
 *    }
 * 
 * Q9: What is exhaustiveness checking?
 * A: Exhaustiveness checking ensures that all possible types are handled
 *    in a pattern match. With sealed types, the compiler can verify this.
 *    sealed interface Animal permits Dog, Cat {}
 *    var output = switch (animal) {
 *        case Dog d -> "Woof";
 *        case Cat c -> "Meow";
 *    };
 * 
 * Q10: How does pattern matching improve performance?
 * A: Pattern matching doesn't directly improve performance, but it enables
 *    better optimizations by making intent clear. Sealed hierarchies with
 *    pattern matching allow the JIT compiler to specialize code better.
 * 
 * ═════════════════════════════════════════════════════════════════════════════
 */
