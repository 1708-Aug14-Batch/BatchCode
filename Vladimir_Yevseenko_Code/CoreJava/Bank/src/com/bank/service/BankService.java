package com.bank.service;

import java.math.BigDecimal;

import com.bank.dao.DBDAO;
import com.bank.pojos.Account;
import com.bank.pojos.AccountType;
import com.bank.pojos.User;

public class BankService {
	private DBDAO dao;
	private User curUser;
	private Account curAcc;
	
	public BankService() {
		dao = new DBDAO();
	}
	
	public boolean createNewUser(String fn, String ln, String e, String pw) {
		return dao.createNewUser(fn, ln, e, pw);
	}
	
	public boolean attemptLogin(String email, String pw) {
		return (curUser = dao.attemptLogin(email, pw)) != null;
	}
	
	public boolean createNewAccount(AccountType type) {
		return dao.createNewAccount(curUser.getId(), type);
	}
	
	public boolean selectAccount(AccountType type) {
		if ((curAcc = dao.selectAccount(curUser.getId(), type)) != null) {
			curAcc.setUser(curUser);
			return true;
		}
		return false;
	}
	
	public BigDecimal getBalance() {
		return curAcc.getBalance();
	}
	
	public void deposit(BigDecimal b) {
		curAcc.deposit(b);
		dao.deposit(curAcc.getId(), b);
	}
	
	public boolean withdraw(BigDecimal b) {
		if (curAcc.withdraw(b)) {
			dao.withdraw(curAcc.getId(), b);
			return true;
		}
		return false;
	}
	
	public boolean hasAccs(AccountType from, AccountType to) {
		if (dao.selectAccount(curUser.getId(), from) == null || 
				dao.selectAccount(curUser.getId(), to) == null)
			return false;
		return true;
	}
	
	public boolean transfer(AccountType from, AccountType to, BigDecimal b) {
		Account fromAcc = dao.selectAccount(curUser.getId(), from);
		if (fromAcc.withdraw(b)) {
			dao.transfer(curUser.getId(), from, to, b);
			return true;
		}
		
		return false;
	}
	
	public void changeFirst(String fn) {
		curUser.setFirst(fn);
		dao.updateUserFirst(curUser.getId(), fn);
	}
	
	public void changeLast(String ln) {
		curUser.setLast(ln);
		dao.updateUserLast(curUser.getId(), ln);
	}
	
	public void changePw(String pw) {
		curUser.setPassword(pw);
		dao.updateUserPassword(curUser.getId(), pw);
	}
	
	public void closeAcc() {
		dao.closeAcc(curAcc.getId());
	}
	
}