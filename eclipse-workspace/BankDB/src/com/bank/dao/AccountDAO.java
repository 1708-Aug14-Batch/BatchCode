package com.bank.dao;

import java.util.ArrayList;

/**
 * Provides CRUD operations for Accounts.
 * @author Will Underwood
 * @param <Account> These CRUD operations can only be used with Account objects.
 */
public interface AccountDAO<Account> {

	int createAccount(Account account);

	ArrayList<Account> readAllAccounts();

	Account readAccount(int id);

	int updateAccount(Account account);

	int disableAccount(int id);

}
