
package question6;

import java.util.Scanner;

/** Determines if given number is odd or even.
 * @author jwurd
 *@version Aug 14 2017
 */
public class EvenInterger {
	public static  int number;


	public static void main(String []args){

		Scanner in = new Scanner(System.in);

		// User Prompt to enter number check if even.
		System.out.print("Enter a number");
		// scans user input for number entered.
		number = in.nextInt();
		isEven(number);
	}

	/**
	 *  Determines if number is even by using division.
	 * @param number
	 */
	public static void isEven(int number){

		int  divide = number/2;
		if( divide == number*2){
			System.out.print("Even");
		}
		else  System.out.print("Odd");

	}
}


/*
 * Checks to see if user input is an interger.
 *  if not promts invalid input. and repeats until user enters an interger
 *  @param number 
 *
	public static void isANumber(int number){

		 Scanner in = new Scanner(System.in);

		 // User Prompt to enter number check if even.
		System.out.print("Enter a number");

		// scans user input for number entered.
		number = in.nextInt();

 */