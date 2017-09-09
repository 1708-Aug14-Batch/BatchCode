package com.resort.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resort.dao.ReimburseDAO;
import com.resort.dao.ReimburseDAOImpl;
import com.resort.dao.UserDAO;
import com.resort.dao.UserDAOImpl;
import com.resort.pojos.User;
import com.resort.service.Service;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	
	static Service service = new Service(new UserDAOImpl(), new ReimburseDAOImpl());
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Grab all params, even though in this case its
				// only 1 JSON string
				
				Map<String, String[]>myMap = req.getParameterMap();
				
				// Get the key set from the map
				Set<String> toObject = myMap.keySet();
				
				// use Jackson API for converting JSON to java
				ObjectMapper jackson = new ObjectMapper();
				
				//convert our keyset into an array, then get
				//what we need from it
				Object obj = toObject.toArray()[0];
				ArrayList<String> to = jackson.readValue((String)obj, ArrayList.class);
				
				HttpSession session = req.getSession();
				
				String email = to.get(0);
				String pass = to.get(1);
				
				int id = service.validateUser(email);
				System.out.println("id = " + id);
				String json = " ";
				
				if(id < 0) { //if user does not exist
					json = "Invalid";
				}
				else { //user exists
					User u = service.login(id, pass);
					if (u == null) {
						json = "Incorrect";
					}
					else {
						json = "resortLogin.html";
						synchronized(session) {
							session.setAttribute("resort_user", u);
						}
						if (u.getIsManager() == 0) {
							json = "employeeHomePage.html";
						}
						else {
							json = "managerHomePage.html";
						}
					}
				}
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				out.write(json);
			}
		}