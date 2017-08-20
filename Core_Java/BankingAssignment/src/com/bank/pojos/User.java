package com.bank.pojos;

// Uses a single bank account
// Only has access to their own data

public class User extends Person {

	// This is used to delimit fields in the toString and fromString fields
	public static final String delimit = ";";
	
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
	
	// Validaes whether these strings can be used to make this object. Uses delimit
	public boolean validateStrings() {
		
		return account.validateStrings();
	}
	
}
