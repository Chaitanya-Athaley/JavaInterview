package com.code.basic.exception;

class MyException11{
	
	void openUrl() throws RuntimeException{
		System.out.println("1 url");
	}

}

class MyException22 extends MyException11{

//  if parent method thows any RuntimeException, child can not throw any checked Exception
//	@Override
//	void openUrl() throws Exception{  // Compile time error
//		System.out.println("2");
//	}
	
// if parent method  thows any RuntimeException, child can throw any same or any other runtime exception
//	@Override
//	void openUrl() throws RuntimeException, NullPointerException{
//		System.out.println("2");
//	}
	
//	No Exception
	@Override
	void openUrl(){
		System.out.println("2");
	}
}

public class ExPropagationForRuntime {

	public static void main(String[] args) {
		MyException22 e = new MyException22();
		e.openUrl();
	}

}
