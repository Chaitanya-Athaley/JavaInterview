package com.code.basic.leetcode;

public class ShiftNegativeToRight {

	public static void main(String[] args) {
		int[] input  = {32,43,-5,6,7,-8,-90,7,5,45};
		int[] output = new int[input.length];
		int j=0;
		for (int i = 0; i < output.length; i++) {
			if(input[i]>0) {
				output[j] = input[i];
				j++;
			}
		}
		for (int i = 0; i < output.length; i++) {
			if(input[i]<0) {
				output[j] = input[i];
				j++;
			}
		}
		
		for (int i = 0; i < output.length; i++) {
			System.out.println(output[i]);
		}
	}

}
