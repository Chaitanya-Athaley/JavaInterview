package com.code.basic.trickly;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		System.out.println("------------------------------------------------------------");

		System.out.println("------------------------------------------------------------");

		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");

		Callable<String> task1 = () -> {
			Thread.sleep(3000);
			return "i am Task 1";
		};
		
		Callable<String> task2 = () -> {
			return "i am Task 2";
		};
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future<String> submit = executorService.submit(task1);
		Future<String> submit2 = executorService.submit(task2);

		
		for (int i = 0; i < 10; i++) {
			String string = submit2.get();
			String string2 = submit.get();
			System.out.println(string);
			System.out.println(string2);
		}
		executorService.shutdown();
		System.out.println("------------------------------------------------------------");
		System.out.println("------------------------------------------------------------");

		System.out.println("------------------------------------------------------------");

		System.out.println("------------------------------------------------------------");

	}

}
