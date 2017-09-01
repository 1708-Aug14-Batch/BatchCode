package com.bank.service;

import java.math.BigDecimal;

import pojos.User;

import com.bank.dao.Dao;
import com.bank.dao.DaoTextImpl;

public class Service {


	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void withdraw(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0 || amount.compareTo(currentUser.getBalance()) > 0) return;

		currentUser.setBalance(currentUser.getBalance().subtract(amount));
		dao.editUser(currentUser);
	}
	
	public static void deposit(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) return;

		currentUser.setBalance(currentUser.getBalance().add(amount));
		dao.editUser(currentUser);
	}
	
	public static String createUser(User user) {
		if (dao.hasUser(user.getEmail())) return "Account already associated with that email";
		
		if (!dao.addUser(user)) {
			return "An unknown error occurred creating account";
		}
		return null;
	}
	
	public static void updateUser(User user) {
		dao.editUser(user);
	}
	
	public static String loginUser(String email, String password) {
		if ("".equals(email) || "".equals(password)) return "You must enter an email and password";
		
		User user = dao.getUser(email);
		if (user == null) return "There is no account associated with that email";
		
		if (password.equals(user.getPassword())) {
			currentUser = user;
			return null;
		}
		else {
			return "Incorrect email or password";
		}
	}
	
	public static void logoutUser() {
		currentUser = null;
	}
	
	private static User currentUser;	
	private static Dao dao = new DaoTextImpl();
}