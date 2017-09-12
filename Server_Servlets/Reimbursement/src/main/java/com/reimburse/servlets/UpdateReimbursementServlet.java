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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.pojos.Worker;
import com.reimburse.service.Service;

// Should only ever be called by a manager
@WebServlet("/updateReimbursement")
public class UpdateReimbursementServlet extends HttpServlet {

	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 6190081605793034484L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost in UpdateProfileServlet"); // DEBUG

		ArrayList<String> tx = readValuesFromRequest(request);

		Service service = new Service();

		// Parameters in the following order: [ reimbursement_id, status, notes]
		int reimbursementId = Integer.parseInt(tx.get(0));
		reimbursementStatus status = reimbursementStatus.valueOf(tx.get(1));
		String notes = tx.get(2);

		HttpSession session = request.getSession();
		Worker user = (Worker) session.getAttribute("user");
		int managerId = user.getWorkerId();

		String json = "";
		if (!user.isManager())
			json = "User is not a manager";
		else {
			Reimbursement reimburse = service.getReimbursement(reimbursementId);
			if (reimburse == null)
				json = "Reimbursement does not exist";
			else {

				if (reimburse.getStatus() == reimbursementStatus.APPROVED
						|| reimburse.getStatus() == reimbursementStatus.DENIED)
					json = "That reimbursement has already been resolved";
				else {
					if (service.resolveReimbursement(managerId, reimbursementId, status, notes))
						json = "true";
					else
						json = "Reimbursement could not be updated";
				}
			}
		}
		writeValueToResponse(response, json);
	}

	private ArrayList<String> readValuesFromRequest(HttpServletRequest req)
			throws JsonParseException, JsonMappingException, IOException {
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
		ObjectMapper mapper = new ObjectMapper();
		json = mapper.writeValueAsString(json);
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		out.write(json);
	}
}
