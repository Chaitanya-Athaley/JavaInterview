# THREADING EXAMPLES - COMPLETE INDEX

## Overview
This directory contains comprehensive examples demonstrating how multiple threads acquire locks in a multithreading environment, specifically using `ExecutorService` and `ReentrantLock`.

---

## Files Explained

### 🎯 Main Examples (Runnable Programs)

#### 1. **MovieTicketBookingSystem.java**
**What it does:** Basic movie ticket booking with ReentrantLock

**Key Features:**
- ExecutorService with 5-thread pool
- ReentrantLock for thread-safe access
- 10 concurrent booking tasks
- Real-time seat availability updates

**How to run:**
```bash
javac -d bin src/com/code/basic/threading/MovieTicketBookingSystem.java
java -cp bin com.code.basic.threading.MovieTicketBookingSystem
```

**Expected Output:**
```
✓ Customer 1 - BOOKING CONFIRMED for 2 tickets. Remaining: 98
✓ Customer 2 - BOOKING CONFIRMED for 3 tickets. Remaining: 95
✗ Customer 3 - BOOKING FAILED. Only 50 seats available...
```

**Learn:**
- Basic ExecutorService usage
- ReentrantLock mechanism
- Thread pool management
- Lock acquisition order

---

#### 2. **AdvancedMovieTicketBookingSystem.java**
**What it does:** Advanced booking with Callable and AtomicInteger

**Key Features:**
- Callable tasks with Future return values
- AtomicInteger for lock-free operations
- 15 concurrent booking tasks
- Result collection with Future.get()
- Timeout handling

**How to run:**
```bash
javac -d bin src/com/code/basic/threading/AdvancedMovieTicketBookingSystem.java
java -cp bin com.code.basic.threading.AdvancedMovieTicketBookingSystem
```

**Expected Output:**
```
✓ Customer 2 - BOOKING CONFIRMED for 3 tickets. Remaining: 138
✓ Customer 4 - BOOKING CONFIRMED for 3 tickets. Remaining: 147
Aggregate Statistics:
Successful Bookings: 15
```

**Learn:**
- Callable interface vs Runnable
- Future API for result collection
- AtomicInteger atomic operations
- Lock-free synchronization

---

#### 3. **LockAcquisitionFlow.java** ⭐ IMPORTANT
**What it does:** DETAILED step-by-step lock acquisition visualization

**Key Features:**
- Shows EXACT sequence of lock acquisition
- Tracks lock wait times
- Displays lock holder at each moment
- Shows critical section execution
- Memory visibility changes

**How to run:**
```bash
javac -d bin src/com/code/basic/threading/LockAcquisitionFlow.java
java -cp bin com.code.basic.threading.LockAcquisitionFlow
```

**Sample Output:**
```
┌─ BOOKING REQUEST: Customer 1
│  Thread: pool-1-thread-1
│  [STEP 1] Attempting to acquire lock...
│  ✓ [STEP 2] LOCK ACQUIRED!
│           Wait Time: 0 ms
│  [STEP 3] CRITICAL SECTION
│           Reading availableSeats: 20
│  [STEP 4] LOCK RELEASED!
└─ END OF BOOKING REQUEST
```

**Learn:**
- How lock.lock() works step-by-step
- What happens when lock is held
- How lock.unlock() releases it
- Order of thread execution
- **THIS IS THE MOST EDUCATIONAL ONE!**

---

### 📚 Documentation Files (Educational References)

#### 4. **LockAcquisitionFlowDiagram.java**
**What it contains:**
- Comprehensive ASCII diagrams
- Phase-by-phase explanation
- Lock state transitions
- Queue visualization
- Memory barrier mechanics
- Reentrant lock explained
- Lock wait queue FIFO ordering

**Read sections:**
- "PHASE 1: THREADS SUBMITTED" - Understand task queuing
- "PHASE 3: FIRST THREAD ACQUIRES LOCK" - Core mechanism
- "PHASE 5: THREAD RELEASES LOCK" - Memory barriers
- "KEY CONCEPT: MUTUAL EXCLUSION" - The whole point

---

#### 5. **LOCK_ACQUISITION_EXPLAINED.java**
**What it contains:**
- Complete step-by-step timeline (T0 to T311ms)
- Detailed flow for each phase
- Memory visibility explanation
- What happens in critical section
- Waiting thread behavior
- Common questions answered
- Real-world analogies

**Key sections:**
- "PART 3: LOCK ACQUISITION - THE CRITICAL PART!" - Most important
- "Complete Timeline" - See the full picture
- "KEY POINTS" - Essential concepts
- "COMMON QUESTIONS ANSWERED" - Your doubts

---

#### 6. **LockComparison.java**
**What it contains:**
- Side-by-side comparison: WITHOUT LOCK vs WITH LOCK
- Race condition demonstration
- Why lost updates happen
- Memory visibility issues
- Comparison table
- Real-world analogies (Bank ATM, Theater)

**Key sections:**
- "SCENARIO 1: WITHOUT LOCK (RACE CONDITION)" - See the problem
- "SCENARIO 2: WITH LOCK (SAFE)" - See the solution
- "BANK ATM ANALOGY" - Easy to understand

---

#### 7. **QuickReferenceLocks.java**
**What it contains:**
- Condensed quick reference
- One-sentence answers
- Visual lock queue diagram
- Timeline table
- Key terms defined
- Common mistakes
- Code patterns
- Memory barrier illustration

**Use when:**
- You need quick answers
- You forgot how it works
- You need a refresher
- You're in an interview

---

#### 8. **HOW_LOCKS_WORK_EXPLAINED.md** ⭐ START HERE
**What it contains:**
- Markdown formatted guide
- Clean visual flow diagrams
- Real-world examples
- Step-by-step explanations
- Common mistakes highlighted
- Performance implications
- Timeline examples
- Multiple perspectives

**Read first:** This is the best starting point!

---

### 🎓 Original Examples

#### 9. **MOVIE_TICKET_BOOKING_README.md**
Original comprehensive guide explaining both movie ticket booking systems.

#### 10. **ProducerConsumerProblem.java**
Different example using Producer-Consumer pattern.

#### 11. **CustomThreadPool.java**
Custom thread pool implementation.

---

## Quick Start Guide

### For Beginners: Start Here ↓

**Step 1:** Read documentation in order:
```
1. HOW_LOCKS_WORK_EXPLAINED.md (Markdown, easiest)
   ↓
2. QuickReferenceLocks.java (Visual reference)
   ↓
3. LockAcquisitionFlowDiagram.java (ASCII diagrams)
```

**Step 2:** Run and observe:
```
java -cp bin com.code.basic.threading.LockAcquisitionFlow
```

**Step 3:** Run full examples:
```
java -cp bin com.code.basic.threading.MovieTicketBookingSystem
java -cp bin com.code.basic.threading.AdvancedMovieTicketBookingSystem
```

**Step 4:** Read detailed explanations:
```
LOCK_ACQUISITION_EXPLAINED.java
LockComparison.java
```

---

## Understanding the Flow

### Question: "How do multiple threads acquire locks?"

**Quick Answer:**
1. Thread-A calls `lock.lock()` → **ACQUIRES immediately** (lock free)
2. Thread-B calls `lock.lock()` → **BLOCKS** (lock held by A)
3. Thread-C calls `lock.lock()` → **BLOCKS** (lock held by A)
4. Thread-A calls `lock.unlock()` → **RELEASES lock**
5. Thread-B wakes up → **ACQUIRES lock**
6. Thread-C still **BLOCKED**
7. Repeat...

**Visual:**
```
Lock: FREE
Thread-A: lock.lock() ──► ACQUIRED ✓
Thread-B: lock.lock() ──► BLOCKED ⏳
Thread-C: lock.lock() ──► BLOCKED ⏳

Lock: HELD BY A, Wait Queue: [B, C]

Thread-A: lock.unlock() ──► RELEASED
Thread-B: lock.lock() ──► NOW ACQUIRED ✓
Thread-C: lock.lock() ──► BLOCKED ⏳

Lock: HELD BY B, Wait Queue: [C]
```

---

## Key Concepts

| Concept | File | Section |
|---------|------|---------|
| Lock lifecycle | LockAcquisitionFlowDiagram | PHASE 1-6 |
| Wait queue | QuickReferenceLocks | HOW EXECUTORSERVICE FITS IN |
| Memory barriers | LOCK_ACQUISITION_EXPLAINED | MEMORY BARRIER section |
| Race conditions | LockComparison | WITHOUT LOCK scenario |
| Critical section | HOW_LOCKS_WORK_EXPLAINED | Code Pattern to Remember |
| ReentrantLock | LOCK_ACQUISITION_EXPLAINED | REENTRANT LOCK section |
| Mutual exclusion | LockComparison | KEY DIFFERENCE |

---

## Running All Examples

### Compile All
```bash
cd j:\Chaitanya\code\eclipse-workspace\JavaInterview

javac -d bin src/com/code/basic/threading/MovieTicketBookingSystem.java
javac -d bin src/com/code/basic/threading/AdvancedMovieTicketBookingSystem.java
javac -d bin src/com/code/basic/threading/LockAcquisitionFlow.java
```

### Run All
```bash
java -cp bin com.code.basic.threading.MovieTicketBookingSystem
java -cp bin com.code.basic.threading.AdvancedMovieTicketBookingSystem
java -cp bin com.code.basic.threading.LockAcquisitionFlow
```

---

## Important Patterns

### Safe Lock Pattern (Always Use This)
```java
Lock lock = new ReentrantLock();

public void method() {
    lock.lock();      // ACQUIRE
    try {
        // Critical section
        // Only one thread here
    } finally {
        lock.unlock();  // ALWAYS RELEASE
    }
}
```

### Why try-finally?
- If exception occurs, unlock still executes
- Prevents deadlock
- Guarantees lock release

---

## Interview Questions (Answered in Files)

1. **"How do locks work with multiple threads?"**
   → HOW_LOCKS_WORK_EXPLAINED.md

2. **"What's the difference with and without locks?"**
   → LockComparison.java

3. **"Explain lock acquisition order"**
   → LockAcquisitionFlowDiagram.java

4. **"What are memory barriers?"**
   → LOCK_ACQUISITION_EXPLAINED.java

5. **"What's a race condition?"**
   → LockComparison.java - WITHOUT LOCK section

6. **"Why must we use try-finally?"**
   → HOW_LOCKS_WORK_EXPLAINED.md - Code Pattern

7. **"How does ExecutorService work with locks?"**
   → MovieTicketBookingSystem.java + QuickReferenceLocks.java

8. **"What's ReentrantLock vs Regular Lock?"**
   → LOCK_ACQUISITION_EXPLAINED.java - REENTRANT section

---

## Learning Path

**Beginner:**
1. Read: HOW_LOCKS_WORK_EXPLAINED.md
2. Run: LockAcquisitionFlow
3. Study: MovieTicketBookingSystem

**Intermediate:**
1. Read: LOCK_ACQUISITION_EXPLAINED.java
2. Compare: LockComparison.java
3. Run: AdvancedMovieTicketBookingSystem

**Advanced:**
1. Study: LockAcquisitionFlowDiagram.java (all phases)
2. Analyze: Code patterns
3. Understand: Memory barriers, FIFO queues

---

## Common Mistakes (See LockComparison.java)

❌ Not using locks → Race conditions
❌ Forgetting unlock → Deadlock
❌ Not using try-finally → Exception → Deadlock
❌ Holding locks too long → Poor performance
❌ Acquiring multiple locks → Deadlock risk

---

## Performance Notes

- Lock acquisition: ~100-200 nanoseconds
- Critical section should be milliseconds, not seconds
- Minimize time between lock.lock() and lock.unlock()
- Contention causes threads to block and wait
- Sequential execution with locks is slower but correct

---

## Further Reading

Files have extensive comments explaining:
- Why locks are needed
- How memory barriers work
- FIFO queue management
- Atomicity guarantees
- Visibility guarantees
- Common pitfalls

---

## Summary

This is a complete guide to understanding how multiple threads acquire locks:

✅ **Theory:** HOW_LOCKS_WORK_EXPLAINED.md + LockAcquisitionFlowDiagram.java
✅ **Practice:** Run the examples and see locks in action
✅ **Comparison:** LockComparison.java shows why locks matter
✅ **Reference:** QuickReferenceLocks.java for quick lookups
✅ **Real-world:** MovieTicketBookingSystem.java realistic scenario

**Start with HOW_LOCKS_WORK_EXPLAINED.md, then run LockAcquisitionFlow!**

---

*All code examples compile and run successfully. Tested on Java 8+*
