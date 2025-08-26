package com.code.basic.threading;

import java.util.concurrent.*;

public class ProducerConsumerProblem {
    private static final int BUFFER_SIZE = 5;
    private static BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(BUFFER_SIZE);
    private static volatile boolean isRunning = true;
    
    static class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int value = 0;
                while (isRunning && value < 20) {
                    if (buffer.offer(value)) {
                        System.out.println("Produced: " + value);
                        value++;
                        Thread.sleep(100); // Simulate work
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    static class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                while (isRunning) {
                    Integer value = buffer.poll(100, TimeUnit.MILLISECONDS);
                    if (value != null) {
                        System.out.println("Consumed: " + value);
                        Thread.sleep(200); // Simulate work
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Thread(new Producer());
        Thread consumer1 = new Thread(new Consumer());
        Thread consumer2 = new Thread(new Consumer());
        
        producer.start();
        consumer1.start();
        consumer2.start();
        
        // Let it run for a while
        Thread.sleep(3000);
        isRunning = false;
        
        // Wait for threads to finish
        producer.join();
        consumer1.join();
        consumer2.join();
    }
}
