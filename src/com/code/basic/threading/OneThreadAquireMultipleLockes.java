package com.code.basic.threading;

public class OneThreadAquireMultipleLockes {
	static int balance = 10101;
	public static void main(String[] args) {
		OneThreadAquireMultipleLockes otaml = new OneThreadAquireMultipleLockes();
		otaml.transfer(101);
		System.out.println(balance);
		
	}
	
	  public void transfer(int amount) {
	        synchronized(this) {           // Thread acquires lock A
	            synchronized(this) {         // Same thread acquires lock B
	                this.balance -= amount;
	                //this.balance += amount;
	            }                          // Releases lock B
	        }                              // Releases lock A
	    }

}
