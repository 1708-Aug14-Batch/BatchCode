package com.revature.q09;

import java.util.ArrayList;

/*
 * Fills list with all numbers 2 - 100, since we know 1 isn't prime
 * Then uses the Sieve method to remove all composite numbers
 * Leaving us with only prime numbers in the list
 * Much faster than simply doing plain primality testing of each
 * number from 1 to 100 using the double loop method
 */
public class Primes {
	public static void main(String[] args) {
		ArrayList<Integer> list = new ArrayList<>();
		for (int i=2; i<=100; i++)
			list.add(i);
		for (int i=2; i<=10; i++)
			for (int j=i*2; j<=100; j += i)
				list.remove((Object) j);
		System.out.println("Prime numbers 1 to 100:");
		for (int n: list)
			System.out.print(n + " ");
	}
	
}