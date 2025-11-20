package com.code.java17features;

import java.util.*;

/**
 * SEQUENCED COLLECTIONS - Java 21 Feature
 * 
 * Purpose: Provide a unified API for collections that have a defined sequence/order
 * Introduced: Java 21
 * 
 * Key Features:
 * • New interface: SequencedCollection<E> extends Collection<E>
 * • Methods: getFirst(), getLast(), reversed()
 * • Applied to: List, Deque, LinkedHashSet, LinkedHashMap
 * • Backward compatible: Doesn't break existing code
 * 
 * Common Methods:
 * • getFirst() - Get first element or NoSuchElementException
 * • getLast() - Get last element or NoSuchElementException
 * • addFirst(E) - Add element at beginning
 * • addLast(E) - Add element at end
 * • removeFirst() - Remove and return first element
 * • removeLast() - Remove and return last element
 * • reversed() - Return reversed view (doesn't copy)
 */

public class SequencedCollectionsDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════");
        System.out.println("      SEQUENCED COLLECTIONS - Java 21 Feature");
        System.out.println("════════════════════════════════════════════════════════\n");
        
        demonstrateSequencedList();
        System.out.println("\n");
        demonstrateSequencedDeque();
        System.out.println("\n");
        demonstrateSequencedSet();
        System.out.println("\n");
        demonstrateReversedOperations();
        System.out.println("\n");
        demonstrateSequencedMap();
    }
    
    private static void demonstrateSequencedList() {
        System.out.println("EXAMPLE 1: SequencedCollection with List (Java 21+)");
        System.out.println("──────────────────────────────────────────────────\n");
        
        // List is a SequencedCollection in Java 21
        List<String> colors = new ArrayList<>();
        colors.add("Red");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Yellow");
        colors.add("Purple");
        
        System.out.println("Original List: " + colors);
        
        // New Java 21 methods on SequencedCollection
        System.out.println("\nUsing new SequencedCollection methods:");
        System.out.println("  getFirst(): " + colors.getFirst());
        System.out.println("  getLast(): " + colors.getLast());
        
        System.out.println("\nOld way (still works):");
        System.out.println("  get(0): " + colors.get(0));
        System.out.println("  get(size()-1): " + colors.get(colors.size() - 1));
        
        // Add operations
        System.out.println("\nAdding elements:");
        colors.addFirst("Black");
        colors.addLast("White");
        System.out.println("  After addFirst(\"Black\") and addLast(\"White\"): " + colors);
        
        // Remove operations
        System.out.println("\nRemoving elements:");
        String first = colors.removeFirst();
        String last = colors.removeLast();
        System.out.println("  removeFirst(): " + first);
        System.out.println("  removeLast(): " + last);
        System.out.println("  Remaining: " + colors);
        
        System.out.println("\n✓ List is a SequencedCollection with getFirst/getLast");
    }
    
    private static void demonstrateSequencedDeque() {
        System.out.println("EXAMPLE 2: SequencedCollection with Deque (Java 21+)");
        System.out.println("───────────────────────────────────────────────────\n");
        
        // Deque has been a sequenced collection, now more unified
        Deque<Integer> numbers = new LinkedList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        numbers.add(50);
        
        System.out.println("Deque: " + numbers);
        System.out.println("\nSequencedCollection methods on Deque:");
        System.out.println("  getFirst(): " + numbers.getFirst());
        System.out.println("  getLast(): " + numbers.getLast());
        
        System.out.println("\nDouble-ended operations (still work):");
        System.out.println("  getFirst() == getFirst(): " + numbers.getFirst().equals(numbers.getFirst()));
        System.out.println("  getLast() == getLast(): " + numbers.getLast().equals(numbers.getLast()));
        
        // Deque specific methods
        System.out.println("\nDeque-specific operations:");
        numbers.addFirst(5);
        numbers.addLast(60);
        System.out.println("  After addFirst(5) and addLast(60): " + numbers);
        
        System.out.println("\nPeeking at ends:");
        System.out.println("  peekFirst(): " + numbers.peekFirst());
        System.out.println("  peekLast(): " + numbers.peekLast());
        
        System.out.println("\n✓ Deque benefits from unified SequencedCollection API");
    }
    
    private static void demonstrateSequencedSet() {
        System.out.println("EXAMPLE 3: SequencedCollection with LinkedHashSet (Java 21+)");
        System.out.println("────────────────────────────────────────────────────────────\n");
        
        // LinkedHashSet maintains insertion order
        Set<String> fruits = new LinkedHashSet<>();
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Date");
        fruits.add("Elderberry");
        
        System.out.println("LinkedHashSet (maintains insertion order): " + fruits);
        
        System.out.println("\nSequencedCollection methods on LinkedHashSet:");
        System.out.println("  getFirst(): " + ((LinkedHashSet<String>) fruits).getFirst());
        System.out.println("  getLast(): " + ((LinkedHashSet<String>) fruits).getLast());
        
        // Type casting to access SequencedCollection methods
        LinkedHashSet<String> sequencedFruits = (LinkedHashSet<String>) fruits;
        
        System.out.println("\nRemoving and adding:");
        sequencedFruits.addFirst("Apricot");
        sequencedFruits.addLast("Fig");
        System.out.println("  After addFirst(\"Apricot\") and addLast(\"Fig\"): " + sequencedFruits);
        
        System.out.println("\nRemoving from ends:");
        System.out.println("  removeFirst(): " + sequencedFruits.removeFirst());
        System.out.println("  removeLast(): " + sequencedFruits.removeLast());
        System.out.println("  Remaining: " + sequencedFruits);
        
        System.out.println("\n✓ LinkedHashSet now fully participates in SequencedCollection API");
    }
    
    private static void demonstrateReversedOperations() {
        System.out.println("EXAMPLE 4: Reversed Operations (Java 21+)");
        System.out.println("──────────────────────────────────────────\n");
        
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            numbers.add(i * 10);
        }
        
        System.out.println("Original list: " + numbers);
        
        System.out.println("\nUsing reversed() method:");
        SequencedCollection<Integer> reversed = numbers.reversed();
        System.out.println("  reversed(): " + reversed);
        
        System.out.println("\nIterating through reversed collection:");
        System.out.print("  Elements in reverse: ");
        for (Integer num : reversed) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        System.out.println("\nReversed is a VIEW (no copy):");
        System.out.println("  Original list: " + numbers);
        System.out.println("  Reversed view: " + reversed);
        System.out.println("  Getting reversed().getFirst(): " + reversed.getFirst() + 
                         " (should be same as last element: " + numbers.getLast() + ")");
        System.out.println("  Getting reversed().getLast(): " + reversed.getLast() + 
                         " (should be same as first element: " + numbers.getFirst() + ")");
        
        // Double reversal
        System.out.println("\nDouble reversal (reversed().reversed()):");
        System.out.println("  " + reversed.reversed());
        
        System.out.println("\n✓ reversed() returns a view, not a copy (lazy evaluation)");
    }
    
    private static void demonstrateSequencedMap() {
        System.out.println("EXAMPLE 5: SequencedMap (Java 21+)");
        System.out.println("──────────────────────────────────\n");
        
        // LinkedHashMap maintains insertion order
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 87);
        scores.put("Charlie", 92);
        scores.put("Diana", 88);
        scores.put("Eve", 91);
        
        System.out.println("LinkedHashMap (maintains order): " + scores);
        
        // Cast to SequencedMap for Java 21 methods
        if (scores instanceof SequencedMap) {
            SequencedMap<String, Integer> sequencedScores = (SequencedMap<String, Integer>) scores;
            
            System.out.println("\nSequencedMap methods:");
            System.out.println("  firstEntry(): " + sequencedScores.firstEntry());
            System.out.println("  lastEntry(): " + sequencedScores.lastEntry());
            
            System.out.println("\nReversed entries:");
            System.out.println("  sequencedMap.reversed(): ");
            for (var entry : sequencedScores.reversed().entrySet()) {
                System.out.println("    " + entry.getKey() + ": " + entry.getValue());
            }
            
            System.out.println("\nRemoving from ends:");
            System.out.println("  pollFirstEntry(): " + sequencedScores.pollFirstEntry());
            System.out.println("  pollLastEntry(): " + sequencedScores.pollLastEntry());
            System.out.println("  Remaining: " + sequencedScores);
        }
        
        System.out.println("\n✓ SequencedMap provides sequenced operations on maps");
    }
}

/*
 * ═════════════════════════════════════════════════════════════════════════════
 * SEQUENCED COLLECTIONS - COMPREHENSIVE Q&A (Java 21)
 * ═════════════════════════════════════════════════════════════════════════════
 * 
 * Q1: What is SequencedCollection?
 * A: SequencedCollection is a new interface in Java 21 that represents
 *    collections with a defined sequence/order. It extends Collection
 *    and adds methods like getFirst(), getLast(), addFirst(), etc.
 * 
 * Q2: Which collections implement SequencedCollection?
 * A: List, Deque, LinkedHashSet, and other ordered collections.
 *    Regular HashMap and HashSet do NOT (they have no defined order).
 *    LinkedHashMap and LinkedHashSet DO (they maintain insertion order).
 * 
 * Q3: What is getFirst()?
 * A: Returns the first element in the sequence.
 *    Throws NoSuchElementException if collection is empty.
 *    For List: equivalent to list.get(0)
 *    For Deque: equivalent to deque.getFirst()
 * 
 * Q4: What is getLast()?
 * A: Returns the last element in the sequence.
 *    Throws NoSuchElementException if collection is empty.
 *    For List: equivalent to list.get(list.size()-1)
 *    For Deque: equivalent to deque.getLast()
 * 
 * Q5: What is reversed()?
 * A: Returns a reversed view of the collection WITHOUT copying.
 *    It is a lazy, live view - changes to original reflect in reversed.
 *    Syntax: SequencedCollection<E> rev = collection.reversed();
 * 
 * Q6: What is addFirst()?
 * A: Adds an element at the beginning of the collection.
 *    For List: equivalent to list.add(0, element)
 *    For Deque: equivalent to deque.addFirst(element)
 * 
 * Q7: What is addLast()?
 * A: Adds an element at the end of the collection.
 *    For List: equivalent to list.add(element)
 *    For Deque: equivalent to deque.addLast(element)
 * 
 * Q8: What is removeFirst()?
 * A: Removes and returns the first element.
 *    Throws NoSuchElementException if collection is empty.
 *    For List: equivalent to list.remove(0)
 *    For Deque: equivalent to deque.removeFirst()
 * 
 * Q9: What is removeLast()?
 * A: Removes and returns the last element.
 *    Throws NoSuchElementException if collection is empty.
 *    For List: equivalent to list.remove(list.size()-1)
 *    For Deque: equivalent to deque.removeLast()
 * 
 * Q10: How does SequencedCollection improve the Java Collections API?
 * A: It provides:
 *    • Unified API: Common operations across List, Deque, LinkedHashSet
 *    • Explicit ordering: Makes sequence guarantees clear in type system
 *    • Better readability: getFirst()/getLast() clearer than get(0)/get(n-1)
 *    • Consistency: Same naming across different collection types
 *    • Functional operations: Works with streams and other APIs
 * 
 * ═════════════════════════════════════════════════════════════════════════════
 */
