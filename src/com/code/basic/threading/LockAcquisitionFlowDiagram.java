package com.code.basic.threading;

/**
 * COMPLETE VISUAL EXPLANATION: HOW LOCKS WORK WITH MULTIPLE THREADS
 * 
 * This file contains detailed diagrams and explanations of lock acquisition flow
 */

/*

╔════════════════════════════════════════════════════════════════════════════════╗
║                   LOCK ACQUISITION FLOW - VISUAL EXPLANATION                   ║
╚════════════════════════════════════════════════════════════════════════════════╝

SCENARIO: 5 Customers (Thread-1 to Thread-5) trying to book movie tickets
Available Threads in Pool: 3 (Thread Pool Size)
Lock: ReentrantLock on BookingSystem


═══════════════════════════════════════════════════════════════════════════════
PHASE 1: THREADS SUBMITTED TO EXECUTOR (Time: T0)
═══════════════════════════════════════════════════════════════════════════════

   Main Thread
        │
        ├─> executor.execute(Task for Customer 1)
        ├─> executor.execute(Task for Customer 2)
        ├─> executor.execute(Task for Customer 3)
        ├─> executor.execute(Task for Customer 4)
        └─> executor.execute(Task for Customer 5)
        
        │
        ▼
   ┌─────────────────────────────────┐
   │   ExecutorService Thread Pool   │
   │   (newFixedThreadPool(3))        │
   │   ┌──────────┐                  │
   │   │ Thread-A │ [IDLE]            │
   │   │ Thread-B │ [IDLE]            │
   │   │ Thread-C │ [IDLE]            │
   │   └──────────┘                  │
   │                                 │
   │   Waiting Queue:                │
   │   ┌──────────────────────────┐  │
   │   │ Customer-1               │  │
   │   │ Customer-2               │  │
   │   │ Customer-3               │  │
   │   │ Customer-4               │  │
   │   │ Customer-5               │  │
   │   └──────────────────────────┘  │
   └─────────────────────────────────┘


═══════════════════════════════════════════════════════════════════════════════
PHASE 2: THREADS START EXECUTING (Time: T0 + 1ms)
═══════════════════════════════════════════════════════════════════════════════

   ┌─────────────────────────────────┐
   │   ExecutorService Thread Pool   │
   │   ┌──────────┐                  │
   │   │ Thread-A │ ─> Customer-1 ──> "Trying to acquire lock..."
   │   │ Thread-B │ ─> Customer-2 ──> "Trying to acquire lock..."
   │   │ Thread-C │ ─> Customer-3 ──> "Trying to acquire lock..."
   │   └──────────┘                  │
   │                                 │
   │   Waiting Queue:                │
   │   ┌──────────────────────────┐  │
   │   │ Customer-4               │  │
   │   │ Customer-5               │  │
   │   └──────────────────────────┘  │
   └─────────────────────────────────┘
                       │
                       ▼
              ┌──────────────────┐
              │ BookingSystem    │
              │                  │
              │  Lock (Mutex)    │ ◄───── Only ONE thread at a time!
              │  ┌────────────┐  │
              │  │ [ UNLOCKED]│  │
              │  └────────────┘  │
              │                  │
              │ Available Seats: │
              │      20          │
              └──────────────────┘


═══════════════════════════════════════════════════════════════════════════════
PHASE 3: FIRST THREAD ACQUIRES LOCK (Time: T0 + 2ms) - CRITICAL!
═══════════════════════════════════════════════════════════════════════════════

   Thread-A: lock.lock() ──► SUCCEEDS! Lock acquired!

   ┌─────────────────────────────────┐
   │   ExecutorService Thread Pool   │
   │   ┌──────────┐                  │
   │   │ Thread-A │ ──► INSIDE CRITICAL SECTION
   │   │ Thread-B │ ──► BLOCKED (waiting for lock)
   │   │ Thread-C │ ──► BLOCKED (waiting for lock)
   │   └──────────┘                  │
   └─────────────────────────────────┘
                       │
                       ▼
              ┌──────────────────┐
              │ BookingSystem    │
              │                  │
              │  Lock (Mutex)    │
              │  ┌────────────┐  │
              │  │ [LOCKED]   │◄─────────── Held by: Thread-A
              │  │ by Thread-A│  │
              │  └────────────┘  │
              │                  │
              │ Waiting Queue:   │
              │ ┌──────────────┐ │
              │ │ Thread-B     │ │
              │ │ Thread-C     │ │
              │ └──────────────┘ │
              │                  │
              │ Available Seats: │
              │      20 (READ)   │
              └──────────────────┘

   Thread-A is now in CRITICAL SECTION:
   ├─ Read availableSeats = 20
   ├─ Check if 20 >= numberOfTickets? YES
   ├─ availableSeats = 20 - numberOfTickets
   ├─ Write updated value to memory
   └─ (Other threads cannot see this data yet)


═══════════════════════════════════════════════════════════════════════════════
PHASE 4: THREAD-A STILL HOLDING LOCK - THREAD-B WAITS
═══════════════════════════════════════════════════════════════════════════════

   Thread-A: Still in critical section (lock.lock() still active)
   Thread-B: Calls lock.lock() → BLOCKS HERE (cannot proceed)
   Thread-C: Calls lock.lock() → BLOCKS HERE (cannot proceed)

   Timeline:
   Thread-A:  |--- In Critical Section ---|
   Thread-B:  |-- BLOCKED ON LOCK.LOCK()----|  (Wait queue)
   Thread-C:  |-- BLOCKED ON LOCK.LOCK()----|  (Wait queue)
              T0              T1           T2

   Memory State (ONLY Thread-A can see these changes):
   - availableSeats = 17 (after booking 3 seats)


═══════════════════════════════════════════════════════════════════════════════
PHASE 5: THREAD-A RELEASES LOCK (Time: T0 + 155ms)
═══════════════════════════════════════════════════════════════════════════════

   Thread-A: lock.unlock() ──► LOCK RELEASED!

   ┌─────────────────────────────────┐
   │   ExecutorService Thread Pool   │
   │   ┌──────────┐                  │
   │   │ Thread-A │ ──► CRITICAL SECTION ENDED
   │   │ Thread-B │ ──► (can now try to acquire lock)
   │   │ Thread-C │ ──► (can now try to acquire lock)
   │   └──────────┘                  │
   └─────────────────────────────────┘
                       │
                       ▼
              ┌──────────────────┐
              │ BookingSystem    │
              │                  │
              │  Lock (Mutex)    │
              │  ┌────────────┐  │
              │  │ [UNLOCKED] │  │ ◄───── NOW AVAILABLE!
              │  └────────────┘  │
              │                  │
              │ Available Seats: │
              │      17 (UPDATED)│  ◄───── All threads see this now!
              └──────────────────┘

   IMPORTANT: Memory barrier ensures Thread-B and Thread-C see the
              updated value (availableSeats = 17) when they acquire lock!


═══════════════════════════════════════════════════════════════════════════════
PHASE 6: NEXT THREAD ACQUIRES LOCK (Time: T0 + 156ms)
═══════════════════════════════════════════════════════════════════════════════

   Lock released → Operating System schedules one of waiting threads
   Thread-B: lock.lock() ──► SUCCEEDS! Now holds the lock

   ┌─────────────────────────────────┐
   │   ExecutorService Thread Pool   │
   │   ┌──────────┐                  │
   │   │ Thread-A │ ──► DONE (waiting for next task)
   │   │ Thread-B │ ──► NOW IN CRITICAL SECTION
   │   │ Thread-C │ ──► BLOCKED (waiting for lock)
   │   └──────────┘                  │
   └─────────────────────────────────┘
                       │
                       ▼
              ┌──────────────────┐
              │ BookingSystem    │
              │                  │
              │  Lock (Mutex)    │
              │  ┌────────────┐  │
              │  │ [LOCKED]   │◄─────────── Held by: Thread-B
              │  │ by Thread-B│  │
              │  └────────────┘  │
              │                  │
              │ Available Seats: │
              │      17 (READ)   │
              └──────────────────┘

   Thread-B now sees:
   - availableSeats = 17
   - Updates it based on booking
   - Other threads see the update after unlock


═══════════════════════════════════════════════════════════════════════════════
KEY CONCEPT: MUTUAL EXCLUSION (MUTEX)
═══════════════════════════════════════════════════════════════════════════════

   ❌ WITHOUT LOCK (Race Condition):
   
      Thread-A reads: availableSeats = 20
      Thread-B reads: availableSeats = 20  ◄─── PROBLEM!
      Thread-A writes: availableSeats = 18
      Thread-B writes: availableSeats = 19  ◄─── BOTH lost one update!
      
      Expected: 20 - 1 - 1 = 18
      Actual:   19  ◄───── BUG! Lost update!


   ✓ WITH LOCK (Correct Execution):
   
      Thread-A acquires lock
      Thread-A reads: availableSeats = 20
      Thread-A writes: availableSeats = 18
      Thread-A releases lock
      
      Thread-B acquires lock
      Thread-B reads: availableSeats = 18  ◄─── CORRECT! Sees latest value
      Thread-B writes: availableSeats = 17
      Thread-B releases lock
      
      Expected: 20 - 1 - 1 = 18
      Actual:   17  ✓ CORRECT!


═══════════════════════════════════════════════════════════════════════════════
CODE EXECUTION SEQUENCE IN DETAIL
═══════════════════════════════════════════════════════════════════════════════

   public void bookTickets(int customerId, int numberOfTickets) {
       lock.lock();  // ◄───── STEP 1: TRY TO ACQUIRE LOCK
                     //        If locked: WAIT HERE (blocked)
                     //        If unlocked: ACQUIRE IT and continue
                     
       try {
           // ◄───── STEP 2: NOW IN CRITICAL SECTION
           // Only ONE thread executes here at any given time
           
           if (availableSeats >= numberOfTickets) {  // ◄─ READ
               availableSeats -= numberOfTickets;     // ◄─ MODIFY
               // Booking successful
           }
           // No other thread can interfere here!
           
       } finally {
           lock.unlock();  // ◄───── STEP 3: RELEASE LOCK
                          //        Next waiting thread can acquire it
       }
   }

   Timeline for 3 threads:
   ┌─────────────────────────────────────────────┐
   │ Thread-A │ lock │ Critical Section │ unlock  │ (0-155ms)
   │          │      │                  │        │
   │ Thread-B │                         lock │ Critical Section │ (157-312ms)
   │          │                         │      │
   │ Thread-C │                                 lock │ ... (313+ms)
   └─────────────────────────────────────────────┘


═══════════════════════════════════════════════════════════════════════════════
REENTRANT LOCK - SAME THREAD CAN ACQUIRE AGAIN
═══════════════════════════════════════════════════════════════════════════════

   ReentrantLock allows the SAME thread to acquire the lock multiple times:

   Thread-A:
   ├─ lock.lock();        // Acquisition count = 1
   │  ├─ lock.lock();     // Acquisition count = 2 (same thread, OK!)
   │  │  // Critical section
   │  └─ lock.unlock();   // Acquisition count = 1
   └─ lock.unlock();      // Acquisition count = 0 (lock released)

   Regular Lock would DEADLOCK if same thread tries again!


═══════════════════════════════════════════════════════════════════════════════
LOCK WAIT QUEUE - ORDER MATTERS
═══════════════════════════════════════════════════════════════════════════════

   Current Lock Holder: Thread-B
   
   Waiting Threads Queue:
   ┌──────────────────────────────────┐
   │ Front → Thread-C                 │  ◄─ First to acquire after B releases
   │         Thread-A                 │
   │         Thread-E                 │
   │ Back →  Thread-D                 │  ◄─ Last to acquire
   └──────────────────────────────────┘

   When Thread-B releases lock:
   Thread-C is selected (FIFO by default with ReentrantLock)


═══════════════════════════════════════════════════════════════════════════════
MEMORY VISIBILITY - HAPPENS-BEFORE GUARANTEES
═══════════════════════════════════════════════════════════════════════════════

   lock.lock() ──────┐
   (critical section) ├─ Creates HAPPENS-BEFORE relationship
   lock.unlock() ────┘

   Thread-A writes availableSeats = 17
   Thread-A calls lock.unlock()
   
   ┌────────────────────────┐
   │ Memory Barrier         │  ◄─ Ensures visibility to other threads
   └────────────────────────┘
   
   Thread-B calls lock.lock()
   Thread-B reads availableSeats  ──► SEES updated value (17)

   WITHOUT memory barriers, Thread-B might see cached/stale value!


═══════════════════════════════════════════════════════════════════════════════
PERFORMANCE IMPACT OF LOCKING
═══════════════════════════════════════════════════════════════════════════════

   With 3 threads and 1 lock:

   Time →
   ────────────────────────────────────────────────────────

   Thread-A:  ║ lock ║════ CS ════║ unlock ║ idle  ║
              155ms   150ms        10ms    

   Thread-B:           ║ lock ║════ CS ════║ unlock ║ idle  ║
                       (wait) 150ms  150ms  10ms

   Thread-C:                   ║ lock ║════ CS ════║ unlock ║
                               (wait) 150ms 150ms  10ms

   Total Time: ~3 × 160ms = 480ms


═══════════════════════════════════════════════════════════════════════════════
SUMMARY: HOW MULTIPLE THREADS ACQUIRE LOCK
═══════════════════════════════════════════════════════════════════════════════

   1. Thread calls lock.lock()
      ├─ If lock is FREE: acquires it immediately, continues
      └─ If lock is HELD by another thread:
         └─ Thread BLOCKS (waits in queue)

   2. While holding lock:
      ├─ Thread executes critical section
      ├─ Other threads cannot enter critical section
      └─ Other threads cannot see incomplete updates

   3. Thread calls lock.unlock()
      ├─ Lock is released
      ├─ Memory barrier ensures visibility
      └─ Next waiting thread from queue is selected

   4. Process repeats for next thread

   Result: MUTUAL EXCLUSION ─► DATA CONSISTENCY ─► NO RACE CONDITIONS

*/

public class LockAcquisitionFlowDiagram {
    // This is a documentation class - see the multi-line comment above for details
}
