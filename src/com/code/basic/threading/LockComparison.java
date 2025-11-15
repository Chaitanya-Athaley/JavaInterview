package com.code.basic.threading;

/**
 * VISUAL COMPARISON: WITH LOCK vs WITHOUT LOCK
 */
public class LockComparison {
    
    /*
    
    ╔═══════════════════════════════════════════════════════════════════════════╗
    ║           WITHOUT LOCK: RACE CONDITION vs WITH LOCK: SAFE               ║
    ╚═══════════════════════════════════════════════════════════════════════════╝
    
    
    ═══════════════════════════════════════════════════════════════════════════
    SCENARIO: 3 Threads booking movie tickets simultaneously
    ═══════════════════════════════════════════════════════════════════════════
    
    Initial State: availableSeats = 20
    
    Thread-A wants to book 5 seats
    Thread-B wants to book 4 seats
    Thread-C wants to book 6 seats
    
    Expected result: 20 - 5 - 4 - 6 = 5 seats remaining
    
    
    ═══════════════════════════════════════════════════════════════════════════
    SCENARIO 1: WITHOUT LOCK (RACE CONDITION - UNSAFE)
    ═══════════════════════════════════════════════════════════════════════════
    
    CODE:
    ─────
    public void bookTickets_UNSAFE(int tickets) {
        availableSeats -= tickets;  // NOT PROTECTED
    }
    
    
    EXECUTION TIMELINE:
    ──────────────────
    
    Time │ Thread-A           │ Thread-B           │ Thread-C           │ availableSeats
    ─────┼────────────────────┼────────────────────┼────────────────────┼─────────────
    T0   │ READ: 20           │                    │                    │ 20
         │                    │                    │                    │
    T1   │ (compute 20-5=15)  │ READ: 20           │                    │ 20
         │                    │ (compute 20-4=16)  │                    │
         │                    │                    │                    │
    T2   │ (still computing)  │ (still computing)  │ READ: 20           │ 20
         │                    │                    │ (compute 20-6=14)  │
         │                    │                    │                    │
    T3   │ WRITE: 15 ◄────────┤                    │                    │ 15
         │ (writes result)    │ WRITE: 16 ◄────┐  │                    │ 16 [OVERWRITE!]
         │                    │                │   │                    │
    T4   │ (finished)         │ (finished)     │   │ WRITE: 14 ◄────┐  │ 14 [OVERWRITE!]
         │                    │                │   │                │  │
    
    
    PROBLEM - LOST UPDATES:
    ───────────────────────
    
    ┌─────────────────────────────────────────────────────┐
    │ T0:   All 3 threads read: 20                       │
    │       (They all see the SAME initial value)        │
    │                                                     │
    │ T1-T2: All 3 compute INDEPENDENTLY:               │
    │       A: 20 - 5 = 15                              │
    │       B: 20 - 4 = 16  ◄─ Based on same value!    │
    │       C: 20 - 6 = 14                              │
    │                                                     │
    │ T3-T4: All 3 write SEQUENTIALLY:                  │
    │       A writes 15                                  │
    │       B writes 16 ◄─── OVERWRITES A's update!    │
    │       C writes 14 ◄─── OVERWRITES B's update!    │
    │                                                     │
    │ FINAL: availableSeats = 14                        │
    │                                                     │
    │ Expected: 20 - 5 - 4 - 6 = 5                     │
    │ Actual:   14  ◄───── LOST 5 - 14 = 11 updates!   │
    └─────────────────────────────────────────────────────┘
    
    
    WHY THIS HAPPENS:
    ──────────────────
    
    The problem is the READ-MODIFY-WRITE sequence is NOT ATOMIC:
    
    ┌─────────────┬──────────┬─────────────┐
    │ STEP 1      │ STEP 2   │ STEP 3      │
    │ READ        │ MODIFY   │ WRITE       │
    ├─────────────┼──────────┼─────────────┤
    │ availableSeats=20  │  value-tickets │ availableSeats=result
    │ (can be                │           
    │  interrupted         
    │  between steps)      
    └─────────────┴──────────┴─────────────┘
       ▲              ▲         ▲
       │              │         │
    Another thread can READ here, but sees old data
                      Another thread can READ after WRITE
    
    
    SEQUENCE DIAGRAM:
    ─────────────────
    
    Thread-A:     [READ 20]─(compute)─┐
                                      ├─[WRITE 15]─(done)
    Thread-B:          [READ 20]─(compute)─┐
                                           ├─[WRITE 16]─(done) ◄─ Overwrites A
    Thread-C:               [READ 20]─(compute)─┐
                                                ├─[WRITE 14]─(done) ◄─ Overwrites B
    
    availableSeats:  20 ──► 20 ──► 20 ──► 15 ──► 16 ──► 14
                     (all see same) (writes overwrite each other)
    
    
    MEMORY VISIBILITY ISSUE:
    ────────────────────────
    
    ┌─────────────────────────────────────────┐
    │ Without synchronization:                │
    │                                         │
    │ Thread-A         Thread-B         Thread-C
    │   CPU            CPU              CPU
    │   Cache          Cache            Cache
    │   ┌──┐           ┌──┐            ┌──┐
    │   │20│           │20│            │20│
    │   └──┘           └──┘            └──┘
    │     │              │               │
    │     └──────────────┼───────────────┴─┐ No coordination!
    │                    │
    │ Main Memory
    │     ┌──┐
    │     │20│  ◄───── All threads read from stale cache
    │     └──┘
    │
    │ Each thread's write goes back, but
    │ overwrites other threads' updates!
    └─────────────────────────────────────────┘
    
    
    ═══════════════════════════════════════════════════════════════════════════
    SCENARIO 2: WITH LOCK (SAFE - MUTUAL EXCLUSION)
    ═══════════════════════════════════════════════════════════════════════════
    
    CODE:
    ─────
    public void bookTickets_SAFE(int tickets) {
        lock.lock();
        try {
            availableSeats -= tickets;  // PROTECTED
        } finally {
            lock.unlock();
        }
    }
    
    
    EXECUTION TIMELINE:
    ──────────────────
    
    Time │ Thread-A           │ Thread-B           │ Thread-C           │ Lock│ Seats
    ─────┼────────────────────┼────────────────────┼────────────────────┼─────┼─────
    T0   │ lock.lock()        │                    │                    │  A  │ 20
         │ (ACQUIRED)         │                    │                    │     │
         │                    │                    │                    │     │
    T1   │ READ: 20           │ lock.lock()        │                    │  A  │ 20
         │                    │ (BLOCKED!)         │                    │     │
         │                    │                    │                    │     │
    T2   │ (compute 20-5=15)  │ (BLOCKED)          │ lock.lock()        │  A  │ 20
         │                    │                    │ (BLOCKED!)         │     │
         │                    │                    │                    │     │
    T3   │ WRITE: 15          │ (BLOCKED)          │ (BLOCKED)          │  A  │ 15
         │ lock.unlock()      │                    │                    │  -  │ 15
         │ (RELEASED)         │                    │                    │     │
         │ (DONE)             │ (WAKES UP)         │                    │     │
         │                    │                    │                    │     │
    T4   │ (waiting for next) │ lock.lock()        │ (BLOCKED)          │  B  │ 15
         │                    │ (ACQUIRED!)        │                    │     │
         │                    │                    │                    │     │
    T5   │ (idle)             │ READ: 15 ◄─ UPDATED│ (BLOCKED)          │  B  │ 15
         │                    │                    │                    │     │
    T6   │ (idle)             │ (compute 15-4=11)  │ (BLOCKED)          │  B  │ 15
         │                    │                    │                    │     │
    T7   │ (idle)             │ WRITE: 11          │ (BLOCKED)          │  B  │ 11
         │                    │ lock.unlock()      │                    │  -  │ 11
         │ (idle)             │ (RELEASED)         │ (WAKES UP)         │     │
         │                    │ (DONE)             │                    │     │
         │                    │                    │                    │     │
    T8   │ (idle)             │ (waiting for next) │ lock.lock()        │  C  │ 11
         │                    │                    │ (ACQUIRED!)        │     │
         │                    │                    │                    │     │
    T9   │ (idle)             │ (idle)             │ READ: 11 ◄─ UPDATED│  C  │ 11
         │                    │                    │                    │     │
    T10  │ (idle)             │ (idle)             │ (compute 11-6=5)   │  C  │ 11
         │                    │                    │                    │     │
    T11  │ (idle)             │ (idle)             │ WRITE: 5           │  C  │ 5
         │                    │                    │ lock.unlock()      │  -  │ 5
         │                    │                    │ (RELEASED)         │     │
         │                    │                    │ (DONE)             │     │
    
    
    CORRECT BEHAVIOR:
    ─────────────────
    
    ┌──────────────────────────────────────────────────────────────┐
    │ Phase 1: Thread-A (T0-T3)                                  │
    │ ├─ Acquires lock                                           │
    │ ├─ Reads: availableSeats = 20                              │
    │ ├─ Computes: 20 - 5 = 15                                   │
    │ ├─ Writes: availableSeats = 15                             │
    │ ├─ Releases lock                                           │
    │ └─ Memory barrier ensures visibility                       │
    │                                                             │
    │ Phase 2: Thread-B (T4-T7)                                  │
    │ ├─ Acquires lock (was waiting)                             │
    │ ├─ Reads: availableSeats = 15  ◄─ SEES A's update!       │
    │ ├─ Computes: 15 - 4 = 11                                   │
    │ ├─ Writes: availableSeats = 11                             │
    │ ├─ Releases lock                                           │
    │ └─ Memory barrier ensures visibility                       │
    │                                                             │
    │ Phase 3: Thread-C (T8-T11)                                 │
    │ ├─ Acquires lock (was waiting)                             │
    │ ├─ Reads: availableSeats = 11  ◄─ SEES B's update!       │
    │ ├─ Computes: 11 - 6 = 5                                    │
    │ ├─ Writes: availableSeats = 5                              │
    │ ├─ Releases lock                                           │
    │ └─ Memory barrier ensures visibility                       │
    │                                                             │
    │ FINAL: availableSeats = 5  ✓ CORRECT!                     │
    └──────────────────────────────────────────────────────────────┘
    
    
    SEQUENCE DIAGRAM WITH LOCK:
    ───────────────────────────
    
    Thread-A:  [LOCK acquired]─[READ 20]─(compute)─[WRITE 15]─[UNLOCK]─(done)
               ████████████████████████████████████████
    
    Thread-B:  [WAITING]─[LOCK acquired]─[READ 15]─(compute)─[WRITE 11]─[UNLOCK]─(done)
                         ████████████████████████████████████████
    
    Thread-C:  [WAITING]─[WAITING]─[LOCK acquired]─[READ 11]─(compute)─[WRITE 5]─[UNLOCK]
                                   ████████████████████████████████████████
    
    availableSeats: 20 ──► (locked) ──► 15 ──► (locked) ──► 11 ──► (locked) ──► 5
    
    
    KEY DIFFERENCE:
    ───────────────
    
    WITHOUT LOCK:        WITH LOCK:
    ─────────────        ──────────
    ALL threads read     Threads read SEQUENTIALLY
    SIMULTANEOUSLY       One at a time
    
    ═══════════════════════════════════════════════════════════════════════════
    COMPARISON TABLE
    ═══════════════════════════════════════════════════════════════════════════
    
    Aspect              │ WITHOUT LOCK          │ WITH LOCK
    ────────────────────┼──────────────────────┼─────────────────────
    Data Consistency    │ ✗ RACE CONDITIONS    │ ✓ GUARANTEED
    Execution Pattern   │ Parallel (interleaved)│ Sequential (queued)
    Performance         │ Fast (but WRONG)      │ Slower (but correct)
    Memory Visibility   │ Unpredictable         │ Memory barrier
    Lost Updates        │ Likely                │ Impossible
    Deadlock Risk       │ No                    │ Possible (if misused)
    Code Complexity     │ Simple                │ More complex
    Debugging           │ Extremely hard        │ Easier
    Production Ready    │ NO!                   │ YES!
    
    
    ═══════════════════════════════════════════════════════════════════════════
    CRITICAL SECTIONS: WHAT GETS PROTECTED BY LOCK
    ═══════════════════════════════════════════════════════════════════════════
    
    lock.lock();      ◄─ Acquire lock
    try {
        // ┌─────────────────────────────────────┐
        // │  CRITICAL SECTION                   │
        // │  Only ONE thread at a time          │
        // │                                     │
        // ├─ Read shared data                  │
        // ├─ Modify shared data                │
        // ├─ Write shared data                 │
        // │                                     │
        // │ No other thread can:               │
        // │ • See partial updates              │
        // │ • Interfere with operations        │
        // │ • Cause race conditions            │
        // │                                     │
        // └─────────────────────────────────────┘
    } finally {
        lock.unlock();  ◄─ Release lock
    }
    
    
    ═══════════════════════════════════════════════════════════════════════════
    REAL-WORLD ANALOGY: BANK ATM
    ═══════════════════════════════════════════════════════════════════════════
    
    ACCOUNT BALANCE: $1000
    
    WITHOUT LOCK (Race Condition):
    ──────────────────────────────
    
    ┌────────────────┐                ┌────────────────┐
    │ Person-A       │                │ Person-B       │
    │ Withdraws $200 │                │ Withdraws $300 │
    │ (same account) │                │ (same account) │
    └────────────────┘                └────────────────┘
           │                                  │
           ├─ Reads balance: $1000            │
           │                          ┌──────┤
           │                          │
           │           Reads balance: $1000 ◄─ Also sees $1000!
           │
           ├─ Computes: 1000 - 200 = 800
           │
           │                    ┌────┤
           │                    │
           │       Computes: 1000 - 300 = 700
           │
           ├─ Updates balance: 800
           │
           │                    ├─ Updates balance: 700 ◄─ OVERWRITES!
    
    
    Final balance: $700  ◄─ WRONG!
    Expected: $1000 - $200 - $300 = $500
    
    
    WITH LOCK (Safe):
    ────────────────
    
    ┌────────────────┐                
    │ Person-A       │                
    │ Withdraws $200 │                
    │ Acquires Card  │ ◄─ Only one person can use card
    └────────────────┘                
           │                          
           ├─ Card locked for A       
           │                          
           ├─ Reads balance: $1000    
           │                          
           ├─ Computes: 1000 - 200 = 800
           │                          
           ├─ Updates balance: 800    
           │                          
           ├─ Releases Card           
           │                          
           └─ Transaction complete   
                                      
                        ┌────────────────┐
                        │ Person-B       │
                        │ Withdraws $300 │
                        │ Acquires Card  │ ◄─ Now B can use card
                        └────────────────┘
                               │
                               ├─ Card locked for B
                               │
                               ├─ Reads balance: $800 ◄─ Sees A's update!
                               │
                               ├─ Computes: 800 - 300 = 500
                               │
                               ├─ Updates balance: $500
                               │
                               ├─ Releases Card
                               │
                               └─ Transaction complete
    
    
    Final balance: $500  ✓ CORRECT!
    Expected: $1000 - $200 - $300 = $500
    
    
    ═══════════════════════════════════════════════════════════════════════════
    SUMMARY
    ═══════════════════════════════════════════════════════════════════════════
    
    ✓ WITH LOCKS: Thread-safe, consistent, slow, correct
    ✗ WITHOUT LOCKS: Fast but unsafe, inconsistent, wrong results
    
    ALWAYS USE LOCKS when:
    ├─ Multiple threads access shared data
    ├─ Any thread modifies shared data
    ├─ Data consistency is important
    └─ In production code (almost always!)
    
    ═══════════════════════════════════════════════════════════════════════════
    
    */
    
}
