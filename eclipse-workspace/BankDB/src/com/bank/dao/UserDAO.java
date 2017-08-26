package com.bank.dao;

import java.util.ArrayList;

/**
 * Provides CRUD operations for User objects.
 * @author Will Underwood
 * @param <User> These operations can only be used with User objects
 */
public interface UserDAO<User> {

	int createUser(User user);

	ArrayList<User> readAllUsers();

	User readUser(int id);

	int updateUser(User user);

	int disableUser(User user);

}
