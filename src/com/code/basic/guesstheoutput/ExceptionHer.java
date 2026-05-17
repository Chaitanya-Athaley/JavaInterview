package com.code.basic.guesstheoutput;

import java.io.IOException;

class A1 {
	void m1() throws IOException{
		System.out.println("In m1 A");
	}
}
class B1 extends A1{
// Rules applicable for only checked exception
//	void m1() throws Exception{    //Compile time Exception, Parent class in Derived class
//		System.out.println("In m1 B");
//	}
	void m1() throws RuntimeException{    
		System.out.println("In m1 B");
	}
}

public class ExceptionHer {

	public static void main(String[] args) {
		A1 a = new B1();
		try {
			a.m1();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
