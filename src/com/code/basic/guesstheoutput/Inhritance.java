package com.code.basic.guesstheoutput;

class A{
	void m1() {
		System.out.println("In m1 A");
	}
}
class B extends A{
	void m1() {
		System.out.println("In m1 B");
	}
	void m2() {
		System.out.println("In m2 B");
	}
}

public class Inhritance {

	public static void main(String[] args) {
		A a = new B();
		//a.m2(); what is the output. if CTE then why
	}

}


