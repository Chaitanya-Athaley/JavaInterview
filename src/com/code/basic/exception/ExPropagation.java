package com.code.basic.exception;

class MyException1{
	void name(){ // Not thow any Exception
		System.out.println("1");
	}
	
	void openUrl() throws Exception{
		System.out.println("1 url");
	}

}

class MyException2 extends MyException1{

//  if parent method  Not thows any Exception, child can not throw any checked Exception
//	@Override
//	void name() throws Exception{  // Compile time error
//		System.out.println("2");
//	}
	
// if parent method  Not thows any Exception, child can throw any RuntimeException
	@Override
	void name() throws RuntimeException{
		System.out.println("2");
	}
}

class MyException3 extends MyException1{

//  if parent method thows checked Exception, child can throw SAME checked Exception
//	@Override
//	void openUrl() throws Exception{  
//		System.out.println("2 url");
//	}
	
//	subclass of same parent exception
//	@Override
//	void openUrl() throws CompletionException{
//		System.out.println("2");
//	}
	
//  if parent method thows checked Exception, child can throw any RuntimeException
//	@Override
//	void openUrl() throws RuntimeException{
//		System.out.println("2");
//	}
	
//	No Exception
	@Override
	void openUrl(){
		System.out.println("2");
	}
	
	
}

public class ExPropagation {

	public static void main(String[] args) {
		MyException2 e = new MyException2();
		e.name();
	}

}
