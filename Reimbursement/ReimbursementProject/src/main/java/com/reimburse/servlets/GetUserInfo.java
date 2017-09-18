package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.dto.DTO;
import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.User;
import com.reimburse.service.Services;

@WebServlet("/getUserInfo")
public class GetUserInfo extends HttpServlet{
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
	throws ServletException, IOException{
		
		System.out.println("GetUserInfoServlet -- GET");
		Services service = new Services();
		
		HttpSession session = request.getSession();
		User sessionUser = (User)session.getAttribute("user");
		System.out.println("getting user from session " + sessionUser.toString());
		
		if(sessionUser != null){
			ArrayList<Reimbursement> reimburse = new ArrayList<Reimbursement>();
			reimburse = service.getUserReimbursement(sessionUser);
			
			
			System.out.println("converting our user and accounts to dto");
			DTO dto = new DTO(sessionUser, reimburse);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = mapper.writeValueAsString(dto);
			
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.write(json);
		}
		else{
			response.setStatus(418);
		}
		
		
	}
	
}