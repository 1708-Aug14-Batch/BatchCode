package com.revature.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		String name=req.getParameter("username");
		String pass=req.getParameter("pass");
		
		out.println("<h1> Welcome "+name+"!</h1>");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		res.setContentType("text/html");
		PrintWriter out=res.getWriter();
		
		String name=req.getParameter("username");
		String pass=req.getParameter("pass");
		
		out.println("<h1> Welcome "+name+"!</h1>");
		
		//get param names
		Enumeration<String> paramNames=req.getParameterNames();
		
		while(paramNames.hasMoreElements()){
			out.println("<br>Get Param Names</br>");
			String param=paramNames.nextElement();
			String value=req.getParameter(param);
			out.println(param+": "+value);
		}
	}
}