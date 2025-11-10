package com.code.basic.trickly;

public class PassByValue {

	public static void main(String[] args) {
		int num = 5;
		modifyNum(num);
		System.out.println(num);
	}

	private static int modifyNum(int num) {
		return num = 20;
	}

}
