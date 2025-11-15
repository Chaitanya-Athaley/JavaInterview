package com.code.basic.trickly;


interface A {
	String name();
}
//@FunctionalInterface
//interface B extends A{ // now B have 2 abstract method, so no longer FunctionalInterface , C.T.E.
//	String open();
//}

@FunctionalInterface
interface App {
	String open();
}
@FunctionalInterface
interface Sapp extends App{ // now Sapp have 1 abstract method, so it is FunctionalInterface. valid
	String open();
}

public class Extend2FunctionalInterfaceDemo {

	public static void main(String[] args) {
		A hold = ()-> "i am from b"; 
		String name = hold.name();
		System.out.println(name);
		
		Sapp sp = ()-> "i am valid";
		System.out.println(sp.open());
	}

}
