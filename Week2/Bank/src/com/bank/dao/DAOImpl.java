package com.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.bank.pojos.Account;
import com.bank.pojos.Users;
import com.bank.util.ConnectionFactory;

public class DAOImpl implements DAO {
	
	
	public int addUser(String fn, String ln, String email, String password){
		
		
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "insert into users(firstname, lastname, email, password)" + "values(?,?,?,?)";
			String [] key = new String[1];
			key[0] = "user_id";
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setString(1, fn);
			ps.setString(2, ln);
			ps.setString(3, email);
			ps.setString(4, password);
			
			ps.executeUpdate();
			int id =0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next()){
				id = pk.getInt(1);
				}
			
			conn.commit();
			return id;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	//@SuppressWarnings("null")
	public Account createAccount(int u, int typeId){
		
		Account a = new Account();
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "insert into accounts(user_id, type_id)"+ "values(?,?)";
			String [] key = new String[1];
			key[0]= "account_id";
			PreparedStatement ps = conn.prepareStatement(sql, key);
			ps.setInt(1, u);
			ps.setInt(2, typeId);
			
			ps.executeUpdate();
			int id =0;
			ResultSet pk = ps.getGeneratedKeys();
			while(pk.next()){
				id = pk.getInt(1);
			}
			a.setTypeId(id);
			a.setBalance(0);
			conn.commit();
			return a;
			
		//return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Users getUser(String email, String password){
		Users user = null;
	
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "select user_id, firstname, lastname from users "
					+ "where email = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);			
			ResultSet rs =  ps.executeQuery();//+ /"where email = email and password = password;" )
			if(rs.next()){
				user =new Users();
				int id = rs.getInt("user_id");
				String fn = rs.getString("firstname");
				String ln = rs.getString("lastname");
				user.setId(id);
				user.setFirstname(fn);
				user.setLastname(ln);
				}
			}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
		
	}
	public static Account getAccount(int user_id){
		ArrayList<Account> accList = new ArrayList<Account>();
		Account acc = null;
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "select account_id, balance, type_id from accounts "+
			"where user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);	
			int a = 0;
			ResultSet rs = ps.executeQuery();
			//ArrayList<Account> accList = new ArrayList<Account>();
			while(rs.next()){	
				a = rs.getInt(1); 
				acc = new Account();
				int id = rs.getInt("account_id");
				int bal = rs.getInt("balance");
				int type = rs.getInt("type_id");
				acc.setAccountId(id);
				acc.setBalance(bal);
				acc.setTypeId(type);	
				accList.add(acc);
				//return accList;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
	
		return acc;
	}
	public int updateBalance(int balance, int user_id, int account_id){
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			String sql = "Update accounts SET balance = ? "+ " where user_id = ? and account_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, balance);
			ps.setInt(2,user_id);
			ps.setInt(3, account_id);
			ps.executeUpdate();
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				int bal = rs.getInt(balance);
			}
//				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return 0;	}
}
