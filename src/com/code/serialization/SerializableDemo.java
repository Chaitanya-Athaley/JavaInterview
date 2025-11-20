package com.code.serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SERIALIZABLE INTERFACE - Java Serialization
 * 
 * Purpose: Enable objects to be converted to byte streams and back
 * Use Cases: Saving objects to files, sending over network, caching
 * 
 * Key Features:
 * • Objects implement Serializable interface (marker interface)
 * • Automatic serialization of all non-transient, non-static fields
 * • SerialVersionUID for version control
 * • transient keyword to exclude fields from serialization
 * • static final long serialVersionUID for compatibility
 */

// Simple Serializable class
class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private String email;
    private transient String password; // Not serialized
    
    public Person(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}

// Complex Serializable class with collections
class Employee implements Serializable {
    private static final long serialVersionUID = 2L;
    
    private String employeeId;
    private String name;
    private double salary;
    private List<String> skills; // Collections are serializable too
    private Address address;
    
    public Employee(String employeeId, String name, double salary, Address address) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
        this.address = address;
        this.skills = new ArrayList<>();
    }
    
    public void addSkill(String skill) {
        skills.add(skill);
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId='" + employeeId + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", skills=" + skills +
                ", address=" + address +
                '}';
    }
    
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public List<String> getSkills() { return skills; }
    public Address getAddress() { return address; }
}

// Address class (referenced by Employee)
class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String street;
    private String city;
    private String zipCode;
    private String country;
    
    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
    
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

public class SerializableDemo {
    
    private static final String PERSON_FILE = "person.dat";
    private static final String EMPLOYEE_FILE = "employee.dat";
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("           SERIALIZABLE - Java Serialization");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateSimpleSerialization();
        System.out.println("\n");
        demonstrateComplexSerialization();
        System.out.println("\n");
        demonstrateTransientFields();
        System.out.println("\n");
        demonstrateDeserialization();
    }
    
    private static void demonstrateSimpleSerialization() {
        System.out.println("EXAMPLE 1: Simple Object Serialization");
        System.out.println("──────────────────────────────────────\n");
        
        Person person = new Person("Alice Johnson", 30, "alice@example.com", "secret123");
        
        System.out.println("Original Person object:");
        System.out.println("  " + person);
        
        try {
            // Serialize to file
            System.out.println("\nSerializing to file: " + PERSON_FILE);
            FileOutputStream fos = new FileOutputStream(PERSON_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(person);
            oos.close();
            fos.close();
            System.out.println("  ✓ Serialization successful");
            
            // Show file size
            File file = new File(PERSON_FILE);
            System.out.println("  File size: " + file.length() + " bytes");
            
        } catch (IOException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateComplexSerialization() {
        System.out.println("EXAMPLE 2: Complex Object with Collections");
        System.out.println("────────────────────────────────────────\n");
        
        Address address = new Address("123 Tech Street", "San Francisco", "94105", "USA");
        Employee employee = new Employee("EMP001", "Bob Smith", 95000, address);
        
        employee.addSkill("Java");
        employee.addSkill("Spring Boot");
        employee.addSkill("Microservices");
        employee.addSkill("Docker");
        
        System.out.println("Original Employee object:");
        System.out.println("  " + employee);
        
        try {
            // Serialize complex object
            System.out.println("\nSerializing to file: " + EMPLOYEE_FILE);
            FileOutputStream fos = new FileOutputStream(EMPLOYEE_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employee);
            oos.close();
            fos.close();
            System.out.println("  ✓ Serialization successful");
            
            File file = new File(EMPLOYEE_FILE);
            System.out.println("  File size: " + file.length() + " bytes");
            System.out.println("  ✓ Complex objects with collections serialized!");
            
        } catch (IOException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateTransientFields() {
        System.out.println("EXAMPLE 3: Transient Fields (Not Serialized)");
        System.out.println("────────────────────────────────────────────\n");
        
        Person person = new Person("Charlie Brown", 28, "charlie@example.com", "myPassword!");
        
        System.out.println("Original Person:");
        System.out.println("  Name: " + person.getName());
        System.out.println("  Email: " + person.getEmail());
        System.out.println("  Password (transient): " + person.getPassword());
        
        System.out.println("\nNOTE: The 'password' field is marked as 'transient'");
        System.out.println("  • It will NOT be serialized to the file");
        System.out.println("  • When deserialized, password will be null");
        
        System.out.println("\n✓ Use transient for sensitive data (passwords, tokens, etc.)");
        System.out.println("✓ Use transient for expensive-to-compute fields");
    }
    
    private static void demonstrateDeserialization() {
        System.out.println("EXAMPLE 4: Deserialization (Reading Back Objects)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        try {
            // Deserialize Person
            System.out.println("Deserializing Person from: " + PERSON_FILE);
            FileInputStream fis = new FileInputStream(PERSON_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Person deserializedPerson = (Person) ois.readObject();
            ois.close();
            fis.close();
            
            System.out.println("  Deserialized Person:");
            System.out.println("    " + deserializedPerson);
            System.out.println("    Note: password is " + (deserializedPerson.getPassword() == null ? "NULL (transient)" : "preserved"));
            
            // Deserialize Employee
            System.out.println("\nDeserializing Employee from: " + EMPLOYEE_FILE);
            fis = new FileInputStream(EMPLOYEE_FILE);
            ois = new ObjectInputStream(fis);
            Employee deserializedEmployee = (Employee) ois.readObject();
            ois.close();
            fis.close();
            
            System.out.println("  Deserialized Employee:");
            System.out.println("    ID: " + deserializedEmployee.getEmployeeId());
            System.out.println("    Name: " + deserializedEmployee.getName());
            System.out.println("    Salary: $" + deserializedEmployee.getSalary());
            System.out.println("    Skills: " + deserializedEmployee.getSkills());
            System.out.println("    Address: " + deserializedEmployee.getAddress());
            
            System.out.println("\n✓ All data successfully restored from serialized form!");
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
}

/*
 * ═════════════════════════════════════════════════════════════════════════════
 * SERIALIZABLE - COMPREHENSIVE Q&A
 * ═════════════════════════════════════════════════════════════════════════════
 * 
 * Q1: What is Serializable interface?
 * A: Serializable is a marker interface (no methods) that indicates a class
 *    can be converted to a byte stream (serialized) and reconstructed from
 *    bytes (deserialized). It's used for saving objects, networking, caching.
 * 
 * Q2: What is SerialVersionUID?
 * A: SerialVersionUID is a version identifier for a serializable class.
 *    It's used to verify that a serialized object can be deserialized
 *    correctly by the same class definition.
 *    private static final long serialVersionUID = 1L;
 *    If versions don't match, InvalidClassException is thrown.
 * 
 * Q3: What fields are serialized by default?
 * A: All non-static, non-transient instance variables are serialized.
 *    • static fields: NOT serialized (class-level, not instance)
 *    • transient fields: NOT serialized (marked with transient keyword)
 *    • Serializable objects: Their entire state is serialized recursively
 * 
 * Q4: What is the transient keyword?
 * A: Transient keyword marks a field that should NOT be serialized.
 *    Use cases:
 *    • Sensitive data: passwords, API keys, tokens
 *    • Expensive fields: database connections, threads
 *    • Computed fields: can recalculate after deserialization
 *    Example: private transient String password;
 * 
 * Q5: How do you serialize an object?
 * A: Use ObjectOutputStream to write objects:
 *    FileOutputStream fos = new FileOutputStream("file.dat");
 *    ObjectOutputStream oos = new ObjectOutputStream(fos);
 *    oos.writeObject(myObject);
 *    oos.close();
 * 
 * Q6: How do you deserialize an object?
 * A: Use ObjectInputStream to read objects:
 *    FileInputStream fis = new FileInputStream("file.dat");
 *    ObjectInputStream ois = new ObjectInputStream(fis);
 *    MyClass obj = (MyClass) ois.readObject();
 *    ois.close();
 * 
 * Q7: What exceptions can be thrown during serialization?
 * A: • IOException: File operations fail, stream issues
 *    • NotSerializableException: Object doesn't implement Serializable
 *    • InvalidClassException: serialVersionUID mismatch
 *    • ClassNotFoundException: During deserialization, class not found
 * 
 * Q8: Can you customize serialization?
 * A: Yes, using writeObject() and readObject() methods:
 *    private void writeObject(ObjectOutputStream oos) throws IOException {
 *        // Custom serialization logic
 *    }
 *    private void readObject(ObjectInputStream ois) throws IOException {
 *        // Custom deserialization logic
 *    }
 * 
 * Q9: What's the difference between Serializable and Externalizable?
 * A: • Serializable: Automatic, marker interface, less control
 *    • Externalizable: Manual, implement writeExternal/readExternal, full control
 *    Serializable is easier, Externalizable gives more control over format.
 * 
 * Q10: What are best practices for serialization?
 * A: • Always define serialVersionUID
 *    • Use transient for sensitive/computed fields
 *    • Keep serializable classes backward compatible
 *    • Avoid serializing large, expensive objects
 *    • Use serialization for persistence, not for API communication (use JSON)
 * 
 * ═════════════════════════════════════════════════════════════════════════════
 */
