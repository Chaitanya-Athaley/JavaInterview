package com.code.basic.threading;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * INTERVIEW PREFERRED: Ultra Minimal Approach - Made Readable
 * Approach: Synchronized Block + AtomicInteger
 */
public class InterviewUltraMinimalForEvenOddThread {

    public static void main(String[] args) {
        // ==================== SETUP ====================
        AtomicInteger number = new AtomicInteger(1);  // Shared counter (thread-safe)
        Object lockObject = new Object();              // Lock object for synchronization
        final int MAX = 10;                             // Upper limit

        // ==================== ODD THREAD ====================
        
        Runnable oddTask = () -> {
            while (number.get() <= MAX) {
                synchronized (lockObject) {              // Acquire lock
                    if (number.get() % 2 != 0) {        // If ODD, print
                        System.out.println(number.getAndIncrement()+" "+Thread.currentThread().getName());
                        lockObject.notifyAll();          // Notify EVEN thread
                    } else {                             // If EVEN, wait
                        try {
                            lockObject.wait();           // Release lock and wait
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        // ==================== EVEN THREAD ====================
        Runnable evenTask = () -> {
            while (number.get() <= MAX) {
                synchronized (lockObject) {              // Acquire lock
                    if (number.get() % 2 == 0) {        // If EVEN, print
                        System.out.println(number.getAndIncrement()+" "+Thread.currentThread().getName());
                        lockObject.notifyAll();          // Notify ODD thread
                    } else {                             // If ODD, wait
                        try {
                            lockObject.wait();           // Release lock and wait
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        };

        // ==================== START & WAIT ====================
        Thread oddThread = new Thread(oddTask, "OddThread");
        Thread evenThread = new Thread(evenTask, "EvenThread");

        oddThread.start();
        evenThread.start();

        // Wait for both threads to complete
        try {
            oddThread.join();   // Wait for odd thread to complete
            evenThread.join();  // Wait for even thread to complete
        } catch (InterruptedException e) {
            System.err.println("Main thread was interrupted!");
            e.printStackTrace();
        }

        System.out.println("\nDone! Both threads finished.\n");
    }


	/*
	 * ═════════════════════════════════════════════════════════════════════
	 * 
	 * EXECUTION FLOW:
	 * ─────────────────────────────────────────────────────────────────────
	 * 
	 * Step 1: OddThread grabs lock Reads: number.get() = 1 Check: 1 % 2 == 1? YES
	 * (odd) Action: Print 1, increment to 2 Release lock
	 * 
	 * Step 2: EvenThread grabs lock Reads: number.get() = 2 Check: 2 % 2 == 0? YES
	 * (even) Action: Print 2, increment to 3 Release lock
	 * 
	 * Step 3: OddThread loops, grabs lock Reads: number.get() = 3 Check: 3 % 2 ==
	 * 1? YES (odd) Action: Print 3, increment to 4 Release lock
	 * 
	 * ... continues until number > 10
	 * 
	 */
}
