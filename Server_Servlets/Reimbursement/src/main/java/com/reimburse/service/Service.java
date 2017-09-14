package com.reimburse.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimburse.dao.DaoImpl;
import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.pojos.ReimbursementDTO;
import com.reimburse.pojos.Worker;

// TODO this class is where I will checking updating variables to make
// 		sure they are correct. i.e. valid emails, untaken username, etc
//		ensure correctness on create as well as on update
// TODO add methods to read particular values from database rather than always getting a list of every _____

// FIXME rather than using so many daoImpl.readAllXXX() methods,
//	make more methods in the DaoSqlImpl that are not in the DaoSql interface

public class Service {

	// Logger
	final static Logger logger = Logger.getLogger(Service.class);

	DaoImpl daoImpl = new DaoImpl();

	public Worker validateWorker(String username, String password) {

		if (username == null || password == null) {
			log("validateWorker(...)", "Username or password not supplied.");
			return null;
		}

		username = username.toLowerCase();

		Worker work = daoImpl.readWorker(username);

		if (work == null) {
			log("validateWorker(...)", "Supplied username does not belong to a user.");
			return null;
		}

		if (work.getPassword().equals(password)) {
			log("validateWorker(...)", " user validated: " + username);
			return work;
		} else {
			log("validateWorker(...)", "Password does not match");
			return null;
		}
	}

	public boolean isEmailValid(String email) {

		if (email.contains("@") && email.contains(".com"))
			if (email.lastIndexOf('@') < email.lastIndexOf(".com"))
				return true;

		log("isEmailValid(String email)", "Email addresses must contain a valid domain such as \"something@something.com\"");
		return false;
	}

	public boolean isEmailAvailable(String email) {

		ArrayList<Worker> workerList = daoImpl.readAllWorkers();

		if (workerList.size() == 0)
			return true;

		for (Worker work : workerList)
			if (work.getEmail() != null && work.getEmail().equalsIgnoreCase(email)) {
				log("isEmailAvailable(String email)", "Email unavailable");
				return false;
			}

		return true;
	}

	public boolean isUsernameAvailable(String username) {
		username = username.toLowerCase();

		ArrayList<Worker> userList = daoImpl.readAllWorkers();

		for (Worker work : userList) {
			if (work.getUsername().equalsIgnoreCase(username)) {
				log("isUsernameAvailable(Strig username)", "username is already taken: " + username);
				return false;
			}
		}

		return true;

	}

	// Tries to create a new user
	// Fails if the uername is not unique or if the given worker is already a
	// user
	public Worker tryCreateWorker(int managerId, String firstName, String lastName, String email, String username,
			String password, boolean isManager) {

		if (!isManager(managerId)) {
			log("tryCreateWorker(...)", "Only a manager can create workers");
			return null;
		}

		username = username.toLowerCase();

		// Check that username is unique
		if (!isUsernameAvailable(username))
			return null;

		if (!isEmailValid(email))
			return null;

		if (!isEmailAvailable(email))
			return null;

		Worker work = daoImpl.createWorker(firstName, lastName, email, username, password, isManager, true);

		if (work != null)
			log("tryCreateWorker(...)", "Worker created with username: " + username);
		else
			log("tryCreateWorker(...)", "Worker could not be created with username: " + username);

		return work;
	}

	// Tries to create a new reimbursement
	public Reimbursement tryCreateReimbursement(int workerId, String description, BigDecimal ammount) {

		// Managers cannot create reimbursements
		if (isManager(workerId)) {
			log("tryCreateReimbursement(...)", "A manager cannot submit a reimbursement");
			return null;
		}

		// Negative-value check
		if (ammount.abs() != ammount) {
			log("tryCreateReimbursement(...)", "A reimbursement cannot be submitted with a negative ammount");
			return null;
		}

		Worker work = daoImpl.readWorker(workerId);

		if (work == null) {
			log("tryCreateReimbursement(...)", "Only a valid worker can submit a reimbursement. Worker ID: " + workerId);
			return null;
		}

		Reimbursement reimburse = daoImpl.createReimbursement(workerId, reimbursementStatus.PENDING,
				LocalDateTime.now(), description, ammount);

		if (reimburse != null)
			log("tryCreateReimbursement(...)", "Reimbursement created with id: " + reimburse.getReimbursementId());
		else
			log("tryCreateReimbursement(...)", "Reimbursement could not be created. Username: " + work.getUsername());

		return reimburse;
	}

	// Matches the old worker with the new by id
	// All non-final fields of the worker object will be updated
	public boolean updateWorker(int id, String firstName, String lastName, String email, String username,
			String password) {

		Worker myWorker = daoImpl.readWorker(id);
		username = username.toLowerCase();
		
		if (myWorker == null) {
			log("updateWorker(...)", "There is not record for a worker with ID: " + id);
			return false;
		}
		
		// If the given user's new email is different from their old email
		if (!email.equals(myWorker.getEmail()))
			// If the email is invalid or unavailable
			if (!isEmailValid(email) || !isEmailAvailable(email))
				return false;

		// If the given username is not available and different from the old username
		if (!isUsernameAvailable(username) && !username.equals(myWorker.getUsername()))
			return false;

		myWorker.setFirstName(firstName);
		myWorker.setLastName(lastName);
		myWorker.setEmail(email);
		myWorker.setUsername(username);
		myWorker.setPassword(password);

		boolean result = daoImpl.updateWorker(id, myWorker);
		if (result) 
			log("updateWorker(...)", "Worker with id " + id + " updated");
		else 
			log("updateWorker(...)", "Worker with id " + id + " could not be updated");
		
		return result;
		
	}

	public Worker getWorker(int workerId) {

		Worker work = daoImpl.readWorker(workerId);

		if (work == null)
			log("getWorker(int id)", "Worker does not exist with the following id: " + workerId);
		else log("getWorker(int id)", "Worker found with id: " + workerId);
		
		return work;
	}

	// If the resolvedDate is null, gives the current dateTime as the
	// resolvedDate
	public boolean resolveReimbursement(int managerId, int reimbursementId, reimbursementStatus status,
			String resolveNotes) {

		if (!isManager(managerId)) {
			log("resolveReimbursement(...)", "You must be logged in as a manager to resolve reimbursements");
			return false;
		}

		Worker resolver = daoImpl.readWorker(managerId);

		if (resolver == null) {
			log("resolveReimbursement(...)", "The logged in manager is not in the system. ID: " + managerId);
			return false;
		}
		if (!resolver.isManager() || !resolver.isHired()) {
			log("resolveReimbursement(...)", "That worker is not a current manager. Username: " + resolver.getUsername());
			return false;
		}
		if (status == null || status == reimbursementStatus.NULL || status == reimbursementStatus.PENDING) {
			log("resolveReimbursement(...)", "Invalid status for resolving a reimbursement. Status: " + status);
			return false;
		}

		Reimbursement reimburse = daoImpl.readReimbursement(reimbursementId);
		if (reimburse == null) {
			log("resolveReimbursement(...)", "The specified reimbursement does not exist. ID: " + reimbursementId);
			return false;
		}
		if (reimburse.getStatus() == reimbursementStatus.APPROVED
				|| reimburse.getStatus() == reimbursementStatus.DENIED) {
			log("resolveReimbursement(...)", "That reimbursement is alread resolved. ID: " + reimburse.getReimbursementId());
			return false;
		}

		// Set the reimbursement as resolved and save it to the database
		reimburse.setStatus(status);
		reimburse.setResolverId(managerId);
		reimburse.setResolvedDate(LocalDateTime.now());
		reimburse.setResolveNotes(resolveNotes);
		boolean result = daoImpl.updateReimbursement(reimbursementId, reimburse);

		if (result)
			log("resolveReimbursement(...)", "Reimbursement id " + reimbursementId + " set to status: " + status);
		else log("resolveReimbursement(...)", "Reimbursement id " + reimbursementId + " could not be set to status: " + status);
				
		return result;
	}

	public boolean isAWorker(String username) {
		Worker work = daoImpl.readWorker(username);

		if (work == null) {
			log("isAWorker(String username", "Worker with username: " + username + " is not a worker");
			return false;
		}
		else 
			return true;
	}

	public boolean isAManager(String username) {
		Worker work = daoImpl.readWorker(username);

		if (work == null) {
			log("isAManager(String username)", "Worker with username: " + username + " is not a manager");
			return false;
		}
		else
			return work.isManager();
	}

	public boolean isManager(int id) {
		boolean result = daoImpl.readWorker(id).isManager();
		
		if (!result)
			log("isManager(int id)", "Worker with ID: " + id + " is not a manager");

		return result;
	}
	
	// if id is -1, get reimbursements for all workers
	//		otherwise get reimbursements for workers corresponding to id
	// possible values of type are: "", "PENDING", "RESOLVED"
	//		determine which reimbursements to get.
	public ArrayList<ReimbursementDTO> getAllReimbursements(int id, String type) {
		ArrayList<Reimbursement> reimbursements;
		Worker user = getWorker(id);

		if (id == -1) {
			// No user is specified
			if (type.equals("PENDING"))
				reimbursements = daoImpl.readAllPendingReimbursements();
			else if (type.equals("RESOLVED"))
				reimbursements = daoImpl.readAllResolvedReimbursements();
			else reimbursements = daoImpl.readAllReimbursements();
		} else if (user != null) {
			// Get reimbursements for a particular worker
			if (type.equals("PENDING"))
				reimbursements = daoImpl.readAllPendingReimbursements(id);
			else if (type.equals("RESOLVED"))
				reimbursements = daoImpl.readAllResolvedReimbursements(id);
			else reimbursements = daoImpl.readAllReimbursements(id);
		} else reimbursements = new ArrayList<Reimbursement>();	// Empty

		return convert(reimbursements);
	}
	
	private ArrayList<ReimbursementDTO> convert(ArrayList<Reimbursement> reimbursements) {
		ArrayList<ReimbursementDTO> reimbursementDTOs = new ArrayList<ReimbursementDTO>();
		
		for (Reimbursement r : reimbursements) {
			Worker submitter = daoImpl.readWorker(r.getSubmitterId());
			Worker resolver = daoImpl.readWorker(r.getResolverId());
			String subUsername = null;
			String resUsername = null;
			
			if (submitter != null)
				subUsername = submitter.getUsername();
			if (resolver != null)
				resUsername = resolver.getUsername();
			
			reimbursementDTOs.add(new ReimbursementDTO(r, subUsername, resUsername));
		}
		
		
		return reimbursementDTOs;
	}

	public ReimbursementDTO getReimbursement(int id) {
		Reimbursement reimburse = daoImpl.readReimbursement(id);
		ReimbursementDTO reimburseDto = null;
		
		if (reimburse == null)
			log("getReimbursement(int id)", "reimbursement could not be retrieved with id: " + id);
		else {
			Worker submitter = daoImpl.readWorker(reimburse.getSubmitterId());
			Worker resolver = daoImpl.readWorker(reimburse.getResolverId());
			String subUsername = null;
			String resUsername = null;
			
			if (submitter != null)
				subUsername = submitter.getUsername();
			if (resolver != null)
				resUsername = resolver.getUsername();
			
			reimburseDto = new ReimbursementDTO(reimburse, subUsername, resUsername);
			log("getReimbursement(int id)", reimburse.toString() + " reimbursements retrieved");
		}
		
		return reimburseDto;
	}
	
	public ArrayList<Worker> getAllWorkers() {

		ArrayList<Worker> workers = daoImpl.readAllWorkers();
		
		log("getAllWorkers()", workers.size() + " reimbursements retrieved");
		
		return workers;
	}

	public ArrayList<Worker> getAllNonManagers() {

		ArrayList<Worker> workers = daoImpl.readAllNonManagers();

		log("getAllNonManagers()", workers.size() + " reimbursements retrieved");
		return workers;
	}
	
	public int getNumReimbursements() {
		int num = daoImpl.readNumReimbursements();
		
		log("getNumReimbursements()", num + " reimbursements retrieved");
		
		return num;
	}

	public void log(String methodName, String logMessage) {
		logger.info(methodName + ": " + logMessage);
	}
}
