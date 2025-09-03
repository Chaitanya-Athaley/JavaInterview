package com.code.basic.string;

import java.util.HashSet;
import java.util.Set;

public class RemoveDuplicates {

	public static void main(String[] args) {
		removeDuplicates("sddddssc");
	}
	
	public static String removeDuplicates(String str) {
	    Set<Character> seen = new HashSet<>();
	    StringBuilder result = new StringBuilder();
	    for (char c : str.toCharArray()) {
	        if (!seen.contains(c)) {
	            seen.add(c);
	            result.append(c);
	        }
	    }
	    return result.toString();
	}
}
