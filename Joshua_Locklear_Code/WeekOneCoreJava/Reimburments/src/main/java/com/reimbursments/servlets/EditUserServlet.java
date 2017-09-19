package com.reimbursments.servlets;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimbursments.DAO.DAOImpl;
import com.reimbursments.pojos.Users;
import com.reimbursments.service.Service;

/**
 * Servlet implementation class EditUserServlet
 */
@WebServlet("/EditUserInfo")
public class EditUserServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(EditUserServlet.class);
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.debug("We in here doing the update");
		Map<String,String[]> myMap = request.getParameterMap();
		Set<String> keys = myMap.keySet();
		
		ObjectMapper jackson = new ObjectMapper();
		Object obj = keys.toArray()[0];
		ArrayList<String> list = jackson.readValue((String)obj, ArrayList.class);
		HttpSession session = request.getSession();

		
		Users temp = (Users)session.getAttribute("user");
		
		
		String fname = list.get(0);
		if(fname == "") {
			fname = temp.getFirstName();
		}
		String lname = list.get(1);
		if(lname == "") {
			lname = temp.getLastName();
		}
		String email = list.get(2);
		if(email == "") {
			email = temp.getEmail();
		}
		String password = list.get(3);
		if(password == "") {
			password = temp.getPassword();
		}
		temp.setFirstName(fname);
		temp.setLastName(lname);
		temp.setEmail(email);
		temp.setPassword(password);
		Service service = new Service();
		service.updateUser(temp);
		//doGet(request, response);
	}

}
