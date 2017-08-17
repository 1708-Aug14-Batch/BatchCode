package com.bank.dao;

import java.util.List;

import com.bank.pojo.User;

public interface DAO {

	/*
	 * The contract we make for the information that
	 * we will need to sed to and receive from the data source.
	 * 
	 * CRUD:
	 * 
	 * C: addUser  - new user added to the data
	 * R: getUser  - grab user by id
	 * U: editUser - change fn, ln, username, PW, or balance
	 * D: deleteUser - remove user by id
	 * 
	 * getUsers - grab all users 
	 */
	
	void addUser(User user);
	User getUser(int id);
	void editUser(User user);
	void deleteUser(int id);
	List<User> getUsers();
}
