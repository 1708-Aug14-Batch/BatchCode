package com.ex.bank.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Account {
	private int id;
	private double balance;
	private User userid;
	private AccountType statusid;
	
	public Account(){}

	public Account(int id, double balance, User userid, AccountType statusid) {
		super();
		this.id = id;
		this.balance = balance;
		this.userid = userid;
		this.statusid = statusid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getUserid() {
		return userid;
	}

	public void setUserid(User userid) {
		this.userid = userid;
	}

	public AccountType getStatusid() {
		return statusid;
	}

	public void setStatusid(AccountType statusid) {
		this.statusid = statusid;
	}
	
	

}