package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.revature.logging.Logging;
import com.revature.service.Service;

public class RegisterServlet extends HttpServlet{
	private static final long serialVersionUID = 3979568826540168433L;
	
	private static Logger logger = Logging.getLogger();

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("RegisterServlet doPost()");
		
		Service service = Service.getFromSession(req.getSession());
		
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String isManager = req.getParameter("isManager");
		
		resp.setContentType("application/json");
		JSONObject obj = new JSONObject();
	
		obj.put("success", service.addUser(firstName, lastName, email, password, Boolean.valueOf(isManager)));
	
		resp.getWriter().print(obj);
	}
}