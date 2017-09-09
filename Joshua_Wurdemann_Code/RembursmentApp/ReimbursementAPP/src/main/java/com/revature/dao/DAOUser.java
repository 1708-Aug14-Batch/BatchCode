package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.revature.model.User;
import com.revature.util.ConnectionFactory;

public class DAOUser implements DAO {

  public HashMap<Integer, String> getEmails() {
    HashMap<Integer, String> emails = new HashMap<Integer, String>();
    try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
      String sql = "select userid, email from users";
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery(sql);

      while (rs.next()) {
        int id = rs.getInt(1);
        String email = rs.getString(2);
        emails.put(id, email);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return emails;
  }

   public User getUserById(int id) {
    
    User u = new User();
    try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
      String sql = "select * from users where userid =  ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet info = ps.executeQuery();

      while (info.next()) {
        u.setUserId(info.getInt(1));
        u.setFirstName(info.getString(2));
        u.setLastNAme(info.getString(3));
        u.setEmail(info.getString(4));
        u.setPassword(info.getString(5));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return u;
  }

  public int addUser(String fn, String ln, String email, String pass) {
    try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
      conn.setAutoCommit(false);

      String sql = "insert into users " + "(firstname, lastname, email, password) "
          + "values(?, ?, ?, ? )";
      String[] key = new String[1];
      key[0] = "userid";
      PreparedStatement ps = conn.prepareStatement(sql, key);
      ps.setString(1, fn);
      ps.setString(2, ln);
      ps.setString(3, email);
      ps.setString(4, pass);

      ps.executeUpdate();
      int id = 0;
      ResultSet pk = ps.getGeneratedKeys();
      while (pk.next()) {
        id = pk.getInt(1);
      }

      conn.commit();
      return id;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }
}
