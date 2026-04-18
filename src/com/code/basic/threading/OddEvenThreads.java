package com.code.basic.threading;

/**
 * Print numbers 1-10 using two threads:
 * - Odd Thread: prints 1, 3, 5, 7, 9
 * - Even Thread: prints 2, 4, 6, 8, 10
 * 
 * Approach: Synchronized methods with wait() and notifyAll()
 */
public class OddEvenThreads {

    private int currentNumber = 1;
    private static final int LIMIT = 10;

    /**
     * Synchronized method to print odd numbers
     * Thread waits if it's even number's turn
     */
    public synchronized void printOdd() {
        while (currentNumber <= LIMIT) {
            if (currentNumber % 2 == 0) {
                // It's even number's turn, so wait
                try {
                    wait();                    // Release lock and wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // It's odd number's turn to print
                System.out.println(currentNumber);
                currentNumber++;
                notifyAll();                   // Wake up even thread
            }
        }
    }

    /**
     * Synchronized method to print even numbers
     * Thread waits if it's odd number's turn
     */
    public synchronized void printEven() {
        while (currentNumber <= LIMIT) {
            if (currentNumber % 2 != 0) {
                // It's odd number's turn, so wait
                try {
                    wait();                    // Release lock and wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                // It's even number's turn to print
                System.out.println(currentNumber);
                currentNumber++;
                notifyAll();                   // Wake up odd thread
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Odd-Even Threading (Synchronized Methods) ===\n");

        // Create single shared object for synchronization
        OddEvenThreads printer = new OddEvenThreads();

        // Create and start threads
        Thread oddThread = new Thread(printer::printOdd, "OddThread");
        Thread evenThread = new Thread(printer::printEven, "EvenThread");

        oddThread.start();
        evenThread.start();

        // Wait for both threads to complete
        try {
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted!");
            e.printStackTrace();
        }

        System.out.println("\nDone! Both threads completed.");
    }
}
