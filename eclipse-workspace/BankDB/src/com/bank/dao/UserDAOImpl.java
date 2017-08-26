package com.bank.dao;

import java.util.ArrayList;

import com.bank.model.User;

/**
 * Concrete implementation of UserDAO.
 * @author Will Underwood
 */
public class UserDAOImpl implements UserDAO<User> {

	/**
	 * Inserts a new user into the database.
	 * @precondition User cannot be null
	 * @param user - the User to be inserted
	 * @return The number of rows inserted
	 */
	@Override
	public int createUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Returns all user data from the database.
	 * @precondition None
	 * @param None
	 * @return A collection of all users
	 */
	@Override
	public ArrayList<User> readAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns the data of a single user from the database
	 * @precondition ID cannot be less than 1.
	 * @param id - The ID of the desired user
	 * @return The user whose ID was specified
	 */
	@Override
	public User readUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Updates the data of the specified user
	 * @precondition User cannot be null
	 * @param user - the user to be updated
	 * @return The number of affected rows. Should be 1.
	 */
	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Disables the specified user.
	 * @precondition User cannot be null.
	 * @param user - the user to be disabled.
	 * @return The number of affected rows. Should be 1.
	 */
	@Override
	public int disableUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

}
