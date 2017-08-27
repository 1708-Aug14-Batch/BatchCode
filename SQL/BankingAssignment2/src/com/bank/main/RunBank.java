package com.bank.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.bank.dao.DaoSqlImpl;
import com.bank.pojos.*;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.service.Service;

/*
 * Requirements:
 * 	A bank_user can...
		Create account(s)
		Withdraw
		Deposit
		Close account(s)
		View account(s)
		Edit account(s) - change first/last name, password, email
		Transfer funds between a single user's accounts
 * 
 * and apparently I'm not allowed to keep track of transactions now
 * 
 * Tech Specs
 * 	- core java, file i/o
 */

// TODO add clerk functionality
//			View all transactions, Users, Persons
//			add new clerks (later there will have to be a hierarchy of clerks where only high-level clerks can add lower-level clerks)
//			change password

//			Change a user's email to an available email
//			Credit a uer's account with accrued rewards balance
//			Change a user's username to an available username
//			upgrade an account
//			Provide verification for deleting an account
//		Clerks have two ways to log in: to access the secret clerk menu, and a temporary log in which logs out after verification for doing admin things like changing a user's username
// TODO add transactions (transaction method which calls any transaction) - consider changing transactionType to String
// Add more JUnit tests
public class RunBank {

	private static Scanner scan = new Scanner(System.in);
	private static Service bankService = new Service();
	// Keeps track of the account currently logged into the system
	private static String accountUsernameLoggedIn = null;;
	// Keeps track of the employee ID of the clerk currently logged in
	private static int clerkIDLoggedIn = 0;

	private static String bankName = "People's Bank of Earth";

	// Implementation to actually run bank application
	public static void main(String[] args) {

		// FIXME DEBUG V
		debugStuff();
		// FIXME DEBUG ^
		
		System.out.println("Hello. Welcome to the automating banking system for " +
				bankName + ".");

		bankingSystem();

		System.out.println("Thank you for using the automating banking system. Have a wonderful day.");

		scan.close();

	}

	private static void bankingSystem() {

		bankloop:
			while (true) {
				System.out.println();	// Formatting
				if (accountUsernameLoggedIn != null)
					displayBasicAccountInfo();
				System.out.println("Please choose one of the following commands:");
				System.out.println("login, logout, create account, withdraw, deposit, view account, edit account, quit");

				String input = "";		// used in a few of the case statements
				switch(scan.nextLine().trim().toLowerCase()) {
				case "login":
				case "log in":
					if (accountUsernameLoggedIn != null) {
						System.out.println("A user is already logged in.");
						continue;
					}

					login();

					break;
				case "logout":
				case "log out":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before you can log out.");
						continue;
					}

					logout();

					break;
				case "create account":
				case "create":
					if (accountUsernameLoggedIn != null) {
						System.out.println("Please log out before creating a new account.");
						continue;
					}

					displayTermsAndConditions();
					System.out.println("Do you accept these conditions? yes/no");

					if (scan.nextLine().trim().equalsIgnoreCase("yes"))
						if (!createAccount())
							System.out.println("Account creation failed.");
						else {
							System.out.print("Account created, welcome to " + bankName + ".");
							System.out.println(" Please log in to view or modify your account.");
						}

					break;
				case "withdraw":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					System.out.println("Would you like to withdraw from checking or transfer money from checking to savings?");
					input = scan.nextLine().trim().toLowerCase();
					if (input.contains("checking") || input.contains("withdraw"))
						transferFunds(true);
					else if (input.contains("saving") || input.contains("transfer"))
						transferFunds(false);
					else System.out.println("Input was not recognized. Valid entries are \"checking\" and \"saving\"");

					break;
				case "deposit":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					System.out.println("Would you like to deposit to checking or to savings?");
					input = scan.nextLine().trim().toLowerCase();
					if (input.contains("checking"))
						deposit(true);
					else if (input.contains("saving"))
						deposit(false);
					else System.out.println("Input was not recognized. Valid entries are \"checking\" and \"saving\" in all lower case");

					break;
				case "view account":
				case "view":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					viewAccount();

					break;
				case "edit account":
				case "edit":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					editAccount();

					break;
				case "quit":
				case "q":
					break bankloop;
				case "clerk login":
				case "clerk log in":

					// TODO add implementation

					break;
				default:
					System.out.println("Command not recognized. Please try again.");
				}	// end case statement

			}	// end bankloop

	// If the user is still logged in when they quit, the log them off.
	if (accountUsernameLoggedIn != null)
		logout();

	}

	private static void displayBasicAccountInfo() {
		System.out.println("Logged in as: " + accountUsernameLoggedIn +	", Account type: " +
				bankService.getUser(accountUsernameLoggedIn).getLevel());
	}

	// Returns true if login was successful
	private static boolean login() {
		System.out.print("Enter username: ");
		String username = scan.nextLine().trim().toLowerCase();
		System.out.print("Enter password: ");
		String password = scan.nextLine().trim();

		Account acc = bankService.validateUser(username, password);
		
		if (acc == null) {
			System.out.println("Login failed, incorrect username and password combination.");
			return false;
		} else {
			accountUsernameLoggedIn = acc.getUsername();
			System.out.println("Login successful, welcome " + accountUsernameLoggedIn);
			return true;
		}
	}

	// Returns true if logout was successful
	private static boolean logout() {

		System.out.println("Logout successful.");
		accountUsernameLoggedIn = null;

		return true;
	}

	// Returns true if an account was successfully created
	private static boolean createAccount() {

		// Get information from user to find/create a person
		System.out.print("Enter your first name: ");
		String firstName = scan.nextLine().trim();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine().trim();
		System.out.print("Enter your Social Security Number: ");
		String SSN = scan.nextLine().trim();

		Person per = bankService.tryCreatePerson(SSN, firstName, lastName, "");
		if (per == null)
			return false;

		// Set email. email is not required to be a person but it
		// is required to have an account
		if (!setEmail(per)) {
			return false;
		}

		// Get information from user to create an account
		System.out.print("Enter your username: ");
		String username = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password1 = scan.nextLine().trim();
		System.out.print("Enter password again: ");
		String password2 = scan.nextLine().trim();

		if (password1.equals(password2))
			return bankService.tryCreateUser(per, username, password1, accountLevel.BRONZE);
		else {
			System.out.println("Passwords do not match.");
			return false;
		}
	}

	private static boolean transferFunds(boolean checking) {
		BigDecimal balance = null;
		String accountStr = null;

		if (checking) {
			balance = bankService.getCheckingAccountBalance(accountUsernameLoggedIn);
			accountStr = "Checking";
		}
		else {		// Savings account
			balance = bankService.getSavingsAccountBalance(accountUsernameLoggedIn);
			accountStr = "Savings";
		}

		BigDecimal withdrawAmmount = null;
		try {

			System.out.print("Current funds: $" + balance.toString() +
					"\nEnter an ammount to be withdrawn from your " + accountStr + " account: ");

			withdrawAmmount = new BigDecimal(scan.nextLine().trim());

			if (withdrawAmmount.abs() != withdrawAmmount) {
				System.out.println("You cannot withdraw a negative quantity.");
				return false;
			}

			switch(balance.compareTo(withdrawAmmount)) {
			case -1:
				System.out.println("You cannot withdraw more than your " + accountStr + " balance");
				return false;
			default:

				BankUser guy = bankService.getUser(accountUsernameLoggedIn);

				BigDecimal finalBalance = balance.subtract(withdrawAmmount);
				if (checking)
					guy.getAccount().setCheckingBalance(finalBalance);
				else {		// Savings account
					guy.getAccount().setSavingsBalance(finalBalance);
					BigDecimal checkingBalance = guy.getAccount().getCheckingBalance();
					guy.getAccount().setCheckingBalance(checkingBalance.add(withdrawAmmount));
				}

				if (!bankService.updateUser(guy)) {
					System.out.println("Transaction failed.");
					return false;
				}

				System.out.println("$" + withdrawAmmount.toString() + " withdrawn from " + accountStr);
				System.out.println("$" + guy.getAccount().getCheckingBalance().toString() + " new checking balance");

				return true;
			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}

	}

	private static boolean deposit(boolean checking) {
		BigDecimal balance = null;
		String accountStr = null;

		if (checking) {
			balance = bankService.getCheckingAccountBalance(accountUsernameLoggedIn);
			accountStr = "Checking";
		}
		else {		// Savings account
			balance = bankService.getSavingsAccountBalance(accountUsernameLoggedIn);
			accountStr = "Savings";
		}

		BigDecimal depositAmmount = null;
		try {

			System.out.print("Current funds: $" + balance.toString() +
					"\nEnter an ammount to be deposited to your " + accountStr + " account: ");

			depositAmmount = new BigDecimal(scan.nextLine().trim());

			if (depositAmmount.abs() != depositAmmount) {
				System.out.println("You cannot deposit a negative quantity.");
				return false;
			}

			BankUser guy = bankService.getUser(accountUsernameLoggedIn);

			BigDecimal finalBalance = balance.add(depositAmmount);
			if (checking)
				guy.getAccount().setCheckingBalance(finalBalance);
			else 		// Savings account
				guy.getAccount().setSavingsBalance(finalBalance);

			if (!bankService.updateUser(guy)) {
				System.out.println("Transaction failed.");
				return false;
			}

			System.out.println("$" + depositAmmount.toString() + " deposited to " + accountStr);
			System.out.println("$" + finalBalance + " new " + accountStr + " balance");

			return true;


		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			return false;
		}
	}
	private static boolean viewAccount() {
		Account acc = bankService.getUser(accountUsernameLoggedIn).getAccount();

		System.out.println("Account created on : " + acc.getAccountOpenedDate());
		System.out.println("Account id number: " + acc.getAccountId());
		System.out.println("Savings account interest rate: " + acc.getInterestRate()*100 + "%");
		System.out.println("Rewards account reward rate: " + acc.getInterestRate()*100 + "%");
		System.out.println("Checking account balance: " + acc.getCheckingBalance().toString());
		System.out.println("Savings account balance: " + acc.getSavingsBalance().toString());
		System.out.println("Rewards account balance: " + acc.getRewardsBalance().toString());

		return true;
	}

	private static boolean editAccount() {

		// TODO add option to upgrade account, but requires credentials from a clerk (admin access)

		System.out.println("Would you like to change your password or delete your account?");
		String str = scan.nextLine().trim().toLowerCase();
		if (str.contains("password")) {

			System.out.print("Please enter your current password: ");
			String password1 = scan.nextLine().trim();

			if (bankService.validateUser(accountUsernameLoggedIn, password1) == null) {
				System.out.println("Incorrect password.");
				return false;
			}
			else {
				System.out.print("Enter a new password: ");
				password1 = scan.nextLine().trim();
				System.out.print("Enter the new password one more time: ");
				String password2 = scan.nextLine().trim();

				if (!password1.equals(password2)) {
					System.out.println("New passwords do not match. Password not changed.");
					return false;
				}

				BankUser guy = bankService.getUser(accountUsernameLoggedIn);
				guy.getAccount().setPassword(password1);
				if (bankService.updateUser(guy)) {
					System.out.println("Password changed");
				} else {
					System.out.println("Password NOT changed");
					return false;
				}
			}

		} else if (str.contains("delete")) {
			System.out.println("WARNING: This canot be undone!");
			System.out.println("Our bank system does not currently offer the option to restore a delted account" + 
					"\n OR for a previous customer to create a new account.");
			System.out.println("Are you sure you with to delete your account? yes/no");

			String confirmation = scan.nextLine().trim().toLowerCase();

			if (confirmation.contains("yes") || confirmation.equalsIgnoreCase("y")) {

				String localAccountUsername = accountUsernameLoggedIn;
				logout();
				
				if (bankService.deleteAccount(localAccountUsername, false)) {
					System.out.println("Account has ben DELETED. " + bankName + " thanks your for your patronage.");
				}
				else {
					System.out.println("Account could not be deleted. Please see an administrator.");
					return false;
				}
			}
			else System.out.println("Account NOT deleted. " + bankName + " thanks you for your continued patronage.");
			
		} else {
			return false;
		}

		return true;

	}

	private static void displayTermsAndConditions() {
		System.out.println("By creating an account with " + bankName + " You agree to the following terms and conditions\n" +
				bankName + " reserves the right to collect and use your personal data for any purpose.\n" +
				bankName + " will not be responsible for any loss or damage of property caused by use of \nservices provided by " + bankName + ".\n" + 
				"The user of services provided by " + bankName + " relinquish all rigts to sue or otherwise \ntake " +
				bankName + " or a representative of " + bankName + " to a court of law for any \nreason regarding services provided by " +
				bankName + " or property stored on the property of \n" + bankName);
	}

	// Returns false if a valid email was not supplied
	private static boolean setEmail(Person per) {

		// If the person already has an email
		if (!per.getEmail().equals(""))
			return true;

		// get email
		System.out.print("Enter your email address: ");
		String email = scan.nextLine().trim();

		if (bankService.isEmailValid(email))
			if (bankService.isEmailAvailable(email)) {
				per.setEmail(email);
				return bankService.updatePerson(per);
			}

		return false;
	}
	
	private static void debugStuff() {
		
		DaoSqlImpl dao = new DaoSqlImpl();
		Person per = dao.readPerson(123456789);
		if (per == null)
			per = dao.createPerson(123456789, "firstName", "lastName", LocalDate.now());
		BankUser guy = dao.readBankUser(4);
		if (guy == null)
			guy = dao.createBankUser(per, "username", "password");
		Account acc = dao.readAccount(4);
		if (acc == null)
			acc = dao.createAccount(guy, new BigDecimal(100), accountType.CHECKING, accountLevel.GOLD);
		
	}

}
