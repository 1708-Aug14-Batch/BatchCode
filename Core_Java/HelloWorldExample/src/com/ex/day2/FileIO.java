package com.ex.day2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class FileIO {

	public static void main(String[] args) {

		// NOTE: while the package name is "ex.files.log" it is entered
		// in this string as "ex/files/log" because that is how the filename
		// is read
		String filename1 = "src/com/ex/files/log.txt";
		String filename2 = "src/com/ex/files/log2.txt";

		// Delete file contents
		writeMessage(filename1, "", false);

		// Write the timestamp and a message
		writeMessage(filename1, "" + new Date(), true);
		writeMessage(filename1, "Good morning world! The stars say hello", true);
		System.out.println(retrieveMessage(filename1));

		moveMessage(filename1, filename2);

	}

	private static String retrieveMessage(String filename) {
		Scanner scan = null;
		String result = "";

		try {

			scan = new Scanner(new File(filename));

			while(scan.hasNext())
				result += scan.nextLine() + "\n";

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			scan.close();
		}

		return result;
	}

	// When append = false the file contents are erased
	static void writeMessage(String filename, String message, boolean append) {

		// try with resources block
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename, append))) {

			bw.write(message + "\n");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Moves the contents of filename1 to filename2
	// Erases the contents of filename2
	static void moveMessage(String filename1, String filename2) {
		Scanner scan = null;

		// try with resources block
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename2, false))) {

			scan = new Scanner(new File(filename1));
			
			while (scan.hasNext())
				bw.write(scan.nextLine() + "\n");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scan != null)
				scan.close();
		}

	}

}