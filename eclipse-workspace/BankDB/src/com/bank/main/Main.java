/**
 * 
 */
package com.bank.main;

import com.bank.service.BankService;

/**
 * Contains the main method to start the application.
 * @author Will Underwood
 */
public class Main {

	/**
	 * Starts the application
	 * @param args - not used
	 */
	public static void main(String[] args) {
		BankService service = new BankService();
		service.run();
	}

}
