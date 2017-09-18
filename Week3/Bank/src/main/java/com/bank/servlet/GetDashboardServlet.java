package com.bank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/getDashboard")
public class GetDashboardServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest res, HttpServletResponse req)throws ServletException, IOException{
		res.getRequestDispatcher("partials/dashboard.html")
		.forward(res, req);
	}
	
}