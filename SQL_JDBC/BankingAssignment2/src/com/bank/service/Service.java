package com.bank.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bank.dao.*;
import com.bank.pojos.Account;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.Person;
import com.bank.pojos.BankUser;

// TODO this class is where I will checking updating variables to make
// sure they are correct. i.e. valid emails, untaken username, untaken SSNs, etc
//		ensure correctness on create as well as on update
// TODO add methods to read particular values from database rather than always getting a list of every _____

// FIXME rather than using so many daoImpl.readAllXXX() methods, make more methods in the DaoSqlImpl that are not in the DaoSql interface

public class Service {

	DaoSqlImpl daoImpl = new DaoSqlImpl();

	private static long maxBalance = 10000000000L;
	
	public BankUser validateBankUser(String username, String password) {
		if (username == null || password == null)
			return null;
		
		username = username.toLowerCase();

		BankUser guy = daoImpl.readBankUser(username);

		if (guy == null)
			return null;

		if (guy.getPassword().equals(password))
			return guy;
		else return null;
	}

	// FIXME an email does not need to end with .com, could be .org or others
	//		Change to a regular expression using the method .matches()
	public boolean isEmailValid(String email) {

		if (email.contains("@") && email.contains(".com"))
			if (email.lastIndexOf('@') < email.lastIndexOf(".com"))
				return true;

		System.out.println("Email addresses must contain a valid domain such as \"something@something.com\"");
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

	public boolean isSSNAvailable(int SSN) {

		ArrayList<Person> personList = daoImpl.readAllPersons();

		for (Person per : personList)
			if (per.getSSN() == (SSN)) {
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
			if (per.getEmail() != null && per.getEmail().equals(email)) {
				System.out.println("Email unavailable");
				return false;
			}

		return true;
	}

	private boolean isUsernameAvailable(String username) {
		username = username.toLowerCase();

		ArrayList<BankUser> userList = daoImpl.readAllBankUsers();

		for (BankUser guy : userList) {
			if (guy.getUsername().equals(username))
				return false;
		}

		return true;

	}

	// Attempts to create a person unless a person already exists with
	// the given SSN. Returns the person object either way
	// An email must be supplied but it can be an empty string where it will not
	// be checked for uniqueness
	public Person tryCreatePerson(String SSNStr, String firstName, String lastName, LocalDate birthDate, String email) {

		SSNStr = validateSSN(SSNStr);
		if (SSNStr == null) {
			System.out.println("Invalid SSN. Must contains 9 digits from 0-9 inclusive.");
			return null;
		}

		Integer SSN = Integer.parseInt(SSNStr);
		Person per = daoImpl.readPerson(SSN);

		// If person does not exist yet
		if (per == null) {

			if (isSSNAvailable(SSN)) {

				per = daoImpl.createPerson(SSN, firstName, lastName, birthDate);
				// Auto-set the email if available in the data
				if (!(email == null))
					if (isEmailAvailable(email)) {
						per.setEmail(email);
						daoImpl.updatePerson(SSN, per);	// Update email address
					}
				
			} else {
				return null;
			}
		}

		// If the entered credentials exactly match a person in the data set
		if (firstName.equalsIgnoreCase(per.getFirstName()) &&
				lastName.equalsIgnoreCase(per.getLastName()) &&
				birthDate.toString().equals(per.getBirthDate().toString()))
			return per;
		else {
			System.out.println("That is not the name/birth_date we have on file for that person.");
			return null;
		}

	}

	// Tries to create a new user
	// Fails if the uername is not unique or if the given person is already a user
	public BankUser tryCreateBankUser(Person per, String username, String password, accountLevel level) {
		username = username.toLowerCase();

		// Check that username is unique
		if (!isUsernameAvailable(username)) {
			System.out.println("That username is already taken.");
			return null;
		}
		
		if (personIsAUser(per)) {
			System.out.println("That person is already a bank user");
			return null;
		}

		return daoImpl.createBankUser(per, username, password);
	}

	// Tries to create a new account
	public Account tryCreateAccount(BankUser guy, BigDecimal balance, accountType type, accountLevel level) {
		
		return daoImpl.createAccount(guy, balance, type, level);
	}

	// Updates a person with the new person object.
	// Matches the old person with the new by SSN
	// All non-final fields of the person object
	// will be updated
	public boolean updatePerson(int SSN, Person per) {

		// If the person-to-be updated on file is deceased, their records cannot be updated
		if (daoImpl.readPerson(per.getSSN()).isDeceased()) {
			System.out.println("That person is deceased and their records cannot be updated");
			return false;
		}

		return daoImpl.updatePerson(SSN, per);

	}

	public boolean updateBankUser(int userId, BankUser guy) {

		return daoImpl.updateBankUser(userId, guy);

	}
	
	public boolean updateAccount(int accountId, Account acc) {
		
		if (acc.getBalance().longValue() >= maxBalance) {
			System.out.println("Deposit failed. An account cannot have more than $" + maxBalance);
			return false;
		}
		
		// If the account is deleted, the records cannot be updated
		if (daoImpl.readAccount(accountId).isDeleted()) {
			return false;
		}

		return daoImpl.updateAccount(accountId, acc);

	}	

	public BankUser getBankUser(int userId) {

		return daoImpl.readBankUser(userId);
	}
	
	public Person getPerson(int SSN) {
		
		return daoImpl.readPerson(SSN);
	}
	
	public ArrayList<Account> getAccounts(int userId) {
		
		return daoImpl.readAllAccounts(userId);
	}

	public BigDecimal getAccountBalance(int accountId) {
		Account acc = daoImpl.readAccount(accountId);
		
		if (acc.isDeleted()) {
			System.out.println("Nothing can be withdrawn as this account has been deleted");
			return null;
		}

		return acc.getBalance();

	}

	public boolean deleteAccount(int accountId) {

		return daoImpl.deleteAccount(accountId);

	}

	private boolean personIsAUser(Person per) {

		ArrayList<BankUser> userList = daoImpl.readAllBankUsers();

		for (BankUser guy : userList)
			if (guy.getSSN() == (per.getSSN()))
				return true;

		return false;
	}

}
