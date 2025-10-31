package com.code.basic.java8.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FirstSmallestUniqueEvenNumber {

	public static void main(String[] args) {
		List<String> list = Arrays.asList("2", "4", "6", "4", "8", "10", "2", "3");
		// calculate frequency
		// check even
		// frequency == 1

		List<Integer> numbers = list.stream().map(Integer::parseInt).collect(Collectors.toList());
		Integer uniqueNumber = numbers.stream().filter(n -> n%2==0)
				.filter(n -> Collections.frequency(numbers, n) == 1)
				.findFirst().get();
		System.out.println(uniqueNumber);
	}

}
