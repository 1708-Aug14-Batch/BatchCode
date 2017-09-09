package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.revature.model.Reimbursement;
import com.revature.model.User;
import com.revature.util.ConnectionFactory;

public class DAOReimbursement {

  public int createReimbursement(Reimbursement create){
    try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
      conn.setAutoCommit(false);

      String sql = "insert into reimbursements " + "(SUBMITTERID, "
          + "RESOLVERID, SUBMITDATE, RESOLVED, STATUSID, DESCRIPTION, RESOLVNOTES, AMOUNT)"
          + "values(?, ?, ?, ?, ?, ?, ?, ?)";
      String[] key = new String[1];
      key[0] = "reimburseId";

      PreparedStatement ps = conn.prepareStatement(sql, key);
      ps.setInt(1, create.getSubmitterID() ); //do not really need summitter id?
      ps.setInt(2, create.getResolverID());  // may not need this?
      ps.setTimestamp(3, create.getSubmitDate());// may not need this?
      ps.setTimestamp(4, create.getResolvedDate());// may not need this?
      ps.setInt(5, create.getStatusID());
      ps.setString(6, create.getDescription());
      ps.setString(5, create.getResolvedNote());// This is only for the manger to send. might get rid of
      ps.setDouble(6, create.getAmount());

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
  /**
   * Gets a single reimbursement case.
   * get single case by using primary key.
   * @return Return reimbursement
   */
  public Reimbursement getReimbursementById(int id){

    Reimbursement singleCase = new Reimbursement();

    try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

      /*to only retrieve a single case what is the primary key in this case? "reimbursement id?*/
      String sql = "select * from reimbursements where REIMBURSEID =  ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);
      ResultSet info = ps.executeQuery();

      while (info.next()) {
        singleCase.setReimburseId(info.getInt(1));
        singleCase.setSubmitterID(info.getInt(2));
        singleCase.setResolverID(info.getInt(3)); 
        singleCase.setSubmitDate(info.getTimestamp(4));
        singleCase.setResolvedDate(info.getTimestamp(5));
        singleCase.setStatusID(info.getInt(6));
        singleCase.setDescription(info.getString(7));
        singleCase.setResolvedNote(info.getString(8));
        singleCase.setAmount(info.getDouble(9));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return singleCase;
  }
/** Only mangers can view all reimbursements for all users.
 * 
 * @return Returns all reimbursements for all users.
 */
   public ArrayList<Reimbursement> getAllUsersReimbursements(){
    
     
     
     return null;
     
   }



}
