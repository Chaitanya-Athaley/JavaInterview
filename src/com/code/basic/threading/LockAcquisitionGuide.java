package com.code.basic.threading;

/**
 * COMPREHENSIVE GUIDE: HOW MULTIPLE THREADS ACQUIRE LOCKS
 * 
 * This document provides complete step-by-step explanation with code examples
 */

public class LockAcquisitionGuide {
    
    /*
    
    ╔═══════════════════════════════════════════════════════════════════════════╗
    ║  STEP-BY-STEP: HOW MULTIPLE THREADS ACQUIRE LOCKS IN EXECUTORSERVICE    ║
    ╚═══════════════════════════════════════════════════════════════════════════╝
    
    
    ═══════════════════════════════════════════════════════════════════════════
    QUESTION: "So how multiple threads acquire lock in this flow?"
    ═══════════════════════════════════════════════════════════════════════════
    
    ANSWER: It's a QUEUE-BASED SYSTEM with these phases:
    
    
    ═══════════════════════════════════════════════════════════════════════════
    PART 1: THREAD CREATION & SUBMISSION
    ═══════════════════════════════════════════════════════════════════════════
    
    CODE:
    ─────
    ExecutorService executor = Executors.newFixedThreadPool(3);
    for (int i = 1; i <= 5; i++) {
        executor.execute(new BookingTask(bookingSystem, i));
    }
    
    
    FLOW:
    ─────
    Main Thread
       │
       ├─ Creates ExecutorService with 3 physical threads
       │  ┌─────────────────────────────────┐
       │  │ Thread Pool                     │
       │  │ ┌──────────┐ [IDLE]             │
       │  │ │ Worker-1 │                    │
       │  │ ├──────────┤ [IDLE]             │
       │  │ │ Worker-2 │                    │
       │  │ ├──────────┤ [IDLE]             │
       │  │ │ Worker-3 │                    │
       │  │ └──────────┘                    │
       │  └─────────────────────────────────┘
       │
       ├─ Submits 5 BookingTasks
       │  (queued in executor's work queue)
       │
       └─ executor.shutdown()
          (stops accepting new tasks)
    
    
    ═══════════════════════════════════════════════════════════════════════════
    PART 2: INITIAL STATE - THREADS GET TASKS
    ═══════════════════════════════════════════════════════════════════════════
    
    WHEN: Tasks are submitted
    
    STATE:
    ─────
    ┌───────────────────────────────────────────────────────────┐
    │           ExecutorService Internal State                 │
    ├───────────────────────────────────────────────────────────┤
    │                                                           │
    │ Thread Pool:                                            │
    │ ┌──────────────┐  ┌──────────────┐  ┌──────────────┐   │
    │ │   Worker-1   │  │   Worker-2   │  │   Worker-3   │   │
    │ │ Executing:   │  │ Executing:   │  │   [IDLE]     │   │
    │ │ TaskA        │  │ TaskB        │  │              │   │
    │ └──────────────┘  └──────────────┘  └──────────────┘   │
    │                                                           │
    │ Task Queue (Waiting):                                   │
    │ ┌─────────────────────────────────────────────────────┐ │
    │ │ [TaskC] → [TaskD] → [TaskE] → (empty)              │ │
    │ └─────────────────────────────────────────────────────┘ │
    │                                                           │
    └───────────────────────────────────────────────────────────┘
    
    
    ═══════════════════════════════════════════════════════════════════════════
    PART 3: LOCK ACQUISITION - THE CRITICAL PART!
    ═══════════════════════════════════════════════════════════════════════════
    
    SCENARIO: TaskA, TaskB, TaskC all call lock.lock() almost simultaneously
    
    
    TIME T0 - TaskA runs first:
    ──────────────────────────
    
    TaskA code:
    ┌────────────────────────────────────┐
    │ public void bookTickets(...) {      │
    │     lock.lock();  ◄─── STEP 1      │
    │                                    │
    │     try {                          │
    │         // Critical Section ◄──── STEP 2
    │         availableSeats -= tickets; │
    │     }                              │
    │     finally {                      │
    │         lock.unlock();  ◄─── STEP 3
    │     }                              │
    │ }                                  │
    └────────────────────────────────────┘
    
    
    DETAILED FLOW AT T0:
    ────────────────────
    
    Step 1: TaskA.lock.lock()
    ├─ Calls lock.lock() on BookingSystem
    │  lock.lock() checks: Is lock held?
    │     │
    │     NO → Lock is FREE
    │     └─ TaskA ACQUIRES lock immediately
    │        Lock ownership: TaskA
    │        Lock state: LOCKED
    │
    ├─ TaskA continues to execute
    └─ Other tasks are BLOCKED on lock.lock()
    
    
    STATE AT T0:
    
    ┌─────────────────────────────────────┐
    │     BookingSystem (Shared)          │
    ├─────────────────────────────────────┤
    │                                     │
    │ LOCK:                               │
    │ ┌─────────────────────────────────┐ │
    │ │ State: LOCKED                   │ │
    │ │ Held By: TaskA (Worker-1)       │ │
    │ │ Lock Count: 1                   │ │
    │ └─────────────────────────────────┘ │
    │                                     │
    │ WAITING THREADS QUEUE:              │
    │ [empty for now]                     │
    │                                     │
    │ availableSeats: 20                  │
    │                                     │
    └─────────────────────────────────────┘
    
    TaskA Status: ✓ IN CRITICAL SECTION
    TaskB Status: ⏳ WAITING for lock
    TaskC Status: ⏳ WAITING for lock
    
    
    STEP 2: TaskA executes critical section
    ────────────────────────────────────────
    
    Current time inside critical section:
    ├─ Read: availableSeats = 20
    ├─ Simulate: Thread.sleep(150)  ◄─ Takes 150ms
    ├─ Compute: 20 - 2 (TaskA requests 2 seats) = 18
    ├─ Write: availableSeats = 18
    └─ (Other threads CANNOT interfere)
    
    
    STATE WHILE TaskA IN CRITICAL SECTION:
    
    Timeline:
    T0          T150ms (when TaskA finishes)
    │           │
    │ TaskA:    │════════════════════════════ (150ms in critical section)
    │           │
    │ TaskB:    │ BLOCKED (waiting for lock)
    │           │
    │ TaskC:    │ BLOCKED (waiting for lock)
    │
    
    ┌─────────────────────────────────────┐
    │     BookingSystem (Shared)          │
    ├─────────────────────────────────────┤
    │                                     │
    │ LOCK:                               │
    │ ┌─────────────────────────────────┐ │
    │ │ State: LOCKED                   │ │
    │ │ Held By: TaskA (Worker-1)       │ │
    │ │ Lock Count: 1                   │ │
    │ └─────────────────────────────────┘ │
    │                                     │
    │ WAITING THREADS QUEUE:              │
    │ [Worker-2 (TaskB)] ← First to wait  │
    │ [Worker-3 (TaskC)] ← Second to wait │
    │                                     │
    │ availableSeats: 20  ◄─ Only TaskA  │
    │                       can read this │
    │                                     │
    │ NOTE: Others see stale/cached value│
    │       until lock is released!       │
    │                                     │
    └─────────────────────────────────────┘
    
    
    STEP 3: TaskA releases lock
    ───────────────────────────
    
    At T150ms:
    └─ TaskA calls lock.unlock()
       │
       ├─ Releases lock
       ├─ Updates: Lock state = UNLOCKED
       ├─ Memory barrier: Flushes all changes to main memory
       │  (ensures other threads see updated availableSeats = 18)
       └─ Notifies: One waiting thread from queue
    
    
    ═══════════════════════════════════════════════════════════════════════════
    TIME T150ms - TaskB ACQUIRES LOCK
    ═══════════════════════════════════════════════════════════════════════════
    
    STATE: TaskA released lock
    
    OPERATION SYSTEM SCHEDULER:
    └─ Selects FIRST waiting thread from queue
       └─ That is: TaskB (was waiting in queue)
    
    
    WHAT HAPPENS:
    ─────────────
    ├─ TaskB was blocked on lock.lock()
    ├─ TaskB WAKES UP (notified by unlock)
    ├─ OS scheduler selects TaskB to acquire lock
    ├─ TaskB.lock.lock() NOW SUCCEEDS
    │  Lock ownership: TaskB
    │  Lock state: LOCKED
    ├─ TaskB can now read: availableSeats = 18 ◄─ UPDATED VALUE!
    │  (Memory barrier ensured visibility)
    └─ TaskB continues in critical section
    
    
    STATE AT T150ms (after TaskA releases, before TaskB starts):
    
    ┌─────────────────────────────────────┐
    │     BookingSystem (Shared)          │
    ├─────────────────────────────────────┤
    │                                     │
    │ LOCK:                               │
    │ ┌─────────────────────────────────┐ │
    │ │ State: LOCKED                   │ │
    │ │ Held By: TaskB (Worker-2) ◄─┐  │ │
    │ │ Lock Count: 1                   │ │
    │ └─────────────────────────────────┘ │
    │                                     │
    │ WAITING THREADS QUEUE:              │
    │ [Worker-3 (TaskC)]                  │
    │                                     │
    │ availableSeats: 18  ◄─ Updated!    │
    │ (All threads can see this now)      │
    │                                     │
    └─────────────────────────────────────┘
    
    
    TaskA Status: ✓ DONE (released lock, can do other work)
    TaskB Status: ✓ NOW IN CRITICAL SECTION
    TaskC Status: ⏳ WAITING for lock (in queue)
    
    
    CRITICAL INSIGHT: Memory Barrier
    ─────────────────────────────────
    
    When TaskA calls lock.unlock():
    └─ A MEMORY BARRIER is inserted
    
       TaskA's Changes  │ Memory Barrier │ TaskB's View
       availableSeats=18│────────────────│ availableSeats=18
       (in cache)       │ FLUSH TO RAM   │ (from cache/RAM)
    
    WITHOUT memory barrier:
    └─ TaskB might still see: availableSeats = 20 (old value)
       └─ RACE CONDITION! INCORRECT!
    
    WITH memory barrier (Java guarantees):
    └─ TaskB definitely sees: availableSeats = 18 (new value)
       └─ CORRECT!
    
    
    ═══════════════════════════════════════════════════════════════════════════
    COMPLETE TIMELINE
    ═══════════════════════════════════════════════════════════════════════════
    
    T0ms:         TaskA acquires lock
    ├─ Lock state: LOCKED (TaskA)
    ├─ TaskB: blocked on lock.lock()
    ├─ TaskC: blocked on lock.lock()
    └─ availableSeats: 20 (TaskA reading)
    
    T50ms:        TaskA still in critical section
    ├─ Lock state: LOCKED (TaskA)
    ├─ TaskB: still blocked
    ├─ TaskC: still blocked
    └─ availableSeats: being modified (not yet written)
    
    T150ms:       TaskA finishes and calls unlock()
    ├─ Memory barrier executed
    ├─ Lock state: now UNLOCKED
    ├─ availableSeats: 18 (NEW VALUE in memory)
    └─ OS scheduler wakes TaskB
    
    T151ms:       TaskB acquires lock
    ├─ Lock state: LOCKED (TaskB)
    ├─ TaskB: reading availableSeats = 18 ◄─ NEW VALUE
    ├─ TaskC: still blocked, now second in queue
    └─ availableSeats: 18 (TaskB reading)
    
    T160ms:       TaskB in critical section
    ├─ TaskB: modifying seats
    └─ Simulating processing
    
    T310ms:       TaskB finishes and calls unlock()
    ├─ Memory barrier executed
    ├─ Lock state: now UNLOCKED
    ├─ availableSeats: 15 (updated by TaskB)
    └─ OS scheduler wakes TaskC
    
    T311ms:       TaskC acquires lock
    ├─ Lock state: LOCKED (TaskC)
    ├─ TaskC: reading availableSeats = 15 ◄─ UPDATED AGAIN
    └─ And so on...
    
    
    ═══════════════════════════════════════════════════════════════════════════
    KEY POINTS - HOW LOCKS WORK WITH MULTIPLE THREADS
    ═══════════════════════════════════════════════════════════════════════════
    
    1. MUTUAL EXCLUSION (MUTEX)
       └─ Only ONE thread can hold the lock at any time
       └─ Other threads must WAIT
    
    2. WAITING QUEUE (FIFO)
       └─ Waiting threads are queued in order
       └─ First to wait is first to acquire (generally)
    
    3. BLOCKING OPERATION
       └─ lock.lock() BLOCKS if lock is held
       └─ Thread cannot proceed until lock is acquired
    
    4. MEMORY BARRIERS
       └─ lock.unlock() creates memory barrier
       └─ Ensures all changes are visible to next thread
    
    5. ATOMICITY
       └─ Operations inside critical section are "atomic"
       └─ No other thread can see partial updates
    
    6. REENTRANT (for ReentrantLock)
       └─ Same thread can acquire same lock multiple times
       └─ Must unlock same number of times to release
    
    
    ═══════════════════════════════════════════════════════════════════════════
    WITHOUT LOCK - WHAT WOULD HAPPEN (RACE CONDITION)
    ═══════════════════════════════════════════════════════════════════════════
    
    public void bookTickets_UNSAFE(int ticketsToBook) {
        // NO LOCK!
        availableSeats -= ticketsToBook;  ◄─ RACE CONDITION HERE
    }
    
    
    RACE CONDITION SCENARIO:
    ────────────────────────
    
    Initial: availableSeats = 20
    
    T0:   TaskA reads: value = 20
    T1:   TaskB reads: value = 20  ◄─ Both read same value!
    T2:   TaskA calculates: 20 - 2 = 18
    T3:   TaskB calculates: 20 - 3 = 17
    T4:   TaskA writes: availableSeats = 18
    T5:   TaskB writes: availableSeats = 17  ◄─ OVERWRITES TaskA's change!
    
    Expected result: 20 - 2 - 3 = 15
    Actual result: 17  ◄─ WRONG! Lost TaskA's decrement!
    
    This is called "Lost Update" bug
    
    
    WITH LOCK - CORRECT BEHAVIOR:
    ──────────────────────────────
    
    public void bookTickets_SAFE(int ticketsToBook) {
        lock.lock();  ◄─ Only one thread at a time
        try {
            availableSeats -= ticketsToBook;  ◄─ No race condition
        } finally {
            lock.unlock();
        }
    }
    
    
    CORRECT EXECUTION:
    ──────────────────
    
    Initial: availableSeats = 20
    
    T0:   TaskA acquires lock
    T1:   TaskA reads: value = 20
    T2:   TaskA calculates: 20 - 2 = 18
    T3:   TaskA writes: availableSeats = 18
    T4:   TaskA releases lock ──┐
    T5:                         ├─ Memory barrier ensures visibility
    T6:   TaskB acquires lock   │
    T7:   TaskB reads: value = 18  ◄─ Sees TaskA's update!
    T8:   TaskB calculates: 18 - 3 = 15
    T9:   TaskB writes: availableSeats = 15
    
    Expected result: 20 - 2 - 3 = 15
    Actual result: 15  ✓ CORRECT!
    
    
    ═══════════════════════════════════════════════════════════════════════════
    REAL-WORLD ANALOGY
    ═══════════════════════════════════════════════════════════════════════════
    
    Think of a BATHROOM (shared resource) with a LOCK (door):
    
    WITHOUT LOCK (Race Condition):
    ──────────────────────────────
    ├─ Person-A enters bathroom (no lock)
    ├─ Person-B enters bathroom simultaneously (no lock)
    ├─ Person-C enters bathroom simultaneously (no lock)
    ├─ All three are using the bathroom at once
    ├─ Chaos! Collisions!
    └─ Resource damaged!
    
    
    WITH LOCK (Mutual Exclusion):
    ──────────────────────────────
    ├─ Person-A acquires lock (locks door)
    ├─ Person-B tries to enter (door locked, WAITS in queue)
    ├─ Person-C tries to enter (door locked, WAITS in queue)
    ├─ Person-A finishes and unlocks door
    ├─ Person-B acquires lock (enters and locks door)
    ├─ Person-A and Person-C wait
    ├─ Person-B finishes and unlocks door
    ├─ Person-C acquires lock (enters and locks door)
    └─ Order maintained, no conflicts!
    
    
    ═══════════════════════════════════════════════════════════════════════════
    COMMON QUESTIONS ANSWERED
    ═══════════════════════════════════════════════════════════════════════════
    
    Q1: How does thread know lock is available?
    A:  Operating System maintains lock state
        └─ Thread checks lock status before attempting to acquire
    
    Q2: What happens if thread holds lock forever?
    A:  Other threads wait indefinitely (DEADLOCK risk)
        └─ Always use try-finally to ensure unlock
    
    Q3: Can same thread acquire lock twice?
    A:  YES with ReentrantLock
        └─ NO with regular Lock
        └─ Must unlock same number of times
    
    Q4: Is there performance cost?
    A:  YES!
        └─ Lock.lock() and unlock() take CPU time
        └─ Waiting threads consume CPU cycles
        └─ Critical section should be as small as possible
    
    Q5: What if exceptions occur in critical section?
    A:  Lock stays acquired (thread doesn't release)
        └─ Other threads deadlock waiting for lock
        └─ Use try-finally to guarantee unlock
    
    Q6: Is lock acquisition FIFO?
    A:  Generally, but not guaranteed
        └─ Use ReentrantLock(true) for fair locking
    
    Q7: How many threads can acquire lock?
    A:  Only ONE at a time
        └─ That's the definition of a Mutex Lock
    
    Q8: How does memory barrier work?
    A:  It flushes CPU caches to main memory
        └─ Ensures all writes before unlock are visible
        └─ And all reads after lock see latest values
    
    
    ═══════════════════════════════════════════════════════════════════════════
    VISUAL SUMMARY
    ═══════════════════════════════════════════════════════════════════════════
    
    LOCK LIFECYCLE:
    
    State 0 - UNLOCKED (initial state)
    │
    ├─ Thread-1 calls lock.lock()
    │
    ▼
    State 1 - LOCKED (held by Thread-1)
    │
    ├─ Thread-2 calls lock.lock() → BLOCKED
    ├─ Thread-3 calls lock.lock() → BLOCKED
    │
    │ [Thread-1 in critical section]
    │
    ├─ Thread-1 calls lock.unlock()
    │
    ▼
    State 2 - UNLOCKED (Thread-1 released)
    │
    ├─ Thread-2 wakes up and acquires lock
    │
    ▼
    State 3 - LOCKED (held by Thread-2)
    │
    └─ [Process repeats...]
    
    
    ═══════════════════════════════════════════════════════════════════════════
    CODE PATTERN TO REMEMBER
    ═══════════════════════════════════════════════════════════════════════════
    
    Lock lock = new ReentrantLock();
    
    public void criticalOperation() {
        lock.lock();      // ◄─ ACQUIRE
        try {
            // ONLY ONE THREAD HERE AT ANY TIME
            // Modify shared resources safely
        } finally {
            lock.unlock(); // ◄─ RELEASE (always, even on exception)
        }
    }
    
    
    WHY try-finally?
    ────────────────
    ├─ If exception occurs, finally block still executes
    ├─ Ensures unlock happens even on error
    ├─ Prevents deadlock from hanging locks
    └─ This is CRITICAL for thread safety!
    
    
    ═══════════════════════════════════════════════════════════════════════════
    
    */
    
}
