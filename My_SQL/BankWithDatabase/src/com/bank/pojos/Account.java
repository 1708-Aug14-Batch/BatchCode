package com.bank.pojos;

public class Account {

	@Override
	public String toString() {
		return "Account [id=" + id + ", userId=" + userId + ", balance="
				+ balance + ", type=" + type + "]";
	}

	private int id;
	private int userId;
	private float balance;
	private int type;

	public Account(int id, int type, int userId) {
		super();
		this.id = id;
		this.balance = 0.0f;
		this.type = type;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}