package com.reimburse.servlets;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.reimburse.pojos.User;
import com.reimburse.services.Services;


@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	static Services service = new Services();
	@Override
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)throws ServletException, IOException{
		System.out.println("logging into login servlet");
		HttpSession session = request.getSession();
		System.out.println("hello");
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		int id = service.validateUser(email);
		if(id<0){
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			session.setAttribute("message", "Invalid User");
			rd.forward(request, response);
		}
		else{
			User u = service.login(id, pwd);
			if(u == null){
				session.setAttribute("message", "Incorrect password");
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.forward(request, response);
			}
			else{
				System.out.println("logging user");
				session.setAttribute("user", u);
				System.out.println("Fowarding to app.html");
				request.getRequestDispatcher("app.html").forward(request, response);
			}
		}
		
	}
	
	@Override
	public void destroy(){
		super.destroy();
		System.out.println("Destroying Servlet");
	}

}