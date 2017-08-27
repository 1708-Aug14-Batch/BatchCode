package com.bank.service;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.bank.dao.*;
import com.bank.pojos.Account;
import com.bank.pojos.Person;
import com.bank.pojos.BankUser;

// TODO this class is where I will checking updating variables to make
// sure they are correct. i.e. valid emails, untaken username, untaken SSNs, etc
//		ensure correctness on create as well as on update
// TODO add methods to read particular values from database rather than always getting a list of every _____

public class Service {

	DaoSqlImpl daoImpl = new DaoSqlImpl();

	public Account validateUser(String username, String password) {
		username = username.toLowerCase();

		BankUser guy = daoImpl.readUser(username);

		if (guy == null)
			return null;

		if (guy.getAccount().isDeleted()) {
			System.out.println("This account has been deleted.");
			return null;
		}

		if (guy.getPassword().equals(password))
			return guy.getAccount();
		else return null;
	}

	// FIXME an email does not need to end with .com, could be .org or others
	public boolean isEmailValid(String email) {

		if (email.contains("@") && email.contains(".com"))
			if (email.lastIndexOf('@') < email.lastIndexOf(".com"))
				if (!email.contains(Person.delimit))
					return true;

		System.out.println("Email addresses must not contain invalid character(s): " + Person.delimit + " and must contain a valid domain such as \"something@something.com\"");
		return false;
	}

	// SSN can be given with dashes between some of the characters
	// This removes all non-digit characters and ensures the final result
	// is a 9 digit int
	public String validateSSN(String SSNStr) {
		String result = "";

		char[] SSNCharList = SSNStr.toCharArray();
		for (char cha : SSNCharList)
			if (Character.isDigit(cha))
				result += cha;

		if (result.length() != 9)
			return null;

		return result;
	}

	public boolean isSSNAvailable(String SSN) {

		ArrayList<Person> personList = new ArrayList<Person>();

		for (Person per : personList)
			if (per.getSSN().equals(SSN)) {
				System.out.println("Someone else already has the SSN: " + SSN);
				return false;
			}

		return true;
	}

	public boolean isEmailAvailable(String email) {

		ArrayList<Person> peopleList = daoImpl.readAllPersons();

		if (peopleList.size() == 0)
			return true;

		for (Person per : peopleList)
			if (per.getEmail().equals(email)) {
				System.out.println("Email unavailable");
				return false;
			}

		return true;
	}

	private boolean isUsernameAvailable(String username) {
		username = username.toLowerCase();

		ArrayList<BankUser> userList = daoImpl.readAllUsers();

		for (BankUser guy : userList) {
			if (guy.getAccount().getUsername().equals(username))
				return false;
		}

		return true;

	}

	// Attempts to create a person unless a person already exists with
	// the given SSN. Returns the person object either way
	// An email must be supplied but it can be an empty string where it will not
	// be checked for uniqueness
	public Person tryCreatePerson(String SSNStr, String firstName, String lastName, String email) {

		String SSN = validateSSN(SSNStr);
		if (SSN == null) {
			System.out.println("Invalid SSN. Must contains 9 digits from 0-9 inclusive.");
			return null;
		}

		Person per = daoImpl.readPerson(SSN);

		// If person does not exist yet
		if (per == null) {

			if (isSSNAvailable(SSN)) {
				Person per2 = new Person(SSN, firstName, lastName);

				// Auto-set the email if available in the data
				if (!(email.equals("")))
					if (isEmailAvailable(email))
						per2.setEmail(email);


				daoImpl.createPerson(per2);
				return per2;

			} else {
				return null;
			}
		}

		// If the entered credentials exactly match a person in the data set
		if (firstName.equalsIgnoreCase(per.getFirstName()) && lastName.equalsIgnoreCase(per.getLastName()))
			return per;
		else {
			System.out.println("That is not the name we have on file for that SSN.");
			return null;
		}

	}

	// Tries to create a new user
	// Fails if the uername is not unique or if the given person is already a user
	public BankUser tryCreateUser(Person per, String username, String password, accountLevel level) {
		username = username.toLowerCase();

		// Check that username is unique
		if (!isUsernameAvailable(username)) {
			System.out.println("That username is already taken.");
			return null;
		}

		// Create an account and associated user
		ArrayList<BankUser> userList = daoImpl.readAllUsers();
		// Account ID should be one higher than the current highest account id
		int id = 1;
		for (BankUser guy : userList)
			id = Math.max(id, guy.getAccount().getAccountId());
		BankUser myUser = new BankUser(per, new Account(per, username, password, type, ++id));

		daoImpl.createUser(myUser);

		return guy;
	}

	// Tries to create a new account
	// Fails if ...FIXME
	public Account tryCreateAccount() {

		// Create an account and associated user
		ArrayList<Account> accountList = daoImpl.readAllAccounts();
		// Account ID should be one higher than the current highest account id
		int id = 1;
		for (BankUser guy : userList)
			id = Math.max(id, guy.getAccount().getAccountId());
		BankUser myUser = new BankUser(per, new Account(per, username, password, type, ++id));

		daoImpl.createUser(myUser);

		return guy;
	}

	// Updates a person with the new person object.
	// Matches the old person with the new by SSN
	// All non-final fields of the person object
	// will be updated
	public boolean updatePerson(Person per) {

		// If the person-to-be updated on file is deceased, their records cannot be updated
		if (daoImpl.readPerson(per.getSSN()).isDeceased()) {
			System.out.println("That person is deceased and their records cannot be updated");
			return false;
		}

		return daoImpl.updatePerson(per);


	}

	public boolean updateUser(BankUser guy) {

		// If the user-to-be updated on file has had their account deleted, their records cannot be updated
		if (daoImpl.readUser(guy.getAccount().getUsername()).getAccount().isDeleted()) {
			return false;
		}

		return daoImpl.updateUser(guy);

	}

	public BankUser getUser(String username) {

		return daoImpl.readUser(username.toLowerCase());

	}

	public BigDecimal getCheckingAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getCheckingBalance();

	}

	public BigDecimal getSavingsAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getSavingsBalance();

	}

	public BigDecimal getRewardsAccountBalance(String username) {

		if (daoImpl.readUser(username).getAccount().isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return daoImpl.readUser(username).getAccount().getRewardsBalance();

	}

	public boolean deleteAccount(String username, boolean erase) {

		return daoImpl.deleteUser(username.toLowerCase(), erase);

	}

	private boolean personIsAUser(Person per) {

		ArrayList<BankUser> userList = daoImpl.readAllUsers();

		for (BankUser guy : userList)
			if (guy.getSSN().equals(per.getSSN()))
				return true;

		return false;
	}

}
