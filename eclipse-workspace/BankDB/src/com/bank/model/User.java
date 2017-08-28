package com.bank.model;

/**
 * Models a bank customer.
 * Has a name, password, and email address.
 * @author Will Underwood
 */
public class User {
	
	private int userID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean hasChecking;
	private boolean hasSavings;
	private boolean enabled;
	
	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	public boolean hasChecking() {
		return hasChecking;
	}

	public void setHasChecking(boolean hasChecking) {
		this.hasChecking = hasChecking;
	}

	public boolean hasSavings() {
		return hasSavings;
	}

	public void setHasSavings(boolean hasSavings) {
		this.hasSavings = hasSavings;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + "]";
	}

}
