package com.code.basic.threading;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DETAILED LOCK ACQUISITION FLOW FOR MULTIPLE THREADS
 * 
 * This example explains exactly how multiple threads acquire locks step-by-step
 * with detailed logging at each stage of the lock lifecycle
 */

public class LockAcquisitionFlow {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== HOW MULTIPLE THREADS ACQUIRE LOCKS ==========\n");
        
        // Create a simple booking system with detailed logging
        SimpleBookingSystemWithLockLogging bookingSystem = 
            new SimpleBookingSystemWithLockLogging(20);
        
        // Create thread pool with 3 threads (to see clear lock contention)
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        System.out.println("Created ExecutorService with 3 threads\n");
        System.out.println("Submitting 5 booking tasks...\n");
        
        // Submit 5 booking tasks
        for (int i = 1; i <= 5; i++) {
            executor.execute(new DetailedBookingTask(bookingSystem, i));
        }
        
        executor.shutdown();
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
        
        System.out.println("\n========== LOCK ACQUISITION COMPLETED ==========");
    }
}

/**
 * Booking system with detailed lock acquisition logging
 */
class SimpleBookingSystemWithLockLogging {
    
    private int availableSeats;
    private final Lock lock = new ReentrantLock();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    // Track lock state for visualization
    private volatile String currentThreadHoldingLock = "NONE";
    private volatile int lockAcquisitionCount = 0;
    
    public SimpleBookingSystemWithLockLogging(int totalSeats) {
        this.availableSeats = totalSeats;
    }
    
    /**
     * Book tickets with DETAILED lock acquisition logging
     */
    public void bookTickets(int customerId, int numberOfTickets) {
        String threadName = Thread.currentThread().getName();
        String timestamp = LocalDateTime.now().format(formatter);
        
        System.out.println("\n┌─ BOOKING REQUEST: Customer " + customerId);
        System.out.println("│  Thread: " + threadName);
        System.out.println("│  Time: " + timestamp);
        System.out.println("│  Requested Tickets: " + numberOfTickets);
        System.out.println("│  Current Available Seats: " + availableSeats);
        System.out.println("│  Lock Status: " + getCurrentLockStatus());
        System.out.println("│");
        
        // ============================================================
        // STEP 1: THREAD TRIES TO ACQUIRE LOCK
        // ============================================================
        System.out.println("│  [STEP 1] Attempting to acquire lock...");
        System.out.println("│           Currently held by: " + currentThreadHoldingLock);
        System.out.println("│           Is queue empty? " + !lock.tryLock());
        
        long startTime = System.nanoTime();
        
        // ACTUAL LOCK ACQUISITION HAPPENS HERE
        lock.lock();  // BLOCKS if another thread holds the lock
        
        long lockWaitTime = (System.nanoTime() - startTime) / 1_000_000;  // Convert to ms
        lockAcquisitionCount++;
        currentThreadHoldingLock = threadName;
        
        // ============================================================
        // STEP 2: LOCK ACQUIRED - ENTERING CRITICAL SECTION
        // ============================================================
        System.out.println("│");
        System.out.println("│  ✓ [STEP 2] LOCK ACQUIRED!");
        System.out.println("│           Thread: " + threadName);
        System.out.println("│           Wait Time: " + lockWaitTime + " ms");
        System.out.println("│           Acquisition #" + lockAcquisitionCount);
        System.out.println("│");
        System.out.println("│  [STEP 3] CRITICAL SECTION - Only this thread can execute here");
        System.out.println("│           Reading availableSeats: " + availableSeats);
        
        try {
            // Simulate some processing time
            Thread.sleep(150);
            
            // ============================================================
            // STEP 4: PERFORM BOOKING LOGIC (INSIDE CRITICAL SECTION)
            // ============================================================
            System.out.println("│           Checking: " + numberOfTickets + " <= " + availableSeats + "?");
            
            if (availableSeats >= numberOfTickets) {
                availableSeats -= numberOfTickets;
                System.out.println("│           ✓ Booking SUCCESS!");
                System.out.println("│           Updated availableSeats: " + availableSeats);
                System.out.println("│           Other threads CANNOT see this data until lock is released");
            } else {
                System.out.println("│           ✗ Booking FAILED - Not enough seats");
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // ============================================================
            // STEP 5: RELEASE LOCK - CRITICAL SECTION ENDS
            // ============================================================
            currentThreadHoldingLock = "BEING_RELEASED";
            lock.unlock();
            currentThreadHoldingLock = "NONE";
            
            System.out.println("│");
            System.out.println("│  [STEP 4] LOCK RELEASED!");
            System.out.println("│           Critical section ended");
            System.out.println("│           Other waiting threads can now acquire lock");
            System.out.println("└─ END OF BOOKING REQUEST\n");
        }
    }
    
    private String getCurrentLockStatus() {
        if (lock.tryLock()) {
            lock.unlock();
            return "UNLOCKED (available)";
        } else {
            return "LOCKED (held by: " + currentThreadHoldingLock + ")";
        }
    }
}

/**
 * Detailed booking task with visible lock acquisition
 */
class DetailedBookingTask implements Runnable {
    private final SimpleBookingSystemWithLockLogging bookingSystem;
    private final int customerId;
    
    public DetailedBookingTask(SimpleBookingSystemWithLockLogging bookingSystem, int customerId) {
        this.bookingSystem = bookingSystem;
        this.customerId = customerId;
    }
    
    @Override
    public void run() {
        int ticketsToBook = (customerId % 3) + 1;  // 1, 2, or 3 tickets
        bookingSystem.bookTickets(customerId, ticketsToBook);
    }
}
