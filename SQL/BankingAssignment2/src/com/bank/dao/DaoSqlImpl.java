package com.bank.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bank.pojos.Account;
import com.bank.pojos.Account.accountLevel;
import com.bank.pojos.Account.accountType;
import com.bank.pojos.BankUser;
import com.bank.pojos.Person;
import com.bank.util.ConnectionFactory;

// NOTE: SQL is 1-indexed rather than 0-indexed

public class DaoSqlImpl implements DaoSql {
	
	public String getFormatedDate(LocalDate day) {
		return day.toString();
	}

	public Person createPerson(int SSN, String firstName, String lastName, LocalDate birthDate) {
		Person per = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// No semi-colon inside the quotes
			String sql = "INSERT INTO person(ssn, first_name, last_name, email, birth_date, deceased)" + 
					" VALUES(?, ?, ?, ?, TO_DATE(?,'yyyy-mm-dd'), ?)";
			String[] key = new String[1];
			key[0] = "ssn";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, SSN);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, null);
			ps.setString(5, getFormatedDate(birthDate));
			ps.setInt(6, 0);		// false

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();
			
			per = new Person(SSN, firstName, lastName, birthDate);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return per;

	}

	@Override
	public Person readPerson(int SSN) {
		Person per = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM person WHERE ssn=?";
			String[] key = new String[1];
			key[0] = "ssn";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, SSN);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String email = rs.getString(4);
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return per;
	}

	@Override
	public Person readPerson(String email) {
		Person per = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM person WHERE email=?";
			String[] key = new String[1];
			key[0] = "ssn";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, email);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int ssn = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				email = rs.getString(4);		// This line is redundant
				LocalDate birthDate = LocalDate.parse(rs.getString(5).substring(0, 10));
				boolean deceased = (rs.getInt(6) == 0)?false:true;

				per = new Person(ssn, firstName, lastName, birthDate);
				per.setEmail(email);
				per.setDeceased(deceased);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return per;
	}

	@Override
	public boolean updatePerson(Person per) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePerson(String SSN, boolean erase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Person> readAllPersons() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BankUser createBankUser(Person per, String username, String password) {
		BankUser guy = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO bank_user(username, password, ssn) VALUES(?, ?, ?)";
			String[] key = new String[1];
			key[0] = "user_id";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setInt(3, per.getSSN());
			
			ps.executeUpdate();
			
			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);
			
			guy = new BankUser(per, id, username, password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guy;
		
	}

	@Override
	public BankUser readBankUser(String username) {
		BankUser guy = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM bank_user WHERE username=?";
			String[] key = new String[1];
			key[0] = "user_id";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				int id = rs.getInt(1);
				username = rs.getString(2);		// This line is redundant
				String password = rs.getString(3);
				int SSN = rs.getInt(4);
				
				Person per = readPerson(SSN);
				
				guy = new BankUser(per, id, username, password);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return guy;
	}

	@Override
	public BankUser readBankUser(int userId) {
		BankUser guy = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM bank_user WHERE user_id=?";
			String[] key = new String[1];
			key[0] = "user_id";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				userId = rs.getInt(1);		// This line is redundant
				String username = rs.getString(2);
				String password = rs.getString(3);
				int SSN = rs.getInt(4);
				
				Person per = readPerson(SSN);
				
				guy = new BankUser(per, userId, username, password);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return guy;
	}

	@Override
	public boolean updateBankUser(BankUser guy) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBankUser(int userId, boolean erase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<BankUser> readAllBankUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	// FIXME Don't take in an account, return an account
	public Account createAccount(BankUser guy, BigDecimal balance, accountType type, accountLevel level) {
		Account acc = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			conn.setAutoCommit(false);

			// FIXME sql statement
			String sql = "INSERT INTO account(balance, opened_date, user_id, type_id, level_id, deleted)" + 
					" VALUES(?, TO_DATE(?,'yyyy-mm-dd'), ?, ?, ?, ?)";
			String[] key = new String[1];
			key[0] = "account_id";

			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setDouble(1, balance.doubleValue());	// FIXME I'll likely lose data with this conversion. Find a better way to do it
			LocalDate day = LocalDate.now();
			ps.setString(2, getFormatedDate(day));
			ps.setInt(3, guy.getUserId());
			ps.setInt(4, type.ordinal());
			ps.setInt(5, level.ordinal());
			ps.setInt(6, 0);		// false

			// executeUpdate() returns the number of rows updated
			ps.executeUpdate();
			
			Integer id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next())
				id = pk.getInt(1);
			
			acc = new Account(id, day, balance, false, type, level, guy.getUserId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Something went wrong
		return acc;

	}

	@Override
	public Account readAccount(int accountId) {
		Account acc = null;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			String sql = "SELECT * FROM account WHERE account_id=?";
			String[] key = new String[1];
			key[0] = "account_id";
			
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, accountId);

			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				accountId = rs.getInt(1);		// This line is redundant
				BigDecimal balance = new BigDecimal(rs.getDouble(2));	// FIXME change getDouble if I change the lossy conversion for creating an account
				LocalDate accountOpenedDate = LocalDate.parse(rs.getString(3).substring(0, 10));
				int userId = rs.getInt(4);
				int typeId = rs.getInt(5);
				int levelId = rs.getInt(6);
				boolean deleted = (rs.getInt(7) == 0)?false:true;

				acc = new Account(accountId, accountOpenedDate, balance, deleted, accountType.values()[typeId], accountLevel.values()[levelId], userId);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return acc;
	}

	@Override
	public boolean updateAccount(Account acc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAccount(int accountId, boolean erase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Account> readAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}


}
