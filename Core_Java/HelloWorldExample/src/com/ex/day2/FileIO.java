package com.ex.day2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class FileIO {
	
	// NOTE: while the package name is "ex.files.log" it is entered
	// in this string as "ex/files/log" because that is how the filename
	// is read
	static String filename = "src/com/ex/files/log.txt";
	
	public static void main(String[] args) {
		
		// Delete file contents
		writeMessage("", false);
		
		// Write the timestamp and a message
		writeMessage("" + new Date(), true);
		writeMessage("Good morning world! The stars say hello", true);
		System.out.println(retrieveMessage());
		
	}

	private static String retrieveMessage() {
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
	static void writeMessage(String message, boolean append) {
		
		// try with resources block
		try(BufferedWriter bw = new BufferedWriter(
				new FileWriter(filename, append))) {
			
			bw.write(message + "\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
