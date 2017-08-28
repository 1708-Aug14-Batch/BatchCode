package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.model.Account;
import com.bank.util.ConnectionSingleton;

/**
 * Concrete implementation of interface AccountDAO.
 * @author Will Underwood
 */
public class AccountDAOImpl implements AccountDAO<Account> {

	/**
	 * Adds a new user account to the database.
	 * @precondition Account cannot be null
	 * @param account - Account whose details will be placed in the database
	 * @return The id of the inserted account
	 */
	@Override
	public int createAccount(Account account) {
		if (account == null) {
			throw new IllegalArgumentException("Account cannot be null");
		}
		try(Connection conn = ConnectionSingleton.getInstance().getConnection()) {
			conn.setAutoCommit(false);
			String sql = "INSERT INTO bankaccount(account_id, balance, user_id, account_type_id) VALUES(DEFAULT, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "account_id";
			PreparedStatement statement = conn.prepareStatement(sql, key);
			statement.setBigDecimal(1, account.getBalance());
			statement.setInt(2, account.getUserID());
			statement.setInt(3, account.getAccountTypeID());
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
	 * Returns all Accounts in database
	 * @precondition None
	 * @param None
	 * @return A collection of all Accounts
	 */
	@Override
	public ArrayList<Account> readAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a single account from the database.
	 * @precondition ID cannot be less than 1
	 * @param ID - ID of the desired Account
	 * @return A single Account
	 */
	@Override
	public Account readAccount(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Edits the data of the specified account.
	 * @precondition Account cannot be null
	 * @param Account - The account whose data will be edited
	 * @return The number of rows affected. Should be one.
	 */
	@Override
	public int updateAccount(Account account) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Disables the account whose ID is specified
	 * @precondition ID cannot be less than 1
	 * @param ID - The ID of the account to be disabled
	 * @return - The number of rows affected. Should be 1.
	 */
	@Override
	public int disableAccount(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
