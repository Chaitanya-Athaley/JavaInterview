package com.code.basic.leetcode;

import java.util.LinkedHashSet;
import java.util.Set;

public class HappyNumber {

	public static void main(String[] args) {
		//A happy number is a positive integer that leads to 1 when repeatedly replaced by the sum of the squares of its digits; otherwise, it enters a cycle not including 1. 
//		example : 
//			input :19 		19→82→68→100→1	Happy ✓
//			input :20		20→4→16→37→58→89→145→42→20(cycle)	Unhappy ✗
		boolean happy = isHappy(20);
		System.out.println(happy);
		
	}

	public static boolean isHappy(int n) {
		Set<Integer> store = new LinkedHashSet<>();
		while(n != 1 && !store.contains(n)) {
			store.add(n);
			n = sumOfSqure(n);
		}
		return n == 1;
	}

	private static int sumOfSqure(int n) {
		int sum = 0;
		while(n>0) {
			int digit = n % 10;
			sum = sum + digit * digit;
			n = n / 10;
		}
		return sum;
	}

}
