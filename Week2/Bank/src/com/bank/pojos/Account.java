package com.bank.pojos;

public class Account {
	private int accountId;
	private double balance;
	private AccountType type;
	private Users user;
	

	public Account() {
		// TODO Auto-generated constructor stub
	}
	

	public Account(int accountId, double balance, AccountType type, Users user) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.type = type;
		this.user = user;
	}



	public int getAccountId() {
		return accountId;
	}


	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public AccountType getType() {
		return type;
	}


	public void setTypeId(int id) {
		this.type = type;
	}


	public Users getUser() {
		return user;
	}


	public void setUserId(Users user) {
		this.user = user;
	}
	
	
	@Override
	public String toString() {
		return "Account Information: \nAccountId=" + accountId + "\nBalance=" + balance;
	}
	

}
