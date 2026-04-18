package com.code.basic.string;

public class CircularRotation {

	public static void main(String[] args) {
		String base = "abcd";
		String input = "cdab";
		
		// Solution :
		String result = base + base;
		if(result.contains(input) && input.length() == base.length()) {
			System.out.println("TRUE");
		} else {
			System.out.println("FALSE");
		}
	}

}
