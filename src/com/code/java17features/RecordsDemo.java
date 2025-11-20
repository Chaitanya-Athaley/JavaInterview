package com.code.java17features;

/**
 * RECORDS - Java 16+ Feature (Finalized in Java 21)
 * 
 * Purpose: Immutable data carrier class with auto-generated boilerplate
 * Introduced: Java 14 (preview), Java 15 (preview 2), Java 16 (finalized)
 * 
 * What Records Generate Automatically:
 * 1. Constructor (all-args)
 * 2. Getters for all fields (using field name as method name)
 * 3. equals() method
 * 4. hashCode() method
 * 5. toString() method
 * 6. All fields are FINAL and PRIVATE
 * 
 * Syntax: record ClassName(Type field1, Type field2, ...) { }
 */

// EXAMPLE 1: Basic Record - Point
record Point(int x, int y) {}

// EXAMPLE 2: Record with Validation - Person
record Person(String name, int age) {
    public Person {
        // Compact constructor - validates data
        if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
    }
}

// EXAMPLE 3: Record with Custom Methods - Employee
record Employee(String name, String department, double salary) {
    // Custom method
    public void displayInfo() {
        System.out.println("Employee: " + name + ", Dept: " + department + ", Salary: $" + salary);
    }
    
    // Calculated method
    public double getAnnualBonus() {
        return salary * 0.1;
    }
}

// EXAMPLE 4: Generic Record - Pair
record Pair<T, U>(T first, U second) {
    public void printPair() {
        System.out.println("Pair: [" + first + ", " + second + "]");
    }
}

// EXAMPLE 5: Nested Records - Address and User
record Address(String street, String city, String zipCode) {}

record User(String username, int age, Address address) {
    public String getFullAddress() {
        return username + " lives at " + address.street() + ", " + address.city() + " " + address.zipCode();
    }
}

// EXAMPLE 6: API Response Record
record ApiResponse(int statusCode, String message, Object data) {
    public boolean isSuccess() {
        return statusCode >= 200 && statusCode < 300;
    }
}

public class RecordsDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("         RECORDS - Java 16+ Feature");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateBasicRecords();
        System.out.println("\n");
        demonstrateRecordsWithValidation();
        System.out.println("\n");
        demonstrateRecordsWithMethods();
        System.out.println("\n");
        demonstrateGenericRecords();
        System.out.println("\n");
        demonstrateNestedRecords();
    }
    
    private static void demonstrateBasicRecords() {
        System.out.println("EXAMPLE 1: Basic Records");
        System.out.println("──────────────────────\n");
        
        // Create and use Point record
        Point p1 = new Point(10, 20);
        Point p2 = new Point(10, 20);
        Point p3 = new Point(5, 15);
        
        System.out.println("Point 1: " + p1);
        System.out.println("Point 2: " + p2);
        System.out.println("Point 3: " + p3);
        
        // Records provide equals() automatically
        System.out.println("\nEquality checks:");
        System.out.println("p1.equals(p2): " + p1.equals(p2) + " (same coordinates)");
        System.out.println("p1.equals(p3): " + p1.equals(p3) + " (different coordinates)");
        
        // Getters use field name (no 'get' prefix)
        System.out.println("\nAccessing fields:");
        System.out.println("p1.x(): " + p1.x());
        System.out.println("p1.y(): " + p1.y());
        
        // Records provide hashCode() automatically
        System.out.println("\nHash codes:");
        System.out.println("p1.hashCode(): " + p1.hashCode());
        System.out.println("p2.hashCode(): " + p2.hashCode());
        System.out.println("Same hash: " + (p1.hashCode() == p2.hashCode()));
        
        System.out.println("\n✓ Records are immutable - cannot change fields after creation");
    }
    
    private static void demonstrateRecordsWithValidation() {
        System.out.println("EXAMPLE 2: Records with Validation");
        System.out.println("──────────────────────────────────\n");
        
        // Valid person
        Person p1 = new Person("Alice", 30);
        System.out.println("Valid Person: " + p1);
        System.out.println("  Name: " + p1.name());
        System.out.println("  Age: " + p1.age());
        
        Person p2 = new Person("Bob", 25);
        System.out.println("\nValid Person: " + p2);
        
        // Try invalid person (age < 0)
        System.out.println("\nTrying to create person with negative age:");
        try {
            Person invalid = new Person("Charlie", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Caught error: " + e.getMessage());
        }
        
        // Try invalid person (empty name)
        System.out.println("\nTrying to create person with empty name:");
        try {
            Person invalid = new Person("", 25);
        } catch (IllegalArgumentException e) {
            System.out.println("  ✗ Caught error: " + e.getMessage());
        }
        
        System.out.println("\n✓ Compact constructor validates data automatically");
    }
    
    private static void demonstrateRecordsWithMethods() {
        System.out.println("EXAMPLE 3: Records with Custom Methods");
        System.out.println("──────────────────────────────────────\n");
        
        // Create employees
        Employee emp1 = new Employee("Alice Johnson", "Engineering", 150000);
        Employee emp2 = new Employee("Bob Smith", "Sales", 75000);
        Employee emp3 = new Employee("Carol White", "Management", 120000);
        
        System.out.println("Employees:");
        emp1.displayInfo();
        emp2.displayInfo();
        emp3.displayInfo();
        
        System.out.println("\nAnnual bonuses:");
        System.out.println("Alice bonus: $" + String.format("%.2f", emp1.getAnnualBonus()));
        System.out.println("Bob bonus: $" + String.format("%.2f", emp2.getAnnualBonus()));
        System.out.println("Carol bonus: $" + String.format("%.2f", emp3.getAnnualBonus()));
        
        System.out.println("\n✓ Records can have custom methods while remaining data-focused");
    }
    
    private static void demonstrateGenericRecords() {
        System.out.println("EXAMPLE 4: Generic Records");
        System.out.println("─────────────────────────\n");
        
        // Pair of String and Integer
        Pair<String, Integer> pair1 = new Pair<>("Count", 42);
        pair1.printPair();
        System.out.println("  First: " + pair1.first());
        System.out.println("  Second: " + pair1.second());
        
        // Pair of Double and String
        Pair<Double, String> pair2 = new Pair<>(3.14, "Pi");
        pair2.printPair();
        
        // Pair of Pair (nested generics)
        Pair<Integer, String> innerPair = new Pair<>(100, "Hundred");
        Pair<Pair<Integer, String>, Double> complexPair = 
            new Pair<>(innerPair, 99.99);
        System.out.println("\nNested pair: " + complexPair);
        
        System.out.println("\n✓ Records support generics and type parameters");
    }
    
    private static void demonstrateNestedRecords() {
        System.out.println("EXAMPLE 5: Nested Records");
        System.out.println("──────────────────────────\n");
        
        // Create address
        Address addr1 = new Address("123 Main St", "San Francisco", "94105");
        System.out.println("Address: " + addr1);
        
        // Create user with address
        User user1 = new User("alice_wonder", 28, addr1);
        System.out.println("\nUser: " + user1);
        System.out.println(user1.getFullAddress());
        
        // Another user
        Address addr2 = new Address("456 Oak Ave", "New York", "10001");
        User user2 = new User("bob_builder", 35, addr2);
        System.out.println("\nUser: " + user2);
        System.out.println(user2.getFullAddress());
        
        // Accessing nested record fields
        System.out.println("\nAccessing nested fields:");
        System.out.println("user1 city: " + user1.address().city());
        System.out.println("user1 zip: " + user1.address().zipCode());
        
        System.out.println("\n✓ Records can contain other records");
    }
}

/*

════════════════════════════════════════════════════════════════════════════
RECORDS - INTERVIEW Q&A
════════════════════════════════════════════════════════════════════════════

Q1: What is a record?
─────────────────────
A: A record is a special class designed to be a simple, immutable carrier of 
   data. It's like a class but much more concise - you only specify the fields,
   and the compiler automatically generates constructor, accessors, equals(),
   hashCode(), and toString().

   Syntax: record Person(String name, int age) { }


Q2: What boilerplate does a record generate?
──────────────────────────────────────────────
A: For each record, the compiler auto-generates:
   1. Constructor - takes all fields as parameters
   2. Accessor methods - named after field (e.g., name(), age())
   3. equals() - compares all field values
   4. hashCode() - based on all field values
   5. toString() - shows all fields
   
   Without records, you'd write ~30 lines. With records: 1 line!


Q3: Are records immutable?
────────────────────────────
A: Yes, absolutely. All record fields are implicitly:
   • private
   • final
   
   You cannot change values after creation. No setters are provided.
   This makes records thread-safe by default.


Q4: What is a compact constructor?
───────────────────────────────────
A: A compact constructor is a special syntax for validation in records.
   Instead of explicitly declaring the constructor parameter list,
   you just validate the fields:
   
   record Person(String name, int age) {
       public Person {  // Compact constructor
           if (age < 0) throw new IllegalArgumentException("Invalid age");
       }
   }
   
   The compiler automatically adds all parameters.


Q5: What are record components?
────────────────────────────────
A: Record components are the fields declared in the record header:
   record Person(String name, int age) { }
   
   Here, "String name" and "int age" are the components.
   They automatically become:
   • Constructor parameters
   • Accessor methods (name(), age())
   • Fields for equals/hashCode/toString


Q6: Can records extend other classes?
──────────────────────────────────────
A: No. Records implicitly extend java.lang.Record and cannot extend other classes.
   This is by design - records are meant to be simple data carriers.
   
   You can implement interfaces though:
   record Person(String name) implements Serializable { }


Q7: Can records implement interfaces?
──────────────────────────────────────
A: Yes! Records can implement interfaces:
   
   record Person(String name) implements Comparable<Person> {
       @Override
       public int compareTo(Person other) {
           return name.compareTo(other.name());
       }
   }


Q8: What are ideal use cases for records?
──────────────────────────────────────────
A: ✓ Data Transfer Objects (DTOs)
  ✓ API request/response objects
  ✓ Configuration objects
  ✓ Immutable value objects
  ✓ Method return values (multiple values)
  ✓ HashMap/stream collectors
  ✓ Any simple data container


Q9: Can you add methods to records?
────────────────────────────────────
A: Yes! Records can have custom methods:
   
   record Employee(String name, double salary) {
       public double getAnnualBonus() {
           return salary * 0.1;
       }
   }
   
   But the focus remains on data - records are not meant for complex logic.


Q10: How do records compare to classes?
─────────────────────────────────────────
A: Classes vs Records:
   
   Class:
   • Mutable (setters common)
   • 30+ lines for basic data class
   • Flexible but verbose
   
   Record:
   • Immutable by design
   • 1 line for basic data
   • Concise and intentional
   
   Use records for data carriers, classes for other purposes.


════════════════════════════════════════════════════════════════════════════

*/
