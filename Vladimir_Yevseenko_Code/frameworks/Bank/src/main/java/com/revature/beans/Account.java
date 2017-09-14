package com.revature.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.EnumType;

@Entity
@Table(name = "accounts")
public class Account {
	
	@Id
	@Column(name = "account_id")
	@SequenceGenerator(name="account_id_seq", sequenceName="account_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "id")
	private User user;
	
	
	public enum AccountType {
		SAVINGS, CHECKING, CREDIT;
	}
	
	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
	@Column(nullable = false)
	private double balance;
	
	public Account() {}
	
	public Account(User user, AccountType accountType, double balance) {
		this.user = user;
		this.accountType = accountType;
		this.balance = balance;
	}
	
	public Account(int id, User user, AccountType accountType, double balance) {
		this.id = id;
		this.user = user;
		this.accountType = accountType;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}
	
	public AccountType getType() {
		return accountType;
	}
	
	public double getBalance() {
		return balance;
	}
	
	
}