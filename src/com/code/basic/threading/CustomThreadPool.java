package com.code.basic.threading;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class CustomThreadPool {
    private final BlockingQueue<Runnable> taskQueue;
    private final List<WorkerThread> workerThreads;
    private volatile boolean isShutdown;
    
    public CustomThreadPool(int numThreads) {
        taskQueue = new LinkedBlockingQueue<>();
        workerThreads = new ArrayList<>(numThreads);
        isShutdown = false;
        
        // Create and start worker threads
        for (int i = 0; i < numThreads; i++) {
            WorkerThread worker = new WorkerThread();
            workerThreads.add(worker);
            worker.start();
        }
    }
    
    public void submit(Runnable task) {
        if (!isShutdown) {
            taskQueue.offer(task);
        }
    }
    
    public void shutdown() {
        isShutdown = true;
        for (WorkerThread worker : workerThreads) {
            worker.interrupt();
        }
    }
    
    private class WorkerThread extends Thread {
        @Override
        public void run() {
            while (!isShutdown) {
                try {
                    Runnable task = taskQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (task != null) {
                        task.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with 3 threads
        CustomThreadPool threadPool = new CustomThreadPool(3);
        
        // Submit some tasks
        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Task " + taskId + " executed by thread " + 
                                 Thread.currentThread().getName());
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        
        // Let tasks run for a while
        Thread.sleep(5000);
        
        // Shutdown the thread pool
        threadPool.shutdown();
    }
}
