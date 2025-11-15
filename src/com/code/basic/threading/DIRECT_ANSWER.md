# DIRECT ANSWER TO YOUR QUESTION

## You Asked: "So how multiple threads acquire lock in this flow?"

---

## THE DIRECT ANSWER

**Threads queue up and acquire the lock ONE AT A TIME:**

```
Think of a BATHROOM with ONE LOCK on the door:

Person-1: Tries lock (AVAILABLE) → Enters ✓
Person-2: Tries lock (LOCKED by 1) → WAITS
Person-3: Tries lock (LOCKED by 1) → WAITS

Person-1 leaves and unlocks door
         ↓
Person-2: WAKES UP → Enters ✓
Person-3: Still WAITS

Person-2 leaves and unlocks door
         ↓
Person-3: WAKES UP → Enters ✓
```

---

## IN CODE TERMS

```java
Lock lock = new ReentrantLock();

// Thread-A
lock.lock();        // ◄─ Acquires immediately (lock was free)
try {
    // Only Thread-A here
} finally {
    lock.unlock();  // ◄─ Releases lock
}

// Thread-B (while Thread-A held lock)
lock.lock();        // ◄─ BLOCKS! Waits for lock
try {
    // Can't enter yet...
    // ...still waiting...
    // ...Thread-A just released!
    // Now Thread-B enters ✓
} finally {
    lock.unlock();  // ◄─ Releases lock
}

// Thread-C (while Thread-B held lock)
lock.lock();        // ◄─ BLOCKS! Waits for lock
try {
    // Thread-C's turn now
} finally {
    lock.unlock();
}
```

---

## VISUAL TIMELINE

```
Time →

Thread-A: [lock.lock()] ─── CRITICAL SECTION ─── [lock.unlock()]
          ████████████████████████████████████

Thread-B:                      (BLOCKED) ─── [lock.lock()] ─── CS ─── [unlock]
                               ⏳⏳⏳⏳⏳⏳    ██████████████████████

Thread-C:                                          (BLOCKED) ─── [lock.lock()] ─── CS
                                                  ⏳⏳⏳⏳⏳⏳⏳⏳⏳⏳ █████████
```

---

## WHAT'S HAPPENING BEHIND THE SCENES

### When Thread-B calls lock.lock()

**OS checks:** Is lock held?
```
YES → lock held by Thread-A
      │
      └─ Add Thread-B to WAIT QUEUE
      └─ Thread-B BLOCKS (cannot proceed)
      └─ Thread-B becomes inactive
```

### When Thread-A calls lock.unlock()

**OS does:**
```
1. Release lock (mark as UNLOCKED)
2. Execute MEMORY BARRIER (sync changes)
3. Check WAIT QUEUE: [Thread-B, Thread-C, ...]
4. Wake up Thread-B (first in queue)
5. Thread-B now acquires lock automatically!
```

### Now Thread-B can proceed

**Thread-B:**
```
lock.lock()   ← Was BLOCKED here
try {
    // Now Thread-B sees Thread-A's changes
    // Thread-B can access shared data
}
```

---

## WHY LOCKS MATTER

### WITHOUT LOCK (Race Condition ❌)

```
shared variable: availableSeats = 20

Thread-A reads:   availableSeats = 20
Thread-B reads:   availableSeats = 20  ◄─── Both see SAME value!

Thread-A computes: 20 - 2 = 18
Thread-B computes: 20 - 3 = 17

Thread-A writes: availableSeats = 18
Thread-B writes: availableSeats = 17  ◄─── OVERWRITES Thread-A's update!

Expected result: 20 - 2 - 3 = 15
Actual result:   17 ❌ WRONG! LOST UPDATE!
```

### WITH LOCK (Safe ✓)

```
shared variable: availableSeats = 20

Thread-A: lock.lock()
          availableSeats = 20 (READ)
          20 - 2 = 18 (COMPUTE)
          availableSeats = 18 (WRITE)
          lock.unlock()
          ↓
Thread-B: lock.lock()
          availableSeats = 18 (READ) ✓ Sees Thread-A's update!
          18 - 3 = 15 (COMPUTE)
          availableSeats = 15 (WRITE)
          lock.unlock()

Expected result: 20 - 2 - 3 = 15
Actual result:   15 ✓ CORRECT!
```

---

## THE MECHANISM IN 4 STEPS

### STEP 1: Thread calls lock.lock()

```
lock.lock() is called

OS checks: Is lock currently held by another thread?

NO  → Lock is FREE
      └─ Thread ACQUIRES lock immediately
      └─ Thread continues to critical section

YES → Lock is HELD
      └─ Add thread to WAIT QUEUE
      └─ Thread BLOCKS (stops here, waits)
      └─ Cannot execute code after lock.lock()
```

### STEP 2: Thread executes Critical Section

```
Inside try block (between lock.lock() and lock.unlock()):

✓ This thread is executing
✗ No other thread can be here
✓ Data is safe to modify
✓ No race conditions
✗ Other threads see STALE/CACHED values
```

### STEP 3: Thread calls lock.unlock()

```
lock.unlock() is called

OS does:
1. Release lock (mark UNLOCKED)
2. MEMORY BARRIER (flush CPU caches)
   └─ Ensures changes visible to all threads
3. Check WAIT QUEUE
4. Wake up FIRST waiting thread
5. That thread now acquires lock
```

### STEP 4: Next thread acquires lock

```
First thread from WAIT QUEUE wakes up

lock.lock() now SUCCEEDS
(was BLOCKED before, now succeeds)

Thread can now:
✓ See previous thread's changes
✓ Access shared data
✓ Execute critical section
```

---

## WAIT QUEUE MECHANISM

```
Initial:
lock = FREE
wait_queue = []


Thread-A: lock.lock()
  └─ SUCCEEDS (lock was free)
  lock = HELD BY THREAD-A
  wait_queue = []


Thread-B: lock.lock()
  └─ BLOCKS (lock held by A)
  lock = HELD BY THREAD-A
  wait_queue = [THREAD-B]


Thread-C: lock.lock()
  └─ BLOCKS (lock held by A)
  lock = HELD BY THREAD-A
  wait_queue = [THREAD-B, THREAD-C]


Thread-A: lock.unlock()
  └─ RELEASES
  lock = FREE
  wait_queue = [THREAD-B, THREAD-C]
  ↓
  OS wakes up THREAD-B (first in queue)


Thread-B: lock.lock()
  └─ SUCCEEDS (lock just became free)
  lock = HELD BY THREAD-B
  wait_queue = [THREAD-C]


Thread-B: lock.unlock()
  └─ RELEASES
  lock = FREE
  wait_queue = [THREAD-C]
  ↓
  OS wakes up THREAD-C


Thread-C: lock.lock()
  └─ SUCCEEDS (lock just became free)
  lock = HELD BY THREAD-C
  wait_queue = []
```

---

## MEMORY VISIBILITY (Critical!)

### The Problem

```
Thread-A writes: availableSeats = 18
                 ↓
                 Stored in CPU Cache (not main memory!)

Thread-B reads: availableSeats = ?
                ↓
                Still sees OLD value from its cache
                ↓
                availableSeats = 20 ❌ STALE!
```

### The Solution (Memory Barrier)

```
Thread-A writes: availableSeats = 18
                 ↓
Thread-A: lock.unlock()
          ↓
    MEMORY BARRIER INSERTED!
          ├─ Flush CPU-A's cache to main memory
          ├─ availableSeats = 18 now in RAM
          └─ All threads can see it

Thread-B: lock.lock()
          ↓
    MEMORY BARRIER INSERTED!
          ├─ Clear CPU-B's cache
          ├─ Read fresh from main memory
          └─ availableSeats = 18 ✓ CORRECT!
```

---

## CORRECT CODE PATTERN

```java
private final Lock lock = new ReentrantLock();

public void bookTickets(int customerId, int numberOfTickets) {
    lock.lock();      // STEP 1: ACQUIRE LOCK
    try {
        // STEP 2: CRITICAL SECTION
        // Only ONE thread executes here at any time
        if (availableSeats >= numberOfTickets) {
            availableSeats -= numberOfTickets;
            System.out.println("Booked!");
        }
        
    } finally {
        lock.unlock();  // STEP 3: RELEASE LOCK (ALWAYS!)
    }
    // STEP 4: Next thread acquires lock
}
```

### Why try-finally?

```
If exception occurs inside try block:

❌ WITHOUT finally:
   Exception thrown
   │
   ├─ Lock never released
   └─ Other threads DEADLOCK forever!

✅ WITH finally:
   Exception thrown
   │
   ├─ finally block executes anyway
   ├─ lock.unlock() is called
   └─ Other threads can now acquire lock
```

---

## EXECUTORSERVICE CONTEXT

```
ExecutorService executor = Executors.newFixedThreadPool(3);

Creates 3 WORKER THREADS in a pool:
├─ Worker-1
├─ Worker-2
└─ Worker-3

You submit 5 tasks:
executor.execute(task1);  ───┐
executor.execute(task2);  ───┼─ Task Queue
executor.execute(task3);  ───┤
executor.execute(task4);  ───┤
executor.execute(task5);  ───┘

Execution by workers:
T0:   Worker-1 runs task1 (tries lock.lock())
      Worker-2 runs task2 (tries lock.lock() → BLOCKED)
      Worker-3 runs task3 (tries lock.lock() → BLOCKED)
      task4, task5 wait in task queue

When task1 completes and releases lock:
└─ task2 continues (lock.lock() succeeds)

When task2 completes and releases lock:
└─ task3 continues (lock.lock() succeeds)

And so on...
```

---

## TIMING EXAMPLE

```
Time (ms)    Event
─────────────────────────────────────────────────
0            Task-1: lock.lock() ─► ACQUIRED
             Task-2: lock.lock() ─► BLOCKED
             Task-3: lock.lock() ─► BLOCKED

50           Task-1: [Critical section active]

150          Task-1: lock.unlock() ─► RELEASED
             (Memory barrier)

151          Task-2: [Now enters critical section]
             Task-3: lock.lock() ─► STILL BLOCKED

250          Task-2: [Critical section active]

310          Task-2: lock.unlock() ─► RELEASED
             (Memory barrier)

311          Task-3: [Now enters critical section]

Total time: ~310ms instead of parallel ~150ms
But result is CORRECT!
```

---

## KEY TAKEAWAYS

✅ **Only ONE thread holds lock**
   - All others WAIT in queue

✅ **When released, next in queue acquires**
   - Automatic by OS

✅ **Queue is FIFO** (First In First Out)
   - First to wait is first to acquire

✅ **Memory barriers ensure visibility**
   - Changes seen by next thread

✅ **Try-finally ensures unlock**
   - Even if exception occurs

✅ **Critical section must be fast**
   - Minimize time between lock/unlock

✅ **Sequential execution is correct**
   - Parallel would have race conditions

---

## SUMMARY

**Multiple threads acquire ONE lock in a QUEUE:**

1. Thread-1 acquires → Works safely → Releases
2. Thread-2 (was waiting) acquires → Works safely → Releases
3. Thread-3 (was waiting) acquires → Works safely → Releases

Result: **Perfect data consistency!**

---

## Next Steps

1. ✅ Understand this (you just did!)
2. ⬜ Read: `HOW_LOCKS_WORK_EXPLAINED.md`
3. ⬜ Run: `LockAcquisitionFlow.java`
4. ⬜ Study: The code examples

---

**That's your answer! Locks work through a queue system!**

