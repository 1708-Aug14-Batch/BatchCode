package com.revature.pojos;

import org.apache.log4j.Logger;

import com.revature.logging.Logging;

public class User {
	private final int id;
	private final String first, last, email, password;
	private final boolean isManager;
	
	private static Logger log = Logging.getLogger();
	
	public User(String first, String last, String email, String password, boolean isManager) {
		this.id = -1;
		this.first = first;
		this.last = last;
		this.email = email;
		this.password = password;
		this.isManager = isManager;
		log.debug("User(...) created");
	}
	
	public User(int id, String first, String last, String email, String password, boolean isManager) {
		this.id = id;
		this.first = first;
		this.last = last;
		this.email = email;
		this.password = password;
		this.isManager = isManager;
		log.debug("User(id, ...) created");
	}
	
	public User(int id, User u) {
		this.id = id;
		this.first = u.getFirst();
		this.last = u.getLast();
		this.email = u.getEmail();
		this.password = u.getPassword();
		this.isManager = u.getIsManager();
		log.debug("User(id, user) created");
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirst() {
		return first;
	}
	
	public String getLast() {
		return last;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getIsManager() {
		return isManager;
	}
}
