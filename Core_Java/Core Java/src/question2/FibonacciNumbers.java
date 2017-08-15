package question2;

import java.util.Arrays;

/*
 * Q2. Write a program to display the first 25 Fibonacci numbers beginning at 0. 
 */

public class FibonacciNumbers {
	
	private static final int NUMBERS = 25;
	
	public static void main(String[] args) {
		int[] sequence = Fibonacci(NUMBERS);
		
		System.out.println(Arrays.toString(sequence));
	}

	/*
	 * Returns a string containing a sequence of Fibonacci numbers
	 * The length of the sequence is determined by int num 
	 */
	private static int[] Fibonacci(int num) {
		
		// Check border cases for input number
		if (num <= 0)
			return null;
		else if (num == 1)
			return new int[1];
		
		// Start with an empty string to append numbers to
		int[] sequence = new int[num];
		
		// Add the first two numbers of the sequence
		sequence[0] = 0;
		sequence[1] = 1;
		
		int index = 0 + 2;	// Start with the third index
		while (index < num) {
			sequence[index] = getNextFibonacciNumber(sequence[index-1], sequence[index-2]);
			index++;
		}
		
		return sequence;
	}
	
	private static int getNextFibonacciNumber(int a, int b) {
		return a + b;
	}
}
