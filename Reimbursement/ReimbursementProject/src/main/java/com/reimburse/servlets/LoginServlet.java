package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.pojos.User;
import com.reimburse.service.Services;
@WebServlet("/login")
public class LoginServlet extends HttpServlet{	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

	
	protected String servletConfigParam = null;
	
	static Services service = new Services();
	@Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)throws ServletException, IOException{
		
		
		System.out.println("logging into login servlet");	
				ObjectMapper jackson = new ObjectMapper();
		Map<String, String[]> myMap = request.getParameterMap();
		System.out.println("test3");
		Set<String> txObject = myMap.keySet();		
	
		System.out.println("test1");
		
		
		Object obj = txObject.toArray()[0];
		
		System.out.println("test2");
		
		
		
		ArrayList<String> tx = jackson.readValue(((String)obj), ArrayList.class);
		
		HttpSession session = request.getSession(true);
		//session.setAttribute("user", user);
		
		System.out.println("hello");
		
		String email = tx.get(0);
		String pwd = tx.get(1);
		int id = service.validateUser(email);//user.getEmail());
		if(id < 0){
			String json = "pass";
			PrintWriter out = response.getWriter();
			response.setContentType("applicatioon/json");
			out.write(json);
		}
		else{
			User u = service.login(id, pwd);
			if(u == null){
				String json = "fail";
				PrintWriter out = response.getWriter();
				response.setContentType("applicatioon/json");
				out.write(json);
			}
			else{
				session.setAttribute("user", u);
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(u);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.write(json);
				
			}
		}
		
	}

}