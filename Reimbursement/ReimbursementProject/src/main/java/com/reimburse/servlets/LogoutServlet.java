package com.reimburse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Logging out user");
		if(req.getSession(false)== null){
			resp.sendRedirect("app.html");
		}
		HttpSession session = req.getSession(false);
		if(session != null){
			session.removeAttribute("user");
			session.invalidate();
			System.out.println("Session Ended");
		}
		resp.sendRedirect("app.html");
		
	}

}