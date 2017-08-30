package com.bank.pojos;

// A BankUser can have many acounts
// Only has access to their own data

public class BankUser extends Person {

	private final int userId;
	private final String username;
	private String password;
	
	public BankUser(Person per, int userId, String username, String password) {
		super(per.getPersonId(), per.getFirstName(), per.getLastName(), per.getEmail());
		this.userId = userId;
		this.username = username;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return "BankUser [userId=" + userId + ", username=" + username + ", password=" + password 
				+ "]";
	}
	
}
