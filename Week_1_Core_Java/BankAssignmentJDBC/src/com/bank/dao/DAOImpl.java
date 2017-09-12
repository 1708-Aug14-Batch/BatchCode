package com.bank.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bank.pojos.Account;
import com.bank.pojos.AccountType;
import com.bank.pojos.User;
import com.bank.util.ConnectionFactory;



public class DAOImpl implements DAO{
	
	@Override
	public int addUser(String fn, String ln, String em, String pw) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			
			
			String sql = "Insert into Users(firstname, lastname, email, password) " + 
					"values(?,?,?,?)";
			
			String[] keys = new String[1];
			keys[0] = "user_id"; //Same as the primary key of the table
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setString(1, fn);
			ps.setString(2, ln);
			ps.setString(3, em);
			ps.setString(4, pw);
			ps.executeUpdate(); //Execute update for this example because its editing the table
			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next()) {
				id = pk.getInt(1);
			}
			conn.commit();
			return id;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Account createAccount(User u, int typeID, String status) {
		Account a = new Account();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			conn.setAutoCommit(false);
			String sql = "insert into Account(user_id, type_id, status) " + 
					"values(?,?,?) ";
			String[] keys = new String[1];
			keys[0] = "acct_id";
			PreparedStatement ps = conn.prepareStatement(sql,keys);
			ps.setInt(1, u.getId());
			ps.setInt(2, typeID);
			ps.setString(3, status);
			ps.executeUpdate();
			int id = 0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next()) {
				id = pk.getInt(1);
			}
			a.setId(id);
			a.setBalance(0);
			a.setStatus("Open");
			//a.setType();
			
			conn.commit();
			return a;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return a;
	}
	
	public ArrayList<User> readUser() {
		ArrayList<User> list = new ArrayList<User>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select * from users";
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt(1);
				String firstname = rs.getString(2);
				String lastname = rs.getString(3);
				String email = rs.getString(4);
				String password = rs.getString(5);
				
				User e = new User(id,firstname,lastname,email,password);
				list.add(e);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Account> readAccount(){
		ArrayList<Account> list = new ArrayList<Account>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()){
			String sql = "select * from account";
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt(1);
				int balance = rs.getInt(2); 
				int userId = rs.getInt(3);
				int t = rs.getInt(4);
				String status = rs.getString(5);
				
				Account a = new Account(id,balance,userId,t,status);
				list.add(a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<AccountType> readAccountType(){
		ArrayList<AccountType> list = new ArrayList<AccountType>();
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			String sql = "select * from accounttype";
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery(sql);
			while(rs.next()) {
				int typeID = rs.getInt(1);
				String name = rs.getString(2);
				
				AccountType t = new AccountType(typeID,name);
				list.add(t);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public void UpdateBalance(Account a) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "Update account " + 
					"set balance = ? " + 
					"Where acct_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
//				System.out.println(test.getBalance());
				ps.setInt(1, a.getBalance());
				ps.setInt(2, a.getId());
			
			ps.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateAccount(Account a) {
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "Update account " + 
					"set status = ? " + 
					"Where acct_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
//				System.out.println(test.getBalance());
				ps.setString(1, a.getStatus());
				ps.setInt(2, a.getId());
			
			ps.executeUpdate();
			conn.commit();
			conn.setAutoCommit(true);
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void editUser(User u) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			
			conn.setAutoCommit(false);
			String sql = "Update users "
					+ "set firstname = ?, lastname = ?, email = ?, password = ? " +
					"where user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, u.getFirstname());
			ps.setString(2, u.getLastname());
			ps.setString(3, u.getEmail());
			ps.setString(4, u.getPassword());
			ps.setInt(5, u.getId());
			
			ps.executeUpdate();
			System.out.println("Here");
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
////	public int getBalance() {
////		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
////			
////			String sql = "Select users.firstname, users.lastname, users.email, account.balance " + 
////					"from users " + 
////					"Inner Join Account on users.user_id = account.user_id";
////			Statement state = conn.createStatement();
////			ResultSet rs = state.executeQuery(sql);
////			while(rs.next()) {
////				int id = rs.getInt(1);
////				String firstname = rs.getString(2);
////				String lastname = rs.getString(3);
////				int song = rs.getInt(4);
////				Example e = new Example(id,firstname,lastname,song);
////				list.add(e);
////			}
////			
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		return list;
//	}
}