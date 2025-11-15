package com.code.basic.threading;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Advanced Movie Ticket Booking System using ExecutorService with Callable
 * Demonstrates:
 * - Using Callable instead of Runnable to return results
 * - ExecutorService with Future objects
 * - AtomicInteger for lock-free thread-safe operations
 * - Thread pool monitoring
 * - Async booking with result collection
 */

public class AdvancedMovieTicketBookingSystem {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("========== ADVANCED MOVIE TICKET BOOKING SYSTEM ==========\n");
        
        // Create booking service with 150 available seats
        AdvancedBookingService bookingService = new AdvancedBookingService(150);
        
        // Create ExecutorService with thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        
        // Keep track of futures for result collection
        ConcurrentHashMap<Integer, Future<BookingResultAdvanced>> futureMap = new ConcurrentHashMap<>();
        
        System.out.println("Launching 15 concurrent booking requests...\n");
        
        // Submit 15 booking tasks
        for (int i = 1; i <= 15; i++) {
            BookingCallableTask task = new BookingCallableTask(bookingService, i);
            Future<BookingResultAdvanced> future = executorService.submit(task);
            futureMap.put(i, future);
        }
        
        // Wait for all tasks and collect results
        System.out.println("\nCollecting results...\n");
        int successCount = 0;
        int failureCount = 0;
        int totalTicketsBooked = 0;
        
        for (int i = 1; i <= 15; i++) {
            try {
                BookingResultAdvanced result = futureMap.get(i).get(10, TimeUnit.SECONDS);
                
                if (result.isSuccessful()) {
                    successCount++;
                    totalTicketsBooked += result.getTicketsRequested();
                } else {
                    failureCount++;
                }
            } catch (TimeoutException e) {
                System.out.println("⏱ Booking request " + i + " timed out!");
                failureCount++;
            }
        }
        
        // Shutdown executor service
        executorService.shutdown();
        if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
        
        // Print comprehensive report
        System.out.println("\n========== FINAL BOOKING REPORT ==========");
        bookingService.printDetailedReport();
        System.out.println("\nAggregate Statistics:");
        System.out.println("Successful Bookings: " + successCount);
        System.out.println("Failed Bookings: " + failureCount);
        System.out.println("Total Tickets Booked: " + totalTicketsBooked);
    }
}

/**
 * AdvancedBookingService with AtomicInteger for lock-free operations
 */
class AdvancedBookingService {
    
    private final int totalSeats;
    private final AtomicInteger availableSeats;
    private final AtomicInteger bookedSeats;
    private final AtomicInteger failedBookings;
    private final AtomicInteger successfulBookings;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    
    public AdvancedBookingService(int totalSeats) {
        this.totalSeats = totalSeats;
        this.availableSeats = new AtomicInteger(totalSeats);
        this.bookedSeats = new AtomicInteger(0);
        this.failedBookings = new AtomicInteger(0);
        this.successfulBookings = new AtomicInteger(0);
    }
    
    /**
     * Book tickets using atomic operations
     * This is lock-free and more efficient than ReentrantLock for simple counters
     * 
     * @param customerId Customer identifier
     * @param numberOfTickets Number of tickets to book
     * @return BookingResultAdvanced
     */
    public BookingResultAdvanced bookTicketsAtomic(int customerId, int numberOfTickets) {
        String timestamp = LocalDateTime.now().format(formatter);
        
        try {
            // Simulate processing
            Thread.sleep(80);
            
            // Loop to atomically update available seats
            int currentAvailable;
            do {
                currentAvailable = availableSeats.get();
                if (currentAvailable < numberOfTickets) {
                    // Not enough seats - booking fails
                    failedBookings.incrementAndGet();
                    
                    System.out.println("✗ [" + timestamp + "] Customer " + customerId + 
                                     " - BOOKING FAILED. Only " + currentAvailable + 
                                     " seats available, but requested " + numberOfTickets);
                    
                    return new BookingResultAdvanced(customerId, numberOfTickets, false, 
                                                    currentAvailable, timestamp);
                }
            } while (!availableSeats.compareAndSet(currentAvailable, currentAvailable - numberOfTickets));
            
            // Booking successful
            bookedSeats.addAndGet(numberOfTickets);
            successfulBookings.incrementAndGet();
            int newAvailable = availableSeats.get();
            
            System.out.println("✓ [" + timestamp + "] Customer " + customerId + 
                             " - BOOKING CONFIRMED for " + numberOfTickets + 
                             " tickets. Remaining seats: " + newAvailable);
            
            return new BookingResultAdvanced(customerId, numberOfTickets, true, 
                                            newAvailable, timestamp);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new BookingResultAdvanced(customerId, numberOfTickets, false, 
                                            availableSeats.get(), timestamp);
        }
    }
    
    /**
     * Print detailed booking report
     */
    public void printDetailedReport() {
        System.out.println("Total Available Seats: " + totalSeats);
        System.out.println("Currently Available: " + availableSeats.get());
        System.out.println("Tickets Booked: " + bookedSeats.get());
        System.out.println("Successful Bookings: " + successfulBookings.get());
        System.out.println("Failed Bookings: " + failedBookings.get());
        System.out.println("Occupancy Rate: " + String.format("%.2f%%", 
            (bookedSeats.get() * 100.0) / totalSeats));
    }
}

/**
 * BookingResultAdvanced
 */
class BookingResultAdvanced {
    private final int customerId;
    private final int ticketsRequested;
    private final boolean isSuccessful;
    private final int remainingSeats;
    private final String bookingTime;
    
    public BookingResultAdvanced(int customerId, int ticketsRequested, boolean isSuccessful, 
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
 * BookingCallableTask implements Callable to return BookingResultAdvanced
 */
class BookingCallableTask implements Callable<BookingResultAdvanced> {
    
    private final AdvancedBookingService bookingService;
    private final int customerId;
    
    public BookingCallableTask(AdvancedBookingService bookingService, int customerId) {
        this.bookingService = bookingService;
        this.customerId = customerId;
    }
    
    @Override
    public BookingResultAdvanced call() throws Exception {
        // Generate random number of tickets (1-4)
        int ticketsToBook = (int) (Math.random() * 4) + 1;
        
        // Book tickets asynchronously and return result
        return bookingService.bookTicketsAtomic(customerId, ticketsToBook);
    }
}
