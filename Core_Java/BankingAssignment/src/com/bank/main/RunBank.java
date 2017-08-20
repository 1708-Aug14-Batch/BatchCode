package com.bank.main;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;

import com.bank.pojos.*;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.Transaction.transactionType;
import com.bank.service.Service;

/*
 * Requirements:
 * 	User can...
 * 		- login
 * 		- create an account
 * 		- logout
 * 		- withdraw or deposit funds
 * 		- view own account balance
 * 		- edit own information
 * 
 * Tech Specs
 * 	- core java, file i/o
 */

// FIXME add clerk functionality
//			View all transactions, Users, Persons
//			add new clerks (later there will have to be a hierarchy of clerks where only high-level clerks can add lower-level clerks)
//			change password

//			Change a user's email to an available email
//			Credit a uer's account with accrued rewards balance
//			Change a user's username to an available username
//			upgrade an account
//			Provide verification for deleting an account
//		Clerks have two ways to log in: to access the secret clerk menu, and a temporary log in which logs out after verification for doing admin things like changing a user's username
// FIXME add transactions (transaction method which calls any transaction) - consider changing transactionType to String
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

		System.out.println("Hello. Welcome to the automating banking system for " +
				bankName + ".");

		makeTransaction(transactionType.BOOT_UP);

		bankingSystem();

		makeTransaction(transactionType.QUIT);

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

					makeTransaction(transactionType.LOGIN);

					break;
				case "logout":
				case "log out":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before you can log out.");
						continue;
					}

					makeTransaction(transactionType.LOGOUT);

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
						if (!makeTransaction(transactionType.CREATE_ACCOUNT))
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
						makeTransaction(transactionType.WITHDRAW_CHECKING);
					else if (input.contains("saving") || input.contains("transfer"))
						makeTransaction(transactionType.TRANSFER_SAVINGS);
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
						makeTransaction(transactionType.DEPOSIT_CHECKING);
					else if (input.contains("saving"))
						makeTransaction(transactionType.DEPOSIT_SAVINGS);
					else System.out.println("Input was not recognized. Valid entries are \"checking\" and \"saving\" in all lower case");

					break;
				case "view account":
				case "view":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					makeTransaction(transactionType.VIEW_ACCOUNT);

					break;
				case "edit account":
				case "edit":
					if (accountUsernameLoggedIn == null) {
						System.out.println("You must log in before modifying your account");
						continue;
					}

					makeTransaction(transactionType.EDIT_ACCOUNT);

					break;
				case "quit":
				case "q":
					break bankloop;
				case "clerk login":
				case "clerk log in":

					// FIXME add implementation
					// makeTransaction(transactionType.CLERK_LOGIN);

					break;
				default:
					System.out.println("Command not recognized. Please try again.");
				}	// end case statement

			}	// end bankloop

	// If the user is still logged in when they quit, the log them off.
	if (accountUsernameLoggedIn != null)
		makeTransaction(transactionType.LOGOUT);

	}

	private static void displayBasicAccountInfo() {
		System.out.println("Logged in as: " + accountUsernameLoggedIn +	", Account type: " +
				bankService.getUser(accountUsernameLoggedIn).getAccount().getType());
	}

	private static boolean makeTransaction(transactionType type) {
		Transaction tran = new Transaction(type);
		boolean succeeded = false;
		// Set some default values:
		tran.setSummary("");
		tran.setReasonFailed("");
		if (accountUsernameLoggedIn == null) {
			tran.setUsername("");
			tran.setAccountID(0);
		} else {
			tran.setUsername(accountUsernameLoggedIn);
			tran.setAccountID(bankService.getUser(accountUsernameLoggedIn).getAccount().getAccountId());
		}
		tran.setClerkID(clerkIDLoggedIn);

		// Within the switch statement the following fields must either be set or accept the default value:
		//		boolean succeeded
		//		transaction String summary
		//		transaction String reasonFailed
		switch(type) {
		case BOOT_UP:
		case QUIT:
			succeeded = true;
			break;
		case LOGIN:
			succeeded = login(tran);

			if (succeeded) {
				tran.setUsername(accountUsernameLoggedIn);
				tran.setAccountID(bankService.getUser(accountUsernameLoggedIn).getAccount().getAccountId());
			}

			break;
		case LOGOUT:
			succeeded = logout(tran);
			break;
		case CREATE_ACCOUNT:

			succeeded = createAccount(tran);

			break;
		case WITHDRAW_CHECKING:

			succeeded = transferFunds(true, tran);

			break;
		case TRANSFER_SAVINGS:

			succeeded = transferFunds(false, tran);

			break;
		case DEPOSIT_CHECKING:

			succeeded = deposit(true, tran);

			break;
		case DEPOSIT_SAVINGS:

			succeeded = deposit(false, tran);

			break;
		case VIEW_ACCOUNT:

			succeeded = viewAccount(tran);

			break;
		case EDIT_ACCOUNT:

			succeeded = editAccount(tran);

			break;
		case CLERK_LOGIN:
			// FIXME
			break;
		case CLERK_LOGOUT:
			// FIXME
			break;
		default:
			System.out.println("Error. That transaction is not yet supported.");
			return false;
		}

		// Set variables for the transaction
		tran.setDate(new Date().toString());
		tran.setSuccessful(succeeded);

		bankService.createTransaction(tran);
		return succeeded;
	}

	// Returns true if login was successful
	private static boolean login(Transaction tran) {
		System.out.print("Enter username: ");
		String username = scan.nextLine().trim().toLowerCase();
		System.out.print("Enter password: ");
		String password = scan.nextLine().trim();

		Account acc = bankService.validateUser(username, password);
		tran.setUsername(username);

		if (acc == null) {
			System.out.println("Login failed, incorrect username and password combination.");
			tran.setReasonFailed("Username and password do not match");
			return false;
		} else {
			accountUsernameLoggedIn = acc.getUsername();
			System.out.println("Login successful, welcome " + accountUsernameLoggedIn);
			return true;
		}
	}

	// Returns true if logout was successful
	private static boolean logout(Transaction tran) {

		System.out.println("Logout successful.");
		accountUsernameLoggedIn = null;

		return true;
	}

	// Returns true if an account was successfully created
	private static boolean createAccount(Transaction tran) {

		// Get information from user to find/create a person
		System.out.print("Enter your first name: ");
		String firstName = scan.nextLine().trim();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine().trim();
		System.out.print("Enter your Social Security Number: ");
		String SSN = scan.nextLine().trim();

		Person per = bankService.tryCreatePerson(SSN, firstName, lastName, "", tran);
		if (per == null)
			return false;

		// Set email. email is not required to be a person but it
		// is required to have an account
		if (!setEmail(per, tran)) {
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
			return bankService.tryCreateUser(per, username, password1, accountType.BRONZE, tran);
		else {
			System.out.println("Passwords do not match.");
			tran.setReasonFailed("The two supplied passwords did not match");
			return false;
		}
	}

	private static boolean transferFunds(boolean checking, Transaction tran) {
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
				tran.setReasonFailed("User tried to withdraw the negative quantity of " + withdrawAmmount);
				return false;
			}

			switch(balance.compareTo(withdrawAmmount)) {
			case -1:
				System.out.println("You cannot withdraw more than your " + accountStr + " balance");
				tran.setReasonFailed("User tried to withdraw " + withdrawAmmount + " from a balance of " + balance);
				return false;
			default:

				User guy = bankService.getUser(accountUsernameLoggedIn);

				BigDecimal finalBalance = balance.subtract(withdrawAmmount);
				if (checking)
					guy.getAccount().setCheckingBalance(finalBalance);
				else {		// Savings account
					guy.getAccount().setSavingsBalance(finalBalance);
					BigDecimal checkingBalance = guy.getAccount().getCheckingBalance();
					guy.getAccount().setCheckingBalance(checkingBalance.add(withdrawAmmount));
				}

				if (!bankService.updateUser(guy, tran)) {
					System.out.println("Transaction failed.");
					return false;
				}

				System.out.println("$" + withdrawAmmount.toString() + " withdrawn from " + accountStr);
				System.out.println("$" + guy.getAccount().getCheckingBalance().toString() + " new checking balance");

				tran.setSummary(withdrawAmmount + " was taken from " + accountStr);

				return true;
			}

		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			tran.setReasonFailed("User tried to withdraw the non-integer ammount of " + withdrawAmmount);
			return false;
		}

	}

	private static boolean deposit(boolean checking, Transaction tran) {
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
				tran.setReasonFailed("User tried to withdraw the negative quantity of " + depositAmmount);
				return false;
			}

			User guy = bankService.getUser(accountUsernameLoggedIn);

			BigDecimal finalBalance = balance.add(depositAmmount);
			if (checking)
				guy.getAccount().setCheckingBalance(finalBalance);
			else 		// Savings account
				guy.getAccount().setSavingsBalance(finalBalance);

			if (!bankService.updateUser(guy, tran)) {
				System.out.println("Transaction failed.");
				return false;
			}

			System.out.println("$" + depositAmmount.toString() + " deposited to " + accountStr);
			System.out.println("$" + finalBalance + " new " + accountStr + " balance");

			tran.setSummary(depositAmmount + " was deposited to " + accountStr);

			return true;


		} catch (NumberFormatException e) {
			System.out.println("Error: That is not a valid withdraw ammount");
			tran.setReasonFailed("User tried to withdraw the non-integer ammount of " + depositAmmount);
			return false;
		}
	}
	private static boolean viewAccount(Transaction tran) {
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

	private static boolean editAccount(Transaction tran) {

		// FIXME add option to upgrade account, but requires credentials from a clerk (admin access)

		System.out.println("Would you like to change your password or delete your account?");
		String str = scan.nextLine().trim().toLowerCase();
		if (str.contains("password")) {

			System.out.print("Please enter your current password: ");
			String password1 = scan.nextLine().trim();

			if (bankService.validateUser(accountUsernameLoggedIn, password1) == null) {
				System.out.println("Incorrect password.");
				tran.setReasonFailed("Incorrectly entered current password");
				return false;
			}
			else {
				System.out.print("Enter a new password: ");
				password1 = scan.nextLine().trim();
				System.out.print("Enter the new password one more time: ");
				String password2 = scan.nextLine().trim();

				if (!password1.equals(password2)) {
					System.out.println("New passwords do not match. Password not changed.");
					tran.setReasonFailed("New password was not verified and therefore not changed");
					return false;
				}

				User guy = bankService.getUser(accountUsernameLoggedIn);
				guy.getAccount().setPassword(password1);
				if (bankService.updateUser(guy, tran)) {
					tran.setSummary("Password changed");
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
				makeTransaction(transactionType.LOGOUT);

				if (bankService.deleteAccount(localAccountUsername, false)) {
					System.out.println("Account has ben DELETED. " + bankName + " thanks your for your patronage.");
					tran.setSummary("Account has been deleted");
				}
				else {
					System.out.println("Account could not be deleted. Please see an administrator.");
					tran.setReasonFailed("Account could not be deleted");
					return false;
				}
			}
			else System.out.println("Account NOT deleted. " + bankName + " thanks you for your continued patronage.");
			tran.setSummary("User chose to delete account but did not confirm the choice");

		} else {
			tran.setReasonFailed("Invalid choice for editing account: " + str);
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
	private static boolean setEmail(Person per, Transaction tran) {

		// If the person already has an email
		if (!per.getEmail().equals(""))
			return true;

		// get email
		System.out.print("Enter your email address: ");
		String email = scan.nextLine().trim();

		if (bankService.isEmailValid(email))
			if (bankService.isEmailAvailable(email)) {
				per.setEmail(email);
				return bankService.updatePerson(per, tran);
			}

		tran.setReasonFailed("Invalid email supplied: " + email);
		return false;
	}

}
