package com.WeekOneHW.QuestionSeventeen;

import java.util.*; //Importing in Java.util for the scanner

public class Main {

	public static void main(String[] args) {
		int years; //Establishing the years as an int as it won't ever been a decimal number
		double rate, interest, principal; //The rate, interest, and principal are doubles as they can be decimal numbers
		Scanner input = new Scanner(System.in); //Creating the scanner object
		
		/*
		 * The following outputs and gets the user input for the principal, the rate, and the years.
		 */
		System.out.print("Enter the prinipal investment: ");
		principal = input.nextInt();
		System.out.print("Enter the percent rate: ");
		rate = input.nextDouble();
		rate = (rate/100); //Since rate is a percent we divide by 100 to make it accurate
		System.out.print("Enter the number of years: ");
		years = input.nextInt();
		
		interest = principal * rate * years; //Sets interest to the formula of principal * rate * years
		
		System.out.println("The interest is: " + interest); //Outputs the result.
		
	}

}
