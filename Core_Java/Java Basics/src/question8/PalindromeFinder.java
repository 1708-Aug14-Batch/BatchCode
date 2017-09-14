package question8;

import java.util.ArrayList;

/*
 * Q8. Write a program that stores the following strings in an ArrayList and saves all the palindromes in another ArrayList.
 * "karan", "madam", "tom", "civic", "radar", "sexes", "jimmy", "kayak", "john",  "refer", "billy", "did"
 */

public class PalindromeFinder {

	private static String[] inputStrings = {"karan", "madam", "tom", "civic", "radar", "sexes", "jimmy", "kayak", "john", "refer", "billy", "did"};
	
	public static void main(String[] args) {
		
		// 1. Stroe all of the strings in an ArrayList
		ArrayList<String> strings = new ArrayList<String>();
		
		for (String str : inputStrings)
			strings.add(str);
		
		// 2. Store all the palindromes in another ArrayList
		ArrayList<String> palindromes = new ArrayList<String>();
		for (String str : inputStrings)
			if (isPalindrome(str))
				palindromes.add(str);
		
		// Print results
		System.out.println("Palindrome strings: ");
		for(String str : palindromes)
			System.out.println(str);
			
	}

	// Returns true if str is a palindrome
	// Checks characters two at a time for equality from both the front and back
	private static boolean isPalindrome(String str) {
		
		for (int i = 0; i < str.length()/2; i++)
			if (str.charAt(i) != str.charAt(str.length()-i-1))
				return false;
		
		return true;
	}
}