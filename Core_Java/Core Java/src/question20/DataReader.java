package question20;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Q20. Create a notepad file called Data.txt and enter the following: 
Mickey:Mouse:35:Arizona
Hulk:Hogan:50:Virginia
Roger:Rabbit:22:California
Wonder:Woman:18:Montana

Write a program that would read from the file and print it out to the screen in the following format:

Name: Mickey Mouse
Age: 35 years
State: Arizona State

 */

public class DataReader {
	
	public static void main(String[] args) {
		
		Scanner scan = null;
		ArrayList<Character> characters = new ArrayList<Character>();
		
		try {
			scan = new Scanner(new File("Data.txt"));
			scan.useDelimiter(":");
			
			while (scan.hasNext()) {
				// Each line has four values: first name, last name,
				// age, and the state that they live in
				String firstName = scan.next();
				String lastName = scan.next();
				int age = Integer.parseInt(scan.next());
				String residence = scan.nextLine();
				
				// Create a new character from the data read in
				characters.add(new Character(firstName, lastName, age, residence));
			}
			
			for (Character cha : characters)
				System.out.println(cha);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}
	}

}
