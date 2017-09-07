package src.main.java.test;

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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.services.Services;
import com.reimburse.pojos.User;

@WebServlet("/loginmessagetest")
public class LoginMessageServlet extends HttpServlet{

	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("test -- POST");
		//console.log("djjdf");
		
		System.out.println("are you working");
		Map<String, String[]> myMap = request.getParameterMap();
		
		
		Set<String> txObject = myMap.keySet();
		
	
		ObjectMapper jackson = new ObjectMapper();
		
		Object obj = txObject.toArray()[0];
		ArrayList<String> tx = jackson.readValue((String)obj, ArrayList.class);
		
		@SuppressWarnings("unused")
		HttpSession session = request.getSession();
		Services service = new Services();
		
		String email = tx.get(0);
		String pwd = tx.get(1);
		
		int id = service.validateUser(email);
		System.out.println("id = " + id);
		
		String json = "";
		if(id<0){
			json = "Invalid user. try again";
		} 
		else {
			
		 User u = service.login(id, pwd);
			if(u == null){
				json = "Incorrect Password. try again";
			}
			else{
				json = "success";
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.write(json);
	}
	
}