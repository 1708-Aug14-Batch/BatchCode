package com.bank.pojos;

// Uses a single bank account
// Only has access to their own data

// FIXME any String fields in the user class (including the Account class and Person class)
// 		cannot contain the ";" character as it is used to read/write the object in toString() and fromString()

public class User extends Person {

	// This is used to delimit fields in the toString and fromString fields
	protected static String delimit = ";";
	
	// Users do not have administrator privileges
	final boolean admin = false;

	// User is limited to only one account
	private final Account account;
	
	public User(Person person, Account account) {
		super(person.getSSN(), person.getFirstName(), person.getLastName());
		
		this.account = account;
	}

	public boolean isAdmin() {
		return admin;
	}

	public Account getAccount() {
		return account;
	}
	
	public String toString() {
		return super.toString() + delimit + account.toString();
	}
	public static User fromString(String str) throws NumberFormatException {
		String[] splitStr = str.split(delimit);

		Person per = Person.fromString(splitStr[0]);
		
		Account acc = Account.fromString(splitStr[1]);

		return new User(per, acc);
	}
	
}
