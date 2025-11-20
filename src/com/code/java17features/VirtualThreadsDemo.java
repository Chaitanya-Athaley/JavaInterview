package com.code.java17features;

import java.util.concurrent.*;

/**
 * VIRTUAL THREADS - Java 19-21 Feature
 * 
 * Purpose: Lightweight threads that make it easier to write high-throughput,
 *          concurrent applications
 * Introduced: Java 19 (preview), Java 21 (finalized)
 * 
 * Key Features:
 * • Millions can be created without excessive memory
 * • Simpler programming model than platform threads
 * • Use: Thread.ofVirtual().start(() -> { })
 * • Use: Thread.startVirtualThread(() -> { })
 * • Use: ExecutorService.newVirtualThreadPerTaskExecutor()
 * • Automatic parking/unparking when blocking
 * • One-to-one mapping to OS scheduler threads
 */

public class VirtualThreadsDemo {
    
    public static void main(String[] args) throws Exception {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("     VIRTUAL THREADS - Java 19-21 Feature");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateBasicVirtualThreads();
        System.out.println("\n");
        demonstrateVirtualThreadBuilder();
        System.out.println("\n");
        demonstrateVirtualThreadExecutor();
        System.out.println("\n");
        demonstrateHighThroughputWorkload();
    }
    
    private static void demonstrateBasicVirtualThreads() {
        System.out.println("EXAMPLE 1: Basic Virtual Threads (Java 21+)");
        System.out.println("──────────────────────────────────────────\n");
        
        System.out.println("Creating 5 virtual threads with startVirtualThread():");
        
        for (int i = 0; i < 5; i++) {
            final int taskNum = i;
            // Java 21: Simple way to start a virtual thread
            Thread.startVirtualThread(() -> {
                System.out.println("  Virtual Task " + taskNum + 
                                 " on thread: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);  // Simulate I/O work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("  Virtual Task " + taskNum + " completed");
            });
        }
        
        // Wait for all tasks to complete
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n✓ Thread.startVirtualThread() - Quick and simple virtual threads");
    }
    
    private static void demonstrateVirtualThreadBuilder() {
        System.out.println("EXAMPLE 2: Virtual Thread Builder (Java 21+)");
        System.out.println("──────────────────────────────────────────\n");
        
        System.out.println("Creating virtual threads with custom names:");
        
        for (int i = 0; i < 3; i++) {
            // Java 21: Thread.ofVirtual() for more control
            Thread.ofVirtual()
                    .name("worker-", i)  // Custom naming
                    .start(() -> {
                        System.out.println("  Thread: " + Thread.currentThread().getName() + 
                                         " is virtual: " + Thread.currentThread().isVirtual());
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("  Thread: " + Thread.currentThread().getName() + " done");
                    });
        }
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n✓ Thread.ofVirtual() - Builder pattern for customization");
    }
    
    private static void demonstrateVirtualThreadExecutor() {
        System.out.println("EXAMPLE 3: Virtual Thread Executor (Java 21+)");
        System.out.println("────────────────────────────────────────────\n");
        
        System.out.println("Using ExecutorService.newVirtualThreadPerTaskExecutor():");
        
        // Java 21: Virtual thread executor - one virtual thread per task
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            System.out.println("Submitting 10 tasks to virtual thread executor:");
            for (int i = 0; i < 10; i++) {
                final int taskNum = i;
                executor.submit(() -> {
                    System.out.println("  Task " + taskNum + " on " + 
                                     Thread.currentThread().getName());
                    try {
                        // Simulate I/O operation
                        simulateNetworkCall(200 + (taskNum % 5) * 100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            
            // Wait for all tasks
            executor.shutdown();
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Timeout!");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n✓ newVirtualThreadPerTaskExecutor() - One virtual thread per task");
    }
    
    private static void demonstrateHighThroughputWorkload() throws Exception {
        System.out.println("EXAMPLE 4: High-Throughput Workload (Java 21+)");
        System.out.println("─────────────────────────────────────────────\n");
        
        System.out.println("Processing 1000 I/O tasks with virtual threads:");
        
        long startTime = System.currentTimeMillis();
        
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            // Submit 1000 tasks - would be impractical with platform threads
            for (int i = 0; i < 1000; i++) {
                final int taskNum = i;
                executor.submit(() -> {
                    try {
                        // Simulate varied I/O times (50-200ms)
                        int delay = 50 + (taskNum % 15) * 10;
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            
            executor.shutdown();
            if (executor.awaitTermination(30, TimeUnit.SECONDS)) {
                long duration = System.currentTimeMillis() - startTime;
                System.out.println("  Processed 1000 tasks in " + duration + "ms");
                System.out.println("  ✓ This would be impossible with 1000 platform threads!");
                System.out.println("    (Would consume gigabytes of memory)");
            }
        }
        
        System.out.println("\n✓ Virtual threads enable practical high-concurrency applications");
    }
    
    private static void simulateNetworkCall(int delayMs) throws InterruptedException {
        // Simulate network I/O blocking
        Thread.sleep(delayMs);
    }
}

/*
 * ═════════════════════════════════════════════════════════════════════════════
 * VIRTUAL THREADS - COMPREHENSIVE Q&A (Java 19-21)
 * ═════════════════════════════════════════════════════════════════════════════
 * 
 * Q1: What are virtual threads?
 * A: Virtual threads are lightweight threads managed by the JVM, not the OS.
 *    They are much cheaper to create and maintain than platform threads,
 *    enabling millions of concurrent tasks in a single application.
 * 
 * Q2: When were virtual threads introduced?
 * A: Virtual threads were introduced as a preview feature in Java 19 and Java 20,
 *    and were finalized in Java 21.
 * 
 * Q3: What's the difference between virtual and platform threads?
 * A: Platform threads: Tied to OS threads, expensive (1-2MB memory each),
 *    limited by OS thread limit (typically 10K-100K)
 *    Virtual threads: Lightweight (100 bytes), managed by JVM, millions possible
 * 
 * Q4: How do you create a virtual thread?
 * A: Three ways:
 *    1. Thread.startVirtualThread(() -> { code });
 *    2. Thread.ofVirtual().name("name").start(() -> { code });
 *    3. ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();
 * 
 * Q5: What is Thread.startVirtualThread()?
 * A: The simplest way to create and start a virtual thread.
 *    Thread.startVirtualThread(() -> {
 *        // Task runs on a virtual thread
 *    });
 * 
 * Q6: What is Thread.ofVirtual()?
 * A: A builder API for creating virtual threads with customization.
 *    Thread t = Thread.ofVirtual()
 *        .name("worker-", 1)
 *        .start(() -> { code });
 * 
 * Q7: What is newVirtualThreadPerTaskExecutor()?
 * A: An executor that creates a new virtual thread for each submitted task.
 *    Ideal for high-concurrency I/O-bound workloads.
 *    ExecutorService e = Executors.newVirtualThreadPerTaskExecutor();
 * 
 * Q8: How do virtual threads help with scalability?
 * A: They reduce resource consumption dramatically:
 *    Platform threads: 1000 threads = ~1GB memory, OS overhead
 *    Virtual threads: 1000 threads = ~100KB memory, negligible overhead
 *    This enables handling millions of concurrent connections.
 * 
 * Q9: When should I use virtual threads?
 * A: Virtual threads are best for I/O-bound applications:
 *    - Web servers handling many concurrent connections
 *    - Message processing systems
 *    - Database connection pools
 *    NOT for CPU-bound workloads (virtual threads do not help)
 * 
 * Q10: How do virtual threads work internally?
 * A: Virtual threads are parked (suspended) when they block on I/O.
 *    The JVM schedules them on a limited pool of platform threads
 *    (typically = CPU cores). When I/O completes, they resume execution.
 *    This allows millions of virtual threads on just a few OS threads.
 * 
 * ═════════════════════════════════════════════════════════════════════════════
 */
