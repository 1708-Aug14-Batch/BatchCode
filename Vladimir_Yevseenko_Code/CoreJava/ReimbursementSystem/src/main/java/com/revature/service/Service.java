package com.revature.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.revature.dao.DBDAO;
import com.revature.logging.Logging;
import com.revature.pojos.Reimbursement;
import com.revature.pojos.User;

public class Service {
	private DBDAO dao;
	private User curUser;
	
	private static Logger logger = Logging.getLogger();
	
	public Service() {
		dao = new DBDAO();
		logger.debug("Service() created");
	}
	
	public boolean attemptLogin(String email, String password) {
		logger.debug("Service attemptLogin()");
		return (curUser = dao.attemptLogin(email, password)) != null;
	}
	
	public boolean addUser(String first, String last, String email, String pass, boolean isManager) {
		logger.debug("Service addUser()");
		return dao.addUser(new User(first, last, email, pass, isManager));
	}
	
	public User getCurUser() {
		logger.debug("Service getCurUser()");
		return curUser;
	}
	
	
	public static Service getFromSession(HttpSession ses) {
		logger.debug("Service getFromSession()");
		Service serv = (Service) ses.getAttribute("service");
		if (serv == null) {
			Service newServ = new Service();
			ses.setAttribute("service", newServ);
			return newServ;
		}
		return serv;
	}
	
	
	public boolean updateUserInfo(String first, String last, String email, String password) {
		logger.debug("Service updateUserInfo");
		curUser = new User(curUser.getId(), first, last, email, password, curUser.getIsManager());
		return dao.updateUserInfo(curUser.getId(), first, last, email, password);
	}
	
	
	public Reimbursement[] getUsersReimbursements() {
		logger.debug("Service getUsersReimbursements()");
		List<Reimbursement> reimbs = dao.getUsersReimbursements(curUser);
		return reimbs.toArray(new Reimbursement[reimbs.size()]);
	}
	
	public Reimbursement[] getAllReimbursements() {
		logger.debug("Service getAllReimbursements()");
		List<Reimbursement> reimbs = dao.getAllReimbursements();
		return reimbs.toArray(new Reimbursement[reimbs.size()]);
	}
	
	public boolean resolveReimbursement(int id, boolean approved) {
		logger.debug("Service resolveReimbursement()");
		return dao.resolveReimbursement(id, approved);
	}
	
	public User[] getAllNonManagers() {
		logger.debug("Service getAllNonManagers()");
		List<User> users = dao.getAllNonManagers();
		return users.toArray(new User[users.size()]);
	}
	
	
	public boolean doesUserExist(String email) {
		logger.debug("Service doesUserExist()");
		return dao.doesUserExistByEmail(email);
	}
}