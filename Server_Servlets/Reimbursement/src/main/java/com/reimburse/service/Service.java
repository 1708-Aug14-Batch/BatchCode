package com.reimburse.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.reimburse.dao.DaoImpl;
import com.reimburse.pojos.Reimbursement;
import com.reimburse.pojos.Reimbursement.reimbursementStatus;
import com.reimburse.pojos.Worker;

// TODO this class is where I will checking updating variables to make
// 		sure they are correct. i.e. valid emails, untaken username, etc
//		ensure correctness on create as well as on update
// TODO add methods to read particular values from database rather than always getting a list of every _____

// FIXME rather than using so many daoImpl.readAllXXX() methods,
//	make more methods in the DaoSqlImpl that are not in the DaoSql interface

public class Service implements ServiceInterface {

	// Logger
	final static Logger logger = Logger.getLogger(Service.class);

	DaoImpl daoImpl = new DaoImpl();
	
	public Worker validateWorker(String username, String password) {
		
		// TODO make a method that logs information by appending filename and method nae to a message
		System.out.println( Thread.currentThread().getStackTrace());
		// TODO
		
		if (username == null || password == null) {
			logger.info("Username or password not supplied");
			return null;
		}

		username = username.toLowerCase();

		Worker work = daoImpl.readWorker(username);

		if (work == null)
			return null;

		if (work.getPassword().equals(password)) {
			logger.info("User logged in");
			return work;
		}
		else
			return null;
	}

	private boolean isEmailValid(String email) {

		if (email.contains("@") && email.contains(".com"))
			if (email.lastIndexOf('@') < email.lastIndexOf(".com"))
				return true;
		
		logger.info("Email addresses must contain a valid domain such as \"something@something.com\"");
		return false;
	}

	private boolean isEmailAvailable(String email) {

		ArrayList<Worker> workerList = daoImpl.readAllWorkers();

		if (workerList.size() == 0)
			return true;

		for (Worker work : workerList)
			if (work.getEmail() != null && work.getEmail().equalsIgnoreCase(email)) {
				logger.info("Email unavailable");
				return false;
			}

		return true;
	}

	private boolean isUsernameAvailable(String username) {
		username = username.toLowerCase();

		ArrayList<Worker> userList = daoImpl.readAllWorkers();

		for (Worker work : userList) {
			if (work.getUsername().equalsIgnoreCase(username))
				return false;
		}

		return true;

	}

	// Tries to create a new user
	// Fails if the uername is not unique or if the given worker is already a
	// user
	public Worker tryCreateWorker(int managerId, String firstName, String lastName, String email,
			String username, String password, boolean isManager) {

		if (!isManager(managerId))
			return null;

		username = username.toLowerCase();

		// Check that username is unique
		if (!isUsernameAvailable(username)) {
			logger.info("That username is already taken.");
			return null;
		}
		if (!isEmailValid(email)) {
			logger.info("That email is invalid.");
			return null;
		}
		if (!isEmailAvailable(email)) {
			logger.info("That email is already taken.");
			return null;
		}

		return daoImpl.createWorker(firstName, lastName, email, username, password, isManager, true);
	}

	// Tries to create a new reimbursement
	public Reimbursement tryCreateReimbursement(int employeeId, String description, BigDecimal ammount) {

		// Managers cannot create reimbursements
		if (isManager(employeeId))
			return null;

		// Negative-value check
		if (ammount.abs() != ammount)
			return null;

		return daoImpl.createReimbursement(employeeId, reimbursementStatus.PENDING, LocalDateTime.now(), description, ammount);
	}

	// Matches the old worker with the new by id
	// All non-final fields of the worker object will be updated
	public boolean updateWorker(int id, String firstName, String lastName, String email, String username,
			String password) {

		Worker myWorker = daoImpl.readWorker(id);
		username = username.toLowerCase();
		
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

		return daoImpl.updateWorker(id, myWorker);
	}

	public Worker getWorker(int workerId) {

		return daoImpl.readWorker(workerId);
	}

	// If the resolvedDate is null, gives the current dateTime as the
	// resolvedDate
	public boolean resolveReimbursement(int managerId, int reimbursementId, reimbursementStatus status, String resolveNotes) {
		if (!isManager(managerId)) {
			logger.info("You must be logged in as a manager to resolve reimbursements");
			return false;
		}

		Worker resolver = daoImpl.readWorker(managerId);

		if (resolver == null) {
			logger.info("The logged in manager is not in the system. ID: " + managerId);
			return false;
		}
		if (!resolver.isManager() || !resolver.isHired()) {
			logger.info("That employee is not a current manager. Username: " + resolver.getUsername());
			return false;
		}
		if (status == null || status == reimbursementStatus.NULL || status == reimbursementStatus.PENDING) {
			logger.info("Invalid status for resolving a reimbursement. Status: " + status);
			return false;
		}

		Reimbursement reimburse = daoImpl.readReimbursement(reimbursementId);
		if (reimburse == null) {
			logger.info("The specified reimbursement does not exist. ID: " + reimbursementId);
			return false;
		}
		if (reimburse.getStatus() == reimbursementStatus.APPROVED
				|| reimburse.getStatus() == reimbursementStatus.DENIED) {
			logger.info("That reimbursement is alread closed. ID: " + reimburse.getReimbursementId());
			return false;
		}

		logger.info("Reimbursement id " + reimbursementId + " set to status: " + status);
		// Set the reimbursement as resolved and save it to the database
		reimburse.setStatus(status);
		reimburse.setResolvedDate(LocalDateTime.now());
		reimburse.setResolveNotes(resolveNotes);
		return daoImpl.updateReimbursement(reimbursementId, reimburse);

	}

	public boolean isAWorker(String username) {
		Worker work = daoImpl.readWorker(username);

		if (work == null)
			return false;
		else
			return true;
	}

	public boolean isAManager(String username) {
		Worker work = daoImpl.readWorker(username);

		if (work == null)
			return false;
		else
			return work.isManager();
	}

	public boolean isManager(int id) {
		return daoImpl.readWorker(id).isManager();
	}

	@Override
	public ArrayList<Reimbursement> getPendingReimbursements() throws NullPointerException {
		ArrayList<Reimbursement> reimbursements = daoImpl.readAllReimbursements();
		ArrayList<Reimbursement> removeList = new ArrayList<Reimbursement>();

		for (Reimbursement r : reimbursements)
			if (r.getStatus() != reimbursementStatus.PENDING)
				removeList.add(r);

		reimbursements.removeAll(removeList);
		return reimbursements;
	}

	@Override
	public ArrayList<Reimbursement> getResolvedReimbursements() throws NullPointerException {
		ArrayList<Reimbursement> reimbursements = daoImpl.readAllReimbursements();
		ArrayList<Reimbursement> removeList = new ArrayList<Reimbursement>();

		for (Reimbursement r : reimbursements)
			if (r.getStatus() != reimbursementStatus.DENIED && r.getStatus() != reimbursementStatus.APPROVED)
				removeList.add(r);

		reimbursements.removeAll(removeList);
		return reimbursements;
	}

	@Override
	public ArrayList<Reimbursement> getEmployeesReimbursements(int employeeId) throws NullPointerException {

		if (daoImpl.readWorker(employeeId).isManager())
			return null;

		ArrayList<Reimbursement> reimbursements = daoImpl.readAllReimbursements();
		ArrayList<Reimbursement> removeList = new ArrayList<Reimbursement>();

		for (Reimbursement r : reimbursements)
			if (r.getSubmitterId() != employeeId)
				removeList.add(r);

		reimbursements.removeAll(removeList);
		return reimbursements;
	}

	@Override
	public ArrayList<Worker> getAllEmployees() {

		return daoImpl.readAllWorkers();
	}
	
	@Override
	public ArrayList<Worker> getAllNonManagers() {
		
		ArrayList<Worker> workers = daoImpl.readAllWorkers();
		ArrayList<Worker> removeList = new ArrayList<Worker>();

		for (Worker w : workers)
			if (w.isManager())
				removeList.add(w);

		workers.removeAll(removeList);
		return workers;
	}

	@Override
	public ArrayList<Reimbursement> getPendingReimbursements(int employeeId) throws NullPointerException {
		ArrayList<Reimbursement> reimbursements = getPendingReimbursements();
		ArrayList<Reimbursement> removeList = new ArrayList<Reimbursement>();

		for (Reimbursement r : reimbursements)
			if (r.getSubmitterId() != employeeId)
				removeList.add(r);

		reimbursements.removeAll(removeList);
		return reimbursements;
	}

	@Override
	public ArrayList<Reimbursement> getResolvedReimbursements(int employeeId) throws NullPointerException {
		ArrayList<Reimbursement> reimbursements = getResolvedReimbursements();
		ArrayList<Reimbursement> removeList = new ArrayList<Reimbursement>();

		for (Reimbursement r : reimbursements)
			if (r.getSubmitterId() != employeeId)
				removeList.add(r);

		reimbursements.removeAll(removeList);
		return reimbursements;
	}
	
	@Override
	public ArrayList<Reimbursement> getAllReimbursements() {

		return daoImpl.readAllReimbursements();
	}

	@Override
	public Reimbursement getReimbursement(int id) {
		
		return daoImpl.readReimbursement(id);
	}
}
