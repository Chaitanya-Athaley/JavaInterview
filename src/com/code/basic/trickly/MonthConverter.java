package com.code.basic.trickly;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public class MonthConverter {

	public static void main(String[] args) {
		Month month = Month.of(2);
		String displayName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
		System.out.println(displayName);
	}

}
