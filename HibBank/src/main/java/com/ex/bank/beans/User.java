package com.ex.bank.beans;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class User {
	private int id;
	private String fristname;
	private String lastname;
	private String email;
	private String password;
	
	
	public User(){}


	public User(int id, String fristname, String lastname, String email, String password) {
		super();
		this.id = id;
		this.fristname = fristname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFristname() {
		return fristname;
	}


	public void setFristname(String fristname) {
		this.fristname = fristname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}