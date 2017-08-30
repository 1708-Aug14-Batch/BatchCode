package com.bank.pojos;

import java.time.LocalDate;

public class Person {
	
	// Each person must have a unique identification number
	protected final int personId;
	
	// Each person must have a firstName and lastName
	private String firstName;
	private String lastName;

	// A person must have an email
	private String email = "";
	
	private LocalDate birthDate = null;

	private boolean deceased = false;
	
	public Person(int personId, String firstName, String lastName, String email) {

		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
	
	public void setDeceased(boolean deceased) {
		this.deceased = deceased;
	}
	
	public boolean isDeceased() {
		return deceased;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public int getPersonId() {
		return personId;
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", birthDate=" + birthDate + ", deceased=" + deceased + "]";
	}

}
