package com.hibernate.beans;

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
	@Column(name="STUDENTID")
	@SequenceGenerator(name="STUDENTID_SEQ", sequenceName="STUDENTID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="STUDENTID_SEQ")
	private int id;
	
	@Column
	private String firstname;
	
	@Column
	private String lastname;
	
	@Column
	private String email;
	
	// One-to-One
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TRANSCRIPID")
	private Transcript transcript;
	
	public Student() {
		
	}
	
	public Student(String firstname, String lastname, String email) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
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
	
	public Transcript getTranscript() {
		return transcript;
	}

	public void setTranscript(Transcript transcript) {
		this.transcript = transcript;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}
}