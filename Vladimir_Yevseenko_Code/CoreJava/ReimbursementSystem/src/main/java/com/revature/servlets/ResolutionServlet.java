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

public class ResolutionServlet extends HttpServlet {
	private static final long serialVersionUID = -8499667970000249726L;

	private static Logger logger = Logging.getLogger();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("ResolutionServlet doPost()");
		
		Service service = Service.getFromSession(req.getSession());
		
		int id = Integer.parseInt(req.getParameter("id"));
		String resolutionState = req.getParameter("approved");
		
		System.out.println(id + " " + resolutionState);
		
		JSONObject obj = new JSONObject();
		
		obj.put("success", service.resolveReimbursement(id, resolutionState.equals("approved")));
		
		resp.getWriter().println(obj);
	}
}