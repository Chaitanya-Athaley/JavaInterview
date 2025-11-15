package com.code.basic.threading;

/**
 * QUICK REFERENCE: LOCK ACQUISITION FOR MULTIPLE THREADS
 */
public class QuickReferenceLocks {

/*

╔═══════════════════════════════════════════════════════════════════════════╗
║                         QUICK REFERENCE GUIDE                            ║
║              HOW MULTIPLE THREADS ACQUIRE LOCKS                          ║
╚═══════════════════════════════════════════════════════════════════════════╝


QUESTION: "So how multiple threads acquire lock in this flow?"
──────────────────────────────────────────────────────────────

ANSWER IN ONE SENTENCE:
───────────────────────
"Threads queue up to acquire the lock one at a time - when the current holder
releases it, the next waiting thread from the queue gets it."


┌──────────────────────────────────────────────────────────────────────────┐
│ THE BASIC FLOW                                                           │
└──────────────────────────────────────────────────────────────────────────┘

1. Thread calls lock.lock()
   ├─ If UNLOCKED → Thread acquires it immediately
   └─ If LOCKED → Thread goes to waiting queue (BLOCKS)

2. Thread now in "Critical Section" (protected code)
   ├─ Only THIS thread executes here
   └─ Other threads CANNOT interfere

3. Thread calls lock.unlock()
   ├─ Lock is released
   └─ Next waiting thread wakes up and acquires lock

4. Repeat for next thread


┌──────────────────────────────────────────────────────────────────────────┐
│ VISUAL: LOCK QUEUE                                                       │
└──────────────────────────────────────────────────────────────────────────┘

Step 1: Initial State (Lock Available)
──────────────────────────────────────

    Thread-A: lock.lock() ─────► ACQUIRED
    Thread-B: lock.lock() ─────► BLOCKED (waiting)
    Thread-C: lock.lock() ─────► BLOCKED (waiting)

    ┌─────────────────────┐
    │ LOCK: [OWNED BY: A] │
    ├─────────────────────┤
    │ Wait Queue:         │
    │ [B] → [C]          │
    └─────────────────────┘


Step 2: Thread-A in Critical Section
─────────────────────────────────────

    Thread-A: [IN CRITICAL SECTION] (150ms) ✓ Holding lock
    Thread-B: BLOCKED (1st in queue)        ⏳ Waiting
    Thread-C: BLOCKED (2nd in queue)        ⏳ Waiting

    availableSeats = 20
    (only A can see/modify)


Step 3: Thread-A Releases Lock
───────────────────────────────

    Thread-A: lock.unlock() ─────► RELEASED
    availableSeats = 18 (updated)

    ┌─────────────────────┐
    │ LOCK: [UNLOCKED]    │
    ├─────────────────────┤
    │ Wait Queue:         │
    │ [B] → [C]          │
    └─────────────────────┘
    ↑
    Next thread (B) will acquire


Step 4: Thread-B Acquires Lock
──────────────────────────────

    Thread-A: (done, idle)                     ✓ Released
    Thread-B: lock.lock() ─────► ACQUIRED      ✓ Now holding
    Thread-C: BLOCKED (1st in queue)           ⏳ Waiting

    ┌─────────────────────┐
    │ LOCK: [OWNED BY: B] │
    ├─────────────────────┤
    │ Wait Queue:         │
    │ [C]                │
    └─────────────────────┘

    availableSeats = 18 (B reads this updated value)
    (B can now see/modify)


Step 5: Thread-B Releases Lock
───────────────────────────────

    Thread-B: lock.unlock() ─────► RELEASED
    availableSeats = 14 (updated by B)

    ┌─────────────────────┐
    │ LOCK: [UNLOCKED]    │
    ├─────────────────────┤
    │ Wait Queue:         │
    │ [C]                │
    └─────────────────────┘
    ↑
    Next thread (C) will acquire


Step 6: Thread-C Acquires Lock
──────────────────────────────

    Thread-A: (done)                           ✓ Released
    Thread-B: (done)                           ✓ Released
    Thread-C: lock.lock() ─────► ACQUIRED      ✓ Now holding

    availableSeats = 14 (C reads this updated value)
    (C can now see/modify)


┌──────────────────────────────────────────────────────────────────────────┐
│ TIMELINE                                                                 │
└──────────────────────────────────────────────────────────────────────────┘

Time (ms)    Thread-A          Thread-B          Thread-C         Lock Status
─────────────────────────────────────────────────────────────────────────────
0            lock.lock() ✓     lock.lock() ⏳     lock.lock() ⏳   Held by A
             (acquired)        (blocked)         (blocked)

1-150        [CS: active]      (blocked)         (blocked)        Held by A

151          unlock()          (wakes up)        (blocked)        Released
             (released)        

152          (done)            lock.lock() ✓     lock.lock() ⏳   Held by B
                               (acquired)        (blocked)

153-310      (idle)            [CS: active]      (blocked)        Held by B

311          (idle)            unlock()          (wakes up)        Released
                               (released)

312          (idle)            (done)            lock.lock() ✓    Held by C
                                                 (acquired)

313-470      (idle)            (idle)            [CS: active]     Held by C

471          (idle)            (idle)            unlock()         Released


┌──────────────────────────────────────────────────────────────────────────┐
│ MEMORY VISIBILITY: WHY UNLOCK MATTERS                                    │
└──────────────────────────────────────────────────────────────────────────┘

Without lock.unlock():
──────────────────────

Thread-A writes: availableSeats = 18 (to CPU cache, not main memory)
Thread-B reads: availableSeats = 20 ✗ STALE VALUE (from cache)

    CPU-A Cache      CPU-B Cache
    availableSeats:18  availableSeats:20 ← Inconsistent!
         │                │
         Main Memory: availableSeats: ??? (uncertain)


With lock.unlock():
───────────────────

Thread-A calls lock.unlock()
├─ Memory barrier flushes CPU cache to main memory
└─ availableSeats = 18 goes to main memory

Thread-B calls lock.lock()
├─ Memory barrier flushes CPU cache first
├─ Then reads from main memory
└─ availableSeats = 18 ✓ CORRECT VALUE

    CPU-A Cache      Main Memory      CPU-B Cache
    availableSeats:18    │            availableSeats:18
         │                ▼                │
         └─ FLUSH to 18 ──► (barrier) ─ FETCH 18
                            │
                        availableSeats:18 ✓ Synchronized


┌──────────────────────────────────────────────────────────────────────────┐
│ CRITICAL CODE PATTERN                                                    │
└──────────────────────────────────────────────────────────────────────────┘

Lock lock = new ReentrantLock();

public void modifySharedData(int value) {
    lock.lock();  // ◄─────────── STEP 1: ACQUIRE
    try {
        // ┌─────────────────────────────────┐
        // │ CRITICAL SECTION                │
        // │ Only one thread here at a time  │
        // │                                 │
        availableSeats -= value;    // STEP 2: MODIFY
        // │                                 │
        // └─────────────────────────────────┘
    } finally {
        lock.unlock();  // ◄────── STEP 3: RELEASE (ALWAYS!)
    }
}

WHY try-finally?
┌──────────────────────────────────────────────────────────┐
│ If exception happens in critical section:               │
│ ├─ Without finally: lock.unlock() never called ✗        │
│ │                 Other threads deadlock forever        │
│ ├─ With finally: lock.unlock() ALWAYS called ✓          │
│ │               Lock is released, no deadlock           │
│                                                           │
│ finally block executes even on exception!               │
└──────────────────────────────────────────────────────────┘


┌──────────────────────────────────────────────────────────────────────────┐
│ HOW EXECUTORSERVICE FITS IN                                              │
└──────────────────────────────────────────────────────────────────────────┘

ExecutorService executor = Executors.newFixedThreadPool(3);

Creates 3 WORKER THREADS:
├─ Worker-1
├─ Worker-2
└─ Worker-3

When you submit 5 tasks:

executor.execute(Task-1); ─┐
executor.execute(Task-2); ─┼─► Task Queue
executor.execute(Task-3); ─┤
executor.execute(Task-4); ─┤
executor.execute(Task-5); ─┘

Execution:

Time 0:  Worker-1 grabs Task-1
         Worker-2 grabs Task-2
         Worker-3 grabs Task-3
         Tasks 4-5 wait in queue

Time 1:  Worker-1 finishes Task-1, grabs Task-4
         Worker-2 busy with Task-2
         Worker-3 busy with Task-3

Time 2:  Worker-1 busy with Task-4
         Worker-2 finishes Task-2, grabs Task-5
         Worker-3 busy with Task-3

So locks are acquired by different WORKER THREADS, not main thread!


┌──────────────────────────────────────────────────────────────────────────┐
│ KEY TERMS                                                                │
└──────────────────────────────────────────────────────────────────────────┘

LOCK/MUTEX:
└─ A resource that ensures only one thread can access critical section

ACQUIRE/LOCK:
└─ Action of gaining ownership of a lock

RELEASE/UNLOCK:
└─ Action of giving up ownership of a lock

CRITICAL SECTION:
└─ Code protected by lock (only one thread at a time)

WAIT QUEUE:
└─ Queue of threads waiting for lock

BLOCKED:
└─ Thread cannot proceed, waiting for lock

REENTRANT:
└─ Same thread can acquire same lock multiple times

DEADLOCK:
└─ Threads waiting for each other forever

RACE CONDITION:
└─ Unpredictable behavior when threads access shared data

MEMORY BARRIER:
└─ Ensures CPU cache is synchronized with main memory


┌──────────────────────────────────────────────────────────────────────────┐
│ COMMON QUESTIONS                                                         │
└──────────────────────────────────────────────────────────────────────────┘

Q: Can 2 threads hold lock simultaneously?
A: NO - that's the definition of a lock!

Q: What if lock holder crashes?
A: Other threads wait forever (deadlock)
   └─ Use timeouts: lock.tryLock(1, TimeUnit.SECONDS)

Q: Can same thread acquire lock twice?
A: YES with ReentrantLock (counts acquistions)
   NO with regular Lock (deadlock)

Q: Is lock acquisition FIFO?
A: Generally, but not guaranteed by spec

Q: What's the performance cost?
A: 
   ├─ lock.lock(): ~100-200 nanoseconds
   ├─ lock.unlock(): ~100-200 nanoseconds
   └─ Thread waiting: ~microseconds to milliseconds

Q: When should I use locks?
A: Whenever multiple threads access shared data

Q: Any alternative to locks?
A: Yes - Atomic classes, volatile, synchronized keyword
   └─ But ReentrantLock is most explicit and flexible


┌──────────────────────────────────────────────────────────────────────────┐
│ REMEMBER                                                                 │
└──────────────────────────────────────────────────────────────────────────┘

1. ✓ Use try-finally to ensure unlock
2. ✓ Keep critical section as small as possible
3. ✓ Minimize lock contention
4. ✓ Always pair lock.lock() with lock.unlock()
5. ✓ Understand memory barriers (visibility)
6. ✗ Don't hold locks across I/O operations
7. ✗ Don't acquire multiple locks (risk of deadlock)
8. ✗ Don't forget to release locks


╔═══════════════════════════════════════════════════════════════════════════╗
║                              SUMMARY                                      ║
╠═══════════════════════════════════════════════════════════════════════════╣
║ Multiple threads acquire lock in a QUEUE:                                ║
║                                                                           ║
║ Thread-1 acquires → Critical Section → Releases                          ║
║                                          │                               ║
║                    Thread-2 acquires → Critical Section → Releases       ║
║                                          │                               ║
║                                Thread-3 acquires → Critical Section      ║
║                                                                           ║
║ Result: Mutual Exclusion → Data Consistency → No Race Conditions        ║
╚═══════════════════════════════════════════════════════════════════════════╝

*/

}
