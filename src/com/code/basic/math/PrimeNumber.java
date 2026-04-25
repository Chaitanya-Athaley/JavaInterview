package com.code.basic.math;

public class PrimeNumber {

	public static void main(String[] args) {
		int number = 2;
		boolean flag = isPrime(number);
		System.out.println(flag);
	}

	private static boolean isPrime(int number) {
		for(int i=2; i<number; i++) {
			if(number % i == 0) {
				return false;
			}
		}
		return true;
	}

}
