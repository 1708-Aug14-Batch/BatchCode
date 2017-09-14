package com.reimburse.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.pojos.Worker;
import com.reimburse.service.Service;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	final static Logger logger = Logger.getLogger(LoginServlet.class);
	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = -7645111975570458433L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost");

		ArrayList<String> tx = readValuesFromRequest(req);

		HttpSession session = req.getSession();
		Service service = new Service();
		
		// Format is: [username, password]
		String username = tx.get(0);
		String password = tx.get(1);
		
		Worker user = service.validateWorker(username, password);

		String json = "";
		if (user == null) {
			// Login failed
			if (service.isAWorker(username)) {
				// Bad password
				json = "password";
			} else {
				// Bad username
				json = "username";
			}
		} else {
			// Login successful
			session.setAttribute("user", user);
			json = "success";

			ObjectMapper mapper = new ObjectMapper();

			json = mapper.writeValueAsString(user);
		}
		
		writeValueToResponse(resp, json);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		logger.info("destroying login servlet");
	}
	
	private ArrayList<String> readValuesFromRequest(HttpServletRequest req) throws JsonParseException, JsonMappingException, IOException {
		// Grab all params, even though in this case it is 1 JSON String
		// name values
		Map<String, String[]> myMap = req.getParameterMap();

		// Get the keyset from the map
		Set<String> dtoObject = myMap.keySet();

		// use Jackson. API for converting JSON to java
		ObjectMapper jackson = new ObjectMapper();

		// Convert our keyset into an array, then get what we need
		Object obj = dtoObject.toArray()[0];
		return jackson.readValue((String) obj, ArrayList.class);
	}
	
	private void writeValueToResponse(HttpServletResponse resp, String json) throws IOException {
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		out.write(json);
	}

}
