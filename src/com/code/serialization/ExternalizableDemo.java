package com.code.serialization;

import java.io.*;

/**
 * EXTERNALIZABLE INTERFACE - Java Serialization Control
 * 
 * Purpose: Manual control over serialization/deserialization process
 * vs Serializable: Automatic serialization with less control
 * 
 * Key Features:
 * • Must implement writeExternal() and readExternal() methods
 * • Full control over what gets serialized and how
 * • Can choose custom formats (binary, compressed, etc.)
 * • Must implement public no-arg constructor for deserialization
 * • More efficient for large objects with selective serialization
 */

// Externalizable Person class with manual control
class ExternalizablePerson implements Externalizable {
    private String name;
    private int age;
    private String email;
    private transient String password; // Won't be serialized anyway
    
    // REQUIRED: Public no-arg constructor for deserialization
    public ExternalizablePerson() {
    }
    
    public ExternalizablePerson(String name, int age, String email, String password) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Manually write only the fields we want to serialize
        out.writeObject(name);
        out.writeInt(age);
        out.writeObject(email);
        // password is NOT written - sensitive data
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // Manually read in the same order as written
        name = (String) in.readObject();
        age = in.readInt();
        email = (String) in.readObject();
        // password remains uninitialized (null)
    }
    
    @Override
    public String toString() {
        return "ExternalizablePerson{" +
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

// Complex Externalizable class with selective serialization
class ExternalizableStudent implements Externalizable {
    private String studentId;
    private String name;
    private double gpa;
    private String[] courses; // Only serialize non-zero courses
    private transient int courseCount;
    
    // REQUIRED: Public no-arg constructor
    public ExternalizableStudent() {
        this.courses = new String[10];
    }
    
    public ExternalizableStudent(String studentId, String name, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
        this.courses = new String[10];
        this.courseCount = 0;
    }
    
    public void addCourse(String course) {
        if (courseCount < courses.length) {
            courses[courseCount++] = course;
        }
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Write basic info
        out.writeObject(studentId);
        out.writeObject(name);
        out.writeDouble(gpa);
        
        // Only write the actual courses (not the entire array)
        out.writeInt(courseCount);
        for (int i = 0; i < courseCount; i++) {
            out.writeObject(courses[i]);
        }
        
        System.out.println("  [Externalizing] Wrote " + courseCount + " courses (optimized)");
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        // Read basic info
        studentId = (String) in.readObject();
        name = (String) in.readObject();
        gpa = in.readDouble();
        
        // Read only the actual courses
        courseCount = in.readInt();
        courses = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            courses[i] = (String) in.readObject();
        }
        
        System.out.println("  [Externalizing] Read " + courseCount + " courses");
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExternalizableStudent{")
                .append("studentId='").append(studentId).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", gpa=").append(gpa)
                .append(", courses=[");
        for (int i = 0; i < courseCount; i++) {
            if (i > 0) sb.append(", ");
            sb.append(courses[i]);
        }
        sb.append("]}");
        return sb.toString();
    }
    
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public double getGpa() { return gpa; }
    public String[] getCourses() { return courses; }
    public int getCourseCount() { return courseCount; }
}

public class ExternalizableDemo {
    
    private static final String PERSON_FILE = "ext_person.dat";
    private static final String STUDENT_FILE = "ext_student.dat";
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("        EXTERNALIZABLE - Manual Serialization Control");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateSimpleExternalization();
        System.out.println("\n");
        demonstrateSelectiveExternalization();
        System.out.println("\n");
        demonstrateExternalizationVsSerialization();
        System.out.println("\n");
        demonstrateDeserialization();
    }
    
    private static void demonstrateSimpleExternalization() {
        System.out.println("EXAMPLE 1: Simple Externalizable Class");
        System.out.println("──────────────────────────────────────\n");
        
        ExternalizablePerson person = new ExternalizablePerson(
            "Diana Prince", 35, "diana@example.com", "secret456"
        );
        
        System.out.println("Original Person:");
        System.out.println("  " + person);
        System.out.println("  Password: " + person.getPassword());
        
        try {
            System.out.println("\nExternalizing to: " + PERSON_FILE);
            FileOutputStream fos = new FileOutputStream(PERSON_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(person);
            oos.close();
            fos.close();
            
            System.out.println("  ✓ Externalizable object written");
            File file = new File(PERSON_FILE);
            System.out.println("  File size: " + file.length() + " bytes");
            
        } catch (IOException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateSelectiveExternalization() {
        System.out.println("EXAMPLE 2: Selective Externalization (Optimized)");
        System.out.println("────────────────────────────────────────────────\n");
        
        ExternalizableStudent student = new ExternalizableStudent("STU001", "Eve Wilson", 3.8);
        student.addCourse("Java Programming");
        student.addCourse("Data Structures");
        student.addCourse("Algorithms");
        student.addCourse("Database Design");
        student.addCourse("Web Development");
        
        System.out.println("Original Student:");
        System.out.println("  " + student);
        System.out.println("  Array size: 10, but only " + student.getCourseCount() + " courses filled");
        
        try {
            System.out.println("\nExternalizing to: " + STUDENT_FILE);
            FileOutputStream fos = new FileOutputStream(STUDENT_FILE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(student);
            oos.close();
            fos.close();
            
            System.out.println("  ✓ Only " + student.getCourseCount() + " courses serialized (not full array)");
            File file = new File(STUDENT_FILE);
            System.out.println("  File size: " + file.length() + " bytes");
            System.out.println("  ✓ Space optimized by not serializing empty array slots!");
            
        } catch (IOException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
    
    private static void demonstrateExternalizationVsSerialization() {
        System.out.println("EXAMPLE 3: Externalizable vs Serializable Comparison");
        System.out.println("────────────────────────────────────────────────────\n");
        
        System.out.println("SERIALIZABLE (Automatic):");
        System.out.println("  • Serializes ALL non-static, non-transient fields");
        System.out.println("  • No control over format");
        System.out.println("  • Easier to implement (marker interface)");
        System.out.println("  • Good for simple objects");
        
        System.out.println("\nEXTERNALIZABLE (Manual):");
        System.out.println("  • YOU control exactly what gets serialized");
        System.out.println("  • Can optimize serialization format");
        System.out.println("  • More efficient for complex objects");
        System.out.println("  • Must implement public no-arg constructor");
        System.out.println("  • Must implement writeExternal/readExternal");
        
        System.out.println("\n✓ Use Serializable for simple cases");
        System.out.println("✓ Use Externalizable for performance-critical code");
    }
    
    private static void demonstrateDeserialization() {
        System.out.println("EXAMPLE 4: Deserialization of Externalizable Objects");
        System.out.println("───────────────────────────────────────────────────\n");
        
        try {
            System.out.println("Deserializing Person from: " + PERSON_FILE);
            FileInputStream fis = new FileInputStream(PERSON_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ExternalizablePerson person = (ExternalizablePerson) ois.readObject();
            ois.close();
            fis.close();
            
            System.out.println("  Deserialized: " + person);
            System.out.println("  Password after deserialization: " + person.getPassword() + " (not stored)");
            
            System.out.println("\nDeserializing Student from: " + STUDENT_FILE);
            fis = new FileInputStream(STUDENT_FILE);
            ois = new ObjectInputStream(fis);
            ExternalizableStudent student = (ExternalizableStudent) ois.readObject();
            ois.close();
            fis.close();
            
            System.out.println("  Deserialized: " + student);
            System.out.println("  Course count preserved: " + student.getCourseCount());
            
            System.out.println("\n✓ Externalizable objects successfully deserialized!");
            
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("  ✗ Error: " + e.getMessage());
        }
    }
}


// ═════════════════════════════════════════════════════════════════════════════
// EXTERNALIZABLE - COMPREHENSIVE Q&A
// ═════════════════════════════════════════════════════════════════════════════
//
// Q1: What is Externalizable interface?
// A: Externalizable is an interface for manual serialization control.
//    Unlike Serializable (automatic), you implement writeExternal() and
//    readExternal() to control exactly what gets serialized and how.
//
// Q2: What methods must Externalizable implement?
// A: Two methods:
//    - void writeExternal(ObjectOutput out) - Write data
//    - void readExternal(ObjectInput in) - Read data
//    Plus a public no-arg constructor for deserialization.
//
// Q3: Why must Externalizable have a public no-arg constructor?
// A: During deserialization, the JVM creates an instance using the
//    no-arg constructor BEFORE calling readExternal(). This allows
//    you to initialize defaults before restoring serialized state.
//
// Q4: What's the difference between writeObject and writeExternal?
// A: - writeObject: Custom serialization in Serializable class
//    - writeExternal: Required implementation in Externalizable
//    Externalizable gives YOU control from the start, while
//    Serializable defaults to automatic with optional customization.
//
// Q5: When should you use Externalizable over Serializable?
// A: Use Externalizable when:
//    - Performance is critical (large objects, many serializations)
//    - You need custom serialization format
//    - You want to serialize only specific fields selectively
//    - You need compression or encryption during serialization
//
// Q6: Can you mix field order in readExternal?
// A: NO! You MUST read in the EXACT same order as writeExternal wrote.
//    writeExternal: out.writeInt(x); out.writeObject(name);
//    readExternal: in.readInt(); (String) in.readObject();
//    Mismatched order causes ClassCastException or InvalidClassException.
//
// Q7: What happens if you don't implement the no-arg constructor?
// A: You get an exception during deserialization:
//    java.io.InvalidClassException: no valid constructor
//    The JVM needs to instantiate the object before reading state.
//
// Q8: Can Externalizable objects reference non-Externalizable objects?
// A: Yes! When you call out.writeObject() on any object,
//    that object must be Serializable (can be Externalizable or Serializable).
//    writeObject automatically handles Serializable objects.
//
// Q9: How does versioning work with Externalizable?
// A: You manually handle versioning by reading/writing version numbers:
//    writeExternal: out.writeInt(VERSION); // Write version first
//    readExternal: int version = in.readInt(); // Read version first
//                  if (version == 1) { handle v1 }
//
// Q10: Is Externalizable faster than Serializable?
// A: Potentially yes, because:
//    - You don't serialize unnecessary fields
//    - You control the serialization algorithm
//    - You can use compression or custom encoding
//    But adds complexity - profile before optimizing.
//
// ═════════════════════════════════════════════════════════════════════════════
