package com.revature.beans;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="STUDENTS")
public class Student {
	
	@Id
	@Column(name = "student_id")
	@SequenceGenerator(name="student_id_seq", sequenceName="student_id_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="student_id_seq")
	private int id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	/*
	 * Student to Transcript is 1 to 1 relationship
	 */
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "transcript_id")
	private Transcript transcript;
	
	public Student() {
		
	}
	
	public Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public Student(int id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String email() {
		return email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}
	
	@Override
	public String toString() {
		return "Student { id: " + id + 
				", firstName: " + firstName + 
				", lastName: " + lastName + 
				", email: " + email + 
				", transcript: " + (transcript == null ? "null" : transcript) + " }";
	}
	
}