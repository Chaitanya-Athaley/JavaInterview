package com.code.basic.linkedlist;

public class LinkedListCycle {
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int val) {
            this.val = val;
        }
    }
    
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        // Floyd's Cycle-Finding Algorithm
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Move one step
            fast = fast.next.next;     // Move two steps
            
            if (slow == fast) {
                return true;    // Cycle detected
            }
        }
        
        return false;   // No cycle found
    }
    
    public static void main(String[] args) {
        // Create a linked list with a cycle
        ListNode head = new ListNode(1);
        ListNode second = new ListNode(2);
        ListNode third = new ListNode(3);
        ListNode fourth = new ListNode(4);
        
        head.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = second;  // Creates a cycle
        
        System.out.println("Testing linked list with cycle:");
        System.out.println("Has cycle: " + hasCycle(head));
        
        // Test linked list without cycle
        ListNode noCycleHead = new ListNode(1);
        noCycleHead.next = new ListNode(2);
        noCycleHead.next.next = new ListNode(3);
        
        System.out.println("\nTesting linked list without cycle:");
        System.out.println("Has cycle: " + hasCycle(noCycleHead));
    }
}
