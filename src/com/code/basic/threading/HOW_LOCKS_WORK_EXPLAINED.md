# How Multiple Threads Acquire Locks - Complete Guide

## Quick Answer

**When multiple threads try to acquire a lock:**

1. **First thread** calls `lock.lock()` → **SUCCEEDS immediately** (lock was free)
2. **Second thread** calls `lock.lock()` → **BLOCKS** (lock held by first thread)
3. **Third thread** calls `lock.lock()` → **BLOCKS** (lock still held by first thread)

When first thread calls `lock.unlock()`:
- Lock becomes free
- Next waiting thread wakes up
- That thread now acquires the lock
- Process repeats

---

## Visual Flow

```
Initial: Lock is UNLOCKED

  Thread-A: lock.lock() ──► ACQUIRED ✓
  Thread-B: lock.lock() ──► BLOCKED ⏳
  Thread-C: lock.lock() ──► BLOCKED ⏳

After Thread-A releases lock:

  Thread-A: lock.unlock() ──► Released
  Thread-B: lock.lock() ──► NOW ACQUIRED ✓  
  Thread-C: lock.lock() ──► BLOCKED ⏳

After Thread-B releases lock:

  Thread-B: lock.unlock() ──► Released
  Thread-C: lock.lock() ──► NOW ACQUIRED ✓
```

---

## Files Created for Learning

### 1. **LockAcquisitionFlow.java**
   - Shows **real execution** with detailed logging at each step
   - Demonstrates lock acquisition timing
   - Shows wait times and queue management
   - Run this to **see it in action**!

### 2. **LockAcquisitionFlowDiagram.java**
   - Contains **ASCII diagrams** showing the complete process
   - Explains each phase in detail
   - Shows memory barriers and visibility
   - Educational reference file

### 3. **LOCK_ACQUISITION_EXPLAINED.java**
   - **Comprehensive explanation** with timeline
   - Shows difference between with/without locks
   - Explains memory barriers
   - Covers all edge cases

### 4. **LockComparison.java**
   - **Side-by-side comparison**: WITHOUT LOCK (race condition) vs WITH LOCK (safe)
   - Shows exact point where race conditions occur
   - Real-world analogies (bathroom, bank ATM)
   - Why locks are critical

### 5. **QuickReferenceLocks.java**
   - Quick cheat sheet
   - Common questions answered
   - Key terminology
   - Code patterns to remember

---

## The Mechanism: Step by Step

### STEP 1: Create Lock
```java
Lock lock = new ReentrantLock();
```
- Lock is initially **UNLOCKED**
- No thread holds it yet

### STEP 2: Thread Attempts to Acquire
```java
lock.lock();  // Thread calls this
```

**Behind the scenes:**
- Operating System checks: Is lock held by another thread?
- **YES** → Add this thread to wait queue, BLOCK
- **NO** → Give lock to this thread, continue execution

### STEP 3: Critical Section Execution
```java
try {
    // ONLY ONE THREAD EXECUTES HERE AT ANY TIME
    availableSeats -= numberOfTickets;
    // Other threads CANNOT interfere
} finally {
    lock.unlock();  // ALWAYS release
}
```

### STEP 4: Release Lock
```java
lock.unlock();
```

**Behind the scenes:**
- Mark lock as UNLOCKED
- Execute memory barrier (sync caches)
- Wake up first waiting thread in queue
- That thread now acquires the lock

---

## Wait Queue Behavior

### Queue Management
```
Lock State: LOCKED (held by Thread-A)

Wait Queue (in order):
┌─────────────────┐
│ Thread-B  ◄─ Will acquire next
│ Thread-C
│ Thread-D
└─────────────────┘

When Thread-A releases:
├─ Thread-B wakes up
├─ Acquires lock
└─ Others still waiting
```

### FIFO or Priority?
- **ReentrantLock()**: Roughly FIFO (unfair)
- **ReentrantLock(true)**: Strictly FIFO (fair)
- Exception: Sometimes OS might choose differently

---

## Memory Visibility: Why It Matters

### WITHOUT Memory Barrier (WRONG ❌)
```
Thread-A writes: availableSeats = 15 (to CPU cache)
                         ↓
                    (NO barrier)
                         ↓
Thread-B reads: availableSeats = 20 (from stale cache)
                ↑ RACE CONDITION! Sees old value!
```

### WITH Memory Barrier (CORRECT ✓)
```
Thread-A writes: availableSeats = 15 (to CPU cache)
                         ↓
                  lock.unlock()
                         ↓
                   MEMORY BARRIER
                         ↓
                  Flush cache to RAM
                         ↓
Thread-B calls lock.lock()
                         ↓
                   MEMORY BARRIER
                         ↓
                   Read from RAM
                         ↓
Thread-B reads: availableSeats = 15 ✓ CORRECT VALUE
```

---

## Why Locks are Essential

### WITHOUT Lock (Race Condition)
```
availableSeats = 20 initially

Thread-A reads:  20
Thread-B reads:  20  ◄─ Both read same value!
Thread-A writes: 20 - 2 = 18
Thread-B writes: 20 - 3 = 17 ◄─ OVERWRITES Thread-A's change!

Expected: 20 - 2 - 3 = 15
Actual:   17 ❌ LOST UPDATE!
```

### WITH Lock (Safe)
```
availableSeats = 20 initially

Thread-A: acquires lock → reads 20 → writes 18 → releases
                            ↓
Thread-B: acquires lock → reads 18 ✓ SEES UPDATE → writes 15 → releases

Expected: 20 - 2 - 3 = 15
Actual:   15 ✓ CORRECT!
```

---

## Code Pattern to Remember

```java
private final Lock lock = new ReentrantLock();

public void criticalOperation() {
    lock.lock();        // STEP 1: ACQUIRE
    try {
        // STEP 2: CRITICAL SECTION
        // Read/modify/write shared data
        // Only this thread executes here
    } finally {
        lock.unlock();  // STEP 3: RELEASE (ALWAYS!)
    }
}
```

### Why `try-finally`?
- If exception occurs, `unlock()` still executes
- Prevents deadlock from forgetting to unlock
- **CRITICAL for thread safety**

---

## Timeline Example (3 Threads, 1 Lock)

| Time | Thread-A | Thread-B | Thread-C | Lock Status |
|------|----------|----------|----------|-------------|
| T0 | lock.lock() ✓ | lock.lock() ⏳ | lock.lock() ⏳ | Held by A |
| T50 | [Critical Section] | (blocked) | (blocked) | Held by A |
| T150 | lock.unlock() | (wakes up) | (blocked) | Released |
| T151 | (done) | lock.lock() ✓ | lock.lock() ⏳ | Held by B |
| T250 | (idle) | [Critical Section] | (blocked) | Held by B |
| T310 | (idle) | lock.unlock() | (wakes up) | Released |
| T311 | (idle) | (done) | lock.lock() ✓ | Held by C |

---

## Common Mistakes to Avoid

### ❌ WRONG: Forget unlock
```java
lock.lock();
// ... code ...
// Forgot lock.unlock()!
// Other threads deadlock forever
```

### ✓ CORRECT: Always use try-finally
```java
lock.lock();
try {
    // ... code ...
} finally {
    lock.unlock();  // Guaranteed to execute
}
```

### ❌ WRONG: Lock in wrong place
```java
public void bookTickets() {
    lock.lock();
    try {
        // Non-atomic operations here
        doSomethingElse();  // ← Other threads might interfere
        availableSeats--;
    } finally {
        lock.unlock();
    }
}
```

### ✓ CORRECT: Minimal critical section
```java
public void bookTickets() {
    // Do non-critical work first
    int seats = calculateSeatsNeeded();
    
    lock.lock();
    try {
        availableSeats -= seats;  // Only critical part locked
    } finally {
        lock.unlock();
    }
}
```

---

## Real-World Analogy: Theater Box Office

### WITHOUT Lock (Chaos!)
```
Ticket Booth

Cashier-A checks availability: 20 seats left
Cashier-B checks availability: 20 seats left
Cashier-A sells 5 tickets, updates: 15 left
Cashier-B sells 8 tickets, updates: 12 left ← LOST 5!

Customers angry! Books say 12, should be 7!
```

### WITH Lock (Organized!)
```
Ticket Booth with Lock (one cashier at a time)

Cashier-A acquires lock:
  ├─ Checks: 20 seats
  ├─ Sells 5 tickets
  ├─ Updates: 15 left
  └─ Releases lock

Cashier-B acquires lock:
  ├─ Checks: 15 seats ✓ Sees A's update
  ├─ Sells 8 tickets
  ├─ Updates: 7 left ✓
  └─ Releases lock

Books correct! 20 - 5 - 8 = 7 ✓
```

---

## Performance Impact

**With locks, execution becomes SEQUENTIAL:**

```
Without lock:  Thread-A ┐ 
               Thread-B ├─ Parallel (FAST but WRONG)
               Thread-C ┘

With lock:     Thread-A ─────► Thread-B ─────► Thread-C
               Sequential (CORRECT but SLOWER)
```

**Trade-off:** Correctness > Performance in 99% of cases!

---

## Key Takeaways

✅ **Only ONE thread holds lock at any time**

✅ **Other threads BLOCK in wait queue**

✅ **When lock released, next thread wakes up and acquires it**

✅ **Memory barriers ensure visibility of changes**

✅ **Try-finally ensures unlock even on exception**

✅ **Critical section should be as small as possible**

✅ **Without locks = race conditions and data corruption**

✅ **With locks = correctness and thread safety**

---

## Test It Yourself!

Run the examples to see it in action:

```bash
# Compile
javac -d bin src/com/code/basic/threading/LockAcquisitionFlow.java

# Run with detailed output
java -cp bin com.code.basic.threading.LockAcquisitionFlow
```

Watch the output to see:
- Exact order threads acquire lock
- How long each waits
- Memory visibility changes
- The complete queue management

---

## Next Steps

1. ✅ Understand basic lock acquisition (you did it!)
2. ⬜ Learn about deadlocks and prevention
3. ⬜ Study Semaphores and other synchronization primitives
4. ⬜ Explore concurrent collections (ConcurrentHashMap)
5. ⬜ Master CompletableFuture for async operations

---

**Remember:** Locks are the foundation of thread-safe programming. Master them!
