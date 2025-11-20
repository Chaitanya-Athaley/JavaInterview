package com.code.java17features;

/**
 * SEALED RECORDS COMBINATION - Java 17 Pattern
 * 
 * Purpose: Demonstrates how sealed classes + records work together
 * This is a pattern used heavily in Java 17+ for type-safe data structures
 * 
 * Note: Sealed classes syntax not supported in Java 15, but this demo
 *       shows the equivalent pattern using regular classes
 */

public class SealedRecordsCombinationDemo {
    
    // ════════════════════════════════════════════════════════════════════════
    // SEALED RECORDS PATTERN (Simulated in Java 15)
    // ════════════════════════════════════════════════════════════════════════
    
    // In Java 17+, you would write:
    // sealed interface Shape permits Circle, Rectangle, Triangle { }
    // record Circle(double radius) implements Shape { }
    // record Rectangle(double width, double height) implements Shape { }
    // record Triangle(double a, double b, double c) implements Shape { }
    
    // For Java 15 compatibility, we simulate with regular classes:
    
    interface Shape {
        double getArea();
        String describe();
    }
    
    // Sealed concept: Only these classes extend Shape
    static class Circle implements Shape {
        final double radius;
        
        Circle(double radius) {
            this.radius = radius;
        }
        
        @Override
        public double getArea() {
            return Math.PI * radius * radius;
        }
        
        @Override
        public String describe() {
            return "Circle(radius=" + radius + ")";
        }
        
        @Override
        public String toString() {
            return describe();
        }
    }
    
    static class Rectangle implements Shape {
        final double width;
        final double height;
        
        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }
        
        @Override
        public double getArea() {
            return width * height;
        }
        
        @Override
        public String describe() {
            return "Rectangle(width=" + width + ", height=" + height + ")";
        }
        
        @Override
        public String toString() {
            return describe();
        }
    }
    
    static class Triangle implements Shape {
        final double a;
        final double b;
        final double c;
        
        Triangle(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
        @Override
        public double getArea() {
            // Heron's formula
            double s = (a + b + c) / 2.0;
            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }
        
        @Override
        public String describe() {
            return "Triangle(a=" + a + ", b=" + b + ", c=" + c + ")";
        }
        
        @Override
        public String toString() {
            return describe();
        }
    }
    
    // HTTP Response type hierarchy
    interface ApiResponse {
        int getStatusCode();
        String getBody();
    }
    
    static class SuccessResponse implements ApiResponse {
        final int statusCode;
        final String data;
        
        SuccessResponse(int statusCode, String data) {
            this.statusCode = statusCode;
            this.data = data;
        }
        
        @Override
        public int getStatusCode() {
            return statusCode;
        }
        
        @Override
        public String getBody() {
            return data;
        }
        
        @Override
        public String toString() {
            return "SuccessResponse(code=" + statusCode + ", data=" + data + ")";
        }
    }
    
    static class ErrorResponse implements ApiResponse {
        final int statusCode;
        final String errorMessage;
        final String errorCode;
        
        ErrorResponse(int statusCode, String errorCode, String errorMessage) {
            this.statusCode = statusCode;
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }
        
        @Override
        public int getStatusCode() {
            return statusCode;
        }
        
        @Override
        public String getBody() {
            return errorCode + ": " + errorMessage;
        }
        
        @Override
        public String toString() {
            return "ErrorResponse(code=" + statusCode + ", errorCode=" + 
                   errorCode + ", message=" + errorMessage + ")";
        }
    }
    
    static class RedirectResponse implements ApiResponse {
        final int statusCode;
        final String location;
        
        RedirectResponse(int statusCode, String location) {
            this.statusCode = statusCode;
            this.location = location;
        }
        
        @Override
        public int getStatusCode() {
            return statusCode;
        }
        
        @Override
        public String getBody() {
            return location;
        }
        
        @Override
        public String toString() {
            return "RedirectResponse(code=" + statusCode + ", location=" + 
                   location + ")";
        }
    }
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("   SEALED RECORDS COMBINATION - Java 17 Pattern");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateShapesExample();
        System.out.println("\n");
        demonstrateApiResponseExample();
        System.out.println("\n");
        explainBenefits();
    }
    
    private static void demonstrateShapesExample() {
        System.out.println("EXAMPLE 1: Sealed Shapes (Type-Safe Hierarchy)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 4.0, 5.0),
            new Circle(3.0),
            new Rectangle(10.0, 5.0)
        };
        
        System.out.println("Processing shapes:");
        double totalArea = 0;
        
        for (Shape shape : shapes) {
            double area = shape.getArea();
            System.out.println("  " + shape.describe() + 
                             " → Area: " + String.format("%.2f", area));
            totalArea += area;
        }
        
        System.out.println("\nTotal area: " + String.format("%.2f", totalArea));
        System.out.println("\n✓ Sealed class pattern ensures only known Shape types exist");
    }
    
    private static void demonstrateApiResponseExample() {
        System.out.println("EXAMPLE 2: API Response Handling (Real-World Pattern)");
        System.out.println("────────────────────────────────────────────────────────\n");
        
        ApiResponse[] responses = {
            new SuccessResponse(200, "{\"name\": \"John\", \"age\": 30}"),
            new ErrorResponse(404, "NOT_FOUND", "Resource not found"),
            new RedirectResponse(301, "https://example.com/new-location"),
            new SuccessResponse(201, "{\"id\": 12345}"),
            new ErrorResponse(500, "INTERNAL_ERROR", "Database connection failed")
        };
        
        System.out.println("Handling API responses:");
        for (ApiResponse response : responses) {
            handleResponse(response);
        }
        
        System.out.println("\n✓ Sealed pattern ensures all response types are handled");
    }
    
    private static void handleResponse(ApiResponse response) {
        if (response instanceof SuccessResponse) {
            SuccessResponse success = (SuccessResponse) response;
            System.out.println("  ✓ Success [" + success.statusCode + "]: " + 
                             success.data);
        } else if (response instanceof ErrorResponse) {
            ErrorResponse error = (ErrorResponse) response;
            System.out.println("  ✗ Error [" + error.statusCode + "]: " + 
                             error.errorCode + " - " + error.errorMessage);
        } else if (response instanceof RedirectResponse) {
            RedirectResponse redirect = (RedirectResponse) response;
            System.out.println("  ↻ Redirect [" + redirect.statusCode + "]: " + 
                             redirect.location);
        }
    }
    
    private static void explainBenefits() {
        System.out.println("EXAMPLE 3: Benefits of Sealed Records Pattern");
        System.out.println("──────────────────────────────────────────────\n");
        
        System.out.println("✓ TYPE SAFETY:");
        System.out.println("  • Compiler knows all possible implementations");
        System.out.println("  • No subclass surprises");
        System.out.println("  • Exhaustiveness checking in pattern matching\n");
        
        System.out.println("✓ IMMUTABILITY (Records in Java 17+):");
        System.out.println("  • All fields final");
        System.out.println("  • No mutable state");
        System.out.println("  • Boilerplate automatically generated\n");
        
        System.out.println("✓ PATTERN MATCHING (Java 16+):");
        System.out.println("  • Type check and cast in one operation");
        System.out.println("  • Clean if-else chains");
        System.out.println("  • Clear business logic\n");
        
        System.out.println("✓ CODE CLARITY:");
        System.out.println("  • Intent is explicit: only these types allowed");
        System.out.println("  • Documentation through code structure");
        System.out.println("  • Easier to maintain and reason about\n");
        
        System.out.println("📊 SEALED RECORDS PATTERN OVERVIEW");
        System.out.println("──────────────────────────────────");
        System.out.println("Sealed Interface/Class");
        System.out.println("    ↓");
        System.out.println("Permits only: Record1, Record2, Record3");
        System.out.println("    ↓");
        System.out.println("Pattern Matching");
        System.out.println("    ↓");
        System.out.println("Type-safe handling\n");
        
        System.out.println("Java 17+ Syntax (in sealed permits):");
        System.out.println("  sealed interface Response permits Success, Error, Redirect { }");
        System.out.println("  record Success(int code, String data) implements Response { }");
        System.out.println("  record Error(int code, String msg) implements Response { }");
        System.out.println("  record Redirect(int code, String url) implements Response { }");
    }
}

// ════════════════════════════════════════════════════════════════════════════
// SEALED RECORDS - INTERVIEW Q&A
// ════════════════════════════════════════════════════════════════════════════
//
// Q1: What is the sealed records pattern?
// ────────────────────────────────────────
// A: A design pattern combining sealed classes (control which classes extend)
//    with records (immutable data carriers). This ensures:
//    1. Only known types can extend the sealed class
//    2. Each type is immutable (records)
//    3. Pattern matching can be exhaustive
//
//
// Q2: Why use sealed classes with records?
// ─────────────────────────────────────────
// A: • Type Safety: Compiler knows all possible implementations
//   • Immutability: All data is final
//   • Exhaustiveness: Pattern matching ensures all cases handled
//   • Performance: JVM can optimize better with fixed hierarchy
//   • Clarity: Intent is explicit in code
//
//
// Q3: When should you use this pattern?
// ──────────────────────────────────────
// A: Use when you have:
//   ✓ Fixed set of related types (e.g., API responses, Result types)
//   ✓ Immutable data structures
//   ✓ Type hierarchies that shouldn't be extended externally
//   ✓ Need for pattern matching exhaustiveness
//   ✓ ADT (Algebraic Data Type) patterns
//
//
// Q4: How does this pattern help with pattern matching?
// ──────────────────────────────────────────────────────
// A: Since sealed classes limit implementations, the compiler can verify
//    that all cases are handled in a pattern match.
//
//    Example (Java 21+):
//    switch (response) {
//        case Success s -> { }
//        case Error e -> { }
//        case Redirect r -> { }
//        // Compiler verifies all cases covered - no default needed
//    }
//
//
// Q5: What's the difference between sealed classes and final classes?
// ────────────────────────────────────────────────────────────────────
// A: • Final: No subclasses at all
//   • Sealed: Limited list of allowed subclasses
//
//    sealed class Foo permits Bar, Baz { }  // Only Bar and Baz extend
//    final class Foo { }                      // Nothing extends


// Q6: Can records extend sealed classes?
// ───────────────────────────────────────
// A: Yes! In Java 17+:
//
//    sealed interface Result permits Success, Failure { }
//    record Success(String data) implements Result { }
//    record Failure(String error) implements Result { }
//
//    This is a core pattern in modern Java.
//
//
// Q7: What about sealed records that extend other records?
// ──────────────────────────────────────────────────────────
// A: Records cannot extend records directly. But sealed interfaces with
//    record implementations is the standard approach:
//
//    sealed interface Pet permits Dog, Cat { }
//    record Dog(String name, String breed) implements Pet { }
//    record Cat(String name, String color) implements Pet { }
//
//
// Q8: How does this pattern compare to enums?
// ─────────────────────────────────────────────
// A: Enums:
//    • Fixed set of constant instances
//    • Lightweight
//    • No data per instance (without anonymous classes)
//
//    Sealed Records:
//    • Fixed set of types
//    • Each instance can carry different data
//    • More flexible, more memory
//
//    Use enums for simple constants, sealed records for rich data types.
//
//
// Q9: What are real-world examples of this pattern?
// ──────────────────────────────────────────────────
// A: ✓ API Responses: Success, Error, Redirect
//   ✓ Result Types: Ok(data), Err(exception)
//   ✓ UI Events: Click, Scroll, KeyPress
//   ✓ Game States: Menu, Playing, Paused, GameOver
//   ✓ Parser Results: Valid(tree), Invalid(errors)
//   ✓ Authentication: LoggedIn(user), LoggedOut, SessionExpired
//
//
// Q10: How would this be implemented in Java 15?
// ──────────────────────────────────────────────
// A: Since sealed classes aren't available in Java 15, you use:
//    ✓ Regular classes/records with documentation
//    ✓ Package-private constructors
//    ✓ Factory methods to control creation
//    ✓ Javadoc noting "this class should not be extended"
//    ✓ Pattern matching on instanceof (Java 16+)
//
// ════════════════════════════════════════════════════════════════════════════
