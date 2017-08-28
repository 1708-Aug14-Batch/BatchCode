package com.bank.run;

import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

import com.bank.pojos.Account;
import com.bank.pojos.AccountType;
import com.bank.pojos.User;
import com.bank.service.Service;

public class RunBank {
	private static Scanner scan;

	public static void main(String[] args) {
		scan = new Scanner(System.in);

		startMenu();

		scan.close();
	}

	private static void startMenu() {

		String in = null;
		do {
			sectionBreak();

			System.out.println("Welcome to the bank!");
			System.out.println("1. Login");
			System.out.println("2. New user");
			System.out.println("3. Exit");

			switch (in = scan.nextLine()) {
			case "1":
				loginDialogue();
				return;
			case "2":
				newUserDialogue();
				return;
			default:
			}
		} while (!"3".equals(in));
	}

	private static void loginDialogue() {
		sectionBreak();

		String email = getUserInput("Email");
		String password = getUserInput("Password");

		String errorMessage;
		if ((errorMessage = Service.loginUser(email, password)) != null) {
			System.out.println("Error: " + errorMessage);
			startMenu();
			return;
		}
		else {
			mainMenu();
			return;
		}
	}

	private static void newUserDialogue() {
		sectionBreak();

		String email = getUserInput("Email");
		String first = getUserInput("First name");
		String last = getUserInput("Last name");

		User user = new User(first, last, email);

		String password = null;
		String reentry = null;
		while (password == null || !password.equals(reentry)) {
			password = getUserInput("Password");
			reentry = getUserInput("Confirm password");

			if (!password.equals(reentry)) {
				System.out.println("Error: Passwords must match");
			}
		}

		String creationError = Service.createUser(user, password);
		if (creationError != null) {
			System.out.println("Error: " + creationError);
		}
	}

	private static void mainMenu() {		
		User user = Service.getCurrentUser();

		for (;;) {
			sectionBreak();

			System.out.println("Welcome " + user.getFirstName() + " " + user.getLastName() + "!");
			System.out.println("1. View balance");
			System.out.println("2. Withdraw");
			System.out.println("3. Deposit");
			System.out.println("4. Transfer funds");
			System.out.println("5. Edit user");
			System.out.println("6. Logout");

			switch (scan.nextLine()) {
			case "1":
				viewBalanceDialogue();
				return;
			case "2":
				withdrawDialogue();
				return;
			case "3":
				depositDialogue();
				return;
			case "4":
				transferDialogue();
				return;
			case "5":
				editUserDialogue();
				return;
			case "6":
				logoutDialogue();
				return;
			default:
			}
		}
	}

	private static void withdrawDialogue() {
		sectionBreak();

		Account account = chooseAccount(-1);		
		String input = getUserInput("Amount to withdraw");

		try {
			float amount = Float.parseFloat(input);

			if (confirm("withdraw "+ NumberFormat.getCurrencyInstance().format(amount))) {
				if (amount <= 0.0f) {
					System.out.println("Error: Must be a positive amount");
				}
				else if (amount > account.getBalance()) {
					System.out.println("Error: Insufficient funds");
				}
				else {
					Service.withdraw(amount, account);
					mainMenu();
					return;
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Error: Bad number format");
		}

		for (;;) {
			sectionBreak();
			System.out.println("1. Try again");
			System.out.println("2. Return to menu");
			switch (scan.nextLine()) {
			case "1":
				withdrawDialogue();
				return;
			case "2":
				mainMenu();
				return;
			default:
			}
		}
	}

	private static void depositDialogue() {
		sectionBreak();

		Account account = chooseAccount(-1);
		String input = getUserInput("Amount to deposit");

		try {
			float amount = Float.parseFloat(input);
			if (confirm("deposit " + NumberFormat.getCurrencyInstance().format(amount))) {
				if (amount <= 0) {
					System.out.println("Error: Must be a positive amount");
				}
				else {
					Service.deposit(amount, account);
					mainMenu();
					return;
				}
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Error: Bad number format.");
		}
		for (;;) {
			System.out.println();
			System.out.println("1. Try again");
			System.out.println("2. Return to menu");
			switch (scan.nextLine()) {
			case "1":
				depositDialogue();
				return;
			case "2":
				mainMenu();
				return;
			default:
			}
		}
	}

	private static void viewBalanceDialogue() {
		sectionBreak();

		Account account = chooseAccount(-1);
		User user = Service.getCurrentUser();

		if (user != null) {
			System.out.println("Current balance: " + NumberFormat.getCurrencyInstance().format(account.getBalance()));
			System.out.println("1. Return to menu");
			for (;;) {
				switch (scan.nextLine()) {
				case "1":
					mainMenu();
					return;
				default:
				}
			}
		}
		else {
			Service.logoutUser();
			startMenu();
		}
	}

	private static void transferDialogue() {
		sectionBreak();

		System.out.println("Which account would you like to transfer from?");
		Account from = chooseAccount(-1);

		System.out.println("Which account would you like to transfer to?");
		Account to = chooseAccount(from.getId());

		String input = getUserInput("Amount to transfer");

		try {
			float amount = Float.parseFloat(input);
			if (confirm("transfer " + NumberFormat.getCurrencyInstance().format(amount))) {
				if (amount <= 0) {
					System.out.println("Error: Must be a positive amount");
				}
				else if (amount > from.getBalance()) {
					System.out.println("Error: Insufficient funds");
				}
				else {
					if (Service.transfer(amount, from, to)) {
						mainMenu();
						return;
					}
					else {
						System.out.println("Error: Transfer failed");
					}
				}
			}

		} catch (NumberFormatException e) {
			System.out.println("Error: Bad number format");
		}

		for (;;) {
			System.out.println();
			System.out.println("1. Try again");
			System.out.println("2. Return to menu");
			switch (scan.nextLine()) {
			case "1":
				depositDialogue();
				return;
			case "2":
				mainMenu();
				return;
			default:
			}
		}
	}

	private static void editUserDialogue() {
		sectionBreak();

		System.out.println("Which info would you like to update?");
		System.out.println("1. Update info");
		System.out.println("2. Open account");
		System.out.println("3. Close account");
		System.out.println("4. Return to menu");

		for (;;) {
			switch (scan.nextLine()) {
			case "1":
				updateInfoDialogue();
				return;
			case "2":
				openAccountDialogue();
				return;
			case "3":
				closeAccountDialogue();
				return;
			case "4":
				mainMenu();
				return;
			default:
			}
		}
	}

	private static void updateInfoDialogue() {
		sectionBreak();

		System.out.println("Which info would you like to update?");
		System.out.println("1. Password");
		System.out.println("2. First name");
		System.out.println("3. Last name");
		System.out.println("4. Return to menu");

		for (;;) {
			switch (scan.nextLine()) {
			case "1":
				String password = null;
				String reentry = null;
				while (password == null || !password.equals(reentry)) {
					password = getUserInput("New password");
					reentry = getUserInput("Confirm password");

					if (!password.equals(reentry)) {
						System.out.println("Error: Passwords must match");
						updateInfoDialogue();
						return;
					}
				}

				Service.updatePassword(password);
				updateInfoDialogue();
				return;
			case "2":
				String first = getUserInput("First name");
				if (confirm("set your first name to " + first)) {
					Service.updateFirstName(first);
				}
				updateInfoDialogue();
				return;
			case "3":
				String last = getUserInput("Last name");
				if (confirm("set your last name to " + last)) {
					Service.updateLastName(last);
				}
				updateInfoDialogue();
				return;
			case "4":
				editUserDialogue();
				return;
			default:
			}
		}
	}
	
	private static void openAccountDialogue() {
		sectionBreak();
		
		System.out.println("What type of account would you like to open?");
		System.out.println();
		System.out.println("1. Savings");
		System.out.println("2. Checking");
		System.out.println("3. Credit");
		System.out.println("4. Cancel");
		
		int type = -1;
		boolean cont = true;
		while (cont) {
			switch (scan.nextLine()) {
			case "1":
				type = 1;
				cont = false;
				break;
			case "2":
				type = 2;
				cont = false;
				break;
			case "3":
				type = 3;
				cont = false;
				break;
			case "4":
				editUserDialogue();
				return;
			default:
			}
		}
		
		if (confirm("create a new account")) {
			Service.openAccount(type);
		}
		
		editUserDialogue();
		return;
	}
	
	private static void closeAccountDialogue() {
		sectionBreak();
		
		System.out.println("Which account would you like to close?");
		System.out.println();
		Account account = chooseAccount(-1);
		
		if (confirm("close this account")) {
			Service.closeAccount(account);
		}
		
		editUserDialogue();
		return;
	}

	private static void logoutDialogue() {
		sectionBreak();

		if (confirm("logout")) {
			Service.logoutUser();
			startMenu();
			return;
		}
		else {
			mainMenu();
			return;
		}
	}

	private static Account chooseAccount(int skipId) {
		System.out.println("Select account:");

		List<Account> accounts = Service.getAccounts(Service.getCurrentUser().getId());

		int i = 0;
		int except = -1;
		for (Account a : accounts) {
			i++;
			if (a.getId() != skipId) {
				System.out.println("" + i + ". " + a.getId() + " (" + AccountType.TYPES.values()[a.getType()] + ")");
			}
			else {
				except = i;
			}
		}

		int index = 0;
		while (index < 1 || index > accounts.size() || index == except) {
			try {
				index = Integer.parseInt(scan.nextLine());

			} catch (NumberFormatException e) {
				System.out.println("Error: Bad number format");
				for (;;) {
					System.out.println();
					System.out.println("1. Try again");
					System.out.println("2. Return to menu");
					switch (scan.nextLine()) {
					case "1":
						return chooseAccount(skipId);
					case "2":
						mainMenu();
						return null;
					default:
					}
				}
			}
		}

		return accounts.get(index-1);
	}

	private static String getUserInput(String inputName) {
		String input = null;
		while (input == null || "".equals(input)) {
			System.out.print(inputName + ": ");
			input = scan.nextLine();
		}
		return input;
	}

	private static boolean confirm(String message) {
		System.out.print("Are you sure you want to " + message + "? (y/n) ");
		for (;;) {
			String input = scan.nextLine();

			if ("y".equals(input)) return true;			
			if ("n".equals(input)) return false;
		}
	}

	private static void sectionBreak() {
		System.out.println("\n***");
	}

	/*
	 * Requirements: As a user I can,
	 *  - Login
	 *  - Create an account
	 *  - Logout
	 *  - Withdraw and deposit funds
	 *  - View my account balance
	 *  - Edit my account info
	 *  
	 * Tech specs:
	 *  - Core Java, file I/O
	 */
}