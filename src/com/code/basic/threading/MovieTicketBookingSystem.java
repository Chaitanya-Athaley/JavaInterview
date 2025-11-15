package com.code.basic.threading;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Movie Ticket Booking System using ExecutorService
 * Demonstrates:
 * - Multiple threads booking tickets concurrently
 * - Data consistency using synchronization (ReentrantLock)
 * - Async operations with ExecutorService
 * - Thread-safe operations on shared resources
 */

public class MovieTicketBookingSystem {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("========== MOVIE TICKET BOOKING SYSTEM ==========\n");
        
        // Create a booking system with 100 available seats
        BookingSystem bookingSystem = new BookingSystem(100);
        
        // Create an ExecutorService with a fixed thread pool of 5 threads
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        
        // Create 10 customers trying to book tickets
        int numberOfCustomers = 10;
        
        for (int i = 1; i <= numberOfCustomers; i++) {
            // Submit booking tasks asynchronously
            executorService.execute(new BookingTask(bookingSystem, i));
        }
        
        // Shutdown the executor service
        executorService.shutdown();
        
        // Wait for all tasks to complete
        if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
        
        // Print final report
        System.out.println("\n========== FINAL REPORT ==========");
        bookingSystem.printReport();
    }
}

/**
 * BookingSystem class manages movie ticket availability
 * Ensures data consistency using ReentrantLock
 */
class BookingSystem {
    
    private int availableSeats;
    private int bookedSeats = 0;
    private int failedBookings = 0;
    private final Lock lock = new ReentrantLock();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    public BookingSystem(int totalSeats) {
        this.availableSeats = totalSeats;
    }
    
    /**
     * Book tickets for a customer
     * Thread-safe operation using ReentrantLock
     * 
     * @param customerId Unique customer identifier
     * @param numberOfTickets Number of tickets to book
     * @return BookingResult object containing booking status
     */
    public BookingResult bookTickets(int customerId, int numberOfTickets) {
        // Acquire lock to ensure thread-safety
        lock.lock();
        try {
            // Simulate some processing time
            Thread.sleep(100);
            
            String timestamp = LocalDateTime.now().format(formatter);
            
            // Check if enough seats are available
            if (availableSeats >= numberOfTickets) {
                // Book the tickets
                availableSeats -= numberOfTickets;
                bookedSeats += numberOfTickets;
                
                BookingResult result = new BookingResult(
                    customerId, 
                    numberOfTickets, 
                    true, 
                    availableSeats,
                    timestamp
                );
                
                System.out.println("✓ [" + timestamp + "] Customer " + customerId + 
                                 " - BOOKING CONFIRMED for " + numberOfTickets + 
                                 " tickets. Remaining seats: " + availableSeats);
                
                return result;
            } else {
                // Booking failed - not enough seats
                failedBookings++;
                
                BookingResult result = new BookingResult(
                    customerId, 
                    numberOfTickets, 
                    false, 
                    availableSeats,
                    timestamp
                );
                
                System.out.println("✗ [" + timestamp + "] Customer " + customerId + 
                                 " - BOOKING FAILED. Only " + availableSeats + 
                                 " seats available, but requested " + numberOfTickets);
                
                return result;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new BookingResult(customerId, numberOfTickets, false, availableSeats, 
                                    LocalDateTime.now().format(formatter));
        } finally {
            // Always release the lock
            lock.unlock();
        }
    }
    
    /**
     * Print booking system report
     */
    public void printReport() {
        lock.lock();
        try {
            System.out.println("Total Seats: " + (availableSeats + bookedSeats));
            System.out.println("Available Seats: " + availableSeats);
            System.out.println("Booked Seats: " + bookedSeats);
            System.out.println("Failed Bookings: " + failedBookings);
            System.out.println("Occupancy Rate: " + String.format("%.2f%%", 
                (bookedSeats * 100.0) / (availableSeats + bookedSeats)));
        } finally {
            lock.unlock();
        }
    }
}

/**
 * BookingResult class stores the result of a booking operation
 */
class BookingResult {
    private final int customerId;
    private final int ticketsRequested;
    private final boolean isSuccessful;
    private final int remainingSeats;
    private final String bookingTime;
    
    public BookingResult(int customerId, int ticketsRequested, boolean isSuccessful, 
                        int remainingSeats, String bookingTime) {
        this.customerId = customerId;
        this.ticketsRequested = ticketsRequested;
        this.isSuccessful = isSuccessful;
        this.remainingSeats = remainingSeats;
        this.bookingTime = bookingTime;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public int getTicketsRequested() {
        return ticketsRequested;
    }
    
    public boolean isSuccessful() {
        return isSuccessful;
    }
    
    public int getRemainingSeats() {
        return remainingSeats;
    }
    
    public String getBookingTime() {
        return bookingTime;
    }
}

/**
 * BookingTask implements Runnable
 * Each task represents a customer trying to book tickets
 */
class BookingTask implements Runnable {
    private final BookingSystem bookingSystem;
    private final int customerId;
    
    public BookingTask(BookingSystem bookingSystem, int customerId) {
        this.bookingSystem = bookingSystem;
        this.customerId = customerId;
    }
    
    @Override
    public void run() {
        // Generate random number of tickets (1-5)
        int ticketsToBook = (int) (Math.random() * 5) + 1;
        
        // Book tickets asynchronously
        BookingResult result = bookingSystem.bookTickets(customerId, ticketsToBook);
        
        // Process result (optional follow-up operations)
        processBookingResult(result);
    }
    
    /**
     * Process the booking result
     * @param result The booking result object
     */
    private void processBookingResult(BookingResult result) {
        if (result.isSuccessful()) {
            // In a real system, this could trigger confirmation email, payment processing, etc.
            // System.out.println("   └─ Sending confirmation to Customer " + result.getCustomerId());
        } else {
            // In a real system, this could show available alternatives
            // System.out.println("   └─ Showing alternative movies to Customer " + result.getCustomerId());
        }
    }
}
