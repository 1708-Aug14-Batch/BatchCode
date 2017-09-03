package com.bank.dao;

import com.bank.pojos.Account;
import com.bank.pojos.User;

public interface DAO {

	
	public int addUser(String fn, String ln, String email, String pass);
	public Account createAccount(User u, int typeId);
	
	// get account
	
	
}