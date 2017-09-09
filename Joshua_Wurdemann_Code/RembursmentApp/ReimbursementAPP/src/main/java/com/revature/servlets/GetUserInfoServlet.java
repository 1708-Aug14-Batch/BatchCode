package com.revature.servlets;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dto.DTO;
//import com.revature.model.Reimbursement;
import com.revature.model.User;
//import com.revature.service.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/getUserInfo")
public class GetUserInfoServlet extends HttpServlet{

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
  throws ServletException, IOException{
    
    System.out.println("GetUserInfoServlet -- GET");
    //Service service = new Service();
    
    HttpSession session = request.getSession();
    User sessionUser = (User)session.getAttribute("user");
    System.out.println("getting user from session " + sessionUser.toString());
    
    if(sessionUser != null){
//      ArrayList<Account> accounts = new ArrayList<Account>();
//      accounts = service.getUserAccounts(sessionUser);
      
      
      System.out.println("converting our user and accounts to dto");
      DTO dto = new DTO(sessionUser);
      
      ObjectMapper mapper = new ObjectMapper();
      
      String json = mapper.writeValueAsString(dto);
      
      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      out.write(json);
    }
//    else{
//      response.setStatus('418');
//    }
    
    
  }
  
}