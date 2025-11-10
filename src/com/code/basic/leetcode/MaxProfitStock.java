package com.code.basic.leetcode;

public class MaxProfitStock {

	public static int maxProfit(int[] prices) {
		if (prices == null || prices.length < 2)
			return 0;

		int minPrice = Integer.MAX_VALUE;
		int maxProfit = 0;

		for (int price : prices) {
			if (price < minPrice) {
				minPrice = price; // update lowest price seen
			} else {
				maxProfit = Math.max(maxProfit, price - minPrice);
			}
		}

		return maxProfit;
	}

	public static void main(String[] args) {
		int[] prices = { 7, 1, 5, 3, 6, 4 };
		System.out.println(maxProfit(prices)); // Output: 5
	}

	//alternate
	public static int maxProfitBrute(int[] prices) {
	    int max = 0;
	    for (int i = 0; i < prices.length; i++) {
	        for (int j = i + 1; j < prices.length; j++) {
	            max = Math.max(max, prices[j] - prices[i]);
	        }
	    }
	    return max;
	}
}
