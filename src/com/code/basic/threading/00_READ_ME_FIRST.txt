ANSWER TO YOUR QUESTION: "How do multiple threads acquire lock in this flow?"
════════════════════════════════════════════════════════════════════════════

DIRECT ANSWER:
──────────────
Threads acquire locks ONE AT A TIME through a FIFO WAIT QUEUE.

When multiple threads call lock.lock():
  • First thread: Gets lock immediately
  • Second thread: Waits in queue (BLOCKED)
  • Third thread: Waits in queue (BLOCKED)

When first thread calls lock.unlock():
  • Lock is released
  • Second thread wakes up
  • Second thread acquires lock
  • Third thread still waits

Process repeats until all threads have their turn.


VISUAL FLOW:
────────────

Thread-A: lock.lock() ──► ACQUIRED ✓
          [Critical Section]
          lock.unlock() ──► RELEASED
                 ↓
Thread-B:          lock.lock() ──► ACQUIRED ✓
                   [Critical Section]
                   lock.unlock() ──► RELEASED
                          ↓
Thread-C:                 lock.lock() ──► ACQUIRED ✓
                          [Critical Section]
                          lock.unlock() ──► RELEASED


WHAT IS CREATED FOR YOU:
────────────────────────

16 Files Total:

📖 DOCUMENTATION (Start with these!)
   • 00_START_HERE.md
   • DIRECT_ANSWER.md
   • ANSWER_TO_YOUR_QUESTION.md
   • SUMMARY.md
   • HOW_LOCKS_WORK_EXPLAINED.md
   • README_COMPLETE_INDEX.md

💻 RUNNABLE EXAMPLES (Run these!)
   • LockAcquisitionFlow.java (SEE IT LIVE!)
   • MovieTicketBookingSystem.java (Practical example)
   • AdvancedMovieTicketBookingSystem.java (Advanced patterns)

📚 DETAILED EXPLANATIONS (Reference)
   • LockAcquisitionFlowDiagram.java (ASCII diagrams)
   • LOCK_ACQUISITION_EXPLAINED.java (Complete timeline)
   • LockComparison.java (With vs Without locks)
   • QuickReferenceLocks.java (Cheat sheet)

🔧 BONUS (Already existed)
   • CustomThreadPool.java
   • ProducerConsumerProblem.java
   • MOVIE_TICKET_BOOKING_README.md


HOW TO START:
─────────────

Option 1 (5 minutes - Quick Answer):
  Read: DIRECT_ANSWER.md

Option 2 (30 minutes - Good Understanding):
  Read: DIRECT_ANSWER.md
  Run: LockAcquisitionFlow.java
  Read: HOW_LOCKS_WORK_EXPLAINED.md

Option 3 (2 hours - Master It):
  Read all markdown files in order
  Run all examples
  Study the code


KEY CONCEPT:
────────────

WITHOUT LOCK (Race Condition):
  Thread-A reads: availableSeats = 20
  Thread-B reads: availableSeats = 20 (both read same!)
  Thread-A writes: availableSeats = 18
  Thread-B writes: availableSeats = 17 (overwrites A!)
  
  Expected: 15
  Actual: 17 ❌ WRONG!


WITH LOCK (Safe):
  Thread-A: lock.lock() → reads 20 → writes 18 → unlock()
       ↓
  Thread-B: lock.lock() → reads 18 ✓ Updated! → writes 15 → unlock()
  
  Expected: 15
  Actual: 15 ✓ CORRECT!


NEXT STEP:
──────────

👉 Read: DIRECT_ANSWER.md

(It answers your exact question with examples and diagrams!)

════════════════════════════════════════════════════════════════════════════
