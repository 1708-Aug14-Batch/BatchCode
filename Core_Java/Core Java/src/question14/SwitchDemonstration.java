package question14;

import java.util.Date;

/*
 * Q14. Write a program that demonstrates the switch case. Implement the following functionalities in the cases:
	Case 1: Find the square root of a number using the Math class method. 
	Case 2: Display today�s date.
	Case 3: Split the following string and store it in a sting array. 
		�I am learning Core Java�
 */

import java.util.Scanner;

public class SwitchDemonstration {

	private static String coreString = "I am learning Core Java";
	
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		try {
			System.out.println("Enter a number from 1-3 inclusive");
			String numString = scan.next();
			int num = Integer.parseInt(numString);

			switch(num) {
			case 1:		// Find the square root of a number using the Math class method.

				System.out.println("Enter another number: ");
				int newNum = Integer.parseInt(scan.next());
				
				System.out.println("The square root of " + newNum + " is " +
						Math.sqrt(newNum));
				
				break;
			case 2:		// Display today�s date.

				System.out.println("Todays date is: " + new Date());
				
				break;
			case 3:		// Split coreString and store it in a sting array. 

				coreString.split(" ");
				System.out.println("The core string has been split.");
				break;

			default:
				System.out.println("That was not a valid value");
				break;
			}

		} catch (NumberFormatException e) {
			System.out.println("That is not a number");
		}


		scan.close();

	}

}
