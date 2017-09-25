package com.reimburse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebServlet("/homepageredirect")
public class HomePageRedirectServlet extends HttpServlet{
	final static Logger logger = Logger.getLogger(HomePageRedirectServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		logger.info("Redirecting to home, user "+session.getAttribute("userid"));
		byte isManager = (byte) session.getAttribute("usertype");
		
		if(isManager == 0){
			res.sendRedirect("homepage.html");
		}
		else if(isManager == 1){
			res.sendRedirect("manager_homepage.html");
		}
		else{
			System.out.println("isManager was " + Byte.toString(isManager));
		}
	}
}