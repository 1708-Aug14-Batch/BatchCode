package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.model.User;
import com.bank.util.ConnectionSingleton;

/**
 * Concrete implementation of UserDAO.
 * @author Will Underwood
 */
public class UserDAOImpl implements UserDAO<User> {

	/**
	 * Inserts a new user into the database.
	 * @precondition User cannot be null
	 * @param user - the User to be inserted
	 * @return The id of the inserted user / or zero if failure
	 */
	@Override
	public int createUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null");
		}
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO bankuser(user_id, firstname, lastname, email, password) VALUES(DEFAULT, ?, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "user_id";
			PreparedStatement statement = conn.prepareStatement(sql, key);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getPassword());
			statement.executeUpdate();
			int id = 0;
			ResultSet returnedKeys = statement.getGeneratedKeys();
			while(returnedKeys.next()) {
				id = returnedKeys.getInt(1);
			}
			conn.commit();
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
