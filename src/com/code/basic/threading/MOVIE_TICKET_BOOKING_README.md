# Movie Ticket Booking System - ExecutorService Examples

## Overview

This project demonstrates real-world multithreading using `ExecutorService` with two complete implementations of a movie ticket booking system. Both examples showcase asynchronous ticket booking with guaranteed data consistency.

## Files Created

1. **MovieTicketBookingSystem.java** - Basic implementation using ReentrantLock
2. **AdvancedMovieTicketBookingSystem.java** - Advanced implementation using AtomicInteger

---

## 1. MovieTicketBookingSystem.java (Basic Version)

### Key Features

- **Thread Pool**: `Executors.newFixedThreadPool(5)` - 5 concurrent threads
- **Synchronization**: `ReentrantLock` for thread-safe operations
- **Async Booking**: Multiple customers booking tickets simultaneously
- **Data Consistency**: Lock ensures no race conditions during seat allocation

### How It Works

```
Customer 1 ─┐
Customer 2  ├─> Thread Pool (5 threads) ─> Booking System (with Lock) ─> Seat Reservation
Customer 3  │                                                            
...         │
Customer 10─┘
```

### Class Structure

#### BookingSystem
- **Lock**: `ReentrantLock` protects shared resources
- **Methods**:
  - `bookTickets(int customerId, int numberOfTickets)` - Main booking method
  - `printReport()` - Display booking statistics

#### BookingResult
- Immutable result object containing booking details
- Returns booking status, remaining seats, timestamp

#### BookingTask (implements Runnable)
- Represents a single customer booking attempt
- Requests 1-5 tickets randomly

### Data Consistency Mechanism

```java
lock.lock();
try {
    // Critical section - only one thread at a time
    if (availableSeats >= numberOfTickets) {
        availableSeats -= numberOfTickets;
        bookedSeats += numberOfTickets;
        return success;
    }
    return failure;
} finally {
    lock.unlock();  // Always release
}
```

### Sample Output

```
========== MOVIE TICKET BOOKING SYSTEM ==========

✓ [16:42:51.663] Customer 1 - BOOKING CONFIRMED for 2 tickets. Remaining seats: 98
✓ [16:42:51.825] Customer 6 - BOOKING CONFIRMED for 2 tickets. Remaining seats: 96
✓ [16:42:51.933] Customer 2 - BOOKING CONFIRMED for 3 tickets. Remaining seats: 93
✓ [16:42:52.042] Customer 5 - BOOKING CONFIRMED for 1 tickets. Remaining seats: 92
✗ [16:42:52.200] Customer 3 - BOOKING FAILED. Only 50 seats available, but requested 51

========== FINAL REPORT ==========
Total Seats: 100
Available Seats: 66
Booked Seats: 34
Failed Bookings: 0
Occupancy Rate: 34.00%
```

### Use Cases

✓ Simple, easy to understand  
✓ Good for educational purposes  
✓ ReentrantLock provides clear control  
✓ Suitable when complex logic is needed in critical section  

---

## 2. AdvancedMovieTicketBookingSystem.java (Advanced Version)

### Key Features

- **Thread Pool**: `ExecutorService.newFixedThreadPool(5)` - 5 concurrent threads
- **Callable Tasks**: Returns results via `Future<BookingResultAdvanced>`
- **Atomic Operations**: `AtomicInteger` for lock-free thread-safe counters
- **Async Result Collection**: Uses `Future.get()` to collect booking results
- **Timeout Handling**: Configurable timeout for each booking operation

### How It Works

```
Callable Task 1 ─┐
Callable Task 2  ├─> Thread Pool ─> Booking Service ─> Returns Future<BookingResult>
Callable Task 3  │                                       
...              │
Callable Task 15─┘

Main Thread collects results using Future.get()
```

### Class Structure

#### AdvancedBookingService
- **Atomic Counters**: `AtomicInteger` instead of locks
- **Lock-Free**: Uses `compareAndSet()` for atomic operations
- **Methods**:
  - `bookTicketsAtomic(int customerId, int numberOfTickets)` - Lock-free booking
  - `printDetailedReport()` - Comprehensive statistics

#### BookingCallableTask (implements Callable)
- Returns `BookingResultAdvanced`
- Integrates with `Future` API for result retrieval

### Data Consistency Mechanism (Lock-Free)

```java
// Atomic Compare-And-Set operation
int currentAvailable;
do {
    currentAvailable = availableSeats.get();
    if (currentAvailable < numberOfTickets) {
        return failure;  // Not enough seats
    }
} while (!availableSeats.compareAndSet(currentAvailable, 
                                       currentAvailable - numberOfTickets));

bookedSeats.addAndGet(numberOfTickets);  // Atomic increment
return success;
```

### Future-Based Result Collection

```java
ExecutorService executor = Executors.newFixedThreadPool(5);
Future<BookingResultAdvanced> future = executor.submit(new BookingCallableTask(...));

try {
    BookingResultAdvanced result = future.get(10, TimeUnit.SECONDS);
    if (result.isSuccessful()) {
        // Process successful booking
    }
} catch (TimeoutException e) {
    // Handle timeout
}
```

### Sample Output

```
========== ADVANCED MOVIE TICKET BOOKING SYSTEM ==========

Launching 15 concurrent booking requests...

Collecting results...

✓ [16:42:59.995] Customer 2 - BOOKING CONFIRMED for 3 tickets. Remaining seats: 138
✓ [16:42:59.995] Customer 4 - BOOKING CONFIRMED for 3 tickets. Remaining seats: 147
✓ [16:42:59.995] Customer 5 - BOOKING CONFIRMED for 3 tickets. Remaining seats: 144
✗ [16:43:00.100] Customer 7 - BOOKING FAILED. Only 5 seats available, but requested 10

========== FINAL BOOKING REPORT ==========
Total Available Seats: 150
Currently Available: 110
Tickets Booked: 40
Successful Bookings: 15
Failed Bookings: 0
Occupancy Rate: 26.67%
```

### Use Cases

✓ High-performance scenarios  
✓ Large number of concurrent operations  
✓ When you need result collection  
✓ Lock-free programming preferred  
✓ Better for simple counter operations  

---

## Comparison: ReentrantLock vs AtomicInteger

| Aspect | ReentrantLock | AtomicInteger |
|--------|---------------|---------------|
| **Synchronization** | Explicit locking | Atomic operations |
| **Performance** | Good for complex logic | Better for simple counters |
| **Complexity** | More explicit | More concise |
| **Fairness** | Configurable | N/A |
| **Nested Logic** | Supports complex sections | Limited to single variable |
| **Debugging** | Easier to trace | Can be harder to debug |

---

## Key Concepts Demonstrated

### 1. ExecutorService Thread Pool
```java
ExecutorService executor = Executors.newFixedThreadPool(5);
// 5 threads share workload
executor.execute(new BookingTask(...));
executor.shutdown();
executor.awaitTermination(30, TimeUnit.SECONDS);
```

### 2. Thread Safety with Locks
```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    // Critical section
} finally {
    lock.unlock();
}
```

### 3. Atomic Operations (Lock-Free)
```java
AtomicInteger counter = new AtomicInteger(100);
counter.decrementAndGet();  // Atomic decrement
counter.compareAndSet(expected, update);  // Atomic CAS
```

### 4. Callable and Future
```java
Callable<Result> task = new BookingCallableTask(...);
Future<Result> future = executor.submit(task);
Result result = future.get(10, TimeUnit.SECONDS);  // Block until done
```

### 5. Timestamp Logging
```java
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
String timestamp = LocalDateTime.now().format(formatter);
```

---

## Running the Examples

### Compile Both
```bash
javac -d bin src/com/code/basic/threading/MovieTicketBookingSystem.java
javac -d bin src/com/code/basic/threading/AdvancedMovieTicketBookingSystem.java
```

### Run Basic Version
```bash
java -cp bin com.code.basic.threading.MovieTicketBookingSystem
```

### Run Advanced Version
```bash
java -cp bin com.code.basic.threading.AdvancedMovieTicketBookingSystem
```

---

## Real-World Applications

1. **E-commerce Checkout** - Multiple users buying limited inventory
2. **Hotel Room Booking** - Concurrent reservation attempts
3. **Flight Ticket Sales** - High-volume concurrent bookings
4. **Bank ATM Transactions** - Multiple withdrawals from shared account
5. **Game Server Matchmaking** - Multiple players joining games simultaneously
6. **Stock Trading Platform** - Concurrent buy/sell orders
7. **Event Ticketing** - Concert/sports event sales

---

## Best Practices

✅ **Always use ExecutorService** - Don't create threads manually  
✅ **Properly shutdown executor** - Prevents resource leaks  
✅ **Use TimeUnit** - For all timeout-based operations  
✅ **Handle InterruptedException** - Can interrupt executor threads  
✅ **Prefer Callable over Runnable** - When you need return values  
✅ **Use AtomicInteger** - For simple counter synchronization  
✅ **Add logging** - For debugging concurrent issues  
✅ **Document synchronization** - Make thread-safety explicit  

---

## Common Issues and Solutions

### Issue: Deadlock
**Solution**: Always use try-finally to release locks

### Issue: Lost Updates
**Solution**: Use synchronized access or atomic operations

### Issue: Race Conditions
**Solution**: Use locks or atomic operations for shared resources

### Issue: Performance Degradation
**Solution**: Consider lock-free alternatives (AtomicInteger)

### Issue: Uncaught Exceptions
**Solution**: Implement Future.get() error handling

---

## Learning Path

1. ✅ Understand basic ExecutorService usage
2. ✅ Learn ReentrantLock synchronization
3. ✅ Study AtomicInteger for lock-free operations
4. ✅ Master Callable and Future API
5. ✅ Explore higher concurrency (ConcurrentHashMap, etc.)

---

## Next Steps

Try extending these examples:

1. Add database persistence for bookings
2. Implement payment processing
3. Add email notifications
4. Create a REST API around the booking system
5. Implement retries for failed bookings
6. Add metrics/monitoring
7. Use CompletableFuture for more complex async workflows

---

**Author**: Learning Example  
**Date**: November 2025  
**Java Version**: 8+  
**Concurrency Level**: Beginner to Intermediate
