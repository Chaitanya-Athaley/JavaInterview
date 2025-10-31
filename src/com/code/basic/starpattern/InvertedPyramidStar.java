package com.code.basic.starpattern;

public class InvertedPyramidStar {
	public static void main(String[] args) {
		
		
//		*********
//		 *******
//		  *****
//		   ***
//		    *
		
		
		int rows = 5;
		for (int i = rows; i >= 1; i--) {
			for (int j = 1; j <= rows - i; j++) {
				System.out.print(" ");
			}
			for (int k = 1; k <= (2 * i - 1); k++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}

