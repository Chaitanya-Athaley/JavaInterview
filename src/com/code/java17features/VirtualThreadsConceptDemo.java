package com.code.java17features;

import java.util.concurrent.*;

/**
 * VIRTUAL THREADS - Java 21 Feature (Preview in Java 19, Java 20)
 * 
 * Purpose: Lightweight threads that make it easier to write high-throughput,
 *          concurrent applications
 * Introduced: Java 19 (preview), Java 21 (preview)
 * 
 * Note: This demo shows traditional threads that would be replaced by virtual
 *       threads in Java 21+. Virtual threads are not available in Java 15.
 *
 * Virtual Threads Key Points:
 * • Millions can be created without excessive memory
 * • Simpler programming model than traditional threads
 * • Use: Thread.ofVirtual().start(() -> { })
 * • Syntax: Thread.startVirtualThread(() -> { })
 */

public class VirtualThreadsConceptDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("     VIRTUAL THREADS - Java 21 Feature Concept");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        System.out.println("NOTE: This demo shows TRADITIONAL threads.");
        System.out.println("Virtual threads (Java 21+) replace these with a better model.\n");
        
        demonstrateTraditionalThreads();
        System.out.println("\n");
        demonstrateLimitationsOfTraditionalThreads();
        System.out.println("\n");
        explainVirtualThreadsBenefit();
    }
    
    private static void demonstrateTraditionalThreads() {
        System.out.println("EXAMPLE 1: Traditional Threads (Current Java 15)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        System.out.println("Creating 5 traditional platform threads:");
        Thread[] threads = new Thread[5];
        
        for (int i = 0; i < 5; i++) {
            final int threadNum = i;
            threads[i] = new Thread(() -> {
                System.out.println("  Platform Thread " + threadNum + 
                                 " (ID: " + Thread.currentThread().getId() + 
                                 ") - Task started");
                try {
                    Thread.sleep(1000);  // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  Platform Thread " + threadNum + " - Task completed");
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        System.out.println("\n✓ Traditional threads: Heavier weight, limited numbers");
    }
    
    private static void demonstrateLimitationsOfTraditionalThreads() {
        System.out.println("EXAMPLE 2: Limitations of Traditional Threads");
        System.out.println("──────────────────────────────────────────────\n");
        
        System.out.println("Creating 1000 traditional platform threads:");
        ExecutorService executor = Executors.newFixedThreadPool(100);
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            final int taskNum = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                System.out.println("Timeout waiting for tasks");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\nCompleted 1000 tasks with 100 platform threads");
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        System.out.println("\n⚠️  Problems with 1000+ platform threads:");
        System.out.println("   • Each platform thread uses ~1-2 MB of memory");
        System.out.println("   • Creating 1000 threads = 1-2 GB memory");
        System.out.println("   • Context switching overhead is huge");
        System.out.println("   • JVM stack per thread limits total count");
    }
    
    private static void explainVirtualThreadsBenefit() {
        System.out.println("EXAMPLE 3: How Virtual Threads Would Help (Java 21+)");
        System.out.println("──────────────────────────────────────────────────────\n");
        
        System.out.println("With Virtual Threads (Java 21+), you would write:");
        System.out.println("───────────────────────────────────────────────────\n");
        
        System.out.println("Creating MILLIONS of virtual threads (conceptually):");
        System.out.println("""
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                for (int i = 0; i < 1_000_000; i++) {
                    executor.submit(() -> {
                        // I/O operation, database call, etc.
                        performNetworkCall();
                    });
                }
            }
            
            // Virtual threads handle context switching efficiently!
        """);
        
        System.out.println("\n✓ Benefits of Virtual Threads:");
        System.out.println("   • Can create MILLIONS of threads");
        System.out.println("   • Each uses only ~1-2 KB (vs 1-2 MB for platform threads)");
        System.out.println("   • Automatic scheduling on carrier (platform) threads");
        System.out.println("   • Simpler async/concurrent code (no callbacks)");
        System.out.println("   • No need for complex ExecutorService tuning");
        System.out.println("   • Blocking I/O becomes efficient");
        
        System.out.println("\n📊 Comparison: Platform vs Virtual Threads");
        System.out.println("──────────────────────────────────────────");
        System.out.println("Feature                Platform Thread    Virtual Thread");
        System.out.println("─────────────────────────────────────────────────────────");
        System.out.println("Memory per thread      ~1-2 MB            ~1-2 KB");
        System.out.println("Max threads (typical)  ~1000-10,000       Millions");
        System.out.println("Context switching      Expensive (OS)     Cheap (JVM)");
        System.out.println("Blocking I/O           Blocks thread      Yields carrier");
        System.out.println("Programming model      Complex            Simple");
        System.out.println("Use case              CPU-bound tasks    I/O-bound tasks");
        
        System.out.println("\n🎯 When to use Virtual Threads (Java 21+):");
        System.out.println("   ✓ I/O-intensive applications (web servers, APIs)");
        System.out.println("   ✓ High concurrency needed");
        System.out.println("   ✓ Blocking I/O patterns");
        System.out.println("   ✓ Microservices with many concurrent requests");
        
        System.out.println("\n🎯 When to use Platform Threads:");
        System.out.println("   ✓ CPU-bound intensive operations");
        System.out.println("   ✓ Legacy code compatibility");
        System.out.println("   ✓ Java < 21");
    }
    
    private static void performNetworkCall() {
        // Simulate network I/O
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// ════════════════════════════════════════════════════════════════════════════
// VIRTUAL THREADS - INTERVIEW Q&A
// ════════════════════════════════════════════════════════════════════════════
//
// Q1: What are virtual threads?
// ──────────────────────────────
// A: Virtual threads are lightweight threads introduced in Java 21 that make it
//    easier to write high-throughput, concurrent applications. They are managed
//    by the JVM instead of the operating system.
//
//    Key idea: Millions of virtual threads are multiplexed onto a small number
//    of platform (OS) threads called "carrier threads."
//
//
// Q2: What's the difference between platform threads and virtual threads?
// ─────────────────────────────────────────────────────────────────────
// A: Platform Thread (Traditional):
//    • Managed by OS
//    • Uses ~1-2 MB memory per thread
//    • Can create ~1000-10,000 threads
//    • Context switching is expensive (OS kernel switch)
//    • 1:1 mapping to OS threads
//
//    Virtual Thread (Java 21+):
//    • Managed by JVM
//    • Uses ~1-2 KB memory per thread
//    • Can create millions of threads
//    • Context switching is cheap (JVM-level)
//    • Many:1 mapping (multiple virtual on one platform thread)
//
//
// Q3: When were virtual threads introduced?
// ──────────────────────────────────────────
// A: • Java 19: First preview (JEP 425)
//   • Java 20: Second preview
//   • Java 21: Finalized (standard feature)
//
//   They are not available in Java 15, which is what your current project uses.
//
//
// Q4: How do you create a virtual thread in Java 21?
// ──────────────────────────────────────────────────
// A: Method 1: Using Thread.startVirtualThread()
//    Thread.startVirtualThread(() -> {
//        System.out.println("Running in virtual thread");
//    });
//
//    Method 2: Using Thread.ofVirtual()
//    Thread vThread = Thread.ofVirtual()
//        .name("my-virtual-thread")
//        .start(() -> {
//            System.out.println("Virtual thread work");
//        });
//
//    Method 3: Using ExecutorService
//    try (ExecutorService executor = 
//         Executors.newVirtualThreadPerTaskExecutor()) {
//        executor.submit(() -> { /* task */ });
//    }
//
//
// Q5: What is a "carrier thread"?
// ────────────────────────────────
// A: A carrier thread is a platform (OS) thread that executes virtual threads.
//    Virtual threads are scheduled onto carrier threads.
//
//    Example: 1 million virtual threads might run on 8 carrier threads
//    (one per CPU core).
//
//    When a virtual thread blocks on I/O, it's automatically unmounted from
//    the carrier thread, which can then execute another virtual thread.
//
//
// Q6: How does virtual thread context switching work?
// ────────────────────────────────────────────────────
// A: Platform Thread (expensive):
//    1. Application code hits blocking I/O
//    2. OS kernel saves thread state
//    3. OS switches to another OS thread
//    4. Kernel restores another thread's state
//    5. (Many CPU cycles wasted)
//
//    Virtual Thread (cheap):
//    1. Application code hits blocking I/O
//    2. JVM saves virtual thread state (in memory)
//    3. Virtual thread is unmounted from carrier
//    4. Carrier thread picks another virtual thread
//    5. (Efficient, no kernel involvement)
//
//
// Q7: What are the benefits of virtual threads?
// ───────────────────────────────────────────────
// A: ✓ Millions can be created without excessive memory
//   ✓ Simpler programming: straightforward blocking code (no callbacks)
//   ✓ Better resource utilization: blocking doesn't waste CPU
//   ✓ No need for complex async libraries (CompletableFuture, Reactive)
//   ✓ Easier debugging: simpler stack traces
//   ✓ Better for I/O-bound applications
//
//
// Q8: Are virtual threads good for CPU-bound work?
// ──────────────────────────────────────────────────
// A: No, not particularly. Virtual threads shine for I/O-bound work.
//
//    For CPU-bound work:
//    ✗ Context switching overhead doesn't save CPU
//    ✗ Still limited by available CPU cores
//    ✓ Traditional thread pools are still better
//
//    Virtual threads are specifically for I/O-bound scenarios where threads
//    need to wait on network, disk, or database operations.
//
//
// Q9: Will virtual threads replace traditional threads?
// ──────────────────────────────────────────────────────
// A: Not completely, but for I/O-bound applications (most web servers, APIs),
//    virtual threads are the new preferred approach.
//
//    Platform threads will still be used for:
//    • CPU-intensive operations
//    • Legacy code
//    • Specific low-level requirements
//    • JVM internal operations
//
//
// Q10: What's the impact on frameworks and libraries?
// ─────────────────────────────────────────────────────
// A: Frameworks are rapidly adopting virtual threads:
//    • Spring Boot 3.2+: Full virtual thread support
//    • Quarkus: Virtual thread support
//    • Project Loom: The JEP that enabled virtual threads
//
//    For library developers:
//    • Avoid ThreadLocal (deprecated - use ScopedValue)
//    • Avoid synchronized blocks (use ReentrantLock)
//    • Ensure I/O libraries support virtual threads
//
// ════════════════════════════════════════════════════════════════════════════
