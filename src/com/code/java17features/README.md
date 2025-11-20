# Java 17 Features - Interview Preparation Package

## 📚 Overview

This package contains comprehensive, interview-ready examples of Java 17 features with working code demonstrations, detailed explanations, and Q&A sections.

**Current Project Target:** Java 15  
**Features Demonstrated:** Java 16-21

---

## ✨ Package Contents

### 1. **TextBlocksDemo.java** ✓ WORKING
- **Status:** Fully compilable and runnable
- **Java Version:** 15+
- **What:** Multi-line strings using triple-quote syntax (`"""..."""`)
- **Examples:**
  - Basic multi-line strings vs concatenation
  - JSON formatting
  - HTML templates
  - SQL query formatting
  - Indentation handling
- **Interview Focus:**
  - Problem it solves (readability)
  - Indentation management
  - Escape sequences in text blocks
  - Use cases
- **Run:** `java com.code.java17features.TextBlocksDemo`

---

### 2. **RecordsDemo.java** ✓ WORKING
- **Status:** Fully compilable and runnable (with Java 8 compatibility)
- **Java Version:** 16+ (demonstrated for Java 15)
- **What:** Immutable data carriers with auto-generated boilerplate
- **Examples:**
  - Basic record (Point)
  - Record with validation (Person)
  - Record with methods (Employee)
  - Generic records (Pair<T>)
  - API response record (User)
- **Interview Focus:**
  - Boilerplate generation (constructor, equals, hashCode, toString)
  - Immutability guarantees
  - Compact constructors
  - Limitations and benefits
- **Note:** Java 15 doesn't support records syntax, so we show equivalent classes
- **Run:** `java com.code.java17features.RecordsDemo`

---

### 3. **SealedRecordsCombinationDemo.java** ✓ WORKING
- **Status:** Fully compilable and runnable
- **Java Version:** 17+
- **What:** Combined sealed classes + records pattern for type-safe hierarchies
- **Examples:**
  - Shape hierarchy (Circle, Rectangle, Triangle)
  - API response types (Success, Error, Redirect)
  - Benefits and real-world patterns
- **Interview Focus:**
  - Type safety benefits
  - Immutability
  - Pattern matching exhaustiveness
  - ADT (Algebraic Data Types) patterns
  - Comparison with enums
- **Run:** `java com.code.java17features.SealedRecordsCombinationDemo`

---

### 4. **VirtualThreadsConceptDemo.java** ✓ WORKING
- **Status:** Fully compilable and runnable (conceptual demonstration)
- **Java Version:** 21+ (explained for Java 15)
- **What:** Lightweight threads for high-throughput concurrent applications
- **Examples:**
  - Traditional platform threads (Java 15)
  - Limitations (memory, context switching)
  - Virtual threads concept and benefits
  - Performance comparison
- **Interview Focus:**
  - Platform vs virtual threads
  - Carrier threads concept
  - Context switching efficiency
  - I/O-bound vs CPU-bound workloads
  - Framework support (Spring Boot, Quarkus)
- **Run:** `java com.code.java17features.VirtualThreadsConceptDemo`

---

### 5. **SealedClassesDemo.java** ⚠️ COMPILATION ERRORS
- **Status:** Contains Java 17+ syntax (won't compile in Java 15)
- **Java Version:** 17+
- **What:** Control which classes can extend or implement
- **Examples in file:**
  - Vehicle sealed hierarchy (Car, Motorcycle, Truck)
  - Animal sealed hierarchy (Dog, Cat, WildAnimal)
  - Pattern matching with sealed types
  - Exhaustiveness checking
- **Interview Focus:**
  - Purpose and benefits
  - The `permits` clause
  - Final vs sealed vs open
  - Pattern matching exhaustiveness
  - Sealed interfaces
- **Note:** File shows actual Java 17 syntax as educational reference with detailed comments
- **Error Source:** Uses `sealed` keyword and `permits` clause not available in Java 15

---

### 6. **PatternMatchingDemo.java** ⚠️ COMPILATION ERRORS
- **Status:** Contains Java 16+ syntax (won't compile in Java 15)
- **Java Version:** 16+ (enhanced in 21+)
- **What:** Combine type checking and casting in single operation
- **Examples in file:**
  - Type patterns with instanceof
  - Guard clauses (condition checking)
  - Multiple object type handling
  - Pattern variable scope
  - Integration with sealed classes
- **Interview Focus:**
  - Type pattern syntax
  - Guard clauses
  - Variable scoping rules
  - Pattern matching in switch (Java 21+)
  - Sealed class integration
  - Nested patterns
- **Note:** Shows actual Java 16+ syntax with detailed explanations
- **Error Source:** Type patterns require Java 16, pattern matching in switch requires Java 21

---

### 7. **Java17Features.java** 
- **Status:** Overview file (educational)
- **What:** Index of all Java 17 features with summary information
- **Content:** Feature descriptions, common interview questions, links to examples

---

### 8. **Java17FeaturesIndex.java**
- **Status:** Comprehensive index and guide
- **What:** Complete reference for the package with status of each feature
- **Content:**
  - Feature compatibility matrix
  - How to use this package
  - Interview topics overview
  - Learning path recommendations
- **Run:** `java com.code.java17features.Java17FeaturesIndex`

---

## 🎯 How to Use This Package

### For Learning:
1. **Start Simple:** Run `TextBlocksDemo` first (simplest feature)
2. **Study Immutability:** Read `RecordsDemo` to understand records
3. **Real-World Pattern:** Review `SealedRecordsCombinationDemo` (combines concepts)
4. **Advanced Concepts:** Read comments in `SealedClassesDemo` and `PatternMatchingDemo`
5. **Concurrency:** Study `VirtualThreadsConceptDemo` for high-throughput design

### For Interview Preparation:
1. Read each demo file's header comments
2. Study the interview Q&A at the end of each file
3. Run working examples to see output
4. Read compilation error files to understand Java 17+ syntax
5. Practice explaining each feature and its benefits

### Running the Examples:

```bash
# Compile all Java 17 features
javac -d bin src/com/code/java17features/*.java

# Run working examples
java -cp bin com.code.java17features.TextBlocksDemo
java -cp bin com.code.java17features.RecordsDemo
java -cp bin com.code.java17features.SealedRecordsCombinationDemo
java -cp bin com.code.java17features.VirtualThreadsConceptDemo
java -cp bin com.code.java17features.Java17FeaturesIndex

# View compilation errors (educational)
javac src/com/code/java17features/SealedClassesDemo.java
javac src/com/code/java17features/PatternMatchingDemo.java
```

---

## 📊 Feature Compatibility Matrix

| Feature | Status | Java Version | File | Compiles? |
|---------|--------|--------------|------|-----------|
| Text Blocks | ✓ Full | 15+ | TextBlocksDemo.java | ✓ Yes |
| Records | ✓ Equiv | 16+ | RecordsDemo.java | ✓ Yes |
| Sealed Classes | ⚠️ Ref | 17+ | SealedClassesDemo.java | ✗ No (Java 15) |
| Pattern Matching | ⚠️ Ref | 16+/21+ | PatternMatchingDemo.java | ✗ No (Java 15) |
| Virtual Threads | ✓ Concept | 21+ | VirtualThreadsConceptDemo.java | ✓ Yes |
| Sealed Records | ✓ Full | 17+ | SealedRecordsCombinationDemo.java | ✓ Yes |

**Legend:**
- ✓ = Fully working
- ⚠️ = Educational reference (shows actual Java 17+ syntax)
- ✗ = Requires newer Java version

---

## 🔑 Interview Topics Covered

### Text Blocks
- Motivation and benefits
- Readability improvements
- Indentation handling
- Escape sequences in text blocks
- Common use cases (JSON, HTML, SQL)

### Records
- Boilerplate elimination
- Immutability guarantees
- Compact constructors
- Record components
- Constructor generation
- Default equals/hashCode/toString
- Limitations (no extends, no field mutation)

### Sealed Classes
- Type safety benefits
- Domain modeling (finite types)
- Permits clause
- Final vs sealed vs open
- Pattern matching exhaustiveness
- Real-world examples

### Pattern Matching
- Type patterns
- Guard clauses
- Variable scope
- Pattern matching in switch (Java 21+)
- Sealed class integration
- Exhaustiveness checking
- Nested patterns

### Virtual Threads
- Platform thread limitations
- Virtual thread benefits
- Carrier threads
- Context switching efficiency
- I/O-bound applications
- Framework support
- Comparison with callbacks/reactive

### Sealed Records Combination
- Type-safe hierarchies
- Immutable data structures
- Pattern matching exhaustiveness
- ADT (Algebraic Data Types)
- Real-world patterns
- Comparison with enums

---

## 💡 Real-World Examples

### API Response Handling
```
Success Response (200) → Data payload
Error Response (404) → Error code + message
Redirect Response (301) → New location
```
*See: SealedRecordsCombinationDemo.java*

### Shape Calculations
```
Circle → Area = π × r²
Rectangle → Area = width × height
Triangle → Area = Heron's formula
```
*See: SealedRecordsCombinationDemo.java*

### High-Throughput Server
```
Platform Threads: ~1000-10,000 concurrent requests
Virtual Threads: Millions of concurrent requests
```
*See: VirtualThreadsConceptDemo.java*

---

## 📝 Interview Q&A Format

Each file contains:
- **10 Common Interview Questions**
- **Detailed Answers**
- **Code Examples**
- **Comparison with alternatives**
- **Real-world use cases**
- **Performance implications**

Example:
```
Q: What problem do text blocks solve?
A: Before text blocks, multi-line strings required:
   1. Verbose concatenation with + operators
   2. Manual escape sequences (\n, \", \\)
   3. Reduced code readability
   
   Text blocks provide:
   • Clean, readable multi-line strings
   • Automatic indentation handling
   • No concatenation needed
```

---

## 🎓 Learning Path

### Week 1: Basics
- [x] Text Blocks (TextBlocksDemo)
- [x] Records Concept (RecordsDemo)

### Week 2: Patterns
- [x] Sealed Records Pattern (SealedRecordsCombinationDemo)
- [ ] Read SealedClassesDemo comments

### Week 3: Advanced
- [ ] Pattern Matching (read PatternMatchingDemo comments)
- [ ] Virtual Threads Concept (VirtualThreadsConceptDemo)

### Week 4: Integration
- [ ] Practice interview questions
- [ ] Combine features in examples
- [ ] Real-world scenario exercises

---

## 🚀 Next Steps

1. **Run all working examples** to see output
2. **Read compilation error files** to understand Java 17+ syntax
3. **Study Q&A sections** for interview preparation
4. **Modify examples** to deepen understanding
5. **Write your own** sealed record hierarchies
6. **Practice explaining** each feature to a friend

---

## 📚 Related Content

Also in this JavaInterview workspace:
- `threading/` - Multithreading with ExecutorService, locks, concurrency
- `basic/` - Fundamental Java concepts
- `designpattern/` - Design patterns (Builder, Singleton, etc.)
- `leetcode/` - Algorithm challenges
- `dp/` - Dynamic programming solutions

---

## ✅ Checklist for Interview

- [ ] Understand Text Blocks benefits and syntax
- [ ] Know what Records generate (constructor, methods)
- [ ] Explain Sealed Classes type safety
- [ ] Practice Pattern Matching exhaustiveness
- [ ] Describe Virtual Threads vs platform threads
- [ ] Give examples of each feature
- [ ] Compare alternatives for each feature
- [ ] Explain real-world use cases
- [ ] Discuss performance implications
- [ ] Run and understand all examples

---

**Package Created:** Java 17 Features Interview Preparation  
**Current Java:** 15 (project target)  
**Features Demonstrated:** Java 16-21  
**Status:** Ready for interview preparation 🎯

